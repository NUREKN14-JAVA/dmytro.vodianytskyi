package kh146vodyanytskyi;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UserTest extends TestCase{

	private User user;
	private Date dateOfBirthd;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1996, Calendar.AUGUST, 28);
		dateOfBirthd = calendar.getTime();
		
	}

	public void testGetFullName(){
		user.setFirstName("Raul");
		user.setLastName("Falcao");
		assertEquals("Falcao, Raul", user.getFullName());
	}
	
	public void testGetAge(){
		user.setDateOfBirthd(dateOfBirthd);
		assertEquals(2008 - 1996, user.getAge());
	}
	
}
