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


public class ReadMessage extends JFrame {

	private JPanel contentPane;
	private JTextField txtTo;
	private JTextField txtFrom;
	private JTextField txtSubject;
	public static String to;
	public static String from;
	public static String subject;
	public static String message;

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
	public ReadMessage() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblReadMessage = new JLabel("Read Message");
		lblReadMessage.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel.add(lblReadMessage);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblTo = new JLabel("To :");
		lblTo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTo.setBounds(10, 11, 70, 14);
		panel_1.add(lblTo);
		
		JLabel lblFrom = new JLabel("From :");
		lblFrom.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFrom.setBounds(10, 35, 70, 14);
		panel_1.add(lblFrom);
		
		JLabel lblSubject = new JLabel("Subject :");
		lblSubject.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSubject.setBounds(10, 60, 70, 14);
		panel_1.add(lblSubject);
		
		txtTo = new JTextField();
		txtTo.setBackground(UIManager.getColor("Button.background"));
		txtTo.setEditable(false);
		txtTo.setColumns(10);
		txtTo.setBounds(90, 8, 163, 20);
		txtTo.setText(to);
		panel_1.add(txtTo);
		
		
		txtFrom = new JTextField();
		txtFrom.setBackground(UIManager.getColor("Button.background"));
		txtFrom.setEditable(false);
		txtFrom.setColumns(10);
		txtFrom.setBounds(90, 32, 163, 20);
		txtFrom.setText(from);
		panel_1.add(txtFrom);
		
		txtSubject = new JTextField();
		txtSubject.setBackground(UIManager.getColor("Button.background"));
		txtSubject.setEditable(false);
		txtSubject.setColumns(10);
		txtSubject.setBounds(89, 57, 325, 20);
		txtSubject.setText(subject);
		panel_1.add(txtSubject);
		
		Border txtBorder = BorderFactory.createLineBorder(Color.BLACK);
		
		JTextArea txtMessage = new JTextArea();
		txtMessage.setBackground(UIManager.getColor("Button.background"));
		txtMessage.setEditable(false);
		txtMessage.setBounds(10, 85, 404, 118);
		txtMessage.setBorder(BorderFactory.createCompoundBorder(txtBorder, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		txtMessage.setText(message);
		panel_1.add(txtMessage);
	}
	
	static void setValues(String to, String from, String subject, String message)
	{
		ReadMessage.to = to;
		ReadMessage.from = from;
		ReadMessage.subject = subject;
		ReadMessage.message = message;
	}
}
