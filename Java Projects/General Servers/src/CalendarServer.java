import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Date;

/**
 * Servlet implementation class UploadLocation
 */
@WebServlet("/CalendarServer")
public class CalendarServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String fullJsonString;
	String personID, taskID, taskName, taskType, taskDesc, addressStreet,
			addressTown, addressCounty, addressCountry, addressPostcode,
			taskDateTime;
	String taskImportant;

	ArrayList<CalendarTask> allTasks = new ArrayList<CalendarTask>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalendarServer() {
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
		String modifyTaskSql = null;
		String deletePerTaskSql = null;
		String deleteTaskSql = null;
		String insertTaskSql = null;
		String insertUserSql = null;

		PreparedStatement insertTaskSqlPS = null;
		PreparedStatement modifyTaskSqlPS = null;
		PreparedStatement deletePerTaskSqlPS = null;
		PreparedStatement deleteTaskSqlPS = null;
		PreparedStatement insertUserSqlPS = null;

		if (request.getParameter("method").equalsIgnoreCase("post")) {
			if (request.getParameter("function").equalsIgnoreCase("add")) {
				conn = connectToDatabase();

				personID = request.getParameter("personID");
				taskName = request.getParameter("taskName");
				taskType = request.getParameter("taskType");
				taskDesc = request.getParameter("taskDesc");
				addressStreet = request.getParameter("addressStreet");
				addressTown = request.getParameter("addressTown");
				addressCounty = request.getParameter("addressCounty");
				addressCountry = request.getParameter("addressCountry");
				addressPostcode = request.getParameter("addressPostcode");
				taskDateTime = request.getParameter("taskDateTime");
				taskImportant = request.getParameter("important");

				insertTaskSql = "INSERT INTO TASKS_TBL(task_id, task_name, task_type, description, task_address, date_time, important) "
						+ "VALUES (?,?,?,?,address_typ(?,?,?,?,?),?,?)";
				insertUserSql = "INSERT INTO person_tasks_tbl (task_id, person_id) "
						+ "VALUES (?,?)";

				try {

					insertTaskSqlPS = conn.prepareStatement(insertTaskSql);
					insertUserSqlPS = conn.prepareStatement(insertUserSql);

					int currentHighest = getHighestID();
					
					insertTaskSqlPS.setInt(1,currentHighest +1);
					insertTaskSqlPS.setString(2, encodeString(taskName));
					insertTaskSqlPS.setString(3, encodeString(taskType));
					insertTaskSqlPS.setString(4, encodeString(taskDesc));
					insertTaskSqlPS.setString(5, encodeString(addressStreet));
					insertTaskSqlPS.setString(6, encodeString(addressTown));
					insertTaskSqlPS.setString(7, encodeString(addressCounty));
					insertTaskSqlPS.setString(8, encodeString(addressCountry));
					insertTaskSqlPS.setString(9, encodeString(addressPostcode));
					insertTaskSqlPS.setString(10, encodeString(taskDateTime));
					insertTaskSqlPS.setString(11, encodeString(taskImportant));
					insertTaskSqlPS.executeUpdate();

					insertUserSqlPS.setInt(1, currentHighest + 1);
					insertUserSqlPS.setInt(2, Integer.parseInt(personID));
					insertUserSqlPS.executeUpdate();
					insertTaskSqlPS.close();
					insertUserSqlPS.close();
					conn.close();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else if (request.getParameter("function")
					.equalsIgnoreCase("delete")) {
				taskID = request.getParameter("taskID");
				personID = request.getParameter("personID");

				try {
					conn = connectToDatabase();
					deletePerTaskSql = "DELETE FROM PERSON_TASKS_TBL WHERE task_id = ? AND person_id = ?";
					deleteTaskSql = "DELETE FROM TASKS_TBL WHERE task_id = ?";
					
					deletePerTaskSqlPS = conn.prepareStatement(deletePerTaskSql);
					deletePerTaskSqlPS.setInt(1, Integer.parseInt(taskID));
					deletePerTaskSqlPS.setInt(2, Integer.parseInt(personID));
					deletePerTaskSqlPS.executeUpdate();
					
					deleteTaskSqlPS = conn.prepareStatement(deleteTaskSql);
					deleteTaskSqlPS.setInt(1, Integer.parseInt(taskID));
					deleteTaskSqlPS.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					deletePerTaskSqlPS.close();
					deleteTaskSqlPS.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else if (request.getParameter("function")
					.equalsIgnoreCase("modify")) {
				taskID = request.getParameter("taskID");

				try {
					conn = connectToDatabase();
					modifyTaskSql = "UPDATE TASKS_TBL SET task_name=?, task_type=?, description=?, task_address=address_typ(?,?,?,?,?), date_time=?, important=? WHERE task_id = ?";

					taskName = request.getParameter("taskName");
					taskType = request.getParameter("taskType");
					taskDesc = request.getParameter("taskDesc");
					addressStreet = request.getParameter("addressStreet");
					addressTown = request.getParameter("addressTown");
					addressCounty = request.getParameter("addressCounty");
					addressCountry = request.getParameter("addressCountry");
					addressPostcode = request.getParameter("addressPostcode");
					taskDateTime = request.getParameter("taskDateTime");
					taskImportant = request.getParameter("important");
					taskID = request.getParameter("taskID");

					modifyTaskSqlPS = conn.prepareStatement(modifyTaskSql);

					modifyTaskSqlPS.setString(1, encodeString(taskName));
					modifyTaskSqlPS.setString(2, encodeString(taskType));
					modifyTaskSqlPS.setString(3, encodeString(taskDesc));
					modifyTaskSqlPS.setString(4, encodeString(addressStreet));
					modifyTaskSqlPS.setString(5, encodeString(addressTown));
					modifyTaskSqlPS.setString(6, encodeString(addressCounty));
					modifyTaskSqlPS.setString(7, encodeString(addressCountry));
					modifyTaskSqlPS.setString(8, encodeString(addressPostcode));
					modifyTaskSqlPS.setString(9, encodeString(taskDateTime));
					modifyTaskSqlPS.setString(10, encodeString(taskImportant));
					modifyTaskSqlPS.setString(11, taskID);
					modifyTaskSqlPS.executeUpdate();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					modifyTaskSqlPS.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (request.getParameter("method").equalsIgnoreCase("get")) {

			// Determine the parameters passed to the servlet
			String personID = request.getParameter("personID");
			String query = "select t.task_id, t.task_name, t.task_type, t.description, t.task_address, t.date_time, t.important from tasks_tbl t join person_tasks_tbl pt on t.task_id = pt.task_id where pt.person_id = "+personID;
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
			fullJsonString += "\"tasks\":[";

			ArrayList<CalendarTask> allTasks = new ArrayList<CalendarTask>();
			String taskDateTime;
			try {
				while (rs.next()) {
					int taskIDInt = rs.getInt("task_id");
					String taskID = taskIDInt + "";
					String taskName = rs.getString("task_name");
					String taskType = rs.getString("task_type");
					String taskDesc = rs.getString("description");
					Struct address = (Struct) rs.getObject(5);
					Object[] taskAddress = address.getAttributes();
					String taskAddressStreet = taskAddress[0].toString();
					String taskAddressTown = taskAddress[1].toString();
					String taskAddressCounty = taskAddress[2].toString();
					String taskAddressCountry = taskAddress[3].toString();
					String taskAddressPostcode = taskAddress[4].toString();
					String taskFullAddress = decodeString(taskAddressStreet) + ", "
							+ decodeString(taskAddressTown) + ", " 
							+ decodeString(taskAddressCounty) + ", "
							+ decodeString(taskAddressCountry) + ", " 
							+ decodeString(taskAddressPostcode);
					taskDateTime = rs.getString("date_time");
					String taskImportant = rs.getString("important");

					CalendarTask newTask = new CalendarTask(taskID, decodeString(taskName),
							decodeString(taskType), decodeString(taskDesc), taskFullAddress, decodeString(taskDateTime),
									decodeString(taskImportant));
					allTasks.add(newTask);

				}
				for (int i = 0; i < allTasks.size(); i++) {
					if (i == allTasks.size() - 1) {
						fullJsonString += "{\"id\":\""
								+ allTasks.get(i).getID() + "\","
								+ "\"name\":\"" + allTasks.get(i).getName()
								+ "\"," + "\"type\":\""
								+ allTasks.get(i).getType() + "\","
								+ "\"desc\":\""
								+ allTasks.get(i).getDescription() + "\","
								+ "\"address\":\""
								+ allTasks.get(i).getAddress() + "\","
								+ "\"dateTime\":\""
								+ allTasks.get(i).getDateTime() + "\","
								+ "\"important\":\""
								+ allTasks.get(i).getImportant() + "\"}";
					} else {
						fullJsonString += "{\"id\":\""
								+ allTasks.get(i).getID() + "\","
								+ "\"name\":\"" + allTasks.get(i).getName()
								+ "\"," + "\"type\":\""
								+ allTasks.get(i).getType() + "\","
								+ "\"desc\":\""
								+ allTasks.get(i).getDescription() + "\","
								+ "\"address\":\""
								+ allTasks.get(i).getAddress() + "\","
								+ "\"dateTime\":\""
								+ allTasks.get(i).getDateTime() + "\","
								+ "\"important\":\""
								+ allTasks.get(i).getImportant() + "\"},";
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
	
	public int getHighestID() throws SQLException
	{
		Connection conn = connectToDatabase();
		String query = "Select MAX(task_id) as highestID from tasks_tbl";
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