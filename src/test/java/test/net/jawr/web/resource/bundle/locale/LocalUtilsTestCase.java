/**
 * 
 */
package test.net.jawr.web.resource.bundle.locale;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.jawr.web.resource.bundle.locale.LocaleUtils;

/**
 * Test case class for Local utils
 * 
 * @author Ibrahim Chaehoi
 *
 */
public class LocalUtilsTestCase extends TestCase {

	public void testGetLocaleAvailablePrefixes(){
		
		List<String> result = LocaleUtils.getAvailableLocaleSuffixesForBundle("bundleLocale.messages");
		Assert.assertEquals(3, result.size());
		Assert.assertEquals("",result.get(0));
		Assert.assertEquals("es",result.get(1));
		Assert.assertEquals("fr",result.get(2));
	}
	
	public void testGetLocaleAvailablePrefixesWithNamespace(){
		
		List<String> result = LocaleUtils.getAvailableLocaleSuffixesForBundle("bundleLocale.messages(mynamespace)");
		Assert.assertEquals(3, result.size());
		Assert.assertEquals("",result.get(0));
		Assert.assertEquals("es",result.get(1));
		Assert.assertEquals("fr",result.get(2));
	}
	
	public void testGetLocaleAvailablePrefixesWithFilter(){
		
		List<String> result = LocaleUtils.getAvailableLocaleSuffixesForBundle("bundleLocale.messages[ui.msg]");
		Assert.assertEquals(3, result.size());
		Assert.assertEquals("",result.get(0));
		Assert.assertEquals("es",result.get(1));
		Assert.assertEquals("fr",result.get(2));
	}
	
	public void testGetLocaleAvailablePrefixesWithFilterAndNamespace(){
		
		List<String> result = LocaleUtils.getAvailableLocaleSuffixesForBundle("bundleLocale.messages(mynamespace)[ui.msg]");
		Assert.assertEquals(3, result.size());
		Assert.assertEquals("",result.get(0));
		Assert.assertEquals("es",result.get(1));
		Assert.assertEquals("fr",result.get(2));
	}
	
	// TODO test for Grails with servlet context
}
