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

package org.apache.chemistry.shell.cmds.cmis;

import java.io.File;
import java.io.FileInputStream;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.shell.app.ChemistryApp;
import org.apache.chemistry.shell.app.ChemistryCommand;
import org.apache.chemistry.shell.app.Context;
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.CommandException;
import org.apache.chemistry.shell.command.CommandLine;
import org.apache.chemistry.shell.util.DummyFileType;
import org.apache.chemistry.shell.util.Path;
import org.apache.chemistry.shell.util.SimplePropertyManager;

@Cmd(syntax="setstream target:item [--zero] [--one] [--text] [--random] [-s|--size:*] [filename:file]", synopsis="Set the given file content as a stream on the current context object")
public class SetStream extends ChemistryCommand {

    @Override
    protected void execute(ChemistryApp app, CommandLine cmdLine)
            throws Exception {

        String target = cmdLine.getParameterValue("target");
        String filename = cmdLine.getParameterValue("filename");
        DummyFileType dummyType = DummyFileType.ZEROS;
        if(cmdLine.getParameter("--zero") != null)
        	dummyType = DummyFileType.ZEROS;
        else if(cmdLine.getParameter("--one") != null)
        	dummyType = DummyFileType.ONES;
        else if(cmdLine.getParameter("--text") != null)
        	dummyType = DummyFileType.TEXT;
        else if(cmdLine.getParameter("--random") != null)
        	dummyType = DummyFileType.RANDOM;
        long size = -1;
        if(cmdLine.getParameterValue("-s")!=null) {
        	size = Long.parseLong(cmdLine.getParameterValue("-s"));
        } else if(filename==null) {
        	throw new CommandException("Missing filename");
        }
        Context ctx = app.resolveContext(new Path(target));
        CmisObject obj = ctx.as(CmisObject.class);

        if (obj == null) {
            throw new CommandException("Cannot resolve "+target);
        }

        if(size < 0) {
        	File file = app.resolveFile(filename);
        	FileInputStream in = new FileInputStream(file);
        	try {
        		new SimplePropertyManager(obj).setStream(in, file.length(), file.getName());
        	} finally {
            	in.close();
        	}
        } else {
    		new SimplePropertyManager(obj).setDummyStream(obj.getName(), dummyType, size);
    	}
    }

}
