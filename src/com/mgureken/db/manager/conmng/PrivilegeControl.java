package com.mgureken.db.manager.conmng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.mgureken.db.manager.model.User;

public abstract class PrivilegeControl {
	private static Connection conn2=null;
	private static String url="jdbc:postgresql://127.0.0.1:5432/KullaniciYonetimi";
	private static String username="postgres";
	private static String password="sifre123";
	private static LinkedList<String> nameList;
	private static LinkedList<Integer> prv;	
	
	public static LinkedList<String> getNameList() {
		return nameList;
	}

	public static void setNameList(LinkedList<String> nameList) {
		PrivilegeControl.nameList = nameList;
	}

	public static LinkedList<Integer> getPrv() {
		return prv;
	}

	public static void setPrv(LinkedList<Integer> prv) {
		PrivilegeControl.prv = prv;
	}

	static {
		nameList = new LinkedList<String>();
		prv = new LinkedList<Integer>();
		
		try {
			Class.forName("org.postgresql.Driver");
			try {
				conn2 = DriverManager.getConnection(url, username, password);
				
				Statement stmt;
				stmt = conn2.createStatement();
				String sql = "select priority, usrname from kullanici where deleted=false";
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println(sql);
				while(rs.next())
				{
					nameList.add(rs.getString("usrname"));
					prv.add(rs.getInt("priority"));
					System.out.println("database username:"+rs.getString("usrname")+" priority:" +rs.getInt("priority"));
				}
				conn2.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //try/catch'e gerek yok, metot throwable yapildi.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
