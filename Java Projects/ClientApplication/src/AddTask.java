import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class AddTask extends JFrame {

	public static String method;
	public static String taskID, personID;
	private JTextField txtName;
	private JTextPane txtDesc;
	private JTextField txtStreet;
	private JTextField txtTown;
	private JTextField txtCounty;
	private JTextField txtCountry;
	private JTextField txtPostcode;
	private JTextField txtDateTime;
	private JButton button;

	private static final String CalendarServerURL = "http://localhost:8888/General_Servers/CalendarServer";

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTask frame = new AddTask();
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
	public AddTask() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		if (getMethod().equalsIgnoreCase("add")) {
			JLabel lblAddNewCalendartask = new JLabel(
					"<html><u>Add New Calendar Task</u></html>");
			lblAddNewCalendartask.setHorizontalAlignment(SwingConstants.CENTER);
			lblAddNewCalendartask.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPane.add(lblAddNewCalendartask, BorderLayout.NORTH);
		}

		if (getMethod().equalsIgnoreCase("modify")) {
			JLabel lblAddNewCalendartask = new JLabel(
					"<html><u>Modify Calendar Task</u></html>");
			lblAddNewCalendartask.setHorizontalAlignment(SwingConstants.CENTER);
			lblAddNewCalendartask.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPane.add(lblAddNewCalendartask, BorderLayout.NORTH);
		}

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);

		JPanel panel_6 = new JPanel();
		scrollPane.setViewportView(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));

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

		txtDesc = new JTextPane();
		panel_6.add(txtDesc);

		JLabel label_4 = new JLabel(
				"<html><center>House No. and Street Name</center></html>");
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

		JLabel label_9 = new JLabel(
				"<html><center>Date and Time</center></html>");
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

		if (getMethod().equalsIgnoreCase("add")) {
			button = new JButton("Add New Task");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = removeSpaces(txtName.getText());
					String type = removeSpaces(cmbType.getSelectedItem()
							.toString());
					String desc = removeSpaces(txtDesc.getText());
					String street = removeSpaces(txtStreet.getText());
					String town = removeSpaces(txtTown.getText());
					String county = removeSpaces(txtCounty.getText());
					String country = removeSpaces(txtCountry.getText());
					String postcode = removeSpaces(txtPostcode.getText());
					String dateTime = removeSpaces(txtDateTime.getText());
					String important = cmbImportant.getSelectedItem()
							.toString();

					try {
						connectCalendar(name, type, desc, street, town,
								county, country, postcode, dateTime, important);
						MainApplication.getCalendarData(CalendarServerURL
								+ "?method=get&personID=" + personID);
						JOptionPane.showMessageDialog(
								new JFrame(),
								"Data successfully added.",
								"Addition Successful",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(
								new JFrame(),
								e1,
								"IOException",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(
								new JFrame(),
								e1,
								"Exception",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

		else if (getMethod().equals("modify")) {
			button = new JButton("Modify Task");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String id = getID();
					String name = removeSpaces(txtName.getText());
					String type = removeSpaces(cmbType.getSelectedItem()
							.toString());
					String desc = removeSpaces(txtDesc.getText());
					String street = removeSpaces(txtStreet.getText());
					String town = removeSpaces(txtTown.getText());
					String county = removeSpaces(txtCounty.getText());
					String country = removeSpaces(txtCountry.getText());
					String postcode = removeSpaces(txtPostcode.getText());
					String dateTime = removeSpaces(txtDateTime.getText());
					String important = cmbImportant.getSelectedItem()
							.toString();

					try {
						modifyCalendar(id, name, type, desc, street, town,
								county, country, postcode, dateTime, important);
						MainApplication.getCalendarData(CalendarServerURL
								+ "?method=get&personID=" + personID);
						JOptionPane.showMessageDialog(
								new JFrame(),
								"Data successfully modified.",
								"Modification Successful",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(
								new JFrame(),
								e1,
								"IOException",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(
								new JFrame(),
								e1,
								"Exception",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

		JButton button_1 = new JButton("Clear");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5
				.setHorizontalGroup(gl_panel_5
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panel_5
										.createSequentialGroup()
										.addContainerGap(66, Short.MAX_VALUE)
										.addGroup(
												gl_panel_5
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																button_1,
																GroupLayout.PREFERRED_SIZE,
																161,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																button,
																GroupLayout.PREFERRED_SIZE,
																161,
																GroupLayout.PREFERRED_SIZE))
										.addGap(60)));
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_5.createSequentialGroup().addGap(52)
						.addComponent(button).addGap(18).addComponent(button_1)
						.addContainerGap(207, Short.MAX_VALUE)));
		panel_5.setLayout(gl_panel_5);

		if (getMethod().equalsIgnoreCase("modify")) {
			populateTextFields();
		}
	}

	public void populateTextFields() {
		txtName.setText("");
		txtDesc.setText("");
		txtStreet.setText("");
		txtTown.setText("");
		txtCounty.setText("");
		txtCountry.setText("");
		txtPostcode.setText("");
		txtDateTime.setText("");
	}

	public static void connectCalendar(String taskName,
			String taskType, String taskDesc, String addressStreet,
			String addressTown, String addressCounty, String addressCountry,
			String addressPostcode, String taskDateTime, String important)
			throws IllegalStateException, IOException {

		String url = CalendarServerURL + "?method=post&function=add&taskName=" + taskName + "&taskType=" + taskType
				+ "&taskDesc=" + taskDesc + "&addressStreet=" + addressStreet
				+ "&addressTown=" + addressTown + "&addressCounty="
				+ addressCounty + "&addressCountry=" + addressCountry
				+ "&addressPostcode=" + addressPostcode + "&taskDateTime="
				+ taskDateTime + "&important=" + important + "&personID="
				+ personID;
		
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

	public String removeSpaces(String text) {
		return text.replace(' ', '+');
	}

	public static void setMethod(String methodStr) {
		method = methodStr;
	}

	public String getMethod() {
		return method;
	}

	public static void modifyCalendar(String id, String taskName,
			String taskType, String taskDesc, String addressStreet,
			String addressTown, String addressCounty, String addressCountry,
			String addressPostcode, String taskDateTime, String important)
			throws IllegalStateException, IOException {
		String url = CalendarServerURL + "?method=post&function=modify&taskID="
				+ id + "&taskName=" + taskName + "&taskType=" + taskType
				+ "&taskDesc=" + taskDesc + "&addressStreet=" + addressStreet
				+ "&addressTown=" + addressTown + "&addressCounty="
				+ addressCounty + "&addressCountry=" + addressCountry
				+ "&addressPostcode=" + addressPostcode + "&taskDateTime="
				+ taskDateTime + "&important=" + important + "&personID="
				+ personID;

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

	public static void setID(String ID) {
		taskID = ID;
	}

	public static String getID() {
		return taskID;
	}

	public static void setPersonID(String staffID) {
		personID = staffID;
	}

	public static String getPersonID() {
		return personID;
	}
}
