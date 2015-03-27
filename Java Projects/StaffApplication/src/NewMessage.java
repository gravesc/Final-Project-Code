import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
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

import javax.swing.JTextArea;

import java.awt.Color;

public class NewMessage extends JFrame {

	private JPanel contentPane;
	private JTextField txtTo;
	private static JTextField txtSubject;
	private static JTextArea txtrMessage;

	private static String clientUsername, subject, message;

	private static final String MessagesServerURL = "http://localhost:8888/General_Servers/MessagingServer";

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
	public NewMessage() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 400);
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

		JLabel lblNewMessage = new JLabel("New Message");
		lblNewMessage.setFont(new Font("Tahoma", Font.BOLD, 23));
		panel_1.add(lblNewMessage);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JLabel lblTo = new JLabel("To : ");
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(21, 30, 31, 14);
		panel_2.add(lblTo);

		JLabel lblSubject = new JLabel("Subject : ");
		lblSubject.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubject.setBounds(6, 55, 46, 14);
		panel_2.add(lblSubject);

		txtTo = new JTextField();
		txtTo.setText("Enter Username Here");
		txtTo.setBounds(54, 27, 308, 20);
		panel_2.add(txtTo);
		txtTo.setColumns(10);

		txtSubject = new JTextField();
		txtSubject.setBounds(54, 57, 308, 20);
		txtSubject.setText(subject);
		panel_2.add(txtSubject);
		txtSubject.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.setBounds(100, 272, 89, 23);
		panel_2.add(btnSend);

		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		txtrMessage = new JTextArea();
		txtrMessage.setText(message);
		txtrMessage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrMessage.setForeground(Color.BLACK);
		txtrMessage.setWrapStyleWord(true);
		txtrMessage.setLineWrap(true);
		txtrMessage.setBounds(6, 88, 356, 173);
		txtrMessage.setBorder(border);
		panel_2.add(txtrMessage);
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String to = removeSpaces(txtTo.getText());
				String subject = removeSpaces(txtSubject.getText());
				String message = removeSpaces(txtrMessage.getText());

				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String formattedDateTime = dateFormat.format(date);
				String dateTime = removeSpaces(formattedDateTime);

				try {
					connectClient(clientUsername, to, subject, message, dateTime);
					MainApplication.getSentData(MessagesServerURL + "?method=get&function=sent&username="
							+ clientUsername);
					JOptionPane.showMessageDialog(
							new JFrame(),
							"Your message has been successfully sent.",
							"Message Sent To "+to.toUpperCase(),
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

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(199, 272, 89, 23);
		panel_2.add(btnClear);

		
	}

	public static void connectClient(String sender, String recipient,
			String subject, String message, String dateTime)
			throws IllegalStateException, IOException {

		String url = MessagesServerURL + "?method=post&function=add&sender=" + sender + "&recipient="
				+ recipient + "&subject=" + subject + "&message=" + message
				+ "&dateTime=" + dateTime;
		
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

	public static void setClientUsername(String sentClientUsername) {
		clientUsername = sentClientUsername;
	}

	public static void setFieldValues(String subject, String message) {
		NewMessage.subject = subject;
		NewMessage.message= message;
	}
	
}