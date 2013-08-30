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
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.CommandException;
import org.apache.chemistry.shell.command.CommandLine;

@Cmd(syntax = "changes [--all] [since]", synopsis = "Dump changes (optional with a date filter for older changes)")
public class ContentChanges extends ChemistryCommand {

	private String latestChangeLogToken = null;

	@Override
	protected void execute(ChemistryApp app, CommandLine cmdLine)
			throws Exception {
		if (app.getContext() instanceof ChemistryContext) {
			ChemistryContext ctx = (ChemistryContext) app.getContext();
			if (ctx.getRepository().getCapabilities().getChangesCapability() == CapabilityChanges.NONE)
				throw new CommandException("The repository doesn't support change log features");
			String since = cmdLine.getParameterValue("since");
			boolean all = cmdLine.getParameter("--all") != null;
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
			if (all)
				latestChangeLogToken = null;
			else {
				if (latestChangeLogToken == null)
					latestChangeLogToken = ctx.getRepository()
							.getLatestChangeLogToken();
				Console.getDefault().println(
						"latestChangeLogToken:     " + latestChangeLogToken);
			}
			ChangeEvents events = ctx.getSession().getContentChanges(
					latestChangeLogToken, true, 1000);
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
					"Number of Changes: " + lines.size() + " of "+ events.getTotalNumItems());
			for (String line : lines) {
				Console.getDefault().println(line);
			}
		} else {
			throw new CommandException(
					"This command can only be executed on inside a repository");
		}
	}

}
