package com.mgureken.db.manager.conmng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager extends Connections{
	//singleton pattern: constructor public olmayacak, new ile instance alinamayacak.
	//constructor kendi icerisinde acilacak.
	private static ConnectionManager instance=null;
	private Connection conn;
	/*private String url="jdbc:postgresql://127.0.0.1:5432/KullaniciYonetimi";
	private String username="postgres";
	private String password="sifre123";*/
	private int connNo;

	private ConnectionManager() throws SQLException{
		//connection acma islemi
		/*boolean durum = prvConn[0];
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password); //try/catch'e gerek yok, metot throwable yapildi.
		} catch (ClassNotFoundException e) {
			System.out.println("where is your postgreSQL driver?"+" Include in your library path!");
			e.printStackTrace();
		}*/
	}

	public int getConnNo() {
		return connNo;
	}

	public void setConnNo(int connNo) {
		this.connNo = connNo;
	}


	public void closeConnection(int i)
	{
		Connections.setConnInUse(false, i);
		Connections.CloseTheConnection(i);
	}

	//disaridan gorulebilen, connection saglayan metot
	public Connection getConnection(int priority)
	{
		Connection conn1=null;
		connNo = -1;
		if(priority==1)
		{
			for(int i=0;i<Connections.getTotalConnectNo();i++)
			{
				System.out.println("connection-"+i+" inuse:"+Connections.getConnInUse(i)+" total connection:"+Connections.getTotalConnectNo());
				System.out.println("private:"+Connections.getPrvConn(i));
				if((Connections.getPrvConn(i))&&!((Connections.getConnInUse(i))))
				{
					Connections.setConnInUse(true, i);
					connNo = i;
					try {
						conn1 = Connections.TryConnection(i);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//getConns(i);
					System.out.println("connection:"+conn1);
					break;
				}
			}
		}
		else
		{
			for(int i=0;i<Connections.getTotalConnectNo();i++)
			{
				System.out.println("connection-"+i+" inuse:"+Connections.getConnInUse(i)+" total connection:"+Connections.getTotalConnectNo());
				System.out.println("private:"+Connections.getPrvConn(i));
				if(!(Connections.getPrvConn(i))&&!((Connections.getConnInUse(i))))
				{
					Connections.setConnInUse(true, i);
					connNo = i;
					try {
						conn1 = Connections.TryConnection(i);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//getConns(i);
					System.out.println("connection:"+conn1);
					break;				
				}
			}			
		}
		return conn1;
	}

	public static ConnectionManager getInstance() throws SQLException
	{
		/*if(instance==null)
		{
			instance = new ConnectionManager();
		}
		else if(instance.getConnection().isClosed())
		{
			instance = new ConnectionManager();
		}*/

		instance = new ConnectionManager();
		return instance;
	}	
}
