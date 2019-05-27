import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField dbField;
	private JTextField loginField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		setTitle("Авторизация");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Авторизация");
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 20, 458, 30);
		contentPane.add(titleLabel);
		
		JLabel dbLabel = new JLabel("Название базы данных");
		dbLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		dbLabel.setBounds(40, 75, 200, 30);
		contentPane.add(dbLabel);
		
		dbField = new JTextField();
		dbField.setFont(new Font("Verdana", Font.PLAIN, 14));
		dbField.setBounds(240, 75, 200, 30);
		contentPane.add(dbField);
		dbField.setColumns(10);
		
		JLabel loginLabel = new JLabel("Логин");
		loginLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		loginLabel.setBounds(40, 145, 200, 30);
		contentPane.add(loginLabel);
		
		loginField = new JTextField();
		loginField.setFont(new Font("Verdana", Font.PLAIN, 14));
		loginField.setColumns(10);
		loginField.setBounds(240, 145, 200, 30);
		contentPane.add(loginField);
		
		JLabel passwordLabel = new JLabel("Пароль");
		passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		passwordLabel.setBounds(40, 215, 200, 30);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 14));
		passwordField.setBounds(240, 215, 200, 30);
		contentPane.add(passwordField);
		
		JButton loginButton = new JButton("Подключиться");
		loginButton.setBounds(171, 280, 150, 40);
		loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
        		Test.login(dbField.getText(), loginField.getText(), new String(passwordField.getPassword()));
        		}catch(SQLException ex) {
        			JOptionPane.showMessageDialog(null, "Неверный логин или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
        		}
        		Open openDB = new Open();
				openDB.setVisible(true);
        	}
        });
		contentPane.add(loginButton);
	}
}
