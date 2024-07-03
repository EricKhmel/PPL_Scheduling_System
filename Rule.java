
public interface Rule {
	public String ruleText = "";
	public int ruleId = 0;
	boolean evaluate(Schedule schedule);
	public String getRuleText();
	
	public void setRuleText(String ruleText);
	
	public int getRuleId();
	
	public void setRuleId(int ruleId);
}