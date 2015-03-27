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

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Servlet implementation class UploadLocation
 */
@WebServlet("/ClientLoginServer")
public class ClientLoginServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClientLoginServer() {
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
		String query = null;
		String secureQuery = null;
		String modifySecurePersonSql = null;
		String modifyPersonSql = null;
		String deleteSecureTaskSql = null;
		String deleteTaskSql = null;

		PreparedStatement queryPS = null;
		PreparedStatement secureQueryPS = null;
		PreparedStatement modifySecurePersonSqlPS = null;
		PreparedStatement modifyPersonSqlPS = null;
		PreparedStatement deleteSecureTaskSqlPS = null;
		PreparedStatement deleteTaskSqlPS = null;

		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("add")) {
				conn = connectToDatabase();

				query = "INSERT INTO PERSON_TBL(person_id, firstname, surname, person_address, client_info) "
						+ "VALUES (?,?,?,address_typ(?,?,?,?,?),client_typ(?,?))";

				secureQuery = "INSERT INTO SECURE_PERSON_TBL(person_id, per_username, per_password) "
						+ "VALUES (?,?,?)";

				String fName = request.getParameter("fname");
				String sName = request.getParameter("sname");
				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String county = request.getParameter("county");
				String country = request.getParameter("country");
				String postcode = request.getParameter("postcode");
				String marital = request.getParameter("marital");
				String children = request.getParameter("children");
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				queryPS = null;
				secureQueryPS = null;
				conn = connectToDatabase();
				try {
					int currentHighID = getHighestID();

					queryPS = conn.prepareStatement(query);
					queryPS.setInt(1, currentHighID + 1);
					queryPS.setString(2, encodeString(fName));
					queryPS.setString(3, encodeString(sName));
					queryPS.setString(4, encodeString(street));
					queryPS.setString(5, encodeString(town));
					queryPS.setString(6, encodeString(county));
					queryPS.setString(7, encodeString(country));
					queryPS.setString(8, encodeString(postcode));
					queryPS.setString(9, encodeString(marital));
					queryPS.setInt(10, Integer.parseInt(children));
					queryPS.executeUpdate();

					queryPS.close();

					secureQueryPS = conn.prepareStatement(secureQuery);
					secureQueryPS.setInt(1, currentHighID + 1);
					secureQueryPS.setString(2, encodeString(username));
					secureQueryPS.setString(3, encodeString(password));
					secureQueryPS.executeUpdate();

					secureQueryPS.close();
					
					conn.close();

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
			if (request.getParameter("function").equalsIgnoreCase("delete")) {
				conn = connectToDatabase();
				String personID = request.getParameter("personID");
				deleteSecureTaskSql = "DELETE FROM SECURE_PERSON_TBL WHERE person_id = ?";
				deleteTaskSql = "DELETE FROM PERSON_TBL WHERE person_id = ? and staff_info is null";

				try {
					deleteSecureTaskSqlPS = conn
							.prepareStatement(deleteSecureTaskSql);
					deleteSecureTaskSqlPS.setInt(1, Integer.parseInt(personID));
					deleteSecureTaskSqlPS.executeUpdate();

					deleteTaskSqlPS = conn.prepareStatement(deleteTaskSql);
					deleteTaskSqlPS.setInt(1, Integer.parseInt(personID));
					deleteTaskSqlPS.executeUpdate();

					deleteSecureTaskSqlPS.close();
					deleteTaskSqlPS.close();
					
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (request.getParameter("function").equalsIgnoreCase("modify")) {
				conn = connectToDatabase();
				String fName = request.getParameter("fname");
				String sName = request.getParameter("sname");
				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String county = request.getParameter("county");
				String country = request.getParameter("country");
				String postcode = request.getParameter("postcode");
				String marital = request.getParameter("marital");
				int children = Integer.parseInt(request
						.getParameter("children"));
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				int personID = Integer.parseInt(request
						.getParameter("personID"));
				
				modifySecurePersonSql = "UPDATE SECURE_PERSON_TBL SET per_username=?, per_password=? WHERE person_id = ?";
				modifyPersonSql = "UPDATE PERSON_TBL SET firstname=?, surname=?, person_address=address_typ(?,?,?,?,?), client_info=client_typ(?,?) WHERE person_id = ?";

				try {
					modifyPersonSqlPS = conn.prepareStatement(modifyPersonSql);
					modifySecurePersonSqlPS = conn
							.prepareStatement(modifySecurePersonSql);

					modifySecurePersonSqlPS.setString(1, encodeString(username));
					modifySecurePersonSqlPS.setString(2, encodeString(password));
					modifySecurePersonSqlPS.setInt(3, personID);
					modifySecurePersonSqlPS.executeUpdate();

					modifyPersonSqlPS.setString(1, encodeString(fName));
					modifyPersonSqlPS.setString(2, encodeString(sName));
					modifyPersonSqlPS.setString(3, encodeString(street));
					modifyPersonSqlPS.setString(4, encodeString(town));
					modifyPersonSqlPS.setString(5, encodeString(county));
					modifyPersonSqlPS.setString(6, encodeString(country));
					modifyPersonSqlPS.setString(7, encodeString(postcode));
					modifyPersonSqlPS.setString(8, encodeString(marital));
					modifyPersonSqlPS.setInt(9, children);
					modifyPersonSqlPS.setInt(10, personID);
					modifyPersonSqlPS.executeUpdate();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						modifySecurePersonSqlPS.close();
						modifyPersonSqlPS.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (request.getParameter("method").equalsIgnoreCase("get")) {
			if (request.getParameter("function").equalsIgnoreCase("client")) {

				// Determine the parameters passed to the servlet

				query = "select * from person_tbl where staff_info is null";

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

				// info parameter is present, so is request for last
				// position data
				// Return current data (JSON format)
				response.setContentType("application/json");

				fullJsonString = "{";
				fullJsonString += "\"clients\":[";
				ArrayList<ClientObj> allClients = new ArrayList<ClientObj>();
				try {
					while (rs.next()) {
						int personID = rs.getInt("person_id");
						String firstName = rs.getString("firstname");
						String secondName = rs.getString("surname");
						Struct address = (Struct) rs.getObject(4);
						Object[] taskAddress = address.getAttributes();
						String taskAddressStreet = taskAddress[0].toString();
						String taskAddressTown = taskAddress[1].toString();
						String taskAddressCounty = taskAddress[2].toString();
						String taskAddressCountry = taskAddress[3].toString();
						String taskAddressPostcode = taskAddress[4].toString();
						String taskFullAddress = decodeString(taskAddressStreet) + ", "
								+ decodeString(taskAddressTown) + ", " 
								+ decodeString(taskAddressCounty)+ ", " 
								+ decodeString(taskAddressCountry) + ", "
								+ decodeString(taskAddressPostcode);
						Struct clientInfoStruct = (Struct) rs.getObject(5);
						Object[] clientInfo = clientInfoStruct.getAttributes();
						String maritalStatus = clientInfo[0].toString();
						int noOfChildren = Integer.parseInt(clientInfo[1]
								.toString());

						ClientObj newClient = new ClientObj(personID,
								decodeString(firstName), decodeString(secondName), taskFullAddress,
										decodeString(maritalStatus), noOfChildren);
						allClients.add(newClient);

					}
					for (int i = 0; i < allClients.size(); i++) {
						if (i == allClients.size() - 1) {
							fullJsonString += "{\"1\":\""
									+ allClients.get(i).getId() + "\","
									+ "\"2\":\"" + allClients.get(i).getFname()
									+ "\"," + "\"3\":\""
									+ allClients.get(i).getSname() + "\","
									+ "\"4\":\""
									+ allClients.get(i).getAddress() + "\","
									+ "\"5\":\""
									+ allClients.get(i).getMaritalStatus()
									+ "\"," + "\"6\":\""
									+ allClients.get(i).getNoOfChildren()
									+ "\"}";
						} else {
							fullJsonString += "{\"1\":\""
									+ allClients.get(i).getId() + "\","
									+ "\"2\":\"" + allClients.get(i).getFname()
									+ "\"," + "\"3\":\""
									+ allClients.get(i).getSname() + "\","
									+ "\"4\":\""
									+ allClients.get(i).getAddress() + "\","
									+ "\"5\":\""
									+ allClients.get(i).getMaritalStatus()
									+ "\"," + "\"6\":\""
									+ allClients.get(i).getNoOfChildren()
									+ "\"},";
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
			} else if (request.getParameter("function").equalsIgnoreCase(
					"secureClient")) {
				
				query = "select * from person_tbl p join secure_person_tbl s on p.person_id = s.person_id and staff_info is null";

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

				// info parameter is present, so is request for last
				// position data
				// Return current data (JSON format)
				response.setContentType("application/json");

				fullJsonString = "{";
				fullJsonString += "\"clients\":[";
				ArrayList<ClientObj> allClientsSecure = new ArrayList<ClientObj>();
				try {
					while (rs.next()) {
						int personID = rs.getInt("person_id");
						String firstName = rs.getString("firstname");
						String secondName = rs.getString("surname");
						Struct address = (Struct) rs.getObject(4);
						Object[] taskAddress = address.getAttributes();
						String taskAddressStreet = taskAddress[0].toString();
						String taskAddressTown = taskAddress[1].toString();
						String taskAddressCounty = taskAddress[2].toString();
						String taskAddressCountry = taskAddress[3].toString();
						String taskAddressPostcode = taskAddress[4].toString();
						String taskFullAddress = decodeString(taskAddressStreet) + ", "
								+ decodeString(taskAddressTown) + ", " + decodeString(taskAddressCounty)
								+ ", " + decodeString(taskAddressCountry) + ", "
								+ decodeString(taskAddressPostcode);
						Struct clientInfoStruct = (Struct) rs.getObject(5);
						Object[] clientInfo = clientInfoStruct.getAttributes();
						String maritalStatus = clientInfo[0].toString();
						int noOfChildren = Integer.parseInt(clientInfo[1]
								.toString());
						String username = rs.getString("per_username");
						String password = rs.getString("per_password");

						ClientObj newClient = new ClientObj(personID,
								decodeString(firstName), decodeString(secondName), taskFullAddress,
										decodeString(maritalStatus), noOfChildren, decodeString(username), decodeString(password));
						allClientsSecure.add(newClient);

					}
					for (int i = 0; i < allClientsSecure.size(); i++) {
						if (i == allClientsSecure.size() - 1) {
							fullJsonString += "{\"1\":\""
									+ allClientsSecure.get(i).getId()
									+ "\","
									+ "\"2\":\""
									+ allClientsSecure.get(i).getFname()
									+ "\","
									+ "\"3\":\""
									+ allClientsSecure.get(i).getSname()
									+ "\","
									+ "\"4\":\""
									+ allClientsSecure.get(i).getAddress()
									+ "\","
									+ "\"5\":\""
									+ allClientsSecure.get(i)
											.getMaritalStatus() + "\","
									+ "\"6\":\""
									+ allClientsSecure.get(i).getNoOfChildren()
									+ "\"," + "\"7\":\""
									+ allClientsSecure.get(i).getUsername()
									+ "\"," + "\"8\":\""
									+ allClientsSecure.get(i).getPassword()
									+ "\"}";
						} else {
							fullJsonString += "{\"1\":\""
									+ allClientsSecure.get(i).getId()
									+ "\","
									+ "\"2\":\""
									+ allClientsSecure.get(i).getFname()
									+ "\","
									+ "\"3\":\""
									+ allClientsSecure.get(i).getSname()
									+ "\","
									+ "\"4\":\""
									+ allClientsSecure.get(i).getAddress()
									+ "\","
									+ "\"5\":\""
									+ allClientsSecure.get(i).getMaritalStatus() + "\","
									+ "\"6\":\""
									+ allClientsSecure.get(i).getNoOfChildren()
									+ "\"," + "\"7\":\""
									+ allClientsSecure.get(i).getUsername()
									+ "\"," + "\"8\":\""
									+ allClientsSecure.get(i).getPassword()
									+ "\"},";
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

	public int getHighestID() throws SQLException {
		Connection conn = connectToDatabase();
		String query = "Select MAX(person_id) as highestID from person_tbl";
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
		while (rs.next()) {
			result = rs.getInt(1);
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