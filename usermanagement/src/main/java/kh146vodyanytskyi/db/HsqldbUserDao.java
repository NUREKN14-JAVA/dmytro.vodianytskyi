package kh146vodyanytskyi.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.spi.DirStateFactory.Result;

import kh146vodyanytskyi.User;

class HsqldbUserDao implements UserDao {

	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String UPDATE_USERS = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
	private static final String DELETE_FROM_USERS = "DELETE FROM users WHERE id = ?";
	private static final String SELECT_ID =  "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
	
	private ConnectionFactory connectionFactory;

	public HsqldbUserDao() {
	}

	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}



	@Override
	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if (n != 1){
				throw new DatabaseException("Number of the inserted rows:" + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if(keys.next()){
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException();
		}
	}

	@Override
	public void update(User user) throws DatabaseException {
		Connection connection = connectionFactory.createConnection();
		
		// Get id to work with
        Long id = user.getId();
        
        
        // convert it into a prepared statement
        PreparedStatement preparedStatement;
        
        try {
            
            preparedStatement = connection.prepareStatement(UPDATE_USERS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
            preparedStatement.setLong(4, id);
            
            // execute query and check that it worked
            int numberOfRowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            
            if (numberOfRowsUpdated!=1) {
                throw new DatabaseException("Number of the inserted rows: " + numberOfRowsUpdated); 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

	}

	@Override
	public void delete(User user) throws DatabaseException {
		// connect to the database
        Connection connection = connectionFactory.createConnection();
        
        
        Long id = user.getId();
        PreparedStatement preparedStatement;
        
        try {
            preparedStatement = connection.prepareStatement(DELETE_FROM_USERS);
            preparedStatement.setLong(1, id);
            int n = preparedStatement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Delete failed: " + n + " rows were deleted");
            }
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }


	}

	@Override
	public User find(Long id) throws DatabaseException {
		Connection connection = connectionFactory.createConnection();
        PreparedStatement preparedStatement;
        User user = null;
        
        try {
            
            preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DatabaseException("User with id" + id + " is not found");
            }
            user = new User();
            user.setId(resultSet.getLong(1));
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setDateOfBirthd(resultSet.getDate(4));
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
	}

	@Override
	public Collection findAll() throws DatabaseException {
		Collection result = new LinkedList();
		
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while(resultSet.next()){
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirthd(resultSet.getDate(4));
				result.add(user);
			}
		}catch (DatabaseException e){
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
	}

}
