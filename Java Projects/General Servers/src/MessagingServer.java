import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.tomcat.util.codec.binary.Base64;

import oracle.sql.*;

/**
 * Servlet implementation class UploadLocation
 */

@WebServlet("/MessagingServer")
public class MessagingServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessagingServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// doPost(request, response);
		response.setStatus(HttpServletResponse.SC_OK);

		Connection conn = null;
		String insertMessageSQL = null;
		String saveMessageSQL = null;
		String deleteSavedMessageSQL = null;

		PreparedStatement insertMessageSQLPS = null;
		PreparedStatement saveMessageSQLPS = null;
		PreparedStatement deleteSavedMessageSQLPS = null;
		
		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("add")) {
				conn = connectToDatabase();

				String personID = request.getParameter("personID");
				String sender = request.getParameter("sender");
				String recipient = request.getParameter("recipient");
				String subject = request.getParameter("subject");
				String message = request.getParameter("message");
				String dateTime = request.getParameter("dateTime");
				

				insertMessageSQL = "INSERT INTO messages_TBL(message_id, sender_username, recipient_username, subject, message, date_time)"
						+ "VALUES (?,?,?,?,?,?)";
			
				try {
					insertMessageSQLPS = conn.prepareStatement(insertMessageSQL);

					int currentHighID = getHighestID();
					
					insertMessageSQLPS.setInt(1, currentHighID+1);
					insertMessageSQLPS.setString(2, encodeString(sender));
					insertMessageSQLPS.setString(3, encodeString(recipient));
					insertMessageSQLPS.setString(4, encodeString(subject));
					insertMessageSQLPS.setString(5, encodeString(message));
					insertMessageSQLPS.setString(6, encodeString(dateTime));
					insertMessageSQLPS.executeUpdate();

					insertMessageSQLPS.close();
					
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			else if (request.getParameter("function")
					.equalsIgnoreCase("saved")) {
				String messageID = request.getParameter("messageID");
				String personID = request.getParameter("personID");
				
				try {
					conn = connectToDatabase();
					saveMessageSQL = "INSERT INTO saved_messages_TBL(message_id, person_id)"
						+ "VALUES (?,?)";

					saveMessageSQLPS = conn.prepareStatement(saveMessageSQL);
					saveMessageSQLPS.setInt(1, Integer.parseInt(messageID));
					saveMessageSQLPS.setInt(2, Integer.parseInt(personID));
					saveMessageSQLPS.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					saveMessageSQLPS.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if (request.getParameter("function")
					.equalsIgnoreCase("delete")) {
				String messageID = request.getParameter("messageID");
				String personID = request.getParameter("personID");
				
				try {
					conn = connectToDatabase();
					deleteSavedMessageSQL = "DELETE FROM saved_messages_tbl WHERE message_id = ? and person_id = ?";

					deleteSavedMessageSQLPS = conn.prepareStatement(deleteSavedMessageSQL);
					deleteSavedMessageSQLPS.setInt(1, Integer.parseInt(messageID));
					deleteSavedMessageSQLPS.setInt(2, Integer.parseInt(personID));
					deleteSavedMessageSQLPS.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					deleteSavedMessageSQLPS.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else if (request.getParameter("method").equalsIgnoreCase("get")) {
			if (request.getParameter("function").equalsIgnoreCase("inbox"))
			{

				// Determine the parameters passed to the servlet
	
				String username = request.getParameter("username");
				
				String query = "select * from messages_tbl where recipient_username = '"+encodeString(username)+"'";
	
				conn = connectToDatabase();
				Statement stmt = null;
				ResultSet rs = null;
	
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				// info parameter is present, so is request for last position data
				// Return current data (JSON format)
				response.setContentType("application/json");
	
				fullJsonString = "{";
				fullJsonString += "\"messages\":[";
	
				ArrayList<MessageObj> allMessages = new ArrayList<MessageObj>();
				try {
					while (rs.next()) {
						int messageID = rs.getInt("message_id");
						String sender = rs.getString("sender_username");
						String recipient = rs.getString("recipient_username");
						String subject = rs.getString("subject");
						String message = rs.getString("message");
						String dateTime = rs.getString("date_time");
	
						MessageObj newTask = new MessageObj(messageID, decodeString(sender), decodeString(recipient), decodeString(subject),
								decodeString(message), decodeString(dateTime));
						allMessages.add(newTask);
	
					}
					for (int i = 0; i < allMessages.size(); i++) {
						if (i == allMessages.size() - 1) {
							fullJsonString += "{\"messageID\":\""
									+ allMessages.get(i).getMessageID() + "\","
									+ "\"sender\":\"" 
									+ allMessages.get(i).getSender() + "\","
									+ "\"recipient\":\""
									+ allMessages.get(i).getRecipient() + "\","
									+ "\"subject\":\""
									+ allMessages.get(i).getSubject() + "\","
									+ "\"message\":\""
									+ allMessages.get(i).getMessage() + "\","
									+ "\"dateTime\":\""
									+ allMessages.get(i).getDateTime() + "\"}";
						} else {
							fullJsonString += "{\"messageID\":\""
									+ allMessages.get(i).getMessageID() + "\","
									+ "\"sender\":\"" 
									+ allMessages.get(i).getSender() + "\","
									+ "\"recipient\":\""
									+ allMessages.get(i).getRecipient() + "\","
									+ "\"subject\":\""
									+ allMessages.get(i).getSubject() + "\","
									+ "\"message\":\""
									+ allMessages.get(i).getMessage() + "\","
									+ "\"dateTime\":\""
									+ allMessages.get(i).getDateTime() + "\"},";
						}
	
					}
					fullJsonString += "]}";
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				finally {
					if (stmt != null) {
						try {
							stmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						PrintWriter out = response.getWriter();
						out.print(fullJsonString);
						out.close();
					}
				}
			} 
			
			else if(request.getParameter("function").equalsIgnoreCase("saved"))
			{
				// Determine the parameters passed to the servlet
				
				String personID = request.getParameter("personID");
				
				String query = "select m.MESSAGE_ID, m.SENDER_USERNAME, m.RECIPIENT_USERNAME, m.subject, m.MESSAGE, m.DATE_TIME from messages_tbl m join saved_messages_tbl s on m.MESSAGE_ID = s.MESSAGE_ID where s.person_id = "+personID;
				
				conn = connectToDatabase();
				Statement stmt = null;
				ResultSet rs = null;
	
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				// info parameter is present, so is request for last position data
				// Return current data (JSON format)
				response.setContentType("application/json");
	
				fullJsonString = "{";
				fullJsonString += "\"messages\":[";
	
				ArrayList<MessageObj> allMessages = new ArrayList<MessageObj>();
				try {
					while (rs.next()) {
						int messageID = rs.getInt("message_id");
						String sender = rs.getString("sender_username");
						String recipient = rs.getString("recipient_username");
						String subject = rs.getString("subject");
						String message = rs.getString("message");
						String dateTime = rs.getString("date_time");
	
						MessageObj newTask = new MessageObj(messageID, decodeString(sender), decodeString(recipient), decodeString(subject),
								decodeString(message), decodeString(dateTime));
						allMessages.add(newTask);
	
					}
					for (int i = 0; i < allMessages.size(); i++) {
						if (i == allMessages.size() - 1) {
							fullJsonString += "{\"messageID\":\""
									+ allMessages.get(i).getMessageID() + "\","
									+ "\"sender\":\"" 
									+ allMessages.get(i).getSender() + "\","
									+ "\"recipient\":\""
									+ allMessages.get(i).getRecipient() + "\","
									+ "\"subject\":\""
									+ allMessages.get(i).getSubject() + "\","
									+ "\"message\":\""
									+ allMessages.get(i).getMessage() + "\","
									+ "\"dateTime\":\""
									+ allMessages.get(i).getDateTime() + "\"}";
						} else {
							fullJsonString += "{\"messageID\":\""
									+ allMessages.get(i).getMessageID() + "\","
									+ "\"sender\":\"" 
									+ allMessages.get(i).getSender() + "\","
									+ "\"recipient\":\""
									+ allMessages.get(i).getRecipient() + "\","
									+ "\"subject\":\""
									+ allMessages.get(i).getSubject() + "\","
									+ "\"message\":\""
									+ allMessages.get(i).getMessage() + "\","
									+ "\"dateTime\":\""
									+ allMessages.get(i).getDateTime() + "\"},";
						}
	
					}
					fullJsonString += "]}";
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				finally {
					if (stmt != null) {
						try {
							stmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						PrintWriter out = response.getWriter();
						out.print(fullJsonString);
						out.close();
					}
				}
			}
			
			else if(request.getParameter("function").equalsIgnoreCase("sent"))
			{
				// Determine the parameters passed to the servlet
				
				String username = request.getParameter("username");
				
				String query = "select * from messages_tbl where sender_username = '"+encodeString(username)+"'";
	
				conn = connectToDatabase();
				Statement stmt = null;
				ResultSet rs = null;
	
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				// info parameter is present, so is request for last position data
				// Return current data (JSON format)
				response.setContentType("application/json");
	
				fullJsonString = "{";
				fullJsonString += "\"messages\":[";
	
				ArrayList<MessageObj> allMessages = new ArrayList<MessageObj>();
				try {
					while (rs.next()) {
						int messageID = rs.getInt("message_id");
						String sender = rs.getString("sender_username");
						String recipient = rs.getString("recipient_username");
						String subject = rs.getString("subject");
						String message = rs.getString("message");
						String dateTime = rs.getString("date_time");
	
						MessageObj newTask = new MessageObj(messageID, decodeString(sender), decodeString(recipient), decodeString(subject),
								decodeString(message), decodeString(dateTime));
						allMessages.add(newTask);
	
					}
					for (int i = 0; i < allMessages.size(); i++) {
						if (i == allMessages.size() - 1) {
							fullJsonString += "{\"messageID\":\""
									+ allMessages.get(i).getMessageID() + "\","
									+ "\"sender\":\"" 
									+ allMessages.get(i).getSender() + "\","
									+ "\"recipient\":\""
									+ allMessages.get(i).getRecipient() + "\","
									+ "\"subject\":\""
									+ allMessages.get(i).getSubject() + "\","
									+ "\"message\":\""
									+ allMessages.get(i).getMessage() + "\","
									+ "\"dateTime\":\""
									+ allMessages.get(i).getDateTime() + "\"}";
						} else {
							fullJsonString += "{\"messageID\":\""
									+ allMessages.get(i).getMessageID() + "\","
									+ "\"sender\":\"" 
									+ allMessages.get(i).getSender() + "\","
									+ "\"recipient\":\""
									+ allMessages.get(i).getRecipient() + "\","
									+ "\"subject\":\""
									+ allMessages.get(i).getSubject() + "\","
									+ "\"message\":\""
									+ allMessages.get(i).getMessage() + "\","
									+ "\"dateTime\":\""
									+ allMessages.get(i).getDateTime() + "\"},";
						}
	
					}
					fullJsonString += "]}";
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				finally {
					if (stmt != null) {
						try {
							stmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						PrintWriter out = response.getWriter();
						out.print(fullJsonString);
						out.close();
					}
				}
			}
		}
	}

	@SuppressWarnings("finally")
	public Connection connectToDatabase() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String URL = "jdbc:oracle:thin:@localhost:1521/xe";
			String USER = "ChrisGraves";
			String PASS = "cgwg1993";
			conn = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} finally {
			return conn;
		}
	}
	
	public int getHighestID() throws SQLException
	{
		Connection conn = connectToDatabase();
		String query = "Select MAX(message_id) as highestID from messages_tbl";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int result = 0;
		while(rs.next())
		{
			result=rs.getInt(1);
		}
		conn.close();
		return result;

	}
	
	public String encodeString(String origString)
	{
		byte[] encodedString = Base64.encodeBase64(origString.getBytes());
		return new String(encodedString);
	}
	
	public String decodeString(String encodedString)
	{
		byte[] origString = Base64.decodeBase64(encodedString.getBytes());
		return new String(origString);
	}
}