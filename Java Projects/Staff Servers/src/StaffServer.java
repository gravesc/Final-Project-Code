import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet("/StaffServer")
public class StaffServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StaffServer() {
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
		String deleteSecurePersonSql = null;
		String deletePersonSql = null;

		PreparedStatement queryPS = null;
		PreparedStatement secureQueryPS = null;
		PreparedStatement modifySecurePersonSqlPS = null;
		PreparedStatement modifyPersonSqlPS = null;
		PreparedStatement deleteSecurePersonSqlPS = null;
		PreparedStatement deletePersonSqlPS = null;

		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("add")) {
				conn = connectToDatabase();

				query = "INSERT INTO PERSON_TBL(person_id, firstname, surname, person_address, staff_info) "
						+ "VALUES (?,?,?,address_typ(?,?,?,?,?),staff_typ(?,?,?))";

				secureQuery = "INSERT INTO SECURE_PERSON_TBL(person_id, per_username, per_password) "
						+ "VALUES (?,?,?)";

				String fName = request.getParameter("fname");
				String sName = request.getParameter("sname");
				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String county = request.getParameter("county");
				String country = request.getParameter("country");
				String postcode = request.getParameter("postcode");
				String jobTitle = request.getParameter("jobTitle");
				int salary = Integer.parseInt(request.getParameter("salary"));
				int manager = Integer.parseInt(request.getParameter("manager"));
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
					queryPS.setString(9, encodeString(jobTitle));
					queryPS.setInt(10, salary);
					queryPS.setInt(11, manager);
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
				deleteSecurePersonSql = "DELETE FROM SECURE_PERSON_TBL WHERE person_id = ?";
				deletePersonSql = "DELETE FROM PERSON_TBL WHERE person_id = ? and client_info is null";

				try {
					deletePersonSqlPS = conn.prepareStatement(deletePersonSql);
					deleteSecurePersonSqlPS = conn
							.prepareStatement(deleteSecurePersonSql);

					deleteSecurePersonSqlPS.setInt(1,
							Integer.parseInt(personID));
					deleteSecurePersonSqlPS.executeUpdate();

					deletePersonSqlPS.setInt(1, Integer.parseInt(personID));
					deletePersonSqlPS.executeUpdate();
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
				String jobTitle = request.getParameter("jobTitle");
				int salary = Integer.parseInt(request.getParameter("salary"));
				int manager = Integer.parseInt(request.getParameter("manager"));
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				int personID = Integer.parseInt(request
						.getParameter("personID"));

				modifySecurePersonSql = "UPDATE SECURE_PERSON_TBL SET per_username=?, per_password=? WHERE person_id = ?";
				modifyPersonSql = "UPDATE PERSON_TBL SET firstname=?, surname=?, person_address=address_typ(?,?,?,?,?), staff_info=staff_typ(?,?,?) WHERE person_id = ?";

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
					modifyPersonSqlPS.setString(8, encodeString(jobTitle));
					modifyPersonSqlPS.setInt(9, salary);
					modifyPersonSqlPS.setInt(10, manager);
					modifyPersonSqlPS.setInt(11, personID);
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
			if (request.getParameter("function").equalsIgnoreCase("staff")) {

			// Determine the parameters passed to the servlet

				query = "select * from person_tbl where client_info is null";

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
			fullJsonString += "\"staff\":[";
			ArrayList<StaffObj> allClients = new ArrayList<StaffObj>();
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
							+ decodeString(taskAddressTown) + ", " + decodeString(taskAddressCounty) + ", "
							+ decodeString(taskAddressCountry) + ", " + decodeString(taskAddressPostcode);
					Struct staffInfoStruct = (Struct) rs.getObject(6);
					Object[] staffInfo = staffInfoStruct.getAttributes();
					String jobTitle = staffInfo[0].toString();
					int salary = Integer.parseInt(staffInfo[1].toString());
					int manager = Integer.parseInt(staffInfo[2].toString());

					StaffObj newStaff = new StaffObj(personID, decodeString(firstName),
							decodeString(secondName), taskFullAddress, decodeString(jobTitle),
							salary, manager);
					allClients.add(newStaff);

				}
				for (int i = 0; i < allClients.size(); i++) {
					if (i == allClients.size() - 1) {
						fullJsonString += "{\"1\":\""
								+ allClients.get(i).getId() + "\","
								+ "\"2\":\"" 
								+ allClients.get(i).getFname() + "\"," 
								+ "\"3\":\""
								+ allClients.get(i).getSname() + "\","
								+ "\"4\":\""
								+ allClients.get(i).getAddress() + "\","
								+ "\"5\":\""
								+ allClients.get(i).getJobTitle() + "\","
								+ "\"6\":\""
								+ allClients.get(i).getSalary() + "\","
								+ "\"7\":\""
								+ allClients.get(i).getManagerID() + "\"}";
					} else {
						fullJsonString += "{\"1\":\""
								+ allClients.get(i).getId() + "\","
								+ "\"2\":\"" 
								+ allClients.get(i).getFname() + "\"," 
								+ "\"3\":\""
								+ allClients.get(i).getSname() + "\","
								+ "\"4\":\""
								+ allClients.get(i).getAddress() + "\","
								+ "\"5\":\""
								+ allClients.get(i).getJobTitle() + "\","
								+ "\"6\":\""
								+ allClients.get(i).getSalary() + "\","
								+ "\"7\":\""
								+ allClients.get(i).getManagerID() + "\"},";
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
			}else if (request.getParameter("function").equalsIgnoreCase("secureStaff")) {
				query = "select * from person_tbl p join secure_person_tbl s on p.person_id = s.person_id where client_info is null";

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
				fullJsonString += "\"staff\":[";
				ArrayList<StaffObj> allStaffSecure = new ArrayList<StaffObj>();
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
								+ decodeString(taskAddressTown) + ", " + decodeString(taskAddressCounty) + ", "
								+ decodeString(taskAddressCountry) + ", " + decodeString(taskAddressPostcode);
						Struct staffInfoStruct = (Struct) rs.getObject(6);
						Object[] staffInfo = staffInfoStruct.getAttributes();
						String jobTitle = staffInfo[0].toString();
						int salary = Integer.parseInt(staffInfo[1].toString());
						int manager = Integer.parseInt(staffInfo[2].toString());
						String username = rs.getString("per_username");
						String password = rs.getString("per_password");

						StaffObj newStaff = new StaffObj(personID, decodeString(firstName),
								decodeString(secondName), taskFullAddress, decodeString(jobTitle),
								salary, manager, decodeString(username), decodeString(password));
						allStaffSecure.add(newStaff);
					}
					
					for (int i = 0; i < allStaffSecure.size(); i++) {
						if (i == allStaffSecure.size() - 1) {
							fullJsonString += "{\"1\":\""
									+ allStaffSecure.get(i).getId() + "\","
									+ "\"2\":\"" 
									+ allStaffSecure.get(i).getFname()
									+ "\"," + "\"3\":\""
									+ allStaffSecure.get(i).getSname() + "\","
									+ "\"4\":\""
									+ allStaffSecure.get(i).getAddress() + "\","
									+ "\"5\":\""
									+ allStaffSecure.get(i).getJobTitle() + "\","
									+ "\"6\":\""
									+ allStaffSecure.get(i).getSalary() + "\","
									+ "\"7\":\""
									+ allStaffSecure.get(i).getManagerID() + "\","
									+ "\"8\":\""
									+ allStaffSecure.get(i).getUsername() + "\","
									+ "\"9\":\""
									+ allStaffSecure.get(i).getPassword() + "\"}";
						} else {
							fullJsonString += "{\"1\":\""
									+ allStaffSecure.get(i).getId() + "\","
									+ "\"2\":\"" 
									+ allStaffSecure.get(i).getFname()
									+ "\"," + "\"3\":\""
									+ allStaffSecure.get(i).getSname() + "\","
									+ "\"4\":\""
									+ allStaffSecure.get(i).getAddress() + "\","
									+ "\"5\":\""
									+ allStaffSecure.get(i).getJobTitle() + "\","
									+ "\"6\":\""
									+ allStaffSecure.get(i).getSalary() + "\","
									+ "\"7\":\""
									+ allStaffSecure.get(i).getManagerID() + "\","
									+ "\"8\":\""
									+ allStaffSecure.get(i).getUsername() + "\","
									+ "\"9\":\""
									+ allStaffSecure.get(i).getPassword() + "\"},";
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