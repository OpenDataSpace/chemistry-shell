/*
 * (C) Copyright 2009-2010 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *   Bogdan Stefanescu (bs@nuxeo.com), Nuxeo
 *   Stefane Fermigier (sf@nuxeo.com), Nuxeo
 *   Florent Guillaume (fg@nuxeo.com), Nuxeo
 */

package org.apache.chemistry.shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.shell.app.ChemistryApp;
import org.apache.chemistry.shell.app.Console;
import org.apache.chemistry.shell.command.ExitException;
import org.apache.chemistry.shell.jline.JLineConsole;
import org.apache.chemistry.shell.util.PasswordReader;

public class Main {

	String username;
	String password;
	String url;
	boolean batchMode;
	boolean execMode;
	boolean testMode;
	boolean enableTime = false;
	boolean useJSONBinding = false;
	String command;
	private ChemistryApp app;

	private Main() {
	}

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.parseArgs(args);
		main.run();
	}

	public void parseArgs(String[] args) throws IOException {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if ("-u".equals(arg)) { // username
					if (++i == args.length) {
						error("Invalid option -u without value. Username required.");
					}
					username = args[i];
				} else if ("-p".equals(arg)) { // password
					if (++i == args.length) {
						error("Invalid option -p without value. Password required.");
					}
					password = args[i];
				} else if ("-t".equals(arg)) { // test mode
					testMode = true;
				} else if ("--enable-time".equals(arg)) { // enable time setting by default
					enableTime = true;
				} else if ("-e".equals(arg)) { // execute mode
					// execute one command
					execMode = true;
					StringBuilder buf = new StringBuilder();
					for (i++; i < args.length; i++) {
						buf.append(args[i]).append(" ");
					}
					command = buf.toString();
					break;
				} else if ("-b".equals(arg)) { // batch mode
					// execute commands in the given file or if no specified
					// read from stdin
					batchMode = true;
					if (++i < args.length) {
						// read commands from a file
						command = args[i];
					}
					break;
				} else if ("-h".equals(arg) || "--help".equals(arg)) { // help
					// execute help command
					usage();
					System.exit(0);
				} else if (!arg.startsWith("-")) {
					url = arg;
				} else if ("-j".equals(arg)) { // use json binding
					useJSONBinding = true;
				} else if ("--version".equals(arg)){ // print actual version
					version();
					System.exit(0);
				} else {
					System.err.println("Skipping unknown argument: " + arg);
				}
			}

			if (url != null) {
				if (!url.contains("://")) {
					url = "http://" + url;
				}
				try {
					URL u = new URL(url);
					if (username == null) {
						String userInfo = u.getUserInfo();
						if (userInfo != null) {
							if (userInfo.contains(":")) {
								int p = userInfo.indexOf(':');
								username = userInfo.substring(0, p);
								if (password == null
										&& p < userInfo.length() - 1) {
									password = userInfo.substring(p + 1);
								}
							} else {
								username = userInfo;
							}
						}
					}
				} catch (MalformedURLException e) {
					System.err.println("Malformed URL: "
							+ e.getLocalizedMessage());
					System.exit(1);
				}
			}
			if (url != null && username == null) {
				username = System.console().readLine("User: ");
			}
			if (username != null && password == null) {
				password = PasswordReader.read();
			}
		}
	}

	public void run() throws Exception {
		if (this.useJSONBinding) {
			app = new ChemistryApp(BindingType.BROWSER);
		} else {
			app = new ChemistryApp();
		}
		if (username != null) {
			app.login(username,
					password == null ? new char[0] : password.toCharArray());
		}
		if (url != null) {
			app.connect(url);
		}

		if (execMode) {
			runInExecMode();
		} else if (batchMode) {
			runInBatchMode();
		} else {
			runInInteractiveMode();
		}
	}

	private void runInExecMode() throws Exception {
		Console.setDefault(new Console());
		Console.getDefault().setPrintTimeStamps(enableTime);
		Console.getDefault().start(app);
		Console.getDefault().runCommand(command);
	}

	private void runInBatchMode() throws IOException {
		Console.setDefault(new Console());
		Console.getDefault().setPrintTimeStamps(enableTime);
		Console.getDefault().start(app);
		List<String> cmds;
		if (command == null) {
			cmds = IOUtils.readLines(System.in);
		} else {
			cmds = IOUtils.readLines(new FileInputStream(new File(command)));
		}
		for (String cmd : cmds) {
			// Ignore empty lines / comments
			if (cmd.length() == 0 || cmd.startsWith("#")) {
				continue;
			}
			Console.getDefault().println("Running: " + cmd);
			try {
				Console.getDefault().runCommand(cmd);
			} catch (ExitException e) {
				Console.getDefault().println("Bye.");
				return;
			} catch (Exception e) {
				Console.getDefault().error(e.getMessage());
				if (testMode) {
					e.printStackTrace();
					Console.getDefault().println("Exiting on error.");
					System.exit(1);
					return;
				}
			}
		}
		Console.getDefault().println("Done.");
	}

	private void runInInteractiveMode() {
		try {
			// TODO use user profiles to setup console like prompt and default
			// service to cd in
			JLineConsole con = new JLineConsole();
			Console.setDefault(con);
			Console.getDefault().setPrintTimeStamps(enableTime);
			con.start(app);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	}
	
	static void version() throws IOException {
		URL url = Main.class.getResource("/help/version.help");
		String help = IOUtils.toString(url.openStream());
		System.out.print(help);
	}

	static void usage() throws IOException {
		URL url = Main.class.getResource("/help/usage.help");
		String help = IOUtils.toString(url.openStream());
		System.out.print(help);
	}

}
