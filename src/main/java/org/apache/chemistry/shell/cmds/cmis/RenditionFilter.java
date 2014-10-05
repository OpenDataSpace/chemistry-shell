package org.apache.chemistry.shell.cmds.cmis;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.shell.app.ChemistryApp;
import org.apache.chemistry.shell.app.ChemistryContext;
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.CommandLine;

@Cmd(syntax="renditionfilter [filter]", synopsis="Gets or sets the rendition filter string")
public class RenditionFilter extends org.apache.chemistry.shell.app.ChemistryCommand {

	@Override
	protected void execute(ChemistryApp app, CommandLine cmdLine)
			throws Exception {
		ChemistryContext ctx = (ChemistryContext)app.getContext();
		Session s = ctx.getSession();
		String filter = cmdLine.getParameterValue("filter");
		if (filter == null) {
			println(s.getDefaultContext().getRenditionFilterString());
		} else {
			s.getDefaultContext().setRenditionFilterString(filter);
		}
	}
}
