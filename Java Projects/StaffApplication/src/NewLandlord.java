import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class NewLandlord extends JFrame {

	private JPanel contentPane;
	private JTextField txtFname;
	private JTextField txtSname;
	private JTextField txtStreet;
	private JTextField txtTown;
	private JTextField txtCounty;
	private JTextField txtCountry;
	private JTextField txtPostcode;
	private static String method;
	private static String id;

	private static final String LandlordServerURL = "http://localhost:8888/landlord_Servers/LandlordServer";
	private JTextField txtHome;
	private JTextField txtMobile;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewClient frame = new NewClient();
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
	public NewLandlord() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewClient = new JLabel("Landlord Editor - "+method.toUpperCase());
		lblNewClient.setFont(new Font("Tahoma", Font.BOLD, 23));
		panel_1.add(lblNewClient);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("First Name : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(21, 30, 141, 14);
		panel_2.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Surname : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(21, 60, 141, 14);
		panel_2.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Street : ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(21, 90, 141, 14);
		panel_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Town : ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(21, 120, 141, 14);
		panel_2.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("County : ");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(21, 150, 141, 14);
		panel_2.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Country : ");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(21, 180, 141, 14);
		panel_2.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Postcode : ");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(21, 210, 141, 14);
		panel_2.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Home Number : ");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_7.setBounds(21, 240, 141, 14);
		panel_2.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Mobile Number : ");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setBounds(21, 270, 141, 14);
		panel_2.add(lblNewLabel_8);

		txtFname = new JTextField();
		txtFname.setBounds(172, 27, 167, 20);
		panel_2.add(txtFname);
		txtFname.setColumns(10);

		txtSname = new JTextField();
		txtSname.setBounds(172, 57, 167, 20);
		panel_2.add(txtSname);
		txtSname.setColumns(10);

		txtStreet = new JTextField();
		txtStreet.setBounds(172, 87, 167, 20);
		panel_2.add(txtStreet);
		txtStreet.setColumns(10);

		txtTown = new JTextField();
		txtTown.setBounds(172, 117, 167, 20);
		panel_2.add(txtTown);
		txtTown.setColumns(10);

		txtCounty = new JTextField();
		txtCounty.setBounds(172, 147, 167, 20);
		panel_2.add(txtCounty);
		txtCounty.setColumns(10);

		txtCountry = new JTextField();
		txtCountry.setBounds(172, 177, 167, 20);
		panel_2.add(txtCountry);
		txtCountry.setColumns(10);

		txtPostcode = new JTextField();
		txtPostcode.setBounds(172, 207, 167, 20);
		panel_2.add(txtPostcode);
		txtPostcode.setColumns(10);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(242, 390, 89, 23);
		panel_2.add(btnClear);
		
		JLabel lblManagerId = new JLabel("Email : ");
		lblManagerId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblManagerId.setBounds(21, 295, 141, 29);
		panel_2.add(lblManagerId);
		
		txtHome = new JTextField();
		txtHome.setColumns(10);
		txtHome.setBounds(172, 237, 167, 20);
		panel_2.add(txtHome);
		
		txtMobile = new JTextField();
		txtMobile.setColumns(10);
		txtMobile.setBounds(172, 267, 167, 20);
		panel_2.add(txtMobile);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(172, 299, 167, 20);
		panel_2.add(txtEmail);

		if (method.equals("add")) {
			JButton btnSubmit = new JButton("Submit");
			btnSubmit.setBounds(95, 390, 89, 23);
			panel_2.add(btnSubmit);

			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String fname = removeSpaces(txtFname.getText());
					String sname = removeSpaces(txtSname.getText());
					String street = removeSpaces(txtStreet.getText());
					String town = removeSpaces(txtTown.getText());
					String county = removeSpaces(txtCounty.getText());
					String country = removeSpaces(txtCountry.getText());
					String postcode = removeSpaces(txtPostcode.getText());
					String home = removeSpaces(txtHome.getText());
					String mobile = removeSpaces(txtMobile.getText());
					String email = removeSpaces(txtEmail.getText());

					try {
						addLandlord(fname, sname, street, town, county,
								country, postcode, home, mobile, email);
						MainApplication.setData("Landlord");
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
		
		if (method.equals("modify")) {
			JButton btnModify = new JButton("Modify");
			btnModify.setBounds(95, 390, 89, 23);
			panel_2.add(btnModify);

			btnModify.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String fname = removeSpaces(txtFname.getText());
					String sname = removeSpaces(txtSname.getText());
					String street = removeSpaces(txtStreet.getText());
					String town = removeSpaces(txtTown.getText());
					String county = removeSpaces(txtCounty.getText());
					String country = removeSpaces(txtCountry.getText());
					String postcode = removeSpaces(txtPostcode.getText());
					String home = removeSpaces(txtHome.getText());
					String mobile = removeSpaces(txtMobile.getText());
					String email = removeSpaces(txtEmail.getText());

					try {
						modifyLandlord(fname, sname, street, town, county,
								country, postcode, home, mobile, email);
						MainApplication.setData("Landlord");
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
	}

	public static void addLandlord(String firstname, String surname,
			String addressStreet, String addressTown, String addressCounty,
			String addressCountry, String addressPostcode, String home,
			String mobile, String email)
			throws IllegalStateException, IOException {		
		
		String url = LandlordServerURL + "?method=post&function=add&fname="
				+ firstname + "&sname=" + surname + "&street=" + addressStreet
				+ "&town=" + addressTown + "&county=" + addressCounty
				+ "&country=" + addressCountry + "&postcode=" + addressPostcode
				+ "&homePhone=" + home + "&mobilePhone=" + mobile + "&email=" + email;
		
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

	public static void modifyLandlord(String firstname, String surname,
			String addressStreet, String addressTown, String addressCounty,
			String addressCountry, String addressPostcode, String home,
			String mobile, String email)
			throws IllegalStateException, IOException {		
		
		String url = LandlordServerURL + "?method=post&function=modify&fname="
				+ firstname + "&sname=" + surname + "&street=" + addressStreet
				+ "&town=" + addressTown + "&county=" + addressCounty
				+ "&country=" + addressCountry + "&postcode=" + addressPostcode
				+ "&homePhone=" + home + "&mobilePhone=" + mobile + "&email=" + email + "&landlordID=" + getID();
		
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

	static void setMethod(String method) {
		NewLandlord.method = method;
	}

	private String getMethod() {
		return method;
	}

	public static void setID(String id) {
		NewLandlord.id = id;
	}

	private static String getID() {
		return id;
	}
}