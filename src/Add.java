import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Add extends JFrame {

	private JPanel contentPane;
	private JTextField idField;
	private JTextField nameField;
	private JTextField placeField;
	private JTextField commField;
	protected static Vector data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add frame = new Add();
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
	public Add() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Object[] columnsHeader = Test.getHeaders().toArray();
		
		JLabel id = new JLabel(columnsHeader[0].toString());
		id.setFont(new Font("Verdana", Font.PLAIN, 14));
		id.setBounds(40, 100, 200, 30);
		contentPane.add(id);
		
		JLabel name = new JLabel(columnsHeader[1].toString());
		name.setFont(new Font("Verdana", Font.PLAIN, 14));
		name.setBounds(40, 180, 200, 30);
		contentPane.add(name);
		
		JLabel place = new JLabel(columnsHeader[2].toString());
		place.setFont(new Font("Verdana", Font.PLAIN, 14));
		place.setBounds(40, 260, 200, 30);
		contentPane.add(place);
		
		JLabel comm = new JLabel(columnsHeader[3].toString());
		comm.setFont(new Font("Verdana", Font.PLAIN, 14));
		comm.setBounds(40, 340, 200, 30);
		contentPane.add(comm);
		
		idField = new JTextField();
		idField.setFont(new Font("Verdana", Font.PLAIN, 14));
		idField.setBounds(250, 100, 200, 30);
		contentPane.add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Verdana", Font.PLAIN, 14));
		nameField.setColumns(10);
		nameField.setBounds(250, 180, 200, 30);
		contentPane.add(nameField);
		
		placeField = new JTextField();
		placeField.setFont(new Font("Verdana", Font.PLAIN, 14));
		placeField.setColumns(10);
		placeField.setBounds(250, 260, 200, 30);
		contentPane.add(placeField);
		
		commField = new JTextField();
		commField.setFont(new Font("Verdana", Font.PLAIN, 14));
		commField.setColumns(10);
		commField.setBounds(250, 340, 200, 30);
		contentPane.add(commField);
		
		
		JButton add = new JButton("Добавить");
		add.setFont(new Font("Verdana", Font.PLAIN, 14));
		add.setBounds(150, 450, 200, 30);
		add.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
				try {
					Test.add(Integer.parseInt(idField.getText()), nameField.getText(), placeField.getText(), Integer.parseInt(commField.getText()));
					Open window = new Open();
					window.setVisible(true);
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Объект с данным индентификатором уже существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
        	}
        });
		contentPane.add(add);
		
		JLabel label = new JLabel("Добавить поле");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Verdana", Font.BOLD, 16));
		label.setBounds(150, 35, 200, 30);
		contentPane.add(label);
	}
}
