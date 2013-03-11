package soundfun.tests;

import org.junit.Test;

import soundfun.plugins.PluginManager;

public class SimplePluginTest {
	
	public static void main(String[] args) throws Exception {
		PluginManager.getSingleton();
	}

	@Test
	public void testGetSingleton() {
		try {
			PluginManager.getSingleton();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
