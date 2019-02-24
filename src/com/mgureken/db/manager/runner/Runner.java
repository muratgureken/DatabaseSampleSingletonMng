package com.mgureken.db.manager.runner;

import java.sql.SQLException;

import com.mgureken.db.manager.conmng.ConnectionManager;

public class Runner {

	public static void main(String[] args) {
		try {
			ConnectionManager temp = ConnectionManager.getInstance();
			temp.getClass();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
