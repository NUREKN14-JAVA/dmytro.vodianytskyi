package kh146vodyanytskyi.db;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase{

	@Test
	public void testGetUserDao() {
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactory instance is null", daoFactory);
			UserDao userDao = daoFactory.getUserDao();
			assertNotNull("UserDao instance is null", userDao);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

}
