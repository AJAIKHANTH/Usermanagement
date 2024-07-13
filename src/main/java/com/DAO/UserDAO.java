package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.User;

public class UserDAO {


	public Connection  getConnection()throws SQLException, ClassNotFoundException{
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/usermanagement","root","Ajai@2458");  

		}catch(SQLException e) {
			System.out.println(e);
		}
		
		return con;

	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		// TODO Auto-generated method stub


		UserDAO Nick = new UserDAO();
		Nick.getConnection();


	}

	public List<User> listUser() throws ClassNotFoundException {
		List<User> users=new ArrayList<User>();


		try {
			Connection con=getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("select * from Users");
			System.out.println(preparedStatement);

			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("UserName");
				String email = rs.getString("Mail");
				int age = rs.getInt("age");
				String city=rs.getString("city");

				User u=new User();
				
				u.setId(id);
				u.setUserName(name);
				u.setAge(age);
				u.setCity(city);
				u.setMail(email);
				
				users.add(u);

			}
		}catch(SQLException e) {
			printSQLException(e);
		}
		return users;
	}	
	private void printSQLException(SQLException ex) {
		for (Throwable e: ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public void createUser(User user) throws ClassNotFoundException {
		try {
			Connection con=getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Users" + "  (UserName, Mail, city,age) VALUES " +
			        " (?, ?, ?,?);");
			
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getMail());
			preparedStatement.setString(3, user.getCity());
			preparedStatement.setInt(4, user.getAge());
			
			System.out.println(preparedStatement);
			preparedStatement.execute();
			
			
		}catch(SQLException e) {
			printSQLException(e);
		}
		
	}
	
	
public void deleteUser(int id) throws ClassNotFoundException {
		
	    try {
	    	Connection con = getConnection();
	    		
	         PreparedStatement preparedStatement = con.prepareStatement("delete from Users WHERE ID = ?");
	    	
	    	preparedStatement.setInt(1, id);
	        preparedStatement.executeUpdate(); // Use executeUpdate() for DELETE statements

	        System.out.println("Row with Id " + id + " deleted successfully.");
	    } catch (SQLException e) {
	        printSQLException(e);
	    }
	}

	

	public User getUser(int id) throws ClassNotFoundException {
		
		User user = null;
		
		try {
			Connection con=getConnection();
			PreparedStatement stmt=con.prepareStatement("select * from Users where Id=?");
			stmt.setInt(1, id);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()) {
				int id2 = rs.getInt("ID");
				String name = rs.getString("UserName");
				String email = rs.getString("Mail");
				int age = rs.getInt("age");
				String city=rs.getString("city");
				
				user=new User();
				user.setId(id2);
				user.setUserName(name);
				user.setAge(age);
				user.setCity(city);
				user.setMail(email);
				
				
			}
		}catch(SQLException e) {
	        printSQLException(e);
	        
	    }
		return user;
	}

	public User updateUser(User user) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
	    User updatedUser = null;
		
		try {
			Connection con=getConnection();
			PreparedStatement stmt=con.prepareStatement("UPDATE Users SET UserName=?, Mail=?, age=?, city=? WHERE ID=?");
			   stmt.setString(1, user.getUserName());
		        stmt.setString(2, user.getMail());
		        stmt.setInt(3, user.getAge());
		        stmt.setString(4, user.getCity());
		        stmt.setInt(5, user.getId());
		        stmt.executeUpdate();
		        
		        // Check if the update was successful
//		        if (rowsUpdated > 0) {
//		            // If successful, return the updated user
//		            updatedUser = user;
//		        }
				
				
		}catch(SQLException e) {
	        printSQLException(e);
	        
	    }
        return updatedUser;
	}


		
	}





