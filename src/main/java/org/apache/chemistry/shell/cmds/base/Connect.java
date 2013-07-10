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

package org.apache.chemistry.shell.cmds.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.chemistry.opencmis.commons.exceptions.CmisPermissionDeniedException;
import org.apache.chemistry.shell.app.Application;
import org.apache.chemistry.shell.app.Console;
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.Command;
import org.apache.chemistry.shell.command.CommandLine;

@Cmd(syntax = "connect|open url ", synopsis = "Open a new session")
public class Connect extends Command {

	@Override
	public void run(Application app, CommandLine cmdLine) throws Exception {
		String u = cmdLine.getParameterValue("url");
		// if parameter not found, return
		if (u == null)
			return;
		try {
			URL url = new URL(u);
			String userInfo = url.getUserInfo();
			if (userInfo != null) {
				String username = "";
				String password = "";
				int p = userInfo.indexOf(':');
				if (p == -1 || p == userInfo.length() - 1) {
					username = userInfo;
					password = Console.getDefault().promptPassword();
					url = new URL(url.getProtocol() + "://" + username + ":"
							+ password + "@" + url.getHost() + ":"
							+ url.getPort() + url.getFile());
				}
			}
			if (url != null) {
				try {
					app.connect(url);
					Console.getDefault().updatePrompt();
				} catch (CmisPermissionDeniedException e) {
					Console.getDefault().error(
							"Permission Denied: " + e.getMessage());
				}
			}
		} catch (MalformedURLException e) {
			Console.getDefault().error(
					"malformed URL \"" + u + "\": " + e.getMessage());
		}
	}
}
