/**
 * Copyright 2011 Ibrahim Chaehoi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package net.jawr.web.resource.bundle.factory.global.postprocessor;

import net.jawr.web.config.JawrConfig;
import net.jawr.web.resource.bundle.global.processor.GlobalProcessingContext;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import net.jawr.web.resource.handler.reader.ResourceReaderHandler;

/**
 * This class defines the context for global postprocessing.
 *  
 * @author Ibrahim Chaehoi
 */
public class GlobalPostProcessingContext extends GlobalProcessingContext {

	private ResourceBundlesHandler bundleHandler;
	
	/**
	 * Constructor
	 * @param config the jawr config
	 * @param bundleHandler the bundle handler
	 * @param resourceHandler the resource handler
	 * @param processBundle the process bundle flag
	 */
	public GlobalPostProcessingContext(JawrConfig config,
			ResourceBundlesHandler bundleHandler, ResourceReaderHandler resourceHandler, boolean processBundle) {
		super(config, resourceHandler, processBundle);
		this.bundleHandler = bundleHandler;
	}

	/**
	 * @return the bundleHandler
	 */
	public ResourceBundlesHandler getBundleHandler() {
		return bundleHandler;
	}
	
}
