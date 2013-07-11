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


import org.apache.chemistry.shell.app.ChemistryApp;
import org.apache.chemistry.shell.app.ChemistryCommand;
import org.apache.chemistry.shell.command.Cmd;
import org.apache.chemistry.shell.command.CommandException;
import org.apache.chemistry.shell.command.CommandLine;

@Cmd(syntax="mv source:item target:item", synopsis="Move a remote document from source to target")
public class Move extends ChemistryCommand {

    @Override
    protected void execute(ChemistryApp app, CommandLine cmdLine)
            throws Exception {
        throw new CommandException("Not yet implemented!");
//
//    	String source = cmdLine.getParameterValue("source");
//        String target = cmdLine.getParameterValue("target");
//
//        Context ctx = app.resolveContext(new Path(source));
//        if (ctx == null) {
//            throw new CommandException("Cannot resolve source: " + source);
//        }
//        
//        Context targetCtx = app.resolveContext(new Path(target));
//
//        if(ctx instanceof ChemistryContext && ((ChemistryContext)ctx).isFolder()){
//        	Folder sourcefolder = ctx.as(Folder.class);
//        }else if(ctx instanceof ChemistryContext){
//        	Document sourceDoc = ctx.as(Document.class);
//        	Path sourceFolder = ctx.getPath().removeLastSegments(1);
//        	String sourceFolderId = app.resolveContext(sourceFolder).id();
//        	// TODO lookup targetFolder or targetFile.parent and execute ctx.move
//        }
    }

}
