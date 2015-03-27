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
@WebServlet("/HousesServer")
public class HousesServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HousesServer() {
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

		PreparedStatement queryPS = null;
		PreparedStatement secureQueryPS = null;

		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("addHouse")) {
				conn = connectToDatabase();

				query = "INSERT INTO HOUSES_TBL(house_id, house_address, house_type, status, landlord_id, price_per_month) "
						+ "VALUES (?,address_typ(?,?,?,?,?),?,?,?,?)";

				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String county = request.getParameter("county");
				String country = request.getParameter("country");
				String postcode = request.getParameter("postcode");
				String type = request.getParameter("type");
				String status = request.getParameter("status");
				String landlord = request.getParameter("landlord");
				String price = request.getParameter("price");
				int personID = Integer.parseInt(request.getParameter("personID"));

				queryPS = null;
				secureQueryPS = null;
				conn = connectToDatabase();
				try {
					int currentHighID = getHighestID();

					queryPS = conn.prepareStatement(query);
					queryPS.setInt(1, currentHighID + 1);
					queryPS.setString(2, encodeString(street));
					queryPS.setString(3, encodeString(town));
					queryPS.setString(4, encodeString(county));
					queryPS.setString(5, encodeString(country));
					queryPS.setString(6, encodeString(postcode));
					queryPS.setString(7, encodeString(type));
					queryPS.setString(8, encodeString(status));
					queryPS.setString(9, landlord);
					queryPS.setString(10, price);
					queryPS.executeUpdate();
					
					queryPS.close();
					conn.close();

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			} else if (request.getParameter("function").equalsIgnoreCase(
					"addInterestedHouse")) {
				conn = connectToDatabase();

				secureQuery = "INSERT INTO INTERESTED_HOUSES_TBL(house_id, person_id) "
						+ "VALUES (?,?)";

				String house = request.getParameter("house_id");
				String person = request.getParameter("person_id");

				queryPS = null;
				secureQueryPS = null;
				conn = connectToDatabase();
				try {
					secureQueryPS = conn.prepareStatement(secureQuery);
					secureQueryPS.setInt(1, Integer.parseInt(house));
					secureQueryPS.setInt(2, Integer.parseInt(person));
					secureQueryPS.executeUpdate();

					secureQueryPS.close();
					conn.close();

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
			
			else if (request.getParameter("function")
					.equalsIgnoreCase("deleteInterested")) {
				String houseID = request.getParameter("houseId");
				String personID = request.getParameter("personId");
				PreparedStatement deleteHouseSqlPS = null;
				try {
					conn = connectToDatabase();
					String deleteHouseSql = "DELETE FROM INTERESTED_HOUSES_TBL WHERE house_id = ? AND person_id = ?";

					deleteHouseSqlPS = conn.prepareStatement(deleteHouseSql);
					deleteHouseSqlPS.setInt(1, Integer.parseInt(houseID));
					deleteHouseSqlPS.setInt(2, Integer.parseInt(personID));
					deleteHouseSqlPS.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					deleteHouseSqlPS.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (request.getParameter("function").equalsIgnoreCase("delete")) {
				conn = connectToDatabase();
				String houseID = request.getParameter("houseID");
				
				String deleteHouseSql = "DELETE FROM HOUSES_TBL WHERE house_id = ?";
				String deleteAllInterestedSql = "DELETE FROM INTERESTED_HOUSES_TBL WHERE house_id = ?";
				try {
					PreparedStatement deleteAllInterestedSqlPS = conn.prepareStatement(deleteAllInterestedSql);
					PreparedStatement deleteHouseSqlPS = conn.prepareStatement(deleteHouseSql);
					
					deleteAllInterestedSqlPS.setInt(1, Integer.parseInt(houseID));
					deleteAllInterestedSqlPS.executeUpdate();
					
					deleteHouseSqlPS.setInt(1, Integer.parseInt(houseID));
					deleteHouseSqlPS.executeUpdate();
					
					deleteAllInterestedSqlPS.close();
					deleteHouseSqlPS.close();
					
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			if (request.getParameter("function").equalsIgnoreCase("modify")) {
				conn = connectToDatabase();
				String personID = request.getParameter("personID");
				String street = request.getParameter("street");
				String town = request.getParameter("town");
				String county = request.getParameter("county");
				String country = request.getParameter("country");
				String postcode = request.getParameter("postcode");
				String type = request.getParameter("type");
				String status = request.getParameter("status");
				int landlord = Integer.parseInt(request.getParameter("landlord"));
				int price = Integer.parseInt(request.getParameter("price"));
				int houseID = Integer.parseInt(request.getParameter("houseID"));
				
				
				String modifyHouseSql = "UPDATE HOUSES_TBL SET house_address=address_typ(?,?,?,?,?), house_type=?, status=?, landlord_id=?, price_per_month=? WHERE house_id = ?";
				PreparedStatement modifyPersonSqlPS = null;
				try {
					modifyPersonSqlPS = conn.prepareStatement(modifyHouseSql);
					
					modifyPersonSqlPS.setString(1, encodeString(street));
					modifyPersonSqlPS.setString(2, encodeString(town));
					modifyPersonSqlPS.setString(3, encodeString(county));
					modifyPersonSqlPS.setString(4, encodeString(country));
					modifyPersonSqlPS.setString(5, encodeString(postcode));
					modifyPersonSqlPS.setString(6,encodeString(type));
					modifyPersonSqlPS.setString(7, encodeString(status));
					modifyPersonSqlPS.setInt(8, landlord);
					modifyPersonSqlPS.setInt(9, price);
					modifyPersonSqlPS.setInt(10, houseID);
					modifyPersonSqlPS.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
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
			if (request.getParameter("function").equalsIgnoreCase("houses")) {

				// Determine the parameters passed to the servlet

				String getQuery = "select h.house_id, h.house_address, h.house_type, h.status, l.firstname, l.surname, h.price_per_month from houses_tbl h join landlord_tbl l on l.landlord_id = h.landlord_id";

				conn = connectToDatabase();
				Statement stmt = null;
				ResultSet getRs = null;
				
				response.setContentType("application/json");
				fullJsonString = "{";
				fullJsonString += "\"houses\":[";
				ArrayList<HouseObj> allHouses = new ArrayList<HouseObj>();
				
				try {
					stmt = conn.createStatement();
					getRs = stmt.executeQuery(getQuery);
					
					while (getRs.next()) {
						int houseID = getRs.getInt(1);
						Struct address = (Struct) getRs.getObject(2);
						Object[] taskAddress = address.getAttributes();
						String houseAddressStreet = taskAddress[0].toString();
						String houseAddressTown = taskAddress[1].toString();
						String houseAddressCounty = taskAddress[2].toString();
						String houseAddressCountry = taskAddress[3].toString();
						String houseAddressPostcode = taskAddress[4].toString();
						String houseFullAddress = decodeString(houseAddressStreet) + ", "
								+ decodeString(houseAddressTown) + ", " 
								+ decodeString(houseAddressCounty) + ", " 
								+ decodeString(houseAddressCountry) + ", "
								+ decodeString(houseAddressPostcode);
						String type = getRs.getString(3);
						String status = getRs.getString(4);
						String landlord = decodeString(getRs.getString(5)) + " "
								+ decodeString(getRs.getString(6));
						int price = getRs.getInt(7);

						HouseObj newClient = new HouseObj(houseID,
								houseFullAddress, decodeString(type), decodeString(status), landlord, price);
						allHouses.add(newClient);
						

					}
					for (int i = 0; i < allHouses.size(); i++) {
						if (i == allHouses.size() - 1) {
							fullJsonString += "{\"1\":\""
									+ allHouses.get(i).getHouseId() + "\","
									+ "\"2\":\""
									+ allHouses.get(i).getAddress() + "\","
									+ "\"3\":\""
									+ allHouses.get(i).getType() + "\","
									+ "\"4\":\""
									+ allHouses.get(i).getStatus() + "\","
									+ "\"5\":\""
									+ allHouses.get(i).getLandlord() + "\","
									+ "\"6\":\""
									+ allHouses.get(i).getPrice() + "\"}";
						} else {
							fullJsonString += "{\"1\":\""
									+ allHouses.get(i).getHouseId() + "\","
									+ "\"2\":\""
									+ allHouses.get(i).getAddress() + "\","
									+ "\"3\":\""
									+ allHouses.get(i).getType() + "\","
									+ "\"4\":\""
									+ allHouses.get(i).getStatus() + "\","
									+ "\"5\":\""
									+ allHouses.get(i).getLandlord() + "\","
									+ "\"6\":\""
									+ allHouses.get(i).getPrice() + "\"},";
						}

					}
					fullJsonString += "]}";
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// info parameter is present, so is request for last position
				// data
				// Return current data (JSON format)
				


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
					"interestedHouses")) {
				String person_id = request.getParameter("personId");
				query = "select h.house_id, h.house_address, h.house_type, h.status, l.firstname, l.surname, h.price_per_month from HOUSES_TBL h join INTERESTED_houses_tbl i on h.house_id = i.house_id join landlord_tbl l on h.LANDLORD_ID = l.LANDLORD_ID join PERSON_TBL p on i.person_id = p.person_id where p.PERSON_ID ="
						+ person_id;

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
				fullJsonString += "\"houses\":[";
				ArrayList<HouseObj> allClientsSecure = new ArrayList<HouseObj>();
				try {
					while (rs.next()) {
						int houseID = rs.getInt("house_id");
						Struct address = (Struct) rs.getObject(2);
						Object[] taskAddress = address.getAttributes();
						String houseAddressStreet = taskAddress[0].toString();
						String houseAddressTown = taskAddress[1].toString();
						String houseAddressCounty = taskAddress[2].toString();
						String houseAddressCountry = taskAddress[3].toString();
						String houseAddressPostcode = taskAddress[4].toString();
						String houseFullAddress = decodeString(houseAddressStreet) + ", "
								+ decodeString(houseAddressTown) + ", " 
								+ decodeString(houseAddressCounty) + ", " 
								+ decodeString(houseAddressCountry) + ", "
								+ decodeString(houseAddressPostcode);
						String type = rs.getString("house_type");
						String status = rs.getString("status");
						String landlord = decodeString(rs.getString("firstname")) + " "
								+ decodeString(rs.getString("surname"));
						int price = rs.getInt("price_per_month");

						HouseObj newClient = new HouseObj(houseID,
								houseFullAddress, decodeString(type), decodeString(status), landlord, price);
						allClientsSecure.add(newClient);

					}
					for (int i = 0; i < allClientsSecure.size(); i++) {
						if (i == allClientsSecure.size() - 1) {
							fullJsonString += "{\"1\":\""
									+ allClientsSecure.get(i).getHouseId()
									+ "\"," + "\"2\":\""
									+ allClientsSecure.get(i).getAddress()
									+ "\"," + "\"3\":\""
									+ allClientsSecure.get(i).getType() + "\","
									+ "\"4\":\""
									+ allClientsSecure.get(i).getStatus()
									+ "\"," + "\"5\":\""
									+ allClientsSecure.get(i).getLandlord()
									+ "\"," + "\"6\":\""
									+ allClientsSecure.get(i).getPrice()
									+ "\"}";
						} else {
							fullJsonString += "{\"1\":\""
									+ allClientsSecure.get(i).getHouseId()
									+ "\"," + "\"2\":\""
									+ allClientsSecure.get(i).getAddress()
									+ "\"," + "\"3\":\""
									+ allClientsSecure.get(i).getType() + "\","
									+ "\"4\":\""
									+ allClientsSecure.get(i).getStatus()
									+ "\"," + "\"5\":\""
									+ allClientsSecure.get(i).getLandlord()
									+ "\"," + "\"6\":\""
									+ allClientsSecure.get(i).getPrice()
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
		String query = "Select MAX(house_id) as highestID from houses_tbl";
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