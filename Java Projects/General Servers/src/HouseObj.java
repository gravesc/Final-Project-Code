
public class HouseObj {
	int houseId, price;
	String address, type, status, landlord;
	
	public HouseObj(int houseId, String address, String type, String status, String landlord, int price)
	{
		this.houseId = houseId;
		this.address = address;
		this.type = type;
		this.status = status;
		this.landlord = landlord;
		this.price = price;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getLandlord() {
		return landlord;
	}

	public void setLandlord(String landlord) {
		this.landlord = landlord;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
