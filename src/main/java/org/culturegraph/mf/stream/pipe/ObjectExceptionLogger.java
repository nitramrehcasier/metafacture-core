/*
 *  Copyright 2013 Deutsche Nationalbibliothek
 *
 *  Licensed under the Apache License, Version 2.0 the "License";
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.culturegraph.mf.stream.pipe;

import org.culturegraph.mf.framework.DefaultObjectPipe;
import org.culturegraph.mf.framework.ObjectReceiver;
import org.culturegraph.mf.framework.annotations.Description;
import org.culturegraph.mf.framework.annotations.In;
import org.culturegraph.mf.framework.annotations.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Logs the string representation of every object.
 * 
 * @param <T> object type
 * @deprecated use {@link ObjectExceptionLogger} instead.
 * @author Christoph Böhme
 * 
 */
@Description("logs objects with the toString method")
@In(Object.class)
@Out(Object.class)
@Deprecated
public final class ObjectExceptionLogger<T> 
		extends DefaultObjectPipe<T, ObjectReceiver<T>> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ObjectExceptionLogger.class);

	private final String logPrefix;

	public ObjectExceptionLogger() {
		this("");
	}
	
	public ObjectExceptionLogger(final String logPrefix) {
		super();
		this.logPrefix = logPrefix;
	}

	@Override
	public void process(final T obj) {
		
		try{
			getReceiver().process(obj);
		}catch(Exception e){
			// NO CHECKSTYLE IllegalCatch FOR -1 LINES:
			// This module is supposed to intercept _all_ exceptions
			// thrown by downstream modules. Hence, we have to catch
			// Exception.
			LOG.error(logPrefix, e);
		}
		
	}

	@Override
	protected void onResetStream() {
		LOG.debug("{}resetStream", logPrefix);
	}
	
	@Override
	protected void onCloseStream() {
		LOG.debug("{}closeStream", logPrefix);
	}

}
