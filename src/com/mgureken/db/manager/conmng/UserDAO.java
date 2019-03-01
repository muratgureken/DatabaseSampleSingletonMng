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
		String sql = "select id, usrname, password from kullanici where usrname= '"+username+"' and deleted=false";
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

	public int updateUser(String username, int id, String password) throws SQLException
	{
		ConnectionManager temp = ConnectionManager.getInstance();
		Connection conn = temp.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "update kullanici set usrname='"+username+"', password="+password+" where id="+id;
		System.out.println("update:"+sql);
		int count = stmt.executeUpdate(sql);
		if(count<=0)
		{
			System.out.println("Update islemi gerceklesmedi!...");
		}
		else
		{
			System.out.println("Update iIslem gerceklesti.");
		}
		return count;
	}
	
	public int deleteUser(int id) throws SQLException
	{
		ConnectionManager temp = ConnectionManager.getInstance();
		Connection conn = temp.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "update kullanici set deleted=true where id="+id;
		System.out.println("delete: "+sql);
		int count = stmt.executeUpdate(sql);
		if(count<=0)
		{
			System.out.println("Delete islemi gerceklesmedi!...");
		}
		else
		{
			System.out.println("Delete iIslem gerceklesti.");
		}
		return count;
	}
	
	public List<User> getAllUsers(User temp) throws SQLException //exception user interface'e gonderiyoruz. Yoksa konsola basilir. Yani cagrildigi yerin catch'ine git diyoruz.
	{
		List<User> users = new ArrayList<User>();
		int count=0;
		ConnectionManager temp2 = ConnectionManager.getInstance();
		Connection conn = temp2.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		String sql = "select id, usrname, password from kullanici where deleted=false";
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
			System.out.println("getall: "+users.get(count).getUsername());
			count++;
		}
		return users;
	}
}