import java.util.*;

public class DateTime {

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int amOrPm;

	public DateTime(int year, int month, int day, int hour, int minute, int amOrPm) {
		this.year =  year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.amOrPm = amOrPm;
	}

	public DateTime(int month, int day, int hour, int minute, int amOrPm) {
		this.year = 2023;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.amOrPm = amOrPm;
	}
	
	public DateTime(int day, int hour, int minute, int amOrPm) {
		this.year = 2023;
		this.month = 11;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.amOrPm = amOrPm;
	}
	
	public boolean equals(DateTime dt) {
		if(this.year == dt.year &&
				this.month == dt.month &&
				this.day == dt.day &&
				this.hour == dt.hour &&
				this.minute == dt.minute &&
				this.amOrPm == dt.amOrPm) {
			return true;
		}
		else { return false; }
	}
	
	public String ToString() {
		return Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year) + " " + ((this.hour<10) ? "0" + Integer.toString(hour) : Integer.toString(hour)) + ":" + Integer.toString(minute) + ((this.amOrPm==0) ? " a.m." : " p.m.");
	}
	
	public String ToStringDate() {
		return Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
	}
	
	public String ToStringTime() {
		return ((this.hour == 0) ? "12" : ((this.hour<10) ? ("0" + Integer.toString(hour)) : Integer.toString(hour))) + ":" + (this.minute<10 ? "0"+Integer.toString(minute) : Integer.toString(minute)) + ((this.amOrPm==0) ? " a.m." : " p.m.");
	}
	
	public long ToLong() {
	    return Long.parseLong(String.format("%04d%02d%02d%02d%02d%02d", year, month, day, hour, minute, amOrPm));
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) { 
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) { 
		this.month = month;
	}
	
	public int getDay() {
		return year;
	}
	
	public void setDay(int day) { 
		this.day = day;
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) { 
		this.hour = hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) { 
		this.minute = minute;
	}
	
	public int getAmOrPm() {
		return amOrPm;
	}
	
	public void setAmOrPm(int amOrPm) { 
		this.amOrPm = amOrPm;
	}
	
	public int getDayOfWeek() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month - 1, day + 1); 
	    return calendar.get(Calendar.DAY_OF_WEEK);
	}
}
