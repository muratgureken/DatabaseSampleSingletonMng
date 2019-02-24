package com.mgureken.db.manager.conmng;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mgureken.db.manager.model.User;
import com.mgureken.db.manager.ui.GirisFrame;
import com.mgureken.db.manager.ui.MainFrame;

public class UserDAO {
	public User getUserForName(String username) throws SQLException
	{
		ConnectionManager temp = ConnectionManager.getInstance();
		Connection conn = temp.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		String sql = "select id, usrname, password from kullanici where usrname= '"+username+"'";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println(sql);
		User usr = null;
		if(rs.next())
		{
			usr = new User();
			usr.setUsername(rs.getString("usrname"));
			usr.setPassword(rs.getString("password"));
			usr.setId(rs.getInt("id"));
			System.out.println("database:"+rs.getString("usrname")+" "+rs.getString("password")+" "+rs.getInt("id"));
		}
		return usr;
	}
	
	public List<User> getAllUsers(User temp) throws SQLException //exception user interface'e gonderiyoruz. Yoksa konsola basilir. Yani cagrildigi yerin catch'ine git diyoruz.
	{
		List<User> users = new ArrayList<User>();
		ConnectionManager temp2 = ConnectionManager.getInstance();
		Connection conn = temp2.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		String sql = "select id, usrname, password from kullanici";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println(sql);
		User usr = null;
		while(rs.next())
		{
			User nusr = new User();
			nusr.setUsername(rs.getString("usrname"));
			nusr.setPassword(rs.getString("password"));
			nusr.setId(rs.getInt("id"));
			users.add(nusr);
		}
		return users;
	}
}
