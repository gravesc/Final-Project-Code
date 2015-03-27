import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.BorderLayout;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private boolean isValid;
	private String currentLoggedID, currentUsername, currentJobTitle;
	protected static final String StaffLoginURL = "http://localhost:8888/Staff_Servers/StaffServer";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Lettings Agency Staff Application");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1
				.setText("<html><center>Hello and thank you for chosing this application to help you on<br>your lettings needs. Simply log in to your existing account or<br>create a new account using the form below.</center></html>");
		panel_2.add(lblNewLabel_1);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Username : ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(88, 38, 96, 14);
		panel_3.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password : ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(88, 78, 96, 14);
		panel_3.add(lblNewLabel_3);

		textField = new JTextField();
		textField.setBounds(181, 38, 181, 20);
		panel_3.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(181, 78, 181, 20);
		panel_3.add(passwordField);

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setBounds(88, 121, 274, 23);
		panel_3.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isValid=false;
				ArrayList<String[]> allStaff = null;
				try {
					allStaff = getValues();
				} catch (IOException | JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String enteredUsername = textField.getText();
				String enteredPassword = new String(passwordField.getPassword());
				
				for(String[] staff: allStaff)
				{
					String username = staff[7];
					String password = staff[8];
					
					if(username.equals(enteredUsername) && password.equals(enteredPassword))
					{
						currentLoggedID = staff[0];
						currentUsername = username;
						currentJobTitle = staff[4];
						isValid=true;
					}
				}
				
				if(isValid)
				{
					try {
						
						MainApplication.setCurrentUserID(currentLoggedID);
						MainApplication.setCurrentUsername(currentUsername);
						MainApplication.setCurrentJobTitle(currentJobTitle);
						MainApplication main = new MainApplication();
						main.setVisible(true); 
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(
							new JFrame(),
							"You have entered and invalid username and password combination. Please try again.",
							"Invalid Username/Password",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	public ArrayList<String[]> getValues() throws IOException, JSONException
	{
		URL url;
		URLConnection conn;
		String result = "";

		url = new URL( StaffLoginURL+"?method=get&function=secureStaff");
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
		jsonArr = jsonObj.getJSONArray("staff");
		
		ArrayList<String[]> allClients = new ArrayList<String[]>();

		for (int i = 0; i < jsonArr.length(); i++) {
			String tempArr[] = new String[9];
			JSONObject client = jsonArr.getJSONObject(i);
			tempArr[0] = client.getString("1");
			tempArr[1] = client.getString("2");
			tempArr[2] = client.getString("3");
			tempArr[3] = client.getString("4");
			tempArr[4] = client.getString("5");
			tempArr[5] = client.getString("6");
			tempArr[6] = client.getString("7");
			tempArr[7] = client.getString("8");
			tempArr[8] = client.getString("9");

			allClients.add(tempArr);
		}
		
		return allClients;
	}
}