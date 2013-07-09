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

package org.apache.chemistry.shell.jline;

import java.io.IOException;


//import jline.console.completer.CandidateListCompletionHandler;
//import jline.console.completer.CompletionHandler;
import jline.console.ConsoleReader;

import org.apache.chemistry.shell.app.Application;
import org.apache.chemistry.shell.app.Console;
import org.apache.chemistry.shell.command.CommandException;
import org.apache.chemistry.shell.command.ExitException;
import org.apache.chemistry.shell.util.ColorHelper;

public class JLineConsole extends Console {

    protected ConsoleReader console;

    public JLineConsole() throws IOException {
        if (JLineConsole.instance != null) {
            throw new IllegalAccessError("Console is already instantiated");
        }
        JLineConsole.instance = this;
        console = new ConsoleReader();
//        CompletionHandler ch = console.getCompletionHandler();
//        if (ch instanceof CandidateListCompletionHandler) {
//            ((CandidateListCompletionHandler) ch).setAlwaysIncludeNewline(false);
//        }
        ColorHelper.enable();
        	
        
    }

    public ConsoleReader getReader() {
        return console;
    }

    /**
     * Executes line.
     *
     * @return false if the users has issued an "exit" command, true otherwise
     */
    protected boolean execute(String line) throws Exception {
        try {
            runCommand(line);
        } catch (ExitException e) {
            return false;
        } catch (CommandException e) {
            console.print(e.getMessage());
        }
        return true;
    }

    @Override
    public void start(Application app) throws IOException {
        super.start(app);
        console.setPrompt("|> ");
        // register completors
        console.addCompleter(new CompositeCompletor(this, app.getCommandRegistry()));
        String line = console.readLine();
        while (line != null) {
            line = line.trim();
            try {
                if (line.length() > 0) {
                    if (!execute(line)) {
                        break;
                    }
                    println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            line = console.readLine();
        }
        console.print("Bye");
        console.println();
    }

    @Override
    public String promptPassword() throws IOException {
        return console.readLine(new Character('*'));
    }

    @Override
    public void updatePrompt() {
        if (app.isConnected()) {
            String path = app.getContext().getPath().getLastSegment();
            if (path == null) {
                path = "/";
            }
            console.setPrompt("|"+app.getHost()+":"+path+"> ");
        } else {
            console.setPrompt("|> ");
        }
    }

    @Override
    public void println() throws IOException {
        console.flush();
        console.println();;
    }

}
