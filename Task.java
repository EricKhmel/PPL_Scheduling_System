import java.util.*;


class Task {
	private int taskId;
	private int resourceId;
	private String name;
	private DateTime startDT;
	private DateTime endDT;
	private boolean assigned;

	public Task(int taskId, int resourceId, String name, DateTime startDT, DateTime endDT, boolean assigned) {
		this.taskId = taskId;
		this.setResourceId(resourceId);
		this.name = name;
		this.startDT = startDT;
		this.endDT = endDT;
		this.assigned = assigned;
	}
	
	public Task(int taskId, int resourceId, String name, DateTime startDT, DateTime endDT) {
		this.taskId = taskId;
		this.setResourceId(resourceId);
		this.name = name;
		this.startDT = startDT;
		this.endDT = endDT;
		this.assigned = true;
	}

	public Task(int taskId, String name, DateTime startDT, DateTime endDT) {
		this.taskId = taskId;
		this.setResourceId(-1);
		this.name = name;
		this.startDT = startDT;
		this.endDT = endDT;
		this.assigned = false;
	}

	public int getTaskId() {
		return taskId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}

	public DateTime getStartDT() {
		return startDT;
	}

	public DateTime getEndDT() {
		return endDT;
	}

	public boolean getAssigned() {
		return assigned;
	}

	public boolean assign() {
		assigned = true;
		return true;
	}

	public boolean unassign() {
		assigned = false;
		resourceId = -1;
		return true;
	}

	public String getStartDTString() {
		String ret = startDT.ToString();
		return ret;
	}

	public String getEndDTString() {
		String ret = endDT.ToString();
		return ret;
	}

	
}