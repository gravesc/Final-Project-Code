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
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class NewHouse extends JFrame {

	private JPanel contentPane;
	private JTextField txtStreet;
	private JTextField txtTown;
	private JTextField txtCounty;
	private JTextField txtCountry;
	private JTextField txtPostcode;
	private static String method;
	private static String id;

	private static final String HouseServerURL = "http://localhost:8888/General_Servers/HousesServer";

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
	public NewHouse() {
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

		JLabel lblNewClient = new JLabel("House Editor - "+method.toUpperCase());
		lblNewClient.setFont(new Font("Tahoma", Font.BOLD, 23));
		panel_1.add(lblNewClient);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Street : ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(21, 33, 141, 14);
		panel_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Town : ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(21, 63, 141, 14);
		panel_2.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("County : ");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(21, 93, 141, 14);
		panel_2.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Country : ");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(21, 123, 141, 14);
		panel_2.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Postcode : ");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(21, 153, 141, 14);
		panel_2.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("House Type : ");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_7.setBounds(21, 181, 141, 14);
		panel_2.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Status : ");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setBounds(21, 209, 141, 14);
		panel_2.add(lblNewLabel_8);

		txtStreet = new JTextField();
		txtStreet.setBounds(172, 30, 167, 20);
		panel_2.add(txtStreet);
		txtStreet.setColumns(10);

		txtTown = new JTextField();
		txtTown.setBounds(172, 60, 167, 20);
		panel_2.add(txtTown);
		txtTown.setColumns(10);

		txtCounty = new JTextField();
		txtCounty.setBounds(172, 90, 167, 20);
		panel_2.add(txtCounty);
		txtCounty.setColumns(10);

		txtCountry = new JTextField();
		txtCountry.setBounds(172, 120, 167, 20);
		panel_2.add(txtCountry);
		txtCountry.setColumns(10);

		txtPostcode = new JTextField();
		txtPostcode.setBounds(172, 150, 167, 20);
		panel_2.add(txtPostcode);
		txtPostcode.setColumns(10);

		final JComboBox<String> cmbType = new JComboBox<String>();
		cmbType.addItem("Semi-Detached");
		cmbType.addItem("Detached");
		cmbType.addItem("Apartment");
		cmbType.addItem("Bungalow");
		cmbType.setBounds(172, 178, 167, 20);
		panel_2.add(cmbType);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(242, 390, 89, 23);
		panel_2.add(btnClear);
		
		JLabel lblManagerId = new JLabel("Landlord ID : ");
		lblManagerId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblManagerId.setBounds(21, 234, 141, 29);
		panel_2.add(lblManagerId);
		
		final JSpinner spnLandlord = new JSpinner();
		spnLandlord.setBounds(172, 238, 49, 20);
		panel_2.add(spnLandlord);
		
		final JComboBox<String> cmbStatus = new JComboBox<String>();
		cmbStatus.setBounds(172, 206, 167, 20);
		panel_2.add(cmbStatus);
		
		cmbStatus.addItem("Available for Rent");
		cmbStatus.addItem("Rented Out");
		cmbStatus.addItem("Not Yet Ready");
		cmbStatus.addItem("Unavailable for Rent");
		
		final JSpinner spnPrice = new JSpinner();
		spnPrice.setBounds(172, 273, 49, 20);
		panel_2.add(spnPrice);
		
		JLabel lblPricePerMonth = new JLabel("Price Per Month : ");
		lblPricePerMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPricePerMonth.setBounds(21, 269, 141, 29);
		panel_2.add(lblPricePerMonth);

		if (method.equals("add")) {
			JButton btnSubmit = new JButton("Submit");
			btnSubmit.setBounds(95, 390, 89, 23);
			panel_2.add(btnSubmit);

			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String street = removeSpaces(txtStreet.getText());
					String town = removeSpaces(txtTown.getText());
					String county = removeSpaces(txtCounty.getText());
					String country = removeSpaces(txtCountry.getText());
					String postcode = removeSpaces(txtPostcode.getText());
					String type = removeSpaces(cmbType.getSelectedItem()
							.toString());
					String status = removeSpaces(cmbStatus.getSelectedItem().toString());
					String landlord = removeSpaces(spnLandlord.getValue()
							.toString());
					String price = removeSpaces(spnPrice.getValue()
							.toString());

					try {
						addHouse(street, town, county, country, postcode, 
								type, status, landlord, price);
						MainApplication.setData("Houses");
						JOptionPane.showMessageDialog(
								new JFrame(),
								"Data successfully Added.",
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
					String street = removeSpaces(txtStreet.getText());
					String town = removeSpaces(txtTown.getText());
					String county = removeSpaces(txtCounty.getText());
					String country = removeSpaces(txtCountry.getText());
					String postcode = removeSpaces(txtPostcode.getText());
					String type = removeSpaces(cmbType.getSelectedItem()
							.toString());
					String status = removeSpaces(cmbStatus.getSelectedItem().toString());
					String landlord = removeSpaces(spnLandlord.getValue()
							.toString());
					String price = removeSpaces(spnPrice.getValue()
							.toString());

					try {
						modifyHouse(street, town, county, country, postcode, 
								type, status, landlord, price);
						MainApplication.setData("Houses");
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

	public static void addHouse(String addressStreet, String addressTown, 
			String addressCounty, String addressCountry, String addressPostcode, 
			String type, String status, String landlord, String price)
			throws IllegalStateException, IOException {

		String url = HouseServerURL + "?method=post&function=addHouse&street=" + addressStreet
				+ "&town=" + addressTown + "&county=" + addressCounty
				+ "&country=" + addressCountry + "&postcode=" + addressPostcode
				+ "&type=" + type + "&status=" + status + "&landlord=" + landlord + "&price=" + price;
		
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

	public static void modifyHouse(String addressStreet, String addressTown, 
			String addressCounty, String addressCountry, String addressPostcode, 
			String type, String status, String landlord, String price)
			throws IllegalStateException, IOException {

		String url = HouseServerURL + "?method=post&function=modify&street=" + addressStreet
				+ "&town=" + addressTown + "&county=" + addressCounty
				+ "&country=" + addressCountry + "&postcode=" + addressPostcode
				+ "&type=" + type + "&status=" + status + "&landlord=" + landlord + "&price=" + price
				+ "&houseID=" + getID();

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
		NewHouse.method = method;
	}
	
	public static void setID(String id) {
		NewHouse.id = id;
	}

	private static String getID() {
		return id;
	}
}