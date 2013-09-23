package org.apache.chemistry.shell.cmds.cmis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import org.apache.chemistry.opencmis.client.api.ChangeEvent;
import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.commons.enums.CapabilityChanges;
import org.apache.chemistry.shell.app.ChemistryApp;
import org.apache.chemistry.shell.app.ChemistryCommand;
import org.apache.chemistry.shell.app.ChemistryContext;
import org.apache.chemistry.shell.app.Console;
import org.apache.chemistry.shell.app.Context;
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.CommandException;
import org.apache.chemistry.shell.command.CommandLine;
import org.apache.chemistry.shell.util.Path;

@Cmd(syntax = "changes [repo:item] [--all] [--token:*] [--max-results:*] [since]", synopsis = "Dump changes (optional with a date filter for older changes)")
public class ContentChanges extends ChemistryCommand {

	private static final int DEFAULT_MAX_RESULTS = 1000;
	private String latestChangeLogToken = null;

	@Override
	protected void execute(ChemistryApp app, CommandLine cmdLine)
			throws Exception {
		ChemistryContext ctx;
		String repo = cmdLine.getParameterValue("repo");
		if (app.getContext() instanceof ChemistryContext) {
			ctx = (ChemistryContext) app.getContext();
		} else if (repo != null) {
			Context c = app.getContext();
			c = app.resolveContext(new Path(repo));
			ctx = (ChemistryContext) c;
		} else {
			throw new CommandException(
					"This command can only be executed on inside a repository");
		}
		if (ctx.getRepository().getCapabilities().getChangesCapability() == CapabilityChanges.NONE)
			throw new CommandException(
					"The repository doesn't support change log features");
		boolean isPropertyChangeDetectionEnabled = ctx.getRepository()
				.getCapabilities().getChangesCapability() == CapabilityChanges.ALL
				&& ctx.getRepository().getCapabilities().getChangesCapability() == CapabilityChanges.PROPERTIES;
		String since = cmdLine.getParameterValue("since");
		Calendar sincecal = null;
		if (since != null) {
			sincecal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat();
			try {
				sincecal.setTime(sdf.parse(since));
			} catch (ParseException e) {
				throw new CommandException("Could not parse date", e);
			}
		}

		String changeLogToken = null;
		if (cmdLine.getParameter("--all") != null)
			changeLogToken = null;
		else if (cmdLine.getParameterValue("--token") != null) {
			changeLogToken = cmdLine.getParameterValue("--token");
			Console.getDefault().println(
					"customChangeLogToken:     " + changeLogToken);
		} else {
			if (latestChangeLogToken == null)
				latestChangeLogToken = ctx.getRepository()
						.getLatestChangeLogToken();
			changeLogToken = latestChangeLogToken;
			Console.getDefault().println(
					"latestChangeLogToken:     " + latestChangeLogToken);
		}

		int maxResults = DEFAULT_MAX_RESULTS;
		if (cmdLine.getParameter("--max-results") != null) {
			try {
				int max = Integer.parseInt(cmdLine
						.getParameterValue("--max-results"));
				if (max <= 0) {
					throw new CommandException(
							"Could not request zero or less results: ",
							new IllegalArgumentException(
									"Max results must be larger than zero"));
				}
				maxResults = max;
			} catch (NumberFormatException e) {
				throw new CommandException(
						"Could not parse max result parameter: ", e);
			}
		}

		ChangeEvents events = ctx.getSession().getContentChanges(
				changeLogToken, isPropertyChangeDetectionEnabled, maxResults);
		latestChangeLogToken = events.getLatestChangeLogToken();
		Console.getDefault().println(
				"New latestChangeLogToken: " + latestChangeLogToken);
		LinkedList<String> lines = new LinkedList<String>();
		for (ChangeEvent event : events.getChangeEvents()) {
			SimpleDateFormat df = new SimpleDateFormat();
			Calendar cal = (Calendar) event.getChangeTime();
			if (sincecal != null && !sincecal.before(cal)) {
				continue;
			}
			String date = df.format(cal.getTime());
			String type = event.getChangeType().toString().toLowerCase();
			lines.add(String.format("Object [%s] %s on %s",
					event.getObjectId(), type, date));
		}
		Console.getDefault().println(
				"Number of Changes: " + lines.size() + " of "
						+ events.getTotalNumItems());
		for (String line : lines) {
			Console.getDefault().println(line);
		}
	}
}
