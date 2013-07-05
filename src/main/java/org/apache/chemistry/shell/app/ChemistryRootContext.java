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

package org.apache.chemistry.shell.app;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.shell.util.ColorHelper;
import org.apache.chemistry.shell.util.Path;

public class ChemistryRootContext extends AbstractContext {

    protected String[] keys;

    protected String[] ls;

    public ChemistryRootContext(ChemistryApp app) {
        super(app, Path.ROOT);
    }

    @Override
    public ChemistryApp getApplication() {
        return (ChemistryApp) app;
    }

    public <T> T as(Class<T> type) {
        return null;
    }

    public Context getContext(String name) {
        load();
        ChemistryApp app = getApplication();
        if (!app.isConnected()) {
            Console.getDefault().error(
                    "Not connected: cannot browse repository");
            return null;
        }
        // lookup repository by name
        Repository repo = null;
        for (Repository re : app.getRepositories()) {
            if (re.getName().equals(name)) {
                repo = re;
                break;
            }
        }
        if (repo == null) {
            return null;
        }
        Session session = repo.createSession();
        CmisObject entry = session.getRootFolder();
        return new ChemistryContext( app, path.append(name),
                session, entry);
    }

    public String[] ls() {
        if (load()) {
            return ls;
        }
        return new String[0];
    }

    public String[] entries() {
        if (load()) {
            return keys;
        }
        return new String[0];
    }

    protected boolean load() {
        if (keys != null) {
            return true;
        }
        if (!getApplication().isConnected()) {
            Console.getDefault().error(
                    "Not connected: cannot browse repository");
            return false;
        }
        List<Repository> repos = getApplication().getRepositories();
        int size = repos.size();
        keys = new String[size];
        ls = new String[size];
        int i = 0;
        for (Repository repo : repos) {
            String name = repo.getName();
            keys[i] = name;
            ls[i] = ColorHelper.decorateNameByType(name, "Repository");
            i++;
        }
        return true;
    }

    public void reset() {
        keys = null;
        ls = null;
    }

    public String id() {
        return "CMIS server: " + app.getServerUrl();
    }

}
