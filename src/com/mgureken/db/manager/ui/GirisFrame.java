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
		txtkullaniciAdi.setBounds(168, 39, 86, 20);
		getContentPane().add(txtkullaniciAdi);
		txtkullaniciAdi.setColumns(10);

		txtSifre = new JTextField();
		txtSifre.setBounds(168, 70, 86, 20);
		getContentPane().add(txtSifre);
		txtSifre.setColumns(10);

		JLabel lblkullaniciadi = new JLabel("Username");
		lblkullaniciadi.setBounds(57, 42, 75, 14);
		getContentPane().add(lblkullaniciadi);

		JLabel lblifre = new JLabel("Password");
		lblifre.setBounds(57, 73, 75, 14);
		getContentPane().add(lblifre);

		JButton btnIptal = new JButton("Cancel");
		btnIptal.setBounds(41, 153, 91, 23);
		getContentPane().add(btnIptal);

		JButton btnGiri = new JButton("Connect");
		btnGiri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UserDAO dao = new UserDAO();
					dao.connectToDatabase(txtkullaniciAdi.getText());
					User usr = dao.getUserForName(txtkullaniciAdi.getText());
					if(!dao.isConnAvailable())
					{
						JOptionPane.showMessageDialog(GirisFrame.this, "No available connection...");
					}
					else
					{
						if(usr!=null)
						{
							usr.getId();

							System.out.println("sifre kontrol:"+txtSifre.getText()+" "+usr.getPassword());
							if(txtSifre.getText().equals(usr.getPassword()))
							{
								//yeni ekran acilir.
								dao.closeConnection();
								MainFrame mainfr = new MainFrame(txtkullaniciAdi.getText());
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
