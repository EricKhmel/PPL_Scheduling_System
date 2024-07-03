import java.util.*;

public class Meeting {
	private String name;
	private List<Resource> Resources;
	private DateTime date;
	private String location;
	
	public Meeting(String name, DateTime date, String location) {
		this.name = name;
		this.date = date;
		this.location = location;
	}
	
	public boolean addResource(Resource r) {
		Resources.add(r);
		//System.out.println("Success!");
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public DateTime getDate() {
		return date;
	}
	
	public String getLocation() {
		return location;
	}
	
	public List<Resource> getResources() {
		return Resources;
	}
}
