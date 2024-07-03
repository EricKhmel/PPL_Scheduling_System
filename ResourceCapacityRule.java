import java.util.*;

public class ResourceCapacityRule implements Rule {
		private String resourceName;
		private int value;
		private ComparisonOperator operator;
		String ruleText;
		int ruleId;
		
		public ResourceCapacityRule(String resourceName, int value, ComparisonOperator operator, String ruleText) {
			this.resourceName = resourceName;
			this.value = value;
			this.operator = operator;
			this.ruleText = ruleText;
			this.ruleId = 1;
		}

		@Override
		public boolean evaluate(Schedule schedule) {
			Resource resource = findResource(schedule, resourceName);

			if (resource == null) {
				return false; // Resource not found
			}

			int assignedTasksCount = resource.getTaskIds().length;

			switch (operator) {
			case LESS_THAN:
				return assignedTasksCount < value;
			case GREATER_THAN:
				return assignedTasksCount > value;
			case EQUAL:
				return assignedTasksCount == value;
			case LESS_THAN_OR_EQUAL:
				return assignedTasksCount <= value;
			case GREATER_THAN_OR_EQUAL:
				return assignedTasksCount >= value;
			default:
				return false; // Invalid operator
			}
		}

		private Resource findResource(Schedule schedule, String resourceName) {
			for (Resource resource : schedule.getResources()) {
				if (resource.getName().equals(resourceName)) {
					return resource;
				}
			}
			return null; // Resource not found
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