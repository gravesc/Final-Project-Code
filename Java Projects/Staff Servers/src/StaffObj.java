
public class StaffObj {
	String fname, sname, address, jobTitle, username, password;
	int id, noOfChildren, salary, managerID;
	
	public StaffObj(int id, String fname, String sname, String address, String jobTitle, int salary, int managerID)
	{
		this.id = id;
		this.fname = fname;
		this.sname = sname;
		this.address = address;
		this.jobTitle = jobTitle;
		this.salary = salary;
		this.managerID = managerID;
	}
	
	public StaffObj(int id, String fname, String sname, String address, String jobTitle, int salary, int managerID, String username, String password)
	{
		this.id = id;
		this.fname = fname;
		this.sname = sname;
		this.address = address;
		this.jobTitle = jobTitle;
		this.salary = salary;
		this.managerID = managerID;
		this.username = username;
		this.password = password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
