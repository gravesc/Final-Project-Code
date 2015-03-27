import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class MainApplication extends JFrame {

	private final JPanel tabWelcome = new JPanel();
	private JTextField textField;
	private static JTable tblHouses;
	private JTextField txtMinimum;
	private JTextField txtValueField;
	private JTextField txtMaximum;
	private static JTable tblInbox;
	private static JTable tblSaved;
	private static JTable tblSent;
	private static JTable tblCalendar;
	private static String clientID;
	private static String clientUsername, subject, message;

	private static final String CalendarServerURL = "http://localhost:8888/General_Servers/CalendarServer";
	private static final String HousesServerURL = "http://localhost:8888/General_Servers/HousesServer";
	private static final String MessagesServerURL = "http://localhost:8888/General_Servers/MessagingServer";

	private static final String calandar_column_names[] = { "ID", "Name",
			"Type", "Description", "Address", "Date and Time", "Important?" };
	private static final String message_column_names[] = { "ID", "To",
			"Subject", "Message", "Date and Time" };
	private static final String houses_column_names[] = { "House ID",
			"Address", "Type", "Status", "Landlord", "Monthly Price" };
	private static final String inbox_column_names[] = { "ID", "From",
			"Subject", "Message", "Date and Time" };
	private JPanel contentPane;
	private static JTable tblInterested;

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
		setTitle("Lettings Agency - Client Application");
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
				"<html><u>Lettings Agency Client Application</u></html>");
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
						+ "application to help you with all your house letting needs. "
						+ "Simply use the tabs above to find houses and monitor their price and status', "
						+ "send messages to other clients as well as members of staff who will be happy to "
						+ "help and add monitor activities in your calandar.<br><br>Before you use these features, you will need to first log in to your existing account. If you do not have an account, you can simply create one by clicking the 'Create New Account' button<br><br>If you need any help, "
						+ "click the help tab above.</center></html>");

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

		JTabbedPane tabHouses = new JTabbedPane(JTabbedPane.TOP);
		ClientApplication.addTab("Houses", null, tabHouses, null);

		JPanel pnlHouses = new JPanel();
		tabHouses.addTab("All Houses", null, pnlHouses, null);
		pnlHouses.setLayout(null);

		tblHouses = new JTable(houses_table_model);
		tblHouses.setBounds(10, 11, 363, 412);
		JScrollPane scrollPane = new JScrollPane(tblHouses);
		scrollPane.setBounds(10, 11, 452, 412);
		pnlHouses.add(scrollPane);

		JLabel lblSearch = new JLabel("<html><u>Search</u><html>");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSearch.setBounds(497, 15, 248, 28);
		pnlHouses.add(lblSearch);

		JLabel lblNewLabel_4 = new JLabel("Attribute");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(498, 54, 247, 14);
		pnlHouses.add(lblNewLabel_4);

		txtMinimum = new JTextField();
		txtMinimum.setColumns(10);
		txtMinimum.setBounds(509, 137, 78, 20);
		pnlHouses.add(txtMinimum);

		txtValueField = new JTextField();
		txtValueField.setColumns(10);
		txtValueField.setBounds(497, 137, 247, 20);
		pnlHouses.add(txtValueField);

		txtMaximum = new JTextField();
		txtMaximum.setColumns(10);
		txtMaximum.setBounds(646, 137, 78, 20);
		pnlHouses.add(txtMaximum);

		final JLabel lblMin = new JLabel("Min : \u00A3");
		lblMin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMin.setBounds(529, 112, 45, 14);
		pnlHouses.add(lblMin);

		final JLabel lblMax = new JLabel("Max : \u00A3");
		lblMax.setHorizontalAlignment(SwingConstants.CENTER);
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMax.setBounds(657, 112, 56, 14);
		pnlHouses.add(lblMax);

		JButton btnResetData = new JButton("Reset");
		btnResetData.setBounds(579, 300, 89, 23);
		pnlHouses.add(btnResetData);

		btnResetData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					getHousesData(HousesServerURL
							+ "?method=get&function=houses");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnAddToInterested = new JButton(
				"Add to Interested Houses List");
		btnAddToInterested.setBounds(498, 346, 247, 23);
		pnlHouses.add(btnAddToInterested);

		final JComboBox<String> cmbAttributeSelect = new JComboBox<String>();
		cmbAttributeSelect.setBounds(497, 79, 248, 20);
		pnlHouses.add(cmbAttributeSelect);

		cmbAttributeSelect.addItem("ID");
		cmbAttributeSelect.addItem("Address");
		cmbAttributeSelect.addItem("Type");
		cmbAttributeSelect.addItem("Status");
		cmbAttributeSelect.addItem("Landlord");
		cmbAttributeSelect.addItem("Monthly Price");

		final JLabel lblValue = new JLabel("Value");
		lblValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblValue.setBounds(584, 112, 63, 14);
		pnlHouses.add(lblValue);

		JButton btnSubmitHouse = new JButton("Submit");
		btnSubmitHouse.setBounds(579, 178, 89, 23);
		pnlHouses.add(btnSubmitHouse);

		btnSubmitHouse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					getHousesData(HousesServerURL
							+ "?method=get&function=houses");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int col;
				String attribute = cmbAttributeSelect.getSelectedItem()
						.toString();
				switch (attribute) {

				case "ID":
					col = 0;
					break;
				case "Address":
					col = 1;
					break;
				case "Type":
					col = 2;
					break;
				case "Status":
					col = 3;
					break;
				case "Landlord":
					col = 4;
					break;
				case "Monthly Price":
					col = 5;
					break;
				default:
					col = -1;
					break;
				}
				
				DefaultTableModel houseModel = new DefaultTableModel(
						houses_column_names, 0);
				if (col <= 4 && col != -1) {

					for (int row = 0; row < tblHouses.getRowCount(); row++) {
						String selectedValue = tblHouses.getValueAt(row, col)
								.toString();
						if (selectedValue.contains(txtValueField.getText())) {
							String[] tempArr = new String[6];
							tempArr[0] = tblHouses.getValueAt(row, 0)
									.toString();
							tempArr[1] = tblHouses.getValueAt(row, 1)
									.toString();
							tempArr[2] = tblHouses.getValueAt(row, 2)
									.toString();
							tempArr[3] = tblHouses.getValueAt(row, 3)
									.toString();
							tempArr[4] = tblHouses.getValueAt(row, 4)
									.toString();
							tempArr[5] = tblHouses.getValueAt(row, 5)
									.toString();

							houseModel.addRow(tempArr);
						}
					}
				} else {
					for (int row = 0; row < tblHouses.getRowCount(); row++) {
						String selectedValue = tblHouses.getValueAt(row, col)
								.toString();
						if (Integer.parseInt(selectedValue) >= Integer
								.parseInt(txtMinimum.getText())
								&& Integer.parseInt(selectedValue) <= Integer
										.parseInt(txtMaximum.getText())) {
							String[] tempArr = new String[6];
							tempArr[0] = tblHouses.getValueAt(row, 0)
									.toString();
							tempArr[1] = tblHouses.getValueAt(row, 1)
									.toString();
							tempArr[2] = tblHouses.getValueAt(row, 2)
									.toString();
							tempArr[3] = tblHouses.getValueAt(row, 3)
									.toString();
							tempArr[4] = tblHouses.getValueAt(row, 4)
									.toString();
							tempArr[5] = tblHouses.getValueAt(row, 5)
									.toString();

							houseModel.addRow(tempArr);
						}

					}
				}
				tblHouses.setModel(houseModel);
			}
		});

		txtMinimum.setVisible(false);
		txtMaximum.setVisible(false);
		txtValueField.setVisible(true);

		lblMin.setVisible(false);
		lblMax.setVisible(false);
		lblValue.setVisible(true);
		lblValue.setText(cmbAttributeSelect.getSelectedItem().toString());

		cmbAttributeSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (cmbAttributeSelect.getSelectedItem()
						.equals("Monthly Price")) {
					txtMinimum.setVisible(true);
					txtMaximum.setVisible(true);
					txtValueField.setVisible(false);

					lblMin.setVisible(true);
					lblMax.setVisible(true);
					lblValue.setVisible(false);
				} else {
					txtMinimum.setVisible(false);
					txtMaximum.setVisible(false);
					txtValueField.setVisible(true);

					lblMin.setVisible(false);
					lblMax.setVisible(false);
					lblValue.setVisible(true);
					lblValue.setText(cmbAttributeSelect.getSelectedItem()
							.toString());
				}
			}
		});

		JPanel pnlInterested = new JPanel();
		tabHouses.addTab("Interested Houses", null, pnlInterested, null);
		pnlInterested.setLayout(new BorderLayout(0, 0));

		JPanel panel_8 = new JPanel();
		pnlInterested.add(panel_8, BorderLayout.NORTH);

		JLabel lblInterestedHouses = new JLabel("Interested Houses");
		panel_8.add(lblInterestedHouses);

		JScrollPane scrollPane_5 = new JScrollPane();
		pnlInterested.add(scrollPane_5, BorderLayout.CENTER);

		tblInterested = new JTable();
		scrollPane_5.setViewportView(tblInterested);

		JPanel panel_9 = new JPanel();
		pnlInterested.add(panel_9, BorderLayout.SOUTH);

		JButton btnDeleteInterested = new JButton("Delete House");
		panel_9.add(btnDeleteInterested);

		btnDeleteInterested.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				deleteInterestedHouse();
			}
		});

		btnAddToInterested.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int row = tblHouses.getSelectedRow();
				if (row == -1)
				{
					showErrorMessage();
				}
				else
				{
					String houseID = tblHouses.getValueAt(row, 0).toString();
	
					String url = HousesServerURL
							+ "?method=post&function=addInterestedHouse&house_id="
							+ houseID + "&person_id=" + clientID;
	
					HttpClient httpclient = new DefaultHttpClient();
					// Prepare a request object
					HttpGet httpget = new HttpGet(url);
					// Execute the request
					HttpResponse response;
					try {
						response = httpclient.execute(httpget);
						getInterestedHousesData(HousesServerURL
								+ "?method=get&function=interestedHouses&personId="
								+ clientID);
						// Examine the response status
					} catch (Exception e1) {
						e1.printStackTrace();
					}
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
				+ clientUsername);

		JPanel panel_3 = new JPanel();
		pnlMessageInbox.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnReadMessage = new JButton("Read Message");
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
					ReadMessage.setValues(clientUsername, from, subject, message);
					ReadMessage readMessage = new ReadMessage();
					readMessage.setVisible(true);
				}
			}
			
		});
		panel_3.add(btnReadMessage);

		JButton btnNewMessage = new JButton("New Message");
		panel_3.add(btnNewMessage);

		btnNewMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewMessage.setClientUsername(clientUsername);
				
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
				if (row == -1)
				{
					showErrorMessage();
				}
				else
				{
					String subject = tblInbox.getValueAt(row, 2).toString();
					String message = tblInbox.getValueAt(row, 3).toString();
	
					NewMessage.setClientUsername(clientUsername);
					NewMessage.setFieldValues(subject,message);
					NewMessage forwardMessage = new NewMessage();
					
					
					forwardMessage.setVisible(true);
				}
			}
		});

		JButton btnSaveMessage = new JButton("Save Message");
		panel_3.add(btnSaveMessage);
		
		JButton btnRefreshMessages = new JButton("Refresh Messages");
		btnRefreshMessages.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					getInboxData(MessagesServerURL + "?method=get&function=inbox&username="
							+ clientUsername);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		panel_3.add(btnRefreshMessages);

		btnSaveMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = tblInbox.getSelectedRow();
				if (index == -1)
				{
					showErrorMessage();
				}
				else
				{
					String messageID = tblInbox.getValueAt(index, 0).toString();
	
					String url = MessagesServerURL
							+ "?method=post&function=saved&messageID=" + messageID
							+ "&personID=" + getCurrentUserID();
	
					HttpClient httpclient = new DefaultHttpClient();
					// Prepare a request object
					HttpGet httpget = new HttpGet(url);
					// Execute the request
					HttpResponse response;
					try {
						response = httpclient.execute(httpget);
						getSavedData(MessagesServerURL
								+ "?method=get&function=saved&personID="
								+ clientID);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});

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
				+ clientID);

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
					ReadMessage.setValues(clientUsername, from, subject, message);
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
				if (row == -1)
				{
					showErrorMessage();
				}
				else
				{
					String subject = tblSaved.getValueAt(row, 2).toString();
					String message = tblSaved.getValueAt(row, 3).toString();
	
					NewMessage.setClientUsername(clientUsername);
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
				+ clientUsername);

		JPanel panel = new JPanel();
		pnlMessageSent.add(panel, BorderLayout.SOUTH);
		
		JButton btnReadMessage3 = new JButton("Read Message");
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
					ReadMessage.setValues(to, clientUsername, subject, message);
					ReadMessage readMessage = new ReadMessage();
					readMessage.setVisible(true);
				}
			}
		});
		panel.add(btnReadMessage3);

		JButton button = new JButton("Forward Message");
		panel.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblSent.getSelectedRow();
				if (row == -1)
				{
					showErrorMessage();
				}
				else
				{
					String subject = tblSent.getValueAt(row, 2).toString();
					String message = tblSent.getValueAt(row, 3).toString();
	
					NewMessage.setClientUsername(clientUsername);
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
				AddTask.setPersonID(clientID);
				AddTask otherFrame = new AddTask();
				otherFrame.setVisible(true);
			}
		});

		JButton btnNewButton_5 = new JButton("Modify Event");
		panel_2.add(btnNewButton_5);

		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int index = tblCalendar.getSelectedRow();
				if (index == -1)
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

		getCalendarData(CalendarServerURL + "?method=get");

		JTabbedPane tabHelp = new JTabbedPane(JTabbedPane.TOP);
		ClientApplication.addTab("Help", null, tabHelp, null);

		JPanel tabHousesHelp = new JPanel();
		tabHousesHelp.setBackground(new Color(152, 251, 152));
		tabHelp.addTab("Houses Help", null, tabHousesHelp, null);
		tabHousesHelp.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_6 = new JScrollPane();
		tabHousesHelp.add(scrollPane_6);
		
		JPanel panel_12 = new JPanel();
		scrollPane_6.setViewportView(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		String housesHelp =   "USING THE HOUSES TAB" +
				   "\n--------------------------------------------------------------------------------------------------------------------------------------------------------" +
				   "\nThe 'houses' tab allows you to view and filter through a range of different houses. You can also" + 
				   "\ncreate a list of all the interested houses you find to minimise search times. The sections below" + 
				   "\nexplain how to perform these tasks." +
		           "\n" +
				   "\nFiltering Through the Data" +
				   "\n------------------------" + 
				   "\nStep 1) Select the Attribute you wish to filter by using the ComboBox on the 'Houses' tab." +
				   "\nStep 2) Enter the value you wish to search for in the 'Value' text field." +
				   "\nStep 3) Click the 'Submit' button. The table will automatically show only data that contains this value." +
				   "\n" +
				   "\nAdd to Interested Houses List" +
				   "\n-------------------" +
				   "\nStep 1) Select the record you wish to save on the table." +
				   "\nStep 2) Click the 'Add to Interested Houses' button." +
				   "\nStep 3) The record will automatically be added the interested houses table." +
				   "\n" +
				   "\nDelete From Interested Houses List" +
				   "\n---------------------" +
				   "\nTHIS CAN ONLY BE PERFORMED FROM THE 'INTEREST HOUSES' SUB-TAB" +
				   "\n" + 
				   "\nStep 1) Select the record you wish to delete on the interested houses table." +
				   "\nStep 2) Click the 'Delete House' button." +
				   "\nStep 4) The table will automatically delete the record." +
				   "\n" ;
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(UIManager.getColor("Button.background"));
		textArea.setText(housesHelp);
		panel_12.add(textArea);
		
		JPanel panel_14 = new JPanel();
		tabHousesHelp.add(panel_14, BorderLayout.NORTH);
		
		JLabel lblHousesHelp = new JLabel("Houses Help");
		lblHousesHelp.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel_14.add(lblHousesHelp);

		JPanel tabMessagingHelp = new JPanel();
		tabMessagingHelp.setBackground(new Color(152, 251, 152));
		tabHelp.addTab("Messaging Help", null, tabMessagingHelp, null);
		tabMessagingHelp.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_7 = new JScrollPane();
		tabMessagingHelp.add(scrollPane_7);
		
		JPanel panel_17 = new JPanel();
		scrollPane_7.setViewportView(panel_17);
		panel_17.setLayout(new BorderLayout(0, 0));
		
		String emailHelp =   "USING THE MESSAGING FUNCTION" +
		   		   "\n--------------------------------------------------------------------------------------------------------------------------------------------------------" +
		   		   "\nThere are functions included that allow you to communicate with staff members and other clients. As well as this, you can save" +
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
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textArea_2.setText(emailHelp);
		textArea_2.setBackground(UIManager.getColor("Button.background"));
		panel_17.add(textArea_2);
		
		JPanel panel_15 = new JPanel();
		tabMessagingHelp.add(panel_15, BorderLayout.NORTH);
		
		JLabel lblEmailHelp = new JLabel("Email Help");
		lblEmailHelp.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel_15.add(lblEmailHelp);

		JPanel tabCalandarHelp = new JPanel();
		tabCalandarHelp.setBackground(new Color(152, 251, 152));
		tabHelp.addTab("Calandar Help", null, tabCalandarHelp, null);
		tabCalandarHelp.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_8 = new JScrollPane();
		tabCalandarHelp.add(scrollPane_8);
		
		JPanel panel_18 = new JPanel();
		scrollPane_8.setViewportView(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
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
		textArea_1.setText(calandarHelp);
		textArea_1.setBackground(UIManager.getColor("Button.background"));
		panel_18.add(textArea_1, BorderLayout.CENTER);
		
		JPanel panel_16 = new JPanel();
		tabCalandarHelp.add(panel_16, BorderLayout.NORTH);
		
		JLabel lblCalandarHelp = new JLabel("Calandar Help");
		lblCalandarHelp.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel_16.add(lblCalandarHelp);

		getHousesData(HousesServerURL + "?method=get&function=houses");

		getInterestedHousesData(HousesServerURL
				+ "?method=get&function=interestedHouses&personId=" + clientID);
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
		tblHouses.setModel(model);
	}

	private static void populateInterestedHousesTable(JSONArray jsonArr)
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
		tblInterested.setModel(model);
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
		if (index == -1)
		{
			showErrorMessage();
		}
		else
		{
			String messageID = tblSaved.getValueAt(index, 0).toString();
	
			String url = MessagesServerURL
					+ "?method=post&function=delete&messageID=" + messageID
					+ "&personID=" + clientID;
	
			HttpClient httpclient = new DefaultHttpClient();
			// Prepare a request object
			HttpGet httpget = new HttpGet(url);
			// Execute the request
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);
				getSavedData(MessagesServerURL
						+ "?method=get&function=saved&personID=" + clientID);
				// Examine the response status
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void deleteTask() {
		int index = tblCalendar.getSelectedRow();
		if (index == -1)
		{
			showErrorMessage();
		}
		else
		{
			String taskID = tblCalendar.getValueAt(index, 0).toString();
	
			String url = CalendarServerURL + "?method=post&function=delete&taskID="
					+ taskID;
	
			HttpClient httpclient = new DefaultHttpClient();
			// Prepare a request object
			HttpGet httpget = new HttpGet(url);
			// Execute the request
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);
				getCalendarData(CalendarServerURL + "?method=get");
				// Examine the response status
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteInterestedHouse() {
		int index = tblInterested.getSelectedRow();
		if(index==-1)
		{
			showErrorMessage();
		} 
		else 
		{
			
			String houseID = tblInterested.getValueAt(index, 0).toString();
	
			String url = HousesServerURL + "?method=post&function=delete&houseID="
					+ houseID + "&personId=" + clientID;
	
			HttpClient httpclient = new DefaultHttpClient();
			// Prepare a request object
			HttpGet httpget = new HttpGet(url);
			// Execute the request
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);
				getInterestedHousesData(HousesServerURL
						+ "?method=get&function=interestedHouses&personId="
						+ clientID);
				// Examine the response status
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setCurrentUserID(String currentLoggedID) {
		clientID = currentLoggedID;
	}
	
	private static String getCurrentUserID()
	{
		return clientID;
	}

	public static void setCurrentUsername(String currentUsername) {
		clientUsername = currentUsername;
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
	
	private void showErrorMessage()
	{
		JOptionPane.showMessageDialog(
				new JFrame(),
				"Please select a record from the table before performing this task.",
				"No Record Selected",
				JOptionPane.ERROR_MESSAGE);
	}

}
