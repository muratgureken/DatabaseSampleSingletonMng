package com.mgureken.db.manager.ui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.mgureken.db.manager.conmng.ConnectionManager;
import com.mgureken.db.manager.conmng.UserDAO;
import com.mgureken.db.manager.model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class GirisFrame extends JFrame{
	private JTextField txtkullaniciAdi;
	private JTextField txtSifre;
	public GirisFrame() {
		initialize();
	}
	
	private void initialize()
	{
		setTitle("User Database Entrance");
		setBounds(100,100,300,350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		txtkullaniciAdi = new JTextField();
		txtkullaniciAdi.setBounds(163, 38, 86, 20);
		getContentPane().add(txtkullaniciAdi);
		txtkullaniciAdi.setColumns(10);
		
		txtSifre = new JTextField();
		txtSifre.setBounds(163, 69, 86, 20);
		getContentPane().add(txtSifre);
		txtSifre.setColumns(10);
		
		JLabel lblkullaniciadi = new JLabel("Username");
		lblkullaniciadi.setBounds(52, 41, 75, 14);
		getContentPane().add(lblkullaniciadi);
		
		JLabel lblifre = new JLabel("Password");
		lblifre.setBounds(52, 72, 75, 14);
		getContentPane().add(lblifre);
		
		JButton btnIptal = new JButton("Cancel");
		btnIptal.setBounds(36, 152, 91, 23);
		getContentPane().add(btnIptal);
		
		JButton btnGiri = new JButton("Connect");
		btnGiri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				UserDAO dao = new UserDAO();
				User usr = dao.getUserForName(txtkullaniciAdi.getText());

					if(usr!=null)
					{
						System.out.println("sifre kontrol:"+txtSifre.getText()+" "+usr.getPassword());
						if(txtSifre.getText().equals(usr.getPassword()))
						{
							//yeni ekran acilir.
							MainFrame mainfr = new MainFrame();
							mainfr.setVisible(true);
							GirisFrame.this.setVisible(false);
						}
						else
						{
							JOptionPane.showMessageDialog(GirisFrame.this, "sifre hatali!...");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(GirisFrame.this, "Kullanici adi hatali!...");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(GirisFrame.this, "SQL calisirken hata oldu!...");
				}
			}
		});
		btnGiri.setBounds(163, 152, 91, 23);
		getContentPane().add(btnGiri);
	}
}
