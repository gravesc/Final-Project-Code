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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class NewMaintanence extends JFrame {

	private JPanel contentPane;
	private JTextField txtDescription;
	private JTextField txtSeverity;
	private JTextField txtOccurred;
	private JTextField txtCompleted;
	private static String method;
	private static String id;

	private static final String MaintanenceServerURL = "http://localhost:8888/landlord_Servers/MaintanenceServer";

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
	public NewMaintanence() {
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

		JLabel lblNewClient = new JLabel("Maintanence Editor - "+method.toUpperCase());
		lblNewClient.setFont(new Font("Tahoma", Font.BOLD, 23));
		panel_1.add(lblNewClient);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("House ID : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(21, 30, 141, 14);
		panel_2.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Description : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(21, 60, 141, 14);
		panel_2.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Severity : ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(21, 129, 141, 14);
		panel_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Date Occurred : ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(21, 159, 141, 14);
		panel_2.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Date Completed : ");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(21, 189, 141, 14);
		panel_2.add(lblNewLabel_4);

		txtDescription = new JTextField();
		txtDescription.setBounds(172, 57, 167, 58);
		panel_2.add(txtDescription);
		txtDescription.setColumns(10);

		txtSeverity = new JTextField();
		txtSeverity.setBounds(172, 126, 167, 20);
		panel_2.add(txtSeverity);
		txtSeverity.setColumns(10);

		txtOccurred = new JTextField();
		txtOccurred.setBounds(172, 156, 167, 20);
		panel_2.add(txtOccurred);
		txtOccurred.setColumns(10);

		txtCompleted = new JTextField();
		txtCompleted.setBounds(172, 186, 167, 20);
		panel_2.add(txtCompleted);
		txtCompleted.setColumns(10);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(224, 225, 89, 23);
		panel_2.add(btnClear);

		final JSpinner spnHouseID = new JSpinner();
		spnHouseID.setBounds(172, 27, 29, 20);
		panel_2.add(spnHouseID);
		
		if(method.equals("add"))
		{
			JButton btnSubmit = new JButton("Submit");
			btnSubmit.setBounds(90, 225, 89, 23);
			panel_2.add(btnSubmit);

			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String houseID = spnHouseID.getValue().toString();
					String description = removeSpaces(txtDescription.getText());
					String severity = removeSpaces(txtSeverity.getText());
					String occurred = removeSpaces(txtOccurred.getText());
					String completed = removeSpaces(txtCompleted.getText());

					try {
						addMaintanence(houseID, description, severity, occurred,
							completed);
						MainApplication.setData("Maintanence");
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
		
		if(method.equals("modify"))
		{
			JButton btnModify = new JButton("Modify");
			btnModify.setBounds(92, 225, 89, 23);
			panel_2.add(btnModify);

			btnModify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String houseID = spnHouseID.getValue().toString();
					String description = removeSpaces(txtDescription.getText());
					String severity = removeSpaces(txtSeverity.getText());
					String occurred = removeSpaces(txtOccurred.getText());
					String completed = removeSpaces(txtCompleted.getText());
					
					try {
						modifyMaintanence(houseID, description, severity, occurred,
							completed, getID());
						    MainApplication.setData("Maintanence");
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

	public static void addMaintanence(String houseID, String description,
			String severity, String occurred, String completed)
			throws IllegalStateException, IOException {

		String url = MaintanenceServerURL + "?method=post&function=add&houseID=" + houseID + "&description="
				+ description + "&severity=" + severity + "&occurred="
				+ occurred + "&completed=" + completed;

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
	
	public static void modifyMaintanence(String houseID, String description,
			String severity, String occurred, String completed, String mainID)
			throws IllegalStateException, IOException {

		String url = MaintanenceServerURL + "?method=post&function=modify&houseID=" + houseID + "&description="
				+ description + "&severity=" + severity + "&occurred="
				+ occurred + "&completed=" + completed + "&maintanenceID=" + mainID;

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

	public static void setMethod(String method) {
		NewMaintanence.method = method;
	}
	
	public String getMethod()
	{
		return method;
	}
	
	public static void setID(String id) {
		NewMaintanence.id = id;
	}

	private String getID() {
		return id;
	}
}