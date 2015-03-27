
public class MaintanenceObj {
	
	public int id, houseID;
	public String description, severity, dateOccurred, dateCompleted;
	
	public MaintanenceObj(int id, int houseID, String description, String severity, String dateOccurred, String dateCompleted) 
	{
		this.id = id;
		this.houseID = houseID;
		this.description = description;
		this.severity = severity;
		this.dateOccurred = dateOccurred;
		this.dateCompleted = dateCompleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseID() {
		return houseID;
	}

	public void setHouseID(int houseID) {
		this.houseID = houseID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDateOccurred() {
		return dateOccurred;
	}

	public void setDateOccurred(String dateOccurred) {
		this.dateOccurred = dateOccurred;
	}

	public String getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(String dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
}
