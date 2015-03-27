

public class CalendarTask {
	
	String ID;
	String Name;
	String Type;
	String Description;
	String Address;
	String DateTime;
	String Important;

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getAddress(){
		return Address;
	}
	
	public void setAddress(String Address){
		this.Address = Address;
	}
	
	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime (String DateTime) {
		this.DateTime = DateTime;
	}
	
	public String getImportant() {
		return Important;
	}

	public void setImportant (String Important) {
		this.Important = Important;
	}
	
	/*public String toString()
	{
		return "Name : "+this.taskName
				+ "\nDescription : "+this.taskDescription
				+ "\nDateTime : "+this.taskDateTime;
	}*/
}
