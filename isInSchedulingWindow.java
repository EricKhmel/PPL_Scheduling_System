import java.util.*;
import java.time.*;

public class isInSchedulingWindow implements Rule {
	private LocalDate currentDate = LocalDate.now();
	
	// Window from the current day that tasks can be in
	private int weeks;
	String ruleText;
	int ruleId;
	
	public isInSchedulingWindow(int weeks, String ruleText) {
		this.weeks = weeks;
		this.ruleText = ruleText;
		this.ruleId = 3;
	}
	
	@Override
	public boolean evaluate(Schedule schedule) {
		List<Task> tasks = schedule.getTasks();
		for(Task t: tasks) {
			DateTime dt = t.getStartDT();
			DateTime futureDT = calculateEndDate();
			if(dt.ToLong() > futureDT.ToLong()) {
				return false;
			}
		}
		return true;
	}
	
	public DateTime calculateEndDate() {
		
        // Add a certain amount of weeks
        int numberOfWeeksToAdd = weeks;
        LocalDate futureDate = currentDate.plusWeeks(numberOfWeeksToAdd);

        // Extract month, day, and year as integers
        int month = futureDate.getMonthValue();
        int day = futureDate.getDayOfMonth();
        int year = futureDate.getYear();
		
        // Create DateTime object for comparison 
		return new DateTime(year, month, day, 0, 0, 0);
	}
	
	public String getRuleText(){
		return ruleText;
	}
	
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}
	
	public int getRuleId(){
		return ruleId;
	}
	
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

}
