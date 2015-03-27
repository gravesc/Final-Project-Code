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
@WebServlet("/MaintanenceServer")
public class MaintanenceServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MaintanenceServer() {
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
		String modifySql = null;
		String deleteSql = null;

		PreparedStatement queryPS = null;
		PreparedStatement modifySqlPS = null;
		PreparedStatement deleteSqlPS = null;

		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("add")) {
				conn = connectToDatabase();

				query = "INSERT INTO maintanence_TBL(maintanence_id, house_id, description, severity, dateoccurred, datecompleted) "
						+ "VALUES (?,?,?,?,?,?)";

				String houseID = request.getParameter("houseID");
				String description = request.getParameter("description");
				String severity = request.getParameter("severity");
				String dateOccurred = request.getParameter("occurred");
				String dateCompleted = request.getParameter("completed");

				queryPS = null;
				conn = connectToDatabase();
				try {
					int currentHighID = getHighestID();

					queryPS = conn.prepareStatement(query);
					queryPS.setInt(1, currentHighID + 1);
					queryPS.setInt(2, Integer.parseInt(houseID));
					queryPS.setString(3, encodeString(description));
					queryPS.setString(4, encodeString(severity));
					queryPS.setString(5, encodeString(dateOccurred));
					queryPS.setString(6, encodeString(dateCompleted));
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
				String maintanenceID = request.getParameter("maintanenceID");
				deleteSql = "DELETE FROM Maintanence_TBL WHERE maintanence_id = ?";

				try {
					deleteSqlPS = conn.prepareStatement(deleteSql);
					deleteSqlPS.setInt(1, Integer.parseInt(maintanenceID));
					deleteSqlPS.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (request.getParameter("function").equalsIgnoreCase("modify")) {
				conn = connectToDatabase();
				String houseID = request.getParameter("houseID");
				String description = request.getParameter("description");
				String severity = request.getParameter("severity");
				String dateOccurred = request.getParameter("occurred");
				String dateCompleted = request.getParameter("completed");
				int maintanenceID = Integer.parseInt(request.getParameter("maintanenceID"));

				modifySql = "UPDATE maintanence_TBL SET house_id=?, description=?, severity=?, dateoccurred=?, datecompleted=? WHERE maintanence_id = ?";

				try {
					modifySqlPS = conn.prepareStatement(modifySql);

					modifySqlPS.setInt(1, Integer.parseInt(houseID));
					modifySqlPS.setString(2, encodeString(description));
					modifySqlPS.setString(3, encodeString(severity));
					modifySqlPS.setString(4, encodeString(dateOccurred));
					modifySqlPS.setString(5, encodeString(dateCompleted));
					modifySqlPS.setInt(6, maintanenceID);
					modifySqlPS.executeUpdate();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						modifySqlPS.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		} else if (request.getParameter("method").equalsIgnoreCase("get")) {

			// Determine the parameters passed to the servlet

			query = "select * from maintanence_tbl";

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
			fullJsonString += "\"maintanence\":[";
			ArrayList<MaintanenceObj> allMaintanence = new ArrayList<MaintanenceObj>();
			try {
				while (rs.next()) {
					int maintanenceID = rs.getInt("maintanence_id");
					int houseID = rs.getInt("house_id");
					String description = rs.getString("description");
					String severity = rs.getString("severity");
					String dateOccurred = rs.getString("dateoccurred");
					String dateCompleted = rs.getString("datecompleted");

					MaintanenceObj newMaintanence = new MaintanenceObj(maintanenceID,
							houseID, decodeString(description), decodeString(severity), decodeString(dateOccurred),
							decodeString(dateCompleted));
					allMaintanence.add(newMaintanence);

				}
				for (int i = 0; i < allMaintanence.size(); i++) {
					if (i == allMaintanence.size() - 1) {
						fullJsonString += "{\"1\":\""
								+ allMaintanence.get(i).getId() + "\","
								+ "\"2\":\""
								+ allMaintanence.get(i).getHouseID() + "\","
								+ "\"3\":\""
								+ allMaintanence.get(i).getDescription() + "\","
								+ "\"4\":\""
								+ allMaintanence.get(i).getSeverity() + "\","
								+ "\"5\":\"" 
								+ allMaintanence.get(i).getDateOccurred() + "\"," 
								+ "\"6\":\""
								+ allMaintanence.get(i).getDateCompleted() + "\"}";
					} else {
						fullJsonString += "{\"1\":\""
								+ allMaintanence.get(i).getId() + "\","
								+ "\"2\":\""
								+ allMaintanence.get(i).getHouseID() + "\","
								+ "\"3\":\""
								+ allMaintanence.get(i).getDescription() + "\","
								+ "\"4\":\""
								+ allMaintanence.get(i).getSeverity() + "\","
								+ "\"5\":\"" 
								+ allMaintanence.get(i).getDateOccurred() + "\"," 
								+ "\"6\":\""
								+ allMaintanence.get(i).getDateCompleted() + "\"},";
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
		String query = "Select MAX(maintanence_id) as highestID from maintanence_tbl";
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