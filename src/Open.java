import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Open extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField searchValue;
	private JScrollPane scrollPane;
	private JTextField newValue;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton deleteDbButton;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Open() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 715);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 110, 620, 400);
		contentPane.add(scrollPane);
		
		final Object[] columnsHeader = Test.getHeaders().toArray();
		Object[][] data = Test.getInfo();
		
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(columnsHeader);
		
		for (int i = 0; i < data.length; i++)
            tableModel.addRow(data[i]);
		
		table = new JTable(tableModel);
		table.setFont(new Font("Verdana", Font.PLAIN, 14));
		table.setRowHeight(50);
		scrollPane.setViewportView(table);
		
		
		JLabel tableName = new JLabel("New label");
		tableName.setFont(new Font("Verdana", Font.PLAIN, 18));
		tableName.setBounds(20, 13, 410, 35);
		contentPane.add(tableName);
		
		Object[] fields = Arrays.copyOfRange(columnsHeader, 1, columnsHeader.length);
		
		JButton backButton = new JButton("Назад");
		backButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		backButton.setBounds(440, 21, 200, 30);
		contentPane.add(backButton);
		
		final JComboBox comboBox = new JComboBox(fields);
		comboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
		comboBox.setBounds(20, 60, 200, 30);
		contentPane.add(comboBox);
		
		searchValue = new JTextField();
		searchValue.setFont(new Font("Verdana", Font.PLAIN, 14));
		searchValue.setBounds(230, 60, 200, 30);
		PlainDocument doc = (PlainDocument) searchValue.getDocument();
		doc.setDocumentFilter(new TextFilter());
		contentPane.add(searchValue);
		
		JButton searchButton = new JButton("Поиск");
		searchButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		searchButton.setBounds(440, 60, 200, 30);
		searchButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Object[][] founded;
				try {
					founded = Test.search(comboBox.getSelectedItem().toString(), searchValue.getText());
	        		JTable foundedTable = new JTable(founded, columnsHeader);
	        		foundedTable.setFont(new Font("Verdana", Font.PLAIN, 14));
	        		foundedTable.setRowHeight(50);
	        		scrollPane.setViewportView(foundedTable);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Объект не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
        	}
        });
		contentPane.add(searchButton);
		
		JButton addButton = new JButton("Добавить");
		addButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add window = new Add();
				window.setVisible(true);
				dispose();
			}
		});
		addButton.setBounds(20, 533, 200, 30);
		contentPane.add(addButton);
		
	
		newValue = new JTextField();
		newValue.setFont(new Font("Verdana", Font.PLAIN, 14));
		newValue.setEnabled(false);
		newValue.setBounds(230, 576, 200, 30);
		PlainDocument newDoc = (PlainDocument) newValue.getDocument();
		newDoc.setDocumentFilter(new TextFilter());
		
		JLabel updateLabel = new JLabel("Редактировать");
		updateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		updateLabel.setBounds(230, 533, 200, 30);
		contentPane.add(updateLabel);
		contentPane.add(newValue);
		
		saveButton = new JButton("Сохранить");
		saveButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		saveButton.setEnabled(false);
		saveButton.setBounds(230, 619, 200, 30);
		contentPane.add(saveButton);
		
		deleteButton = new JButton("Удалить");
		deleteButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		deleteButton.setBounds(440, 533, 200, 30);
		contentPane.add(deleteButton);
		
		ListSelectionModel selModel = table.getSelectionModel();
		
        selModel.addListSelectionListener(new ListSelectionListener() {               
              public void valueChanged(ListSelectionEvent e) {
                   
                   
                   if(table.getSelectedRowCount()==1) {
                	   newValue.setEnabled(true);
                	   saveButton.setEnabled(true);
                	   
                	   saveButton.addActionListener(new ActionListener() {
                		   public void actionPerformed(ActionEvent arg0) {
                			   try {
                			   int selectedRow = table.getSelectedRow();
                               int selectedColumn = table.getSelectedColumn();
                               
                               String field = columnsHeader[selectedColumn].toString();
                			   int id = (int) tableModel.getValueAt(selectedRow, 0);
                			   
                			   String value = newValue.getText();
                			   
								Test.textUpdate(field, value, id);
								tableModel.setValueAt(value, selectedRow, selectedColumn);
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, "У вас нет прав", "Ошибка", JOptionPane.ERROR_MESSAGE);
							}

                		   }
                	   });
                	   
                	   deleteButton.addActionListener(new ActionListener() {
                		   public void actionPerformed(ActionEvent arg0) {
                			   int selectedRow = table.getSelectedRow();
                			   int id = (int) tableModel.getValueAt(selectedRow, 0);
                			   
                			   try {
								Test.deleteRow(id);
								tableModel.removeRow(selectedRow);
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, "У вас нет прав", "Ошибка", JOptionPane.ERROR_MESSAGE);
							}
                			   
                		   }
                	   });
                   }                  
              }               
         });
		

		
		JButton clearButton = new JButton("Очистить таблицу");
		clearButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		clearButton.setBounds(20, 576, 200, 30);
		clearButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
					Test.clearTable();
					
	    			int count = tableModel.getRowCount();
	    			int i=0;
	    			while (count>0) {
	    				tableModel.removeRow(i);
	    				count--;
	    			}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "У вас нет прав", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}

        	}
        });
		contentPane.add(clearButton);
		
		deleteDbButton = new JButton("Удалить базу данных");
		deleteDbButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		deleteDbButton.setBounds(440, 576, 200, 30);
		deleteDbButton.addActionListener(new ActionListener() {
 		   public void actionPerformed(ActionEvent arg0) { 			   
 			   try {
				Test.deleteDb();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "У вас нет прав", "Ошибка", JOptionPane.ERROR_MESSAGE);
			}
 			   dispose();
 			   JOptionPane.showMessageDialog(null, "База данных удалена", "Всё", JOptionPane.INFORMATION_MESSAGE);
 		   }
 	   });
		contentPane.add(deleteDbButton);
		

	}
}
