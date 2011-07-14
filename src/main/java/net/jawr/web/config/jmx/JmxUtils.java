/**
 * Copyright 2009-2011 Ibrahim Chaehoi
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
package net.jawr.web.config.jmx;

import static net.jawr.web.util.ServletContextUtils.getContextPath;

import java.lang.management.ManagementFactory;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.ServletContext;

import net.jawr.web.JawrConstant;
import net.jawr.web.exception.JmxConfigException;
import net.jawr.web.servlet.JawrRequestHandler;
import net.jawr.web.util.ServletContextUtils;
import net.jawr.web.util.StringUtils;

import org.apache.log4j.Logger;

/**
 * Utility class for JMX.
 * 
 * @author Ibrahim Chaehoi
 */
public final class JmxUtils {

	/** The logger */
	private static final Logger LOGGER = Logger.getLogger(JmxUtils.class);

	/** The property which enables the use of JMX */
	public static final String JMX_ENABLE_FLAG_SYSTEM_PROPERTY = "com.sun.management.jmxremote";
	
	/**
	 * Constructor 
	 */
	private JmxUtils() {
		
	}
	
	/**
	 * Initialize the JMX Bean 
	 */
	public static void initJMXBean(JawrRequestHandler requestHandler, ServletContext servletContext, String resourceType, Properties configProperties) {
		
		// Skip the initialisation if no JMX jar is find.
		try {
			JmxUtils.class.getClassLoader().loadClass("javax.management.MBeanServer");
		} catch (ClassNotFoundException e1) {
			LOGGER.info("JMX API is not define in the classpath.");
			return;
		}
		
		try {

			MBeanServer mbs = JmxUtils.getMBeanServer();
			if(mbs != null){
				
				ObjectName jawrConfigMgrObjName = JmxUtils.getMBeanObjectName(servletContext, resourceType);
				JawrApplicationConfigManager appConfigMgr = (JawrApplicationConfigManager) servletContext.getAttribute(JawrConstant.JAWR_APPLICATION_CONFIG_MANAGER);
				if(appConfigMgr == null){
					appConfigMgr = new JawrApplicationConfigManager();
					servletContext.setAttribute(JawrConstant.JAWR_APPLICATION_CONFIG_MANAGER, appConfigMgr);
				}
				
				// register the jawrApplicationConfigManager if it's not already done
				ObjectName appJawrMgrObjectName = JmxUtils.getAppJawrConfigMBeanObjectName(servletContext);
				if(!mbs.isRegistered(appJawrMgrObjectName)){
					mbs.registerMBean(appConfigMgr, appJawrMgrObjectName);
				}
				
				// Create the MBean for the current Request Handler
				JawrConfigManager mbean = new JawrConfigManager(requestHandler, configProperties);
				if(mbs.isRegistered(jawrConfigMgrObjName)){
					LOGGER.warn("The MBean '"+jawrConfigMgrObjName.getCanonicalName()+"' already exists. It will be unregisterd and registered with the new JawrConfigManagerMBean.");
					mbs.unregisterMBean(jawrConfigMgrObjName);
				}
				
				// Initialize the jawrApplicationConfigManager
				if(resourceType.equals(JawrConstant.JS_TYPE)){
					appConfigMgr.setJsMBean(mbean);
				}else if(resourceType.equals(JawrConstant.CSS_TYPE)){
					appConfigMgr.setCssMBean(mbean);
				}else{
					appConfigMgr.setImgMBean(mbean);
				}
				
				mbs.registerMBean(mbean, jawrConfigMgrObjName);
			}
			
		} catch (Exception e) {
			LOGGER.error("Unable to instanciate the Jawr MBean for resource type '"+resourceType+"'", e);
		}

	}
	
	/**
	 * Returns the current MBean server or create a new one if not exist.
	 * 
	 * @return the current MBean server or create a new one if not exist.
	 */
	public static MBeanServer getMBeanServer() {

		MBeanServer mbs = null;
		// Check if JMX is enable
		if(System.getProperty(JMX_ENABLE_FLAG_SYSTEM_PROPERTY) != null){
			mbs = ManagementFactory.getPlatformMBeanServer();
		}
		
		return mbs;
	}
	
	/**
	 * Returns the object name for the Jawr configuration Manager MBean
	 * @param servletContext the servelt context
	 * @param resourceType the resource type
	 * @return the object name for the Jawr configuration Manager MBean
	 * @throws Exception if an exception occurs
	 */
	public static ObjectName getMBeanObjectName(final ServletContext servletContext, final String resourceType) {
		
		String contextPath = getContextPath(servletContext);
		return getMBeanObjectName(contextPath, resourceType);
	}

	/**
	 * Returns the object name for the Jawr configuration Manager MBean
	 * @param contextPath the context path
	 * @param resourceType  the resource type
	 * @return the object name for the Jawr configuration Manager MBean
	 * @throws Exception if an exception occurs
	 */
	public static ObjectName getMBeanObjectName(final String contextPath, final String resourceType) {
		
		String curCtxPath = contextPath;
		if(StringUtils.isEmpty(curCtxPath)){
			
			curCtxPath = ServletContextUtils.getContextPath(null);
		}
		
		if(curCtxPath.charAt(0) == ('/')){
			curCtxPath = curCtxPath.substring(1);
		}
		String objectNameStr = "net.jawr.web.jmx:type=JawrConfigManager,webappContext="+curCtxPath+",name="+resourceType+"MBean";
		
		return getObjectName(objectNameStr);
	}
	
	/**
	 * Returns the object name for the Jawr Application configuration Manager MBean
	 * @param servletContext the servelt context
	 * @return the object name for the Jawr configuration Manager MBean
	 * @throws Exception if an exception occurs
	 */
	public static ObjectName getAppJawrConfigMBeanObjectName(ServletContext servletContext) {
		
		String contextPath = getContextPath(servletContext);
		if(contextPath.charAt(0) == ('/')){
			contextPath = contextPath.substring(1);
		}
		String objectNameStr = "net.jawr.web.jmx:type=JawrAppConfigManager,webappContext="+contextPath;
		
		return getObjectName(objectNameStr);
	}
	
	/**
     * Create an object name from the name passed in parameter
	 * @param name the name
	 * @return the objectName
	 */
	private static ObjectName getObjectName(String name){
		
		ObjectName mBeanName = null;
		try {
			mBeanName = new ObjectName(name);
		} catch (MalformedObjectNameException e) {
			throw new JmxConfigException(e);
		} catch (NullPointerException e) {
			throw new JmxConfigException(e);
		}
		
		return mBeanName;
	}

	/**
	 * Returns true if JMX is enabled for the application
	 * @return true if JMX is enabled for the application
	 */
	public static boolean isJmxEnabled() {
		return System.getProperty(JawrConstant.JMX_ENABLE_FLAG_SYSTEL_PROPERTY) != null;
	}
}
