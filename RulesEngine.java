import java.util.*;

class RulesEngine {
	private static List<Rule> rules;

	public RulesEngine() {
		rules = new ArrayList<>();
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	public List<Rule> getRules() {
		return rules;
	}

	public static boolean evaluateRules(Schedule schedule) {
		for (Object rule : rules) {
			if (rule instanceof ResourceCapacityRule) {
				if (!((ResourceCapacityRule) rule).evaluate(schedule)) {
					return false; // Rule violation detected
				}
			} else if (rule instanceof OverbookingRule) {
				if (!((OverbookingRule) rule).evaluate(schedule)) {
					return false; // Rule violation detected
				}
			} else if (rule instanceof isInSchedulingWindow) {
				if (!((isInSchedulingWindow) rule).evaluate(schedule)) {
					return false; // Rule violation detected
				}
			} else if (rule instanceof workWeekCapacity) {
				if (!((workWeekCapacity) rule).evaluate(schedule)) {
					return false; // Rule violation detected
				}
			} else if (rule instanceof checkDaysOff) {
				if (!((checkDaysOff) rule).evaluate(schedule)) {
					return false; // Rule violation detected
				}
			}
		}
		return true; // All rules passed
	}

	public Rule createCustomRule(String input) {
		String[] parts = input.split(" ");

		if (parts.length > 4) {
			System.out.println("Invalid rule format.");
			return null;
		}

		if(contains(parts, "resourceCapacity")) {
			String resourceName = parts[1];
			String operator = parts[2];
			int value;
	
			try {
				value = Integer.parseInt(parts[3]);
			} catch (NumberFormatException e) {
				System.out.println("Invalid rule format. Value must be an integer.");
				return null;
			}
	
			switch (operator) {
				case "<":
					return new ResourceCapacityRule(resourceName, value, ComparisonOperator.LESS_THAN, input);
				case ">":
					return new ResourceCapacityRule(resourceName, value, ComparisonOperator.GREATER_THAN, input);
				case "=":
					return new ResourceCapacityRule(resourceName, value, ComparisonOperator.EQUAL, input);
				case "<=":
					return new ResourceCapacityRule(resourceName, value, ComparisonOperator.LESS_THAN_OR_EQUAL, input);
				case ">=":
					return new ResourceCapacityRule(resourceName, value, ComparisonOperator.GREATER_THAN_OR_EQUAL, input);
				default:
					System.out.println("Invalid operator. Supported operators are <, >, =, <=, >=.");
					return null;
			}
		} else if(contains(parts, "isInSchedulingWindow")) {
			return new isInSchedulingWindow(Integer.parseInt(parts[1]), input);
		} else if(contains(parts, "workWeekCapacity")) {
			return new workWeekCapacity(Integer.parseInt(parts[1]), input);
		} else if(contains(parts, "OverbookingRule")) {
			return new OverbookingRule(input);
		} else if(contains(parts, "checkDaysOff")) {
			return new checkDaysOff(input);
		} else { 
			System.out.println("Invalid rule format.");
			return null; 
		}
	}
	
	public static boolean contains(String[] sList, String sInput) {
		for(String s : sList) {
			if(s.equals(sInput)) { return true; }
		}
		return false;
	}
	
	public boolean removeRuleByText(String text) {
		for(Rule r : getRules()) {
			if(r.getRuleText().equalsIgnoreCase(text)) {
				rules.remove(r);
				return true;
			}
		}
		return false;
	}
	
	public String getRulesString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("  List of current rules:\n");
	    for (Rule r : rules) {
	        if (r == null) {
	            return sb.toString();
	        } else {
	            sb.append("    -    -    -    -\n  ID:        " + r.getRuleId() + "\n");
	            sb.append("  Rule:   " + r.getRuleText() + "\n");
	        }
	    }
	    return sb.toString();
	}
}
