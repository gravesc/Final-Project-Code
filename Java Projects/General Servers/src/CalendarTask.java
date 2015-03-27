

public class CalendarTask {
	String ID;
	String Name;
	String Type;
	String Description;
	String Address;
	String DateTime;
	String Important;
	
	public CalendarTask(String ID, String Name, String Type, String Desc, String Address, String DateTime, String Important)
	{
		this.ID   = ID;
		this.Name = Name;
		this.Type = Type;
		this.Description = Desc;
		this.Address = Address;
		this.DateTime = DateTime;
		this.Important = Important;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public String getImportant() {
		return Important;
	}

	public void setImportant(String important) {
		Important = important;
	}
}
