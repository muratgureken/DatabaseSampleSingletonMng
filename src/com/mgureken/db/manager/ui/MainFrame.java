package com.mgureken.db.manager.ui;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.mgureken.db.manager.conmng.UserDAO;
import com.mgureken.db.manager.model.User;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame{
	private JTable table;
	JButton btnSil, btnVazgec, btnTamam, btnGncelle;
	boolean silSelected=false, guncelleSelected=false;
	public MainFrame() throws SQLException{
		initialize();
	}

	private void initialize()
	{
		setTitle("Database");
		setBounds(100,100,463,352);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 272, 280);
		getContentPane().add(scrollPane);
		UserDAO dao = new UserDAO();
		String[][] data = null;
		String[] columnNames = {"id","Kullanýcý adi","Þifre"};
		try {
			List<User> liste = dao.getAllUsers(new User());
			data = new String[liste.size()][3];
			for(int i=0;i<liste.size();i++)
			{
				data[i][0] = ""+liste.get(i).getId();
				data[i][1] = liste.get(i).getUsername();
				data[i][2] = liste.get(i).getPassword();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		table = new JTable(data,columnNames);
		table.setEnabled(false);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		scrollPane.setColumnHeaderView(table);

		btnGncelle = new JButton("Update");
		btnGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guncelleSelected = true;
				silSelected = false;
				btnSil.setEnabled(false);
				btnGncelle.setEnabled(false);
				btnVazgec.setEnabled(true);
				btnTamam.setEnabled(true);
				table.setEnabled(true);
			}
		});
		btnGncelle.setBounds(315, 78, 89, 23);
		getContentPane().add(btnGncelle);

		btnSil = new JButton("Delete");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guncelleSelected = false;
				silSelected = true;
				btnSil.setEnabled(false);
				btnGncelle.setEnabled(false);
				btnVazgec.setEnabled(true);
				btnTamam.setEnabled(true);
				table.setEnabled(true);
			}
		});
		btnSil.setBounds(315, 112, 89, 23);
		getContentPane().add(btnSil);

		btnVazgec = new JButton("Cancel");
		btnVazgec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guncelleSelected = false;
				silSelected = false;
				btnSil.setEnabled(true);
				btnGncelle.setEnabled(true);
				btnVazgec.setEnabled(false);
				btnTamam.setEnabled(false);
				table.setEnabled(false);
			}
		});
		btnVazgec.setBounds(315, 180, 89, 23);
		getContentPane().add(btnVazgec);

		btnTamam = new JButton("OK");
		btnTamam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowNumber = table.getRowCount();
				System.out.println("table row:"+table.getRowCount()+" column:"+table.getColumnCount());
				if(guncelleSelected)
				{	
					for(int i=0;i<table.getRowCount();i++)
					{
						String str = ""+table.getValueAt(i, 0);
						try {
							dao.updateUser(""+table.getValueAt(i, 1), Integer.parseInt(str), ""+table.getValueAt(i, 2));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				if(silSelected)
				{
					int[] rows = new int[table.getRowCount()];
					rows = table.getSelectedRows();

					for(int i=0; i<rows.length; i++)
					{
						String idvalue = ""+table.getValueAt(rows[i], 0);
						try {
							dao.deleteUser(Integer.parseInt(idvalue));
						}catch (SQLException e) {
							// TODO Auto-generated catch block
						}
					}
				}

				String[][] data2 = null;

				List<User> liste2;
				try {
					liste2 = dao.getAllUsers(new User());
					data2 = new String[liste2.size()][3];

					liste2 = dao.getAllUsers(new User());
					for(int i=0;i<liste2.size();i++)
					{
						data2[i][0] = ""+liste2.get(i).getId();
						data2[i][1] = liste2.get(i).getUsername();
						data2[i][2] = liste2.get(i).getPassword();
						table.setValueAt(data2[i][0], i, 0);
						table.setValueAt(data2[i][1], i, 1);
						table.setValueAt(data2[i][2], i, 2);
					}
					
					//System.out.println("deletion: from:"+liste2.size()+" to:"+rowNumber);
					
					for(int i=liste2.size();i<rowNumber;i++)
					{
						table.setValueAt("", i, 0);
						table.setValueAt("", i, 1);
						table.setValueAt("", i, 2);
					}
					
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				guncelleSelected = false;
				silSelected = false;
				btnSil.setEnabled(true);
				btnGncelle.setEnabled(true);
				btnVazgec.setEnabled(false);
				btnTamam.setEnabled(false);
				table.setEnabled(false);
			}

		});
		btnTamam.setBounds(315, 146, 89, 23);
		getContentPane().add(btnTamam);

		btnSil.setEnabled(true);
		btnGncelle.setEnabled(true);
		btnVazgec.setEnabled(false);
		btnTamam.setEnabled(false);
		table.setEnabled(false);
	}
}
