package com.mgureken.db.manager.conmng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	//singleton pattern: constructor public olmayacak, new ile instance alinamayacak.
	//constructor kendi icerisinde acilacak.
	private static ConnectionManager instance=null;
	private Connection conn;
	private String url="jdbc:postgresql://127.0.0.1:5432/KullaniciYonetimi";
	private String username="postgres";
	private String password="sifre123";
	
	private ConnectionManager() throws SQLException{
		//connection acma islemi
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password); //try/catch'e gerek yok, metot throwable yapildi.
			//conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("where is your postgreSQL driver?"+" Include in your library path!");
			e.printStackTrace();
		}
	}
	
	//disaridan gorulebilen, connection saglayan metot
	public Connection getConnection()
	{
		return this.conn;
	}
	
	public static ConnectionManager getInstance() throws SQLException
	{
		if(instance==null)
		{
			instance = new ConnectionManager();
		}
		else if(instance.getConnection().isClosed())
		{
			instance = new ConnectionManager();
		}
		return instance;
	}	
}
