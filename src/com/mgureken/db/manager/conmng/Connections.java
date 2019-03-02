package com.mgureken.db.manager.conmng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Connections {
	private static int TotalConnectNo=10;
	private static int PrvConnectNo=9;
	private static boolean[] ConnInUse; 
	private static boolean[] PrvConn;
	private static Connection[] Conns;
	private static String url="jdbc:postgresql://127.0.0.1:5432/KullaniciYonetimi";
	private static String username="postgres";
	private static String password="sifre123";
	
	static
	{
		ConnInUse = new boolean[TotalConnectNo];
		PrvConn= new boolean[TotalConnectNo];
		Conns = new Connection[TotalConnectNo];
		
		for(int i=0;i<TotalConnectNo;i++)
		{
			ConnInUse[i] = false;
			if(i<PrvConnectNo)
			{
				PrvConn[i] = true;
			}
			else
			{
				PrvConn[i] = false;
			}
		}
	}

	public static Connection TryConnection(int i) throws SQLException
	{		
		try {
			Class.forName("org.postgresql.Driver");
			Conns[i] = DriverManager.getConnection(url, username, password); //try/catch'e gerek yok, metot throwable yapildi.
		} catch (ClassNotFoundException e) {
			System.out.println("where is your postgreSQL driver?"+" Include in your library path!");
			e.printStackTrace();
		}
		
		return Conns[i];
	}
	
	public static boolean CloseTheConnection(int i)
	{
		boolean connSuccess = true;
		try {
			Conns[i].close();
		} catch (SQLException e) {
			connSuccess = false;
			e.printStackTrace();
		}
		
		return connSuccess;
	}
	
	public static int getTotalConnectNo() {
		return TotalConnectNo;
	}

	public static void setTotalConnectNo(int totalConnectNo) {
		TotalConnectNo = totalConnectNo;
	}

	public static boolean getConnInUse(int i) {
		return ConnInUse[i];
	}

	public static void setConnInUse(boolean connInUse, int i) {
		ConnInUse[i] = connInUse;
	}

	public static boolean getPrvConn(int i) {
		return PrvConn[i];
	}

	public static void setPrvConn(boolean prvConn, int i) {
		PrvConn[i] = prvConn;
	}

	public static Connection getConns(int i) {
		return Conns[i];
	}

	public static void setConns(Connection conns, int i) {
		Conns[i] = conns;
	}
	
	
}
