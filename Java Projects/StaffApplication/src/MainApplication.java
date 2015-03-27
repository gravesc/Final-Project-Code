import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JComboBox;

import java.awt.GridLayout;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class MainApplication extends JFrame {

	private final JPanel tabWelcome = new JPanel();
	private JTextField textField;
	private static JTable tblInbox;
	private static JTable tblSaved;
	private static JTable tblSent;
	private static JTable tblCalendar;
	private static String staffID;
	private static String staffUsername, staffJobTitle, subject, message;
	private final JComboBox<String> cmbTable;

	private static final String CalendarServerURL = "http://localhost:8888/General_Servers/CalendarServer";
	private static final String HousesServerURL = "http://localhost:8888/General_Servers/HousesServer";
	private static final String MessagesServerURL = "http://localhost:8888/General_Servers/MessagingServer";
	private static final String ClientServerURL = "http://localhost:8888/Client_Servers/ClientLoginServer";
	private static final String StaffServerURL = "http://localhost:8888/Staff_Servers/StaffServer";
	private static final String LandlordServerURL = "http://localhost:8888/landlord_Servers/LandlordServer";
	private static final String MaintanenceServerURL = "http://localhost:8888/landlord_Servers/MaintanenceServer";
	
	private static final String calandar_column_names[] = { "ID", "Name",
			"Type", "Description", "Address", "Date and Time", "Important?" };
	private static final String message_column_names[] = { "ID", "To",
			"Subject", "Message", "Date and Time" };
	private static final String houses_column_names[] = { "House ID",
		"Address", "Type", "Status", "Landlord", "Monthly Price" };
	private static final String clients_column_names[] = { "Person ID",
		"Firstname", "Surname", "Address", "Martial Status", "No. of Children"};
	private static final String staff_column_names[] = { "Person ID",
		"Firstname", "Surname", "Address", "Job Title", "Salary", "Manager ID" };
	private static final String landlords_column_names[] = { "Person ID",
		"Firstname", "Surname", "Address", "Home Number", "Mobile Number", "Email" };
	private static final String inbox_column_names[] = { "ID", "From",
			"Subject", "Message", "Date and Time" };
	private static final String maintanence_column_names[] = { "ID", "House ID",
		"Description", "Severity", "Date Occurred", "Date Completed" };
	private static final String start_table_names[] = {"No Table Selected"};
	private JPanel contentPane;
	private JTextField txtValue;
	private JButton btnDelete;
	private static JTable tblMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication frame = new MainApplication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public MainApplication() throws Exception {
		setTitle("Letting Agency - Staff Application");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane ClientApplication = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(ClientApplication, BorderLayout.CENTER);
		SimpleAttributeSet attri = new SimpleAttributeSet();
		StyleConstants.setAlignment(attri, StyleConstants.ALIGN_CENTER);
		ClientApplication.addTab("Welcome", null, tabWelcome, null);
		tabWelcome.setLayout(new BorderLayout(0, 0));

		JPanel pnlLogInNorth = new JPanel();
		tabWelcome.add(pnlLogInNorth, BorderLayout.NORTH);
		pnlLogInNorth.setLayout(new BorderLayout(0, 0));

		JPanel panel_10 = new JPanel();
		pnlLogInNorth.add(panel_10, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel(
				"<html><u>Lettings Agency Staff Application</u></html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_10.add(lblNewLabel);

		JPanel panel_11 = new JPanel();
		pnlLogInNorth.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(new BorderLayout(0, 0));

		JLabel txtrWelcomeInfo = new JLabel();
		txtrWelcomeInfo.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(txtrWelcomeInfo, BorderLayout.CENTER);
		txtrWelcomeInfo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrWelcomeInfo
				.setText("<html><center>Hello and thank you for chosing this "
						+ "application to help you with all your business needs. "
						+ "Simply use the tabs above to find alter your database and "
						+ "send messages to other members of staff as well as clients. +"
						+ "Yoy can also monitor activities in your calandar.<br><br>Before you use these features, you will need to first log in to your existing account. If you do not have an account, consult your manager. If you need help, use the 'Help' tab to look up how to perform tasks.</center></html>");

		JPanel pnlLogInCenter = new JPanel();
		tabWelcome.add(pnlLogInCenter, BorderLayout.CENTER);
		pnlLogInCenter.setLayout(new BorderLayout(0, 0));

		JPanel panel_13 = new JPanel();
		pnlLogInCenter.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Username : ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		textField = new JTextField();
		textField.setDropMode(DropMode.USE_SELECTION);
		textField.setColumns(10);

		DefaultTableModel houses_table_model = new DefaultTableModel(
				houses_column_names, 3);
		
		JPanel pnlMain = new JPanel();
		ClientApplication.addTab("Main ", null, pnlMain, null);
		pnlMain.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlMainNorth = new JPanel();
		pnlMain.add(pnlMainNorth, BorderLayout.NORTH);
		
		JLabel lblMainTable = new JLabel("Main Table");
		lblMainTable.setFont(new Font("Tahoma", Font.BOLD, 23));
		pnlMainNorth.add(lblMainTable);
		
		JPanel panel_8 = new JPanel();
		pnlMain.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_8.add(scrollPane);
		
		
		DefaultTableModel start = new DefaultTableModel(start_table_names,0);
		
		tblMain = new JTable(start);
		scrollPane.setViewportView(tblMain);
		
		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12);
		panel_12.setLayout(null);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSearch.setBounds(10, 11, 364, 14);
		panel_12.add(lblSearch);
		
		JLabel lblNewLabel_2 = new JLabel("Table : ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 56, 106, 14);
		panel_12.add(lblNewLabel_2);
		
		txtValue = new JTextField();
		txtValue.setBounds(126, 86, 204, 20);
		panel_12.add(txtValue);
		txtValue.setColumns(10);
		
		JLabel label = new JLabel("Value : ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(10, 89, 106, 14);
		panel_12.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(150, 217, 156, 23);
		panel_12.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openAdd();
			}
			
		});
		
		JButton btnModify = new JButton("Modify");
		btnModify.setBounds(150, 251, 156, 23);
		panel_12.add(btnModify);
		
		btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblMain.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String id = tblMain.getValueAt(row, 0).toString();
					openModify(id);
				}
			}
			
		});
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(150, 285, 156, 23);
		panel_12.add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row=tblMain.getSelectedRow();
				if (row==-1)
				{
					showErrorMessage();
				}
				else
				{
					String currentID = tblMain.getValueAt(row, 0).toString();
					try {
						deleteFromDatabase(currentID);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		cmbTable = new JComboBox<String>();
		cmbTable.setBounds(126, 55, 204, 20);
		addComboItems(cmbTable);
		panel_12.add(cmbTable);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(178, 117, 89, 23);
		panel_12.add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filterTable(txtValue.getText());
			}
			
		});
		
		cmbTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					setData(cmbTable.getSelectedItem().toString());
					txtValue.setText("");
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		});

		JTabbedPane tabMessages = new JTabbedPane(JTabbedPane.TOP);
		ClientApplication.addTab("Messages", null, tabMessages, null);

		JPanel pnlMessageInbox = new JPanel();
		tabMessages.addTab("Inbox", null, pnlMessageInbox, null);
		pnlMessageInbox.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		pnlMessageInbox.add(scrollPane_1, BorderLayout.CENTER);

		DefaultTableModel inbox_table_model = new DefaultTableModel(
				inbox_column_names, 3);

		DefaultTableModel sent_table_model = new DefaultTableModel(
				message_column_names, 3);

		tblInbox = new JTable(inbox_table_model);
		scrollPane_1.setViewportView(tblInbox);

		getInboxData(MessagesServerURL + "?method=get&function=inbox&username="
				+ staffUsername);

		JPanel panel_3 = new JPanel();
		pnlMessageInbox.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnReadMessage = new JButton("Read Message");
		panel_3.add(btnReadMessage);
		
		btnReadMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblInbox.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String from = tblInbox.getValueAt(row, 1).toString();
					String subject = tblInbox.getValueAt(row, 2).toString();
					String message = tblInbox.getValueAt(row, 3).toString();
					ReadMessage.setValues(staffUsername, from, subject, message);
					ReadMessage readMessage = new ReadMessage();
					readMessage.setVisible(true);
				}
			}
			
		});

		JButton btnNewMessage = new JButton("New Message");
		panel_3.add(btnNewMessage);

		btnNewMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewMessage.setClientUsername(staffUsername);
				
				NewMessage.setFieldValues("Enter Subject Here","Enter Message Here");
				NewMessage message = new NewMessage();
				message.setVisible(true);
			}
		});

		JButton btnForwardMessage = new JButton("Forward Message");
		panel_3.add(btnForwardMessage);

		btnForwardMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblInbox.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String subject = tblInbox.getValueAt(row, 2).toString();
					String message = tblInbox.getValueAt(row, 3).toString();
	
					NewMessage.setClientUsername(staffUsername);
					NewMessage.setFieldValues(subject,message);
					NewMessage forwardMessage = new NewMessage();
					
					
					forwardMessage.setVisible(true);
				}
			}
		});

		JButton btnSaveMessage = new JButton("Save Message");
		panel_3.add(btnSaveMessage);

		btnSaveMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = tblInbox.getSelectedRow();
				if(index == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String messageID = tblInbox.getValueAt(index, 0).toString();
	
					String url = MessagesServerURL
							+ "?method=post&function=saved&messageID=" + messageID
							+ "&personID=" + staffID;
	
					HttpClient httpclient = new DefaultHttpClient();
					// Prepare a request object
					HttpGet httpget = new HttpGet(url);
					// Execute the request
					HttpResponse response;
					try {
						response = httpclient.execute(httpget);
						getSavedData(MessagesServerURL
								+ "?method=get&function=saved&personID="
								+ staffID);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}

		});

		JButton btnRefreshMessages = new JButton("Refresh Messages");
		btnRefreshMessages.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					getInboxData(MessagesServerURL + "?method=get&function=inbox&username="
							+ staffUsername);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		panel_3.add(btnRefreshMessages);
		
		JPanel panel_7 = new JPanel();
		pnlMessageInbox.add(panel_7, BorderLayout.NORTH);

		JLabel lblinbox = new JLabel("<html><u>Inbox</u></html>");
		lblinbox.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_7.add(lblinbox);

		JPanel pnlMessageSaved = new JPanel();
		tabMessages.addTab("Saved", null, pnlMessageSaved, null);
		pnlMessageSaved.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane((Component) null);
		pnlMessageSaved.add(scrollPane_2, BorderLayout.CENTER);

		tblSaved = new JTable(inbox_table_model);
		scrollPane_2.setViewportView(tblSaved);

		getSavedData(MessagesServerURL + "?method=get&function=saved&personID="
				+ staffID);

		JPanel panel_4 = new JPanel();
		pnlMessageSaved.add(panel_4, BorderLayout.SOUTH);
		
		JButton btnReadMessage2 = new JButton("Read Message");
		btnReadMessage2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = tblSaved.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String from = tblSaved.getValueAt(row, 1).toString();
					String subject = tblSaved.getValueAt(row, 2).toString();
					String message = tblSaved.getValueAt(row, 3).toString();
					ReadMessage.setValues(staffUsername, from, subject, message);
					ReadMessage readMessage = new ReadMessage();
					readMessage.setVisible(true);
				}
			}
		});
		panel_4.add(btnReadMessage2);

		JButton button_1 = new JButton("Forward Message");
		panel_4.add(button_1);

		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblSaved.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String subject = tblSaved.getValueAt(row, 2).toString();
					String message = tblSaved.getValueAt(row, 3).toString();
	
					NewMessage.setClientUsername(staffUsername);
					NewMessage.setFieldValues(subject,message);
					NewMessage forwardMessage = new NewMessage();
					
					
					forwardMessage.setVisible(true);
				}
			}
		});
		
		JButton button_2 = new JButton("Delete Message");
		panel_4.add(button_2);

		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteSavedMessage();
			}
		});

		JPanel panel_6 = new JPanel();
		pnlMessageSaved.add(panel_6, BorderLayout.NORTH);

		JLabel lblsavedMessages = new JLabel(
				"<html><u>Saved Messages</u></html>");
		lblsavedMessages.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_6.add(lblsavedMessages);

		JPanel pnlMessageSent = new JPanel();
		tabMessages.addTab("Sent", null, pnlMessageSent, null);
		pnlMessageSent.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_3 = new JScrollPane((Component) null);
		pnlMessageSent.add(scrollPane_3, BorderLayout.CENTER);

		tblSent = new JTable(sent_table_model);
		scrollPane_3.setViewportView(tblSent);

		getSentData(MessagesServerURL + "?method=get&function=sent&username="
				+ staffUsername);

		JPanel panel = new JPanel();
		pnlMessageSent.add(panel, BorderLayout.SOUTH);
		
		JButton btnReadMessage3 = new JButton("Read Message");
		panel.add(btnReadMessage3);

		btnReadMessage3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblSent.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String to = tblSent.getValueAt(row, 1).toString();
					String subject = tblSent.getValueAt(row, 2).toString();
					String message = tblSent.getValueAt(row, 3).toString();
					ReadMessage.setValues(to, staffUsername, subject, message);
					ReadMessage readMessage = new ReadMessage();
					readMessage.setVisible(true);
				}
			}
		});
		
		JButton button = new JButton("Forward Message");
		panel.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblSent.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String subject = tblSent.getValueAt(row, 2).toString();
					String message = tblSent.getValueAt(row, 3).toString();
	
					NewMessage.setClientUsername(staffUsername);
					NewMessage.setFieldValues(subject,message);
					NewMessage forwardMessage = new NewMessage();
					
					
					forwardMessage.setVisible(true);
				}
			}
		});
		
		JPanel panel_5 = new JPanel();
		pnlMessageSent.add(panel_5, BorderLayout.NORTH);

		JLabel lblNewLabel_5 = new JLabel("<html><u>Sent Messages</u></html>");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_5.add(lblNewLabel_5);

		JPanel tabCalandar = new JPanel();
		ClientApplication.addTab("Calandar", null, tabCalandar, null);
		tabCalandar.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		tabCalandar.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnViewEvent = new JButton("View Event");
		btnViewEvent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblCalendar.getSelectedRow();
				if(row == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String name = tblCalendar.getValueAt(row, 1).toString();
					String type = tblCalendar.getValueAt(row, 2).toString();
					String description = tblCalendar.getValueAt(row, 3).toString();
					String address = tblCalendar.getValueAt(row, 4).toString();
					String dateTime = tblCalendar.getValueAt(row, 5).toString();
					String important = tblCalendar.getValueAt(row, 6).toString();
					ViewTask.setValues(name, type, description, address, dateTime, important);
					ViewTask viewTask = new ViewTask();
					viewTask.setVisible(true);
				}
			}
			
		});
		panel_2.add(btnViewEvent);

		JButton btnNewButton_4 = new JButton("Add New Event");
		panel_2.add(btnNewButton_4);

		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddTask.setMethod("add");
				AddTask.setPersonID(staffID);
				AddTask otherFrame = new AddTask();
				otherFrame.setVisible(true);
			}
		});

		JButton btnNewButton_5 = new JButton("Modify Event");
		panel_2.add(btnNewButton_5);

		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int index = tblCalendar.getSelectedRow();
				if(index == -1)
				{
					showErrorMessage();
				} 
				else
				{
					String taskID = tblCalendar.getValueAt(index, 0).toString();
					AddTask.setID(taskID);
					AddTask.setMethod("modify");
					AddTask otherFrame = new AddTask();
					otherFrame.setVisible(true);
				}
			}
		});

		JButton btnNewButton_6 = new JButton("Delete Event");
		panel_2.add(btnNewButton_6);

		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteTask();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		DefaultTableModel calandar_table_model = new DefaultTableModel(
				calandar_column_names, 3);

		JScrollPane scrollPane_4 = new JScrollPane();
		panel_1.add(scrollPane_4, BorderLayout.CENTER);

		tblCalendar = new JTable(calandar_table_model);
		scrollPane_4.setViewportView(tblCalendar);

		

		JTabbedPane tabHelp = new JTabbedPane(JTabbedPane.TOP);
		ClientApplication.addTab("Help", null, tabHelp, null);

		JPanel tabMainHelp = new JPanel();
		tabMainHelp.setBackground(new Color(152, 251, 152));
		tabHelp.addTab("Main Table Help", null, tabMainHelp, null);
		tabMainHelp.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_5 = new JScrollPane();
		tabMainHelp.add(scrollPane_5, BorderLayout.CENTER);
		
		JPanel panel_9 = new JPanel();
		scrollPane_5.setViewportView(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		String houseHelp =   "USING THE MAIN TAB" +
						   "\n--------------------------------------------------------------------------------------------------------------------------------------------------------" +
						   "\nThe 'main' tab allows you to add, modify and delete records from a selection of tables. The sections below explain how to use\neach of these features." +
				           "\n" +
						   "\nAdding New Records" +
						   "\n------------------------" +
						   "\nStep 1) Select the table you wish to edit from the Table ComboBox of the 'Main' tab." +
						   "\nStep 2) Click the 'Add' button." +
						   "\nStep 3) Fill in ALL of the fields on the form that appears and click 'Submit'." +
						   "\nStep 4) Close down the 'Add' form. The new record will appear on the main table in the 'main' tab." +
						   "\n" +
						   "\nModifying Records" +
						   "\n---------------------" +
						   "\nStep 1) Select the table you wish to edit from the Table ComboBox of the 'Main' tab." +
						   "\nStep 2) Select the record you wish to modify on the table." +
						   "\nStep 3) Click the 'Modify' button." +
						   "\nStep 4) Fill in ALL of the fields on the form that appears and click 'Modify'." +
						   "\nStep 5) Close down the 'Modify' form. The modified record will appear on the main table in the 'main' tab." +
						   "\n" +
						   "\nDeleting Records" +
						   "\n-------------------" +
						   "\nStep 1) Select the table you wish to edit from the Table ComboBox of the 'Main' tab." +
						   "\nStep 2) Select the record you wish to delete on the table." +
						   "\nStep 3) Click the 'Delete' button." +
						   "\nStep 4) The record will automatically be deleted from the table.";
						   
		JTextArea txtMainHelp = new JTextArea();
		txtMainHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMainHelp.setText(houseHelp);
		txtMainHelp.setBackground(UIManager.getColor("Button.background"));
		panel_9.add(txtMainHelp, BorderLayout.CENTER);
		
		JPanel panel_14 = new JPanel();
		tabMainHelp.add(panel_14, BorderLayout.NORTH);
		
		JLabel lblHousesHelp = new JLabel("Houses Help");
		lblHousesHelp.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel_14.add(lblHousesHelp);

		JPanel tabEmailHelp = new JPanel();
		tabEmailHelp.setBackground(new Color(152, 251, 152));
		tabHelp.addTab("Email Help", null, tabEmailHelp, null);
		tabEmailHelp.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_6 = new JScrollPane();
		tabEmailHelp.add(scrollPane_6);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBackground(UIManager.getColor("Button.background"));
		scrollPane_6.setViewportView(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		String emailHelp =   "USING THE MESSAGING FUNCTION" +
				   		   "\n--------------------------------------------------------------------------------------------------------------------------------------------------------" +
				   		   "\nThere are functions included that allow you to communicate with clients and other members of staff. As well as this, you can save" +
				   		   "\nany messages and review your sent messages from the 'Messaging' tab. The sections below describe the processes to undertake to" +
				   		   "\nperform these tasks. " +
				   		   "\n" +
				   		   "\nSending Messages" +
				   		   "\n------------------------" +
				   		   "\nTHIS CAN ONLY BE DONE FROM THE INBOX SUB-TAB" +
				   		   "\n" +
				   		   "\nStep 1) Click the 'New Message' button within the 'Inbox' sub-tab. A new form will appear." +
				   		   "\nStep 2) Enter the details into the form and click the 'Send' button. Note, use the recipients username and not their ID within this" +
				   		   "\nform." +
				   		   "\nStep 3) Once confirmation has been recieved, close down the form. The new message will appear in the 'Sent' table on the" +
				   		   "\n'Sent' tab." +
				   		   "\n" +
				   		   "\nViewing Messages" +
				   		   "\n---------------------" +
				   		   "\nAVAILABLE ON ALL SUB-TABS" +
				   		   "\n" +
				   		   "\nStep 1) Select the record within the inbox, saved or sent tables that you wish to view." +
				   		   "\nStep 2) Click the 'Read Message' button. A new form will appear with the details of this record." +
				   		   "\n" +
				   		   "\nSave Messages" +
				   		   "\n-------------------" +
				   		   "\nONLY AVAILABLE FROM THE INBOX SUB-TAB" +
				   		   "\n" +
				   		   "\nStep 1) Select the record within the inbox table that you wish to save." +
				   		   "\nStep 2) Click the 'Save Message' button. The message will appear within the table in the" +
				   		   "\n'Saved' sub-tab." +
				   		   "\n" +
				   		   "\nForward Messages" +
				   		   "\n-------------------" +
				   		   "\nAVAILABLE ON ALL SUB-TABS" +
				   		   "\n" +
				   		   "\nStep 1) Select the record within the inbox table that you wish to forward." +
				   		   "\nStep 2) Click the 'Forward Message' button. A new form will appear." +
				   		   "\nStep 3) Change the 'Username' field to the relevent recipient username and click 'Send'." +
				   		   "\nStep 4) After confirmation, close down the form. The record will now appear within" + 
				   		   "\nthe 'Sent' table on the 'Sent' sub-tab." +
				   		   "\n" +
				   		   "\nDelete Messages from the Saved List" +
				   		   "\n-------------------" +
				   		   "\nONLY AVAILABLE FROM THE SENT SUB-TAB" +
				   		   "\n" +
				   		   "\nStep 1) Select the record within the sent table that you wish to delete." +
				   		   "\nStep 2) Click the 'Delete Message' button. The record will be automatically" + 
				   		   "\ndeleted from the table.";
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textArea.setText(emailHelp);
		textArea.setBackground(UIManager.getColor("Button.background"));
		panel_18.add(textArea);
		
		JPanel panel_15 = new JPanel();
		tabEmailHelp.add(panel_15, BorderLayout.NORTH);
		
		JLabel lblEmailHelp = new JLabel("Email Help");
		lblEmailHelp.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel_15.add(lblEmailHelp);

		JPanel tabCalandarHelp = new JPanel();
		tabCalandarHelp.setBackground(new Color(152, 251, 152));
		tabHelp.addTab("Calandar Help", null, tabCalandarHelp, null);
		tabCalandarHelp.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_7 = new JScrollPane();
		tabCalandarHelp.add(scrollPane_7);
		
		JPanel panel_17 = new JPanel();
		scrollPane_7.setViewportView(panel_17);
		panel_17.setLayout(new BorderLayout(0, 0));
		
		String calandarHelp =   "USING THE CALANDAR TAB" +
				   "\n--------------------------------------------------------------------------------------------------------------------------------------------------------" +
				   "\nThe 'calandar' tab allows you to store, modify and delete your own individual tasks. The sections below" + 
				   "\nexplain how to use each of these features." +
		           "\n" +
				   "\nAdding New Tasks" +
				   "\n------------------------" + 
				   "\nStep 1) Click the 'Add Task' button." +
				   "\nStep 2) Fill in ALL of the fields on the form that appears and click 'Submit'." +
				   "\nStep 3) Close down the 'Add' form. The new record will appear on the table in the 'calandar' tab." +
				   "\n" +
				   "\nModifying Tasks" +
				   "\n---------------------" +
				   "\nStep 1) Select the record you wish to modify on the calandar table." +
				   "\nStep 2) Click the 'Modify Task' button." +
				   "\nStep 3) Fill in ALL of the fields on the form that appears and click 'Modify'." +
				   "\nStep 4) Close down the 'Modify' form. The modified record will appear on the table in the 'calandar' tab." +
				   "\n" +
				   "\nDeleting Tasks" +
				   "\n-------------------" +
				   "\nStep 1) Select the record you wish to delete on the table." +
				   "\nStep 2) Click the 'Delete' button." +
				   "\nStep 3) The record will automatically be deleted from the table.";
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_17.add(textArea_1, BorderLayout.CENTER);
		textArea_1.setText(calandarHelp);
		textArea_1.setBackground(UIManager.getColor("Button.background"));
		
		JPanel panel_16 = new JPanel();
		tabCalandarHelp.add(panel_16, BorderLayout.NORTH);
		
		JLabel lblCalendarHelp = new JLabel("Calendar Help");
		lblCalendarHelp.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel_16.add(lblCalendarHelp);

		getHousesData(HousesServerURL + "?method=get&function=houses");

		getInterestedHousesData(HousesServerURL
				+ "?method=get&function=interestedHouses&personId=" + staffID);
		
		getCalendarData(CalendarServerURL + "?method=get&personID="+staffID);
	}

	private void addComboItems(JComboBox<String> combo) {
		// TODO Auto-generated method stub
		combo.addItem("Please Select Table");
		combo.addItem("Client");
		if(staffJobTitle.equalsIgnoreCase("manager"))
		{
			combo.addItem("Staff");
		}
		
		combo.addItem("Landlord");
		combo.addItem("Maintanence");
		combo.addItem("Houses");
	}

	private void filterTable(String value) {

		DefaultTableModel model = null;
		String table = cmbTable.getSelectedItem().toString();
		if (table.equals("Client"))
		{
			model = new DefaultTableModel(clients_column_names, 0);
		}
		else if (table.equals("Staff"))
		{
			model = new DefaultTableModel(staff_column_names,0);
		}
		else if (table.equals("Landlord"))
		{
			model = new DefaultTableModel(landlords_column_names,0);
		}
		else if (table.equals("Maintanence"))
		{
			model = new DefaultTableModel(maintanence_column_names,0);
		}
		else if (table.equals("Houses"))
		{
			model = new DefaultTableModel(houses_column_names,0);
		}
		
		ArrayList<String> knownIDs = new ArrayList<String>();
		for(int row=0;row<tblMain.getRowCount();row++)
		{
			for (int col=0;col<tblMain.getColumnCount();col++)
			{
				String selectedValue = tblMain.getValueAt(row, col).toString();
				String currentID = tblMain.getValueAt(row, 0).toString();
				if(selectedValue.contains(value) && !knownIDs.contains(currentID))
				{
					knownIDs.add(currentID);
					ArrayList<String> record = new ArrayList<String>();
					for(int i=0;i<tblMain.getColumnCount();i++)
					{
						record.add(tblMain.getValueAt(row, i).toString());
					}
					
					Object rowData[] = record.toArray();
					model.addRow(rowData);
				}
			}
		}
		
		tblMain.setModel(model);
	}
	
	private void openAdd()
	{
		String table = cmbTable.getSelectedItem().toString();
		if (table.equals("Client"))
		{
			NewClient.setMethod("add");
			NewClient client = new NewClient();
			client.setVisible(true);
		}
		else if (table.equals("Staff"))
		{
			NewStaff.setMethod("add");
			NewStaff staff = new NewStaff();
			staff.setVisible(true);
		}
		else if (table.equals("Landlord"))
		{
			NewLandlord.setMethod("add");
			NewLandlord landlord = new NewLandlord();
			landlord.setVisible(true);
		}
		else if (table.equals("Maintanence"))
		{
			NewMaintanence.setMethod("add");
			NewMaintanence maintanence = new NewMaintanence();
			maintanence.setVisible(true);
		}
		else if (table.equals("Houses"))
		{
			NewHouse.setMethod("add");
			NewHouse house = new NewHouse();
			house.setVisible(true);
		}
	}
	
	private void openModify(String id)
	{
		String table = cmbTable.getSelectedItem().toString();
		if (table.equals("Client"))
		{
			NewClient.setMethod("modify");
			NewClient.setID(id);
			NewClient client = new NewClient();
			client.setVisible(true);
		}
		else if (table.equals("Staff"))
		{
			NewStaff.setMethod("modify");
			NewStaff.setID(id);
			NewStaff staff = new NewStaff();
			staff.setVisible(true);
		}
		else if (table.equals("Landlord"))
		{
			NewLandlord.setMethod("modify");
			NewLandlord.setID(id);
			NewLandlord landlord = new NewLandlord();
			landlord.setVisible(true);
		}
		else if (table.equals("Maintanence"))
		{
			NewMaintanence.setMethod("modify");
			NewMaintanence.setID(id);
			NewMaintanence maintanence = new NewMaintanence();
			maintanence.setVisible(true);
		}
		else if (table.equals("Houses"))
		{
			NewHouse.setMethod("modify");
			NewHouse.setID(id);
			NewHouse house = new NewHouse();
			house.setVisible(true);
		}
	}
	
	private void deleteFromDatabase(String currentID) throws IOException, JSONException
	{
		String url = null;
		String table = cmbTable.getSelectedItem().toString();
		if (table.equals("Client"))
		{
			url = ClientServerURL+"?method=post&function=delete&personID="+currentID;
		}
		else if (table.equals("Staff"))
		{
			url = StaffServerURL+"?method=post&function=delete&personID="+currentID;
		}
		else if (table.equals("Landlord"))
		{
			url = LandlordServerURL+"?method=post&function=delete&landlordID="+currentID;
		}
		else if (table.equals("Maintanence"))
		{
			url = MaintanenceServerURL+"?method=post&function=delete&maintanenceID="+currentID;
		}
		else if (table.equals("Houses"))
		{
			url = HousesServerURL+"?method=post&function=delete&houseID="+currentID;
		}
		
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setData(table);
	}
	
	static void setData(String table) throws IOException, JSONException {
		// TODO Auto-generated method stub
		String url = null;
		String jsonArrayName = null;
		if(table.equals("Client"))
		{
			url = ClientServerURL+"?method=get&function=client";
			jsonArrayName = "clients";
			setTableData(url, jsonArrayName);
		}
		else if(table.equals("Staff"))
		{
			url = StaffServerURL+"?method=get&function=staff";
			jsonArrayName = "staff";
			setTableData(url, jsonArrayName);
		}
		else if(table.equals("Landlord"))
		{
			url = LandlordServerURL+"?method=get";
			jsonArrayName = "landlords";
			setTableData(url, jsonArrayName);
		}
		else if(table.equals("Maintanence"))
		{
			url = MaintanenceServerURL+"?method=get";
			jsonArrayName = "maintanence";
			setTableData(url, jsonArrayName);
		}
		else if(table.equals("Houses"))
		{
			url = HousesServerURL+"?method=get&function=houses";
			jsonArrayName = "houses";
			setTableData(url, jsonArrayName);
		}		
	}

	static void setTableData(String sentUrl, String jsonArrayName) throws IOException, JSONException {
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel(staff_column_names,0);
		if (jsonArrayName.equals("clients"))
		{
			model = new DefaultTableModel(clients_column_names, 0);
		}
		else if (jsonArrayName.equals("staff"))
		{
			model = new DefaultTableModel(staff_column_names,0);
		}
		else if (jsonArrayName.equals("landlords"))
		{
			model = new DefaultTableModel(landlords_column_names,0);
		}
		else if (jsonArrayName.equals("maintanence"))
		{
			model = new DefaultTableModel(maintanence_column_names,0);
		}
		else if (jsonArrayName.equals("houses"))
		{
			model = new DefaultTableModel(houses_column_names,0);
		}
		
		URLConnection conn;
		String result = "";

		URL url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();

		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray(jsonArrayName);
		
		
		ArrayList<String> data = null;
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonData = jsonArr.getJSONObject(i);
			data = new ArrayList<String>();
			for(int k=1; k<=jsonData.length();k++)
			{		
				
				data.add(jsonData.getString(k+""));
			}
			Object allData[] = data.toArray();
			model.addRow(allData);
		}
		tblMain.setModel(model);
	}
	
	
	static void getInboxData(String sentUrl) throws Exception {

		URL url;
		URLConnection conn;
		String result = "";

		url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();

		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray("messages");

		populateInboxTable(jsonArr);
	}

	static void getSavedData(String sentUrl) throws Exception {

		URL url;
		URLConnection conn;
		String result = "";

		url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();

		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray("messages");

		populateSavedTable(jsonArr);
	}

	static void getSentData(String sentUrl) throws Exception {

		URL url;
		URLConnection conn;
		String result = "";

		url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();

		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray("messages");

		populateSentTable(jsonArr);
	}

	static void getHousesData(String sentUrl) throws Exception {

		URL url;
		URLConnection conn;
		String result = "";

		url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();

		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray("houses");

		populateHousesTable(jsonArr);
	}

	static void getInterestedHousesData(String sentUrl) throws Exception {

		URL url;
		URLConnection conn;
		String result = "";

		url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();

		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray("houses");

		populateInterestedHousesTable(jsonArr);
	}

	static void getCalendarData(String sentUrl) throws Exception {

		URL url;
		URLConnection conn;
		String result = "";

		url = new URL(sentUrl);
		conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result += inputLine + "\n";
		}
		in.close();
		JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray();
		jsonArr = jsonObj.getJSONArray("tasks");

		populateCalendarTable(jsonArr);
	}

	private static void populateInboxTable(JSONArray jsonArr)
			throws JSONException {

		String tempArr[] = new String[5];
		DefaultTableModel model = new DefaultTableModel(inbox_column_names, 0);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject message = jsonArr.getJSONObject(i);
			tempArr[0] = message.getString("messageID");
			tempArr[1] = message.getString("sender");
			tempArr[2] = message.getString("subject");
			tempArr[3] = message.getString("message");
			tempArr[4] = message.getString("dateTime");

			model.addRow(tempArr);
		}
		tblInbox.setModel(model);
	}

	private static void populateSavedTable(JSONArray jsonArr)
			throws JSONException {

		String tempArr[] = new String[5];
		DefaultTableModel model = new DefaultTableModel(inbox_column_names, 0);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject message = jsonArr.getJSONObject(i);
			tempArr[0] = message.getString("messageID");
			tempArr[1] = message.getString("sender");
			tempArr[2] = message.getString("subject");
			tempArr[3] = message.getString("message");
			tempArr[4] = message.getString("dateTime");

			model.addRow(tempArr);
		}
		tblSaved.setModel(model);
	}

	private static void populateSentTable(JSONArray jsonArr)
			throws JSONException {

		String tempArr[] = new String[5];
		DefaultTableModel model = new DefaultTableModel(message_column_names, 0);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject message = jsonArr.getJSONObject(i);
			tempArr[0] = message.getString("messageID");
			tempArr[1] = message.getString("recipient");
			tempArr[2] = message.getString("subject");
			tempArr[3] = message.getString("message");
			tempArr[4] = message.getString("dateTime");

			model.addRow(tempArr);
		}
		tblSent.setModel(model);
	}

	private static void populateHousesTable(JSONArray jsonArr)
			throws JSONException {

		String tempArr[] = new String[7];
		DefaultTableModel model = new DefaultTableModel(houses_column_names, 0);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject task = jsonArr.getJSONObject(i);
			tempArr[0] = task.getString("1");
			tempArr[1] = task.getString("2");
			tempArr[2] = task.getString("3");
			tempArr[3] = task.getString("4");
			tempArr[4] = task.getString("5");
			tempArr[5] = task.getString("6");

			model.addRow(tempArr);
		}
	}

	private static void populateInterestedHousesTable(JSONArray jsonArr)
			throws JSONException {

		String tempArr[] = new String[7];
		DefaultTableModel model = new DefaultTableModel(houses_column_names, 0);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject task = jsonArr.getJSONObject(i);
			tempArr[0] = task.getString("id");
			tempArr[1] = task.getString("address");
			tempArr[2] = task.getString("type");
			tempArr[3] = task.getString("status");
			tempArr[4] = task.getString("landlord");
			tempArr[5] = task.getString("price");

			model.addRow(tempArr);
		}
	}

	private static void populateCalendarTable(JSONArray jsonArr)
			throws JSONException {

		String tempArr[] = new String[7];
		DefaultTableModel model = new DefaultTableModel(calandar_column_names,
				0);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject task = jsonArr.getJSONObject(i);
			tempArr[0] = task.getString("id");
			tempArr[1] = task.getString("name");
			tempArr[2] = task.getString("type");
			tempArr[3] = task.getString("desc");
			tempArr[4] = task.getString("address");
			tempArr[5] = task.getString("dateTime");
			tempArr[6] = task.getString("important");

			model.addRow(tempArr);
		}
		tblCalendar.setModel(model);
	}

	public void deleteSavedMessage() {
		int index = tblSaved.getSelectedRow();
		if(index == -1)
		{
			showErrorMessage();
		} 
		else
		{
			String messageID = tblSaved.getValueAt(index, 0).toString();
	
			String url = MessagesServerURL
					+ "?method=post&function=delete&messageID=" + messageID
					+ "&personID=" + staffID;
	
			HttpClient httpclient = new DefaultHttpClient();
			// Prepare a request object
			HttpGet httpget = new HttpGet(url);
			// Execute the request
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);
				getSavedData(MessagesServerURL
						+ "?method=get&function=saved&personID=" + staffID);
				// Examine the response status
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void deleteTask() {
		int index = tblCalendar.getSelectedRow();
		if(index == -1)
		{
			showErrorMessage();
		} 
		else
		{
			String taskID = tblCalendar.getValueAt(index, 0).toString();
	
			String url = CalendarServerURL + "?method=post&function=delete&taskID="
					+ taskID + "&personID="+staffID;
	
			HttpClient httpclient = new DefaultHttpClient();
			// Prepare a request object
			HttpGet httpget = new HttpGet(url);
			// Execute the request
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);
				getCalendarData(CalendarServerURL + "?method=get&personID="+staffID);
				// Examine the response status
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setCurrentUserID(String currentLoggedID) {
		staffID = currentLoggedID;
	}

	public static void setCurrentUsername(String currentUsername) {
		staffUsername = currentUsername;
	}
	
	public static String getSubject()
	{
		return subject;
	}
	
	public static String getMessage()
	{
		return message;
	}
	
	public void setSubject(String subject)
	{
		MainApplication.subject = subject; 
	}
	
	public void setMessage(String message)
	{
		MainApplication.message = message;
	}
	
	public static void setCurrentJobTitle(String currentJobTitle) {
		// TODO Auto-generated method stub
		staffJobTitle = currentJobTitle;
	}
	
	private void showErrorMessage()
	{
		JOptionPane.showMessageDialog(
				new JFrame(),
				"Please select a record from the table before performing this task.",
				"No Record Selected",
				JOptionPane.ERROR_MESSAGE);
	}
}
