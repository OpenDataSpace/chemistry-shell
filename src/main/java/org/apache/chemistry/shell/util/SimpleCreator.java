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

package org.apache.chemistry.shell.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

public class SimpleCreator {

	protected final Folder folder;

	public SimpleCreator(Folder folder) {
		this.folder = folder;
	}

	public void createFolder(String typeName, String name) throws Exception {
		// (minimal set: name and object type id)
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, typeName);
		properties.put(PropertyIds.NAME, name);
		folder.createFolder(properties);
	}

	public Document createFile(String typeName, String name, ContentStream stream) throws Exception {
		// (minimal set: name and object type id)
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, typeName);
		properties.put(PropertyIds.NAME, name);
		return folder.createDocument(properties, stream, VersioningState.NONE);
	}
	
	public Document createFile(String typeName, String name) throws Exception {
		return createFile(typeName, name, null);
	}
	
	public Document createDummyFile(String typeName, String name, DummyFileType dummyType, long size) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, typeName);
		properties.put(PropertyIds.NAME, name);
		String mimetype ="application/octet-stream"; 
		if(dummyType == DummyFileType.TEXT)
			mimetype = "text/plain";
		DummyInputStream in = new DummyInputStream(size, dummyType);
		ContentStream contentDummyStream = new ContentStreamImpl(name, BigInteger.valueOf(size), mimetype, in);
		return folder.createDocument(properties, contentDummyStream, VersioningState.NONE);
	}

}
