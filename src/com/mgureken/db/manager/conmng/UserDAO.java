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

public class UserDAO extends PrivilegeControl{
	private int connNo;
	ConnectionManager temp;
	Connection conn;
	private boolean connAvailable=false;

	public UserDAO() throws SQLException{
		temp = ConnectionManager.getInstance();
		System.out.println("Got connectionManager instance");
		/*conn = temp.getConnection(1);
		connNo = temp.getConnNo();*/
	}

	public void connectToDatabase(String str)
	{
		int index = PrivilegeControl.getNameList().indexOf(str);
		if(index>=0)
		{
			int prvl = PrivilegeControl.getPrv().get(index);
			System.out.println("Priveledge of User: "+prvl);
			conn = temp.getConnection(prvl);
			System.out.println("connected:"+conn);
			connNo = temp.getConnNo();
		}
	}

	public int getConnNo() {
		return connNo;
	}



	public void setConnNo(int connNo) {
		this.connNo = connNo;
	}


	public boolean isConnAvailable() {
		return connAvailable;
	}

	public void setConnAvailable(boolean connAvailable) {
		this.connAvailable = connAvailable;
	}

	public User getUserForName(String username) throws SQLException
	{
		/*ConnectionManager temp = ConnectionManager.getInstance();
		Connection conn = temp.getConnection(1);*/
		connNo = temp.getConnNo();
		System.out.println("getuser conn:"+conn);
		User usr = null;
		if(conn==null)
		{
			System.out.println("No connection available.");
			connAvailable = false;
		}
		else
		{
			connAvailable = true;
			Statement stmt;
			stmt = conn.createStatement();
			String sql = "select id, usrname, password from kullanici where usrname= '"+username+"' and deleted=false";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			if(rs.next())
			{
				usr = new User();
				usr.setUsername(rs.getString("usrname"));
				usr.setPassword(rs.getString("password"));
				usr.setId(rs.getInt("id"));
				System.out.println("database:"+rs.getString("usrname")+" "+rs.getString("password")+" "+rs.getInt("id"));
			}
		}
		return usr;
	}

	public void closeConnection()
	{
		temp.closeConnection(connNo);
	}

	public Boolean insertUser(User usr)
	{
		connNo = temp.getConnNo();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "insert into kullanici(id,usrname,password) values("+usr.getId()+",'"+usr.getUsername()+"','"+usr.getPassword()+"')";
			int count = stmt.executeUpdate(sql);
			if(count>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}

	public int updateUser(String username, int id, String password) throws SQLException
	{
		//ConnectionManager temp = ConnectionManager.getInstance();
		//Connection conn = temp.getConnection(1);
		//connNo = temp.getConnNo();
		int count=-1;
		if(conn==null)
		{
			System.out.println("No connection available.");
		}
		else
		{
			Statement stmt = conn.createStatement();
			String sql = "update kullanici set usrname='"+username+"', password="+password+" where id="+id;
			System.out.println("update:"+sql);
			count = stmt.executeUpdate(sql);
			if(count<=0)
			{
				System.out.println("Update islemi gerceklesmedi!...");
			}
			else
			{
				System.out.println("Update iIslem gerceklesti.");
			}
		}
		return count;
	}

	public int deleteUser(int id) throws SQLException
	{
		//ConnectionManager temp = ConnectionManager.getInstance();
		//Connection conn = temp.getConnection(1);
		//connNo = temp.getConnNo();

		int count=-1;
		if(conn==null)
		{
			System.out.println("No connection available.");
		}
		else
		{
			Statement stmt = conn.createStatement();
			String sql = "update kullanici set deleted=true where id="+id;
			System.out.println("delete: "+sql);
			count = stmt.executeUpdate(sql);
			if(count<=0)
			{
				System.out.println("Delete islemi gerceklesmedi!...");
			}
			else
			{
				System.out.println("Delete iIslem gerceklesti.");
			}
		}
		return count;
	}

	public List<User> getAllUsers(User temp2) throws SQLException //exception user interface'e gonderiyoruz. Yoksa konsola basilir. Yani cagrildigi yerin catch'ine git diyoruz.
	{
		List<User> users = new ArrayList<User>();
		int count=0;
		//ConnectionManager temp2 = ConnectionManager.getInstance();
		//Connection conn = temp.getConnection(1);
		//connNo = temp.getConnNo();

		if(conn==null)
		{
			System.out.println("No connection available.");
		}
		else
		{
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
			System.out.println("-----------------------------------------------");
		}
		return users;
	}
}