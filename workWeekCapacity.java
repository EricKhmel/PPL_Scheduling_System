import java.util.*;
import java.time.*;

public class workWeekCapacity implements Rule {
	private LocalDate currentDate = LocalDate.now();
	private int shiftCapacity;
	private int window = 8;
	String ruleText;
	int ruleId;

	public workWeekCapacity(int shiftCapacity, String ruleText) {
		this.shiftCapacity = shiftCapacity;
		this.ruleText = ruleText;
		this.ruleId = 4;
	}
	
	@Override
	public boolean evaluate(Schedule schedule) {
		for(Resource r : schedule.getResources()) {
			for(int i = 0; i < window; i++) {
				int count = 0;
				for(Task t : r.getTasks()) {
					if(isInWorkWeek(i, t)) {
						count++;
						if(count > shiftCapacity) { return false; }
					}
				}
			}
		}
		return true;
	}
	
	public boolean isInWorkWeek(int i, Task t) {
		LocalDate startDate = currentDate.plusWeeks(i);
		LocalDate endDate = currentDate.plusWeeks(i+1);
		
        int month1 = startDate.getMonthValue();
        int day1 = startDate.getDayOfMonth();
        int year1 = startDate.getYear();
        int month2 = endDate.getMonthValue();
        int day2 = endDate.getDayOfMonth();
        int year2 = endDate.getYear();
		
		DateTime startDT =  new DateTime(year1, month1, day1, 0, 0, 0);
		DateTime endDT =  new DateTime(year2, month2, day2, 0, 0, 0);
		
		if(t.getStartDT().ToLong() >= startDT.ToLong() && t.getStartDT().ToLong() <= endDT.ToLong()) {
			return true;
		}
		return false;
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
