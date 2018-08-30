package LatestWork;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestingTool {

	private JFrame frame;
	private JTextField txtURL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingTool window = new TestingTool();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestingTool() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 890, 479);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblURL = new JLabel("URL");
		lblURL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblURL.setBounds(90, 151, 64, 43);
		frame.getContentPane().add(lblURL);
		
		txtURL = new JTextField();
		txtURL.setBounds(209, 141, 575, 43);
		frame.getContentPane().add(txtURL);
		txtURL.setColumns(10);
		
		JButton txtButton = new JButton("LOGIN");
		txtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 String URL;
				 String UserName, Password;
				 try{
						URL=txtURL.getText();
						//UserName=txtUserName.getText();
						//Password=passwordField.getText();
						BrowserOpen.launchApp(URL);
						
					}catch(Exception e)
					{
						
						JOptionPane.showMessageDialog(null,e);
					
					}
			}
		});
		txtButton.setBounds(411, 265, 153, 50);
		frame.getContentPane().add(txtButton);
	}
}
