package com.yash.HelloSpringBoot2.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



import com.yash.HelloSpringBoot2.model.User;

public class UserDAOImpl {
	
	Connection con;
	public UserDAOImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/userdb","root","root");  
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	public void insertUser(User user) {
		try {
			String sql = ("insert into users (email, user_name, password) values (?, ?, ?)");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getPassword());
			stmt.execute();
			stmt.close();
			
			String sql3 = "select id from users where user_name = \"" + user.getUsername() + "\"";
			Statement stmt3 = con.createStatement();
			ResultSet resultSet = stmt3.executeQuery(sql3);
			int id = 0;
			if(resultSet.next()) {
				id = resultSet.getInt(1);
			}
			
			String sql2 = ("insert into user_detail (user_id, first_name, last_name, phone_number, address) values (?, ?, ?, ?, ?)");
			PreparedStatement stmt2 = con.prepareStatement(sql2);
			
			stmt2.setInt(1, id);
			stmt2.setString(2, user.getFirstname());
			stmt2.setString(3, user.getLastname());
			stmt2.setString(4, user.getNumber());
			stmt2.setString(5, user.getAddress());
			
			stmt2.execute();
			stmt2.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}


	public boolean loginValidation(User user) {
		
		try {
			String sql = "select user_name, password from users where user_name = ? and password = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			ResultSet set = statement.executeQuery();
			
			if(set.next()) {
				return true;
				
			}
			
			else {
				return false;
			}
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
public User loginValidate(User user) {
		User newUser = new User();

		try {
			String sql = "select * from users where user_name = ? and password = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			ResultSet set = statement.executeQuery();
			System.out.println("Hello");
			
			if(set.next()) {
				String sql2 = "select * from user_detail where user_id = \"" + set.getInt(1) + "\"";
				Statement statement2 = con.createStatement();
				ResultSet set2 = statement2.executeQuery(sql2);
				if(set2.next()) {
					newUser.setUsername(set.getString("user_name"));
					newUser.setPassword(set.getString("password"));
					newUser.setEmail(set.getString("email"));
					newUser.setFirstname(set2.getString("first_name"));
					newUser.setLastname(set2.getString("last_name"));
					newUser.setAddress(set2.getString("address"));
					
					return newUser;
				}
				
				else {
					
					return null;
				}
				
				
			}
			
			else {
			
				return null;
			}
			
			
		
		}
		catch (Exception e) {
		
			e.printStackTrace();
		}
		System.out.println("Made it too the end");
		return null;
	}
}
