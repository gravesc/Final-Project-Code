
public class ClientObj {
	String fname, sname, address, maritalStatus, username, password;
	int id, noOfChildren;
	
	public ClientObj(int id, String fname, String sname, String address, String maritalStatus, int noOfChildren)
	{
		this.id = id;
		this.fname = fname;
		this.sname = sname;
		this.address = address;
		this.maritalStatus = maritalStatus;
		this.noOfChildren = noOfChildren;
	}
	
	public ClientObj(int id, String fname, String sname, String address, String maritalStatus, int noOfChildren, String username, String password)
	{
		this.id = id;
		this.fname = fname;
		this.sname = sname;
		this.address = address;
		this.maritalStatus = maritalStatus;
		this.noOfChildren = noOfChildren;
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

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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
