import java.util.*;

public class checkDaysOff implements Rule {

	String ruleText;
	int ruleId;
	
	public checkDaysOff(String ruleText) {
		this.ruleText = ruleText;
		this.ruleId = 5;
	}
	
	@Override
	public boolean evaluate(Schedule schedule) {
		for(Resource r : schedule.getResources()) {
			for(Task t : r.getTasks()) {
				for(DateTime dt : r.getDaysOff()) {
					if((t.getStartDT().getYear() == dt.getYear() && t.getStartDT().getMonth() == dt.getMonth() && t.getStartDT().getDay() == dt.getDay()) || 
							(t.getEndDT().getYear() == dt.getYear() && t.getEndDT().getMonth() == dt.getMonth() && t.getEndDT().getDay() == dt.getDay())){
						return false;		
					}
				}
			}
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
