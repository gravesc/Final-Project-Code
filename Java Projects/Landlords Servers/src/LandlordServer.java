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
@WebServlet("/LandlordServer")
public class LandlordServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LandlordServer() {
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
		String modifyPersonSql = null;
		String deleteSecurePersonSql = null;
		String deletePersonSql = null;

		PreparedStatement queryPS = null;
		PreparedStatement modifyPersonSqlPS = null;
		PreparedStatement deleteSecurePersonSqlPS = null;
		PreparedStatement deletePersonSqlPS = null;

		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("add")) {
				conn = connectToDatabase();

				query = "INSERT INTO landlord_TBL(landlord_id, firstname, surname, landlord_address, home_phone, mobile_phone, email) "
						+ "VALUES (?,?,?,address_typ(?,?,?,?,?),?,?,?)";

				String fName = request.getParameter("fname");
				String sName = request.getParameter("sname");
				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String county = request.getParameter("county");
				String country = request.getParameter("country");
				String postcode = request.getParameter("postcode");
				String homePhone = request.getParameter("homePhone");
				String mobilePhone = request.getParameter("mobilePhone");
				String email = request.getParameter("email");

				queryPS = null;
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
					queryPS.setString(9, encodeString(homePhone));
					queryPS.setString(10, encodeString(mobilePhone));
					queryPS.setString(11, email);
					queryPS.executeUpdate();

					queryPS.close();
					conn.close();

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}

			if (request.getParameter("function").equalsIgnoreCase("delete")) {
				conn = connectToDatabase();
				String landlordID = request.getParameter("landlordID");
				deleteSecurePersonSql = "DELETE FROM HOUSES_TBL WHERE landlord_id = ?";
				deletePersonSql = "DELETE FROM LANDLORD_TBL WHERE landlord_id = ?";

				try {
					deletePersonSqlPS = conn.prepareStatement(deletePersonSql);
					deleteSecurePersonSqlPS = conn
							.prepareStatement(deleteSecurePersonSql);

					deleteSecurePersonSqlPS.setInt(1,
							Integer.parseInt(landlordID));
					deleteSecurePersonSqlPS.executeUpdate();

					deletePersonSqlPS.setInt(1, Integer.parseInt(landlordID));
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
				String homePhone = request.getParameter("homePhone");
				String mobilePhone = request.getParameter("mobilePhone");
				String email = request.getParameter("email");
				int landlordID = Integer.parseInt(request.getParameter("landlordID"));

				modifyPersonSql = "UPDATE landlord_TBL SET firstname=?, surname=?, landlord_address=address_typ(?,?,?,?,?), home_phone=?, mobile_phone=?, email=? WHERE landlord_id = ?";

				try {
					modifyPersonSqlPS = conn.prepareStatement(modifyPersonSql);

					modifyPersonSqlPS.setString(1, encodeString(fName));
					modifyPersonSqlPS.setString(2, encodeString(sName));
					modifyPersonSqlPS.setString(3, encodeString(street));
					modifyPersonSqlPS.setString(4, encodeString(town));
					modifyPersonSqlPS.setString(5, encodeString(county));
					modifyPersonSqlPS.setString(6, encodeString(country));
					modifyPersonSqlPS.setString(7, encodeString(postcode));
					modifyPersonSqlPS.setString(8, encodeString(homePhone));
					modifyPersonSqlPS.setString(9, encodeString(mobilePhone));
					modifyPersonSqlPS.setString(10, encodeString(email));
					modifyPersonSqlPS.setInt(11, landlordID);
					modifyPersonSqlPS.executeUpdate();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						modifyPersonSqlPS.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		} else if (request.getParameter("method").equalsIgnoreCase("get")) {

			// Determine the parameters passed to the servlet

			query = "select * from landlord_tbl";

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

			// info parameter is present, so is request for last position
			// data
			// Return current data (JSON format)
			response.setContentType("application/json");

			fullJsonString = "{";
			fullJsonString += "\"landlords\":[";
			ArrayList<LandlordObj> allLandlords = new ArrayList<LandlordObj>();
			try {
				while (rs.next()) {
					int landlordID = rs.getInt("landlord_id");
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
					String homePhone = rs.getString("home_phone");
					String mobilePhone = rs.getString("mobile_phone");
					String email = rs.getString("email");

					LandlordObj newLandlord = new LandlordObj(landlordID,
							decodeString(firstName), decodeString(secondName), taskFullAddress, decodeString(homePhone),
									decodeString(mobilePhone), decodeString(email));
					allLandlords.add(newLandlord);

				}
				for (int i = 0; i < allLandlords.size(); i++) {
					if (i == allLandlords.size() - 1) {
						fullJsonString += "{\"1\":\""
								+ allLandlords.get(i).getId() + "\","
								+ "\"2\":\""
								+ allLandlords.get(i).getFname() + "\","
								+ "\"3\":\""
								+ allLandlords.get(i).getSname() + "\","
								+ "\"4\":\""
								+ allLandlords.get(i).getAddress() + "\","
								+ "\"5\":\"" 
								+ allLandlords.get(i).getHome() + "\"," 
								+ "\"6\":\""
								+ allLandlords.get(i).getMobile() + "\","
								+ "\"7\":\""
								+ allLandlords.get(i).getEmail() + "\"}";
					} else {
						fullJsonString += "{\"1\":\""
								+ allLandlords.get(i).getId() + "\","
								+ "\"2\":\""
								+ allLandlords.get(i).getFname() + "\","
								+ "\"3\":\""
								+ allLandlords.get(i).getSname() + "\","
								+ "\"4\":\""
								+ allLandlords.get(i).getAddress() + "\","
								+ "\"5\":\"" 
								+ allLandlords.get(i).getHome() + "\"," 
								+ "\"6\":\""
								+ allLandlords.get(i).getMobile() + "\","
								+ "\"7\":\""
								+ allLandlords.get(i).getEmail() + "\"},";
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
		String query = "Select MAX(landlord_id) as highestID from landlord_tbl";
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