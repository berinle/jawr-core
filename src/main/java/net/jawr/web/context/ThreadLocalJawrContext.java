/**
 * Copyright 2009 * @author Matt Ruby, Ibrahim Chaehoi
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
package net.jawr.web.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.ObjectName;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class defines the context for Jawr, it holds the context in a ThreadLocal object.
 * 
 * @author Matt Ruby
 * @author Ibrahim Chaehoi
 */
public final class ThreadLocalJawrContext {
    private static Log log = LogFactory.getLog(ThreadLocalJawrContext.class);

	/**
	 * debugOverride will allow us to override production mode on a request by request basis.
	 * ThreadLocal is used to hold the overridden status throughout a given request.
	 */

    private static ConcurrentLinkedQueue<JawrContext> jawrContext = new ConcurrentLinkedQueue<JawrContext>();
	
	/**
	 * The debugOverride will be automatially set to false
	 */
	private ThreadLocalJawrContext() {
		
	}

    public static JawrContext getJawrContext(){
        JawrContext context = jawrContext.poll();
        if(context == null){
            log.info("ThreadLocalJawrContext.getJawrContext: Creating a new instance of WeakReference<JawrContext>");
            context = new JawrContext();
            jawrContext.add(context);
            return context;
        }
        return context;
    }
	
	/**
	 * Returns the mbean object name of the Jawr config manager
	 * @return the mbean object name of the Jawr config manager
	 */
	public static ObjectName getJawrConfigMgrObjectName() {
		
		return getJawrContext().getJawrConfigMgrObjectName();
	}

	/**
	 * Sets the mbean object name of the Jawr config manager
	 * @param mbeanObjectName the mbean object name of the Jawr config manager
	 */
	public static void setJawrConfigMgrObjectName(ObjectName mbeanObjectName) {

        getJawrContext().setJawrConfigMgrObjectName(mbeanObjectName);
	}
	
	/**
	 * Get the flag stating that production mode should be overridden
	 * @return the flag stating that production mode should be overridden
	 */
	public static boolean isDebugOverriden() {
		
		return getJawrContext().isDebugOverriden();
	}

	/**
	 * Set the override flag that will live only for this request
	 * @param override the flag to set
	 */
	public static void setDebugOverriden(boolean override) {

        getJawrContext().setDebugOverriden(override);
	}
	
	/**
	 * Returns the flag indicating that we are using making a bundle processing at build time
	 * @return the flag indicating that we are using making a bundle processing at build time
	 */
	public static boolean isBundleProcessingAtBuildTime() {
		return getJawrContext().isBundleProcessingAtBuildTime();
	}

	/**
	 * Sets the flag indicating that we are using making a bundle processing at build time
	 * @param bundleProcessingAtBuildTime the flag to set
	 */
	public static void setBundleProcessingAtBuildTime(boolean bundleProcessingAtBuildTime) {
        getJawrContext().setBundleProcessingAtBuildTime(bundleProcessingAtBuildTime);
	}
	
	/**
	 * Returns the current request
	 * @return the request
	 */
	public static String getRequestURL() {
		return getJawrContext().getRequestURL();
	}

	/**
	 * Sets the request
	 * @param request the request to set
	 */
	public static void setRequest(String requestURL) {
        getJawrContext().setRequestURL(requestURL);
	}
	
	/**
	 * Sets the mbean object name
	 * @param mbeanObjectName the mbean object name
	 */
	public static void reset() {

		jawrContext.remove();
	}

    /**
     * Utility method to enforce resource clean up
     */
    public static void shutdown(){
        boolean info = log.isInfoEnabled();
        if(info)
            log.info("start: ThreadLocalJawrContext.shutdown");
        if(null != jawrContext){
            JawrContext context;
            while(!jawrContext.isEmpty()){
                context = jawrContext.poll();
                if(context != null){
                    context.reset();
                }
            }

            jawrContext.clear();
            jawrContext = null;

            log.info(">>>> Done with jawrContext cleanup! <<<<");
        }

        if(info)
            log.info("end: ThreadLocalJawrContext.shutdown");

    }
	
}
