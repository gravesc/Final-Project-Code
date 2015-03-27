import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class newCalendar extends JFrame {

	private JFrame frame;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtStreet;
	private JTextField txtTown;
	private JTextField txtCounty;
	private JTextField txtCountry;
	private JTextField txtPostcode;
	private JTextField txtDateTime;
	
	private static final String CalendarServerURL = "http://localhost:8888/General_Servers/CalendarServer";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newCalendar window = new newCalendar();
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
	public newCalendar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAddNewCalendartask = new JLabel("<html><u>Add New Calendar Task</u></html>");
		lblAddNewCalendartask.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewCalendartask.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblAddNewCalendartask, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		
		JPanel panel_6 = new JPanel();
		scrollPane.setViewportView(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label = new JLabel("<html><center>ID</center></html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		panel_6.add(txtID);
		
		JLabel label_1 = new JLabel("<html><center>Name</center></html>");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_1);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		panel_6.add(txtName);
		
		JLabel label_2 = new JLabel("<html><center>Type</center></html>");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_2);
		
		final JComboBox<String> cmbType = new JComboBox<String>();
		cmbType.addItem("Appointment");
		cmbType.addItem("Viewing");
		cmbType.addItem("Tennancy Agreement Meeting");
		cmbType.addItem("Tennancy Agreement Signature Date");
		cmbType.addItem("Other");
		panel_6.add(cmbType);
		
		JLabel label_3 = new JLabel("<html><center>Description</center></html>");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_3);
		
		final JTextPane txtDesc = new JTextPane();
		panel_6.add(txtDesc);
		
		JLabel label_4 = new JLabel("<html><center>House No. and Street Name</center></html>");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_4);
		
		txtStreet = new JTextField();
		txtStreet.setColumns(10);
		panel_6.add(txtStreet);
		
		JLabel label_5 = new JLabel("<html><center>Town</center></html>");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_5);
		
		txtTown = new JTextField();
		txtTown.setColumns(10);
		panel_6.add(txtTown);
		
		JLabel label_6 = new JLabel("<html><center>County</center></html>");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_6);
		
		txtCounty = new JTextField();
		txtCounty.setColumns(10);
		panel_6.add(txtCounty);
		
		JLabel label_7 = new JLabel("<html><center>Country</center></html>");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_7);
		
		txtCountry = new JTextField();
		txtCountry.setColumns(10);
		panel_6.add(txtCountry);
		
		JLabel label_8 = new JLabel("<html><center>Postcode</center></html>");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_8);
		
		txtPostcode = new JTextField();
		txtPostcode.setColumns(10);
		panel_6.add(txtPostcode);
		
		JLabel label_9 = new JLabel("<html><center>Date and Time</center></html>");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_9);
		
		txtDateTime = new JTextField();
		txtDateTime.setColumns(10);
		panel_6.add(txtDateTime);
		
		JLabel label_10 = new JLabel("<html><center>Important</center></html>");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(label_10);
		
		final JComboBox<String> cmbImportant = new JComboBox<String>();
		cmbImportant.addItem("Y");
		cmbImportant.addItem("N");
		panel_6.add(cmbImportant);
		
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		
		JButton button = new JButton("Add New Task");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtID.getText();
				String name = removeSpaces(txtName.getText());
				String type = cmbType.getSelectedItem().toString();
				String desc = removeSpaces(txtDesc.getText());
				String street = removeSpaces(txtStreet.getText());
				String town = removeSpaces(txtTown.getText());
				String county = removeSpaces(txtCounty.getText());
				String country = removeSpaces(txtCountry.getText());
				String postcode = removeSpaces(txtPostcode.getText());
				String dateTime = removeSpaces(txtDateTime.getText());
				String important = cmbImportant.getSelectedItem().toString();
				
				try {
					connectCalendar(id, name, type, desc, street, town, county, country, postcode, dateTime, important);
				} catch (IllegalStateException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton button_1 = new JButton("Clear");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtID.setText("");
				txtName.setText("");
				txtDesc.setText("");
				txtStreet.setText("");
				txtTown.setText("");
				txtCounty.setText("");
				txtCountry.setText("");
				txtPostcode.setText("");
				txtDateTime.setText("");
			}
		});
		
		JButton button_2 = new JButton("Close");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
					.addContainerGap(71, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
					.addGap(60))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(52)
					.addComponent(button)
					.addGap(18)
					.addComponent(button_1)
					.addGap(18)
					.addComponent(button_2)
					.addContainerGap(166, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);
	}
	
	public static void connectCalendar(String taskID, String taskName,
			String taskType, String taskDesc, String addressStreet,
			String addressTown, String addressCounty, String addressCountry,
			String addressPostcode, String taskDateTime, String important) throws IllegalStateException, IOException {

		String url = CalendarServerURL + "?method=post&taskID=" + taskID + "&taskName="
				+ taskName + "&taskType=" + taskType + "&taskDesc=" + taskDesc
				+ "&addressStreet=" + addressStreet + "&addressTown="
				+ addressTown + "&addressCounty=" + addressCounty
				+ "&addressCountry=" + addressCountry + "&addressPostcode="
				+ addressPostcode + "&taskDateTime=" + taskDateTime
				+ "&important=" + important;
		
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
	}
	
	public String removeSpaces(String text)
	{
		return text.replace(' ','+');
	}
}
