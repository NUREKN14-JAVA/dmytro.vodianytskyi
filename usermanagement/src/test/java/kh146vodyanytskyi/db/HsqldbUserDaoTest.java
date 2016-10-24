package kh146vodyanytskyi.db;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import org.junit.Test;
import org.w3c.dom.UserDataHandler;

import junit.framework.TestCase;
import kh146vodyanytskyi.User;
import org.dbunit.*;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

public class HsqldbUserDaoTest extends DatabaseTestCase {
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;
	private Date dateOfBirthd;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}

	@Test
	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName("Raul");
			user.setLastName("Falcao");
			user.setDateOfBirthd(new Date());
			
			assertNull(user.getId());
			
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testFindAll() {
		try {
			Collection collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size.", 2, collection.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
    public void testDelete() {
        
        User user = new User();
        user.setId(1L);
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        user.setDateOfBirthd(new Date(1991-06-15));

        try {
            dao.delete(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Long id = 1L;
        try {
            user = dao.find(1L);
            fail("User was not deleted");
        } catch (DatabaseException e) {
            assertEquals(e.getMessage().toString(), "User with id" + id + " is not found");
        }
        
    }
	
	@Test
    public void testUpdate() {
        try {
            User user = new User();
            user.setId(1L);
            
            String newFirstName = "Jack";
            String newLastName = "Magger";
            Date dateOfBirthd = new Date(1972-05-15); 
            
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setDateOfBirthd(dateOfBirthd);
            
            dao.update(user);
            user = dao.find(user.getId());
            assertEquals("First name update failed", newFirstName, user.getFirstName());
            assertEquals("Last name update failed", newLastName, user.getLastName());
            assertEquals("Date of birth update failed", dateOfBirthd, user.getDateOfBirthd());
            
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
	
	
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
