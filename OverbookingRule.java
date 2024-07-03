import java.util.*;

public class OverbookingRule implements Rule {
	
	String ruleText;
	int ruleId;
	
	public OverbookingRule(String ruleText) {
		this.ruleText = ruleText;
		this.ruleId = 2;
	}
	
	@Override
	public boolean evaluate(Schedule schedule) {
		List<Resource> resources = schedule.getResources();
		for (Resource resource : resources) {
			int assignedTasksCount = resource.getTaskIds().length;
			int capacity = resource.getCapacity();

			if (assignedTasksCount > capacity) {
				System.out.println("Resource '" + resource.getName() + "' is overbooked.");
				return false; // Rule violation detected
			}
		}
		return true; // All resources are within their capacity
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