package blade.migrate.portal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMigrator2Test {

	
	@Test
	public void reportTest() throws Exception {
		assertEquals("test2", new TestMigrator2().report());
	}
}
