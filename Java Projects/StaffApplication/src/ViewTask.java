import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.UIManager;
import java.awt.SystemColor;


public class ViewTask extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtType;
	public static String taskName;
	public static String type;
	public static String description;
	public static String address;
	public static String dateTime;
	public static String important;
	private JTextField txtDateTime;
	private JTextField txtImportant;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReadMessage frame = new ReadMessage();
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
	public ViewTask() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblReadMessage = new JLabel("View Event");
		lblReadMessage.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel.add(lblReadMessage);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblName = new JLabel("Name :");
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setBounds(10, 11, 116, 14);
		panel_1.add(lblName);
		
		JLabel lblType = new JLabel("Type :");
		lblType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblType.setBounds(10, 35, 116, 14);
		panel_1.add(lblType);
		
		JLabel lblDesc = new JLabel("Description :");
		lblDesc.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDesc.setBounds(10, 60, 116, 14);
		panel_1.add(lblDesc);
		
		txtName = new JTextField();
		txtName.setBackground(UIManager.getColor("Button.background"));
		txtName.setEditable(false);
		txtName.setColumns(10);
		txtName.setBounds(136, 8, 278, 20);
		txtName.setText(taskName);
		panel_1.add(txtName);
		
		
		txtType = new JTextField();
		txtType.setBackground(UIManager.getColor("Button.background"));
		txtType.setEditable(false);
		txtType.setColumns(10);
		txtType.setBounds(136, 32, 278, 20);
		txtType.setText(type);
		panel_1.add(txtType);
		
		Border txtBorder = BorderFactory.createLineBorder(Color.BLACK);
		
		JTextArea txtDesc = new JTextArea();
		txtDesc.setBackground(UIManager.getColor("Button.background"));
		txtDesc.setEditable(false);
		txtDesc.setBounds(136, 60, 278, 58);
		txtDesc.setBorder(BorderFactory.createCompoundBorder(txtBorder, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		txtDesc.setText(description);
		panel_1.add(txtDesc);
		
		JLabel lblAddress = new JLabel("Address :");
		lblAddress.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAddress.setBounds(10, 129, 116, 14);
		panel_1.add(lblAddress);
		
		JTextArea txtAddress = new JTextArea();
		txtAddress.setText((String) null);
		txtAddress.setEditable(false);
		txtAddress.setBackground(SystemColor.menu);
		txtAddress.setBorder(BorderFactory.createCompoundBorder(txtBorder, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		txtAddress.setText(address);
		txtAddress.setBounds(136, 129, 278, 58);
		panel_1.add(txtAddress);
		
		JLabel lblDateAndTime = new JLabel("Date and Time :");
		lblDateAndTime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDateAndTime.setBounds(10, 197, 116, 14);
		panel_1.add(lblDateAndTime);
		
		txtDateTime = new JTextField();
		txtDateTime.setText((String) null);
		txtDateTime.setEditable(false);
		txtDateTime.setColumns(10);
		txtDateTime.setBackground(SystemColor.menu);
		txtDateTime.setBounds(136, 194, 278, 20);
		txtDateTime.setText(dateTime);
		panel_1.add(txtDateTime);
		
		JLabel lblImportant = new JLabel("Important :");
		lblImportant.setHorizontalAlignment(SwingConstants.TRAILING);
		lblImportant.setBounds(10, 225, 116, 14);
		panel_1.add(lblImportant);
		
		txtImportant = new JTextField();
		txtImportant.setText((String) null);
		txtImportant.setEditable(false);
		txtImportant.setColumns(10);
		txtImportant.setBackground(SystemColor.menu);
		txtImportant.setBounds(136, 222, 278, 20);
		txtImportant.setText(important);
		panel_1.add(txtImportant);
	}
	
	static void setValues(String taskName, String type, String description, String address, String dateTime, String important)
	{
		ViewTask.taskName = taskName;
		ViewTask.type = type;
		ViewTask.description = description;
		ViewTask.address = address;
		ViewTask.dateTime = dateTime;
		ViewTask.important = important;
	}
}
