package org.medusa;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.anti_captcha.AccountCreationThread;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtGmailcom;
	private JTextField textField_1;
	private JLabel lblExampleEmailPrefixintervaldomaintld;
	private JTextField textField_3;
	private JLabel lblAccountsToMake;
	private JTextField textField_2;
	private JLabel lblError;
	private JLabel lblAccountPasswords;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static boolean running = false;
	
	public static int runTimes = 0;
	
	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setTitle("Medusa's Account Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 42, 139, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEmailPrefix = new JLabel("Email Prefix");
		lblEmailPrefix.setBounds(10, 17, 114, 14);
		contentPane.add(lblEmailPrefix);
		
		JLabel lblEmailDomain = new JLabel("Email Domain");
		lblEmailDomain.setBounds(10, 73, 198, 14);
		contentPane.add(lblEmailDomain);
		
		txtGmailcom = new JTextField();
		txtGmailcom.setText("gmail.com");
		txtGmailcom.setBounds(10, 98, 139, 20);
		contentPane.add(txtGmailcom);
		txtGmailcom.setColumns(10);
		
		JLabel lblInterval = new JLabel("Start Number");
		lblInterval.setBounds(162, 17, 87, 14);
		contentPane.add(lblInterval);
		
		textField_1 = new JTextField();
		textField_1.setBounds(159, 42, 65, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_1.setText("" + Main.currentProgressive);
		
		lblExampleEmailPrefixintervaldomaintld = new JLabel("Example Email: prefix+number@domain.tld");
		lblExampleEmailPrefixintervaldomaintld.setBounds(10, 135, 424, 14);
		contentPane.add(lblExampleEmailPrefixintervaldomaintld);
		
		JButton btnStart = new JButton("Start!");
		btnStart.setBounds(10, 314, 160, 60);
		contentPane.add(btnStart);
		
		JLabel lblAnticaptchacomKey = new JLabel("Anti-Captcha.com Key");
		lblAnticaptchacomKey.setBounds(10, 195, 198, 14);
		contentPane.add(lblAnticaptchacomKey);
		
		textField_3 = new JTextField();
		textField_3.setBounds(10, 220, 214, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_3.setText(Main.antiCaptchaKey);
		
		lblAccountsToMake = new JLabel("Accounts to make");
		lblAccountsToMake.setBounds(162, 73, 191, 14);
		contentPane.add(lblAccountsToMake);
		
		textField_2 = new JTextField();
		textField_2.setText("3");
		textField_2.setBounds(163, 98, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		lblError = new JLabel("");
		lblError.setBounds(10, 282, 512, 14);
		contentPane.add(lblError);
		
		lblAccountPasswords = new JLabel("Account Passwords");
		lblAccountPasswords.setBounds(276, 17, 198, 14);
		contentPane.add(lblAccountPasswords);
		
		textField_4 = new JTextField();
		textField_4.setBounds(258, 42, 216, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblRememberToRun = new JLabel("Remember to start using cli");
		lblRememberToRun.setBounds(276, 160, 258, 14);
		contentPane.add(lblRememberToRun);
		
		JLabel lblJavajarPathtofilejar = new JLabel("java -jar path/to/file.jar");
		lblJavajarPathtofilejar.setBounds(276, 185, 246, 14);
		contentPane.add(lblJavajarPathtofilejar);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textField.getText().isEmpty()) {
					lblError.setText("Email Prefix can't be null!");
					return;
				}
				
				if (txtGmailcom.getText().isEmpty()) {
					lblError.setText("Email Domain can't be null!");
					return;
				}
				
				if (textField_3.getText().isEmpty()) {
					lblError.setText("API Key can't be null!");
					return;
				}
				
				if (textField_2.getText().isEmpty()) {
					lblError.setText("You have to make at least 1 account!");
					return;
				}
				
				if (textField_1.getText().isEmpty()) {
					lblError.setText("You need at least 0 progressive start!");
					return;
				}
				
				if (textField_4.getText().isEmpty()) {
					lblError.setText("You need a password!");
					return;
				}
				
				if (running) {
					lblError.setText("Already running. Please wait for it to finish :)");
					return;
				}
				
				runTimes++;
				
				Main.currentNumber = 0;
				Main.completeNumber = 0;
				Main.currentNumber = 0;
				Main.emailPrefix = textField.getText();
				Main.emailDomain = txtGmailcom.getText();
				Main.antiCaptchaKey = textField_3.getText();
				Main.passwd = textField_4.getText();
				Main.accountsWanted = Integer.parseInt(textField_2.getText());
				Main.currentProgressive = Integer.parseInt(textField_1.getText());
				Main.started = true;
				Main.accountsCreated = 0;
				
				running = true;
				
				for(int i=0; i<Main.accountsWanted; i++){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					 System.out.println("Account thread started (" + i + ")");
					 (new AccountCreationThread()).start();
		         }
				lblError.setText("Account file: " + Main.logFile + " - Run times: " + runTimes);
			}
		});
	}
}
