import java.util.*;

class Schedule {
	private int taskCapacity = 10;
	private List<Meeting> Meetings = new ArrayList<Meeting>();;
	private List<Resource> Resources = new ArrayList<Resource>();;
	private List<Task> Tasks = new ArrayList<Task>();
	public RulesEngine rulesEngine;

	public Schedule(int capacity) {
		this.taskCapacity = capacity;
		this.Resources = new ArrayList<>();
		this.Tasks = new ArrayList<>();
		this.rulesEngine = new RulesEngine();
	}

	public Schedule copySchedule() {
		int cap = taskCapacity;
	    Schedule copiedSchedule = new Schedule(cap);
	    
	    // Copy resources
	    for (Resource resource : getResources()) {
	        copiedSchedule.Resources.add(new Resource(resource.getId(), resource.getTasks(), resource.getName()));
	    }
	    
	    // Copy tasks
	    for (Task task : getTasks()) {
	        int taskId = task.getTaskId();
	        int resourceId = task.getResourceId();
	        String taskName = task.getName();
	        DateTime startTime = task.getStartDT();
	        DateTime endTime = task.getEndDT();
	        boolean assigned = task.getAssigned();
	        copiedSchedule.addAvailableTask(new Task(taskId, resourceId,taskName, startTime, endTime, assigned));
	    }
	    
	    // Copy rules
	    for (Rule rule : this.rulesEngine.getRules()) {
	        copiedSchedule.addRule(rule);
	    }
	    
	    // Copy meetings
	    for (Meeting meeting : getMeetings()) {
	        copiedSchedule.addMeeting(meeting);
	    }
	    
	    return copiedSchedule;
	}
	
	public void addRule(Rule rule) {
		rulesEngine.addRule(rule);
	}

	public boolean evaluateRules() {
		return rulesEngine.evaluateRules(this);
	}

	public boolean addResource(Resource r) {
		Resources.add(r);
		//System.out.println("Success!");
		return true;
	}
	
	public boolean addAvailableTask(Task t) {
		if(isTaskListFull()) {
			System.out.println("This schedules job list is full");
			return false;
		} else {
			Tasks.add(t);
			//System.out.println("Success");
		} return true;
	}
	
	public boolean addMeeting(Meeting m) {
		Meetings.add(m);
		return true;
	}

	public boolean removeResourceById(int id) {
		for(Resource r : Resources) {
			if(r.getId() == id) { 
				Resources.remove(r);
				System.out.println("Success");
				return true;
			}
		} return false;
	}

	public boolean removeResourceByObj(Resource r) {
		Resources.remove(r);
		System.out.println("Success");
		return true;
	}

	public boolean removeTask(int tId) {
		for(Task t : Tasks) {
			if(t.getTaskId() == tId) {
				Tasks.remove(t);
				if(t.getAssigned()) {
					getResourceById(t.getResourceId()).removeTaskById(tId);
					
				}
				return true;
			}
		}
		return false;
	}
	
	
	
	public boolean removeMeetingByName(String nameOfM) {
		for(Meeting m : Meetings) {
			if(m.getName().equals(nameOfM)) {
				Meetings.remove(m);
				return true;
			}
		}
		return false;
	}
	
	public Resource getResourceById(int rId) {
		for(Resource r: Resources) {
			if(r.getId() == rId) {return r;}
		} return null;
	}
	
	public Task getTaskById(int tId) {
		for(Task t: Tasks) {
			if(t.getTaskId() == tId) {return t;}
		} return null;
	}
	
	public Resource getResourceByName(String rName) {
		for(Resource r: Resources) {
			if(r.getName().equalsIgnoreCase(rName)) {return r;}
		} return null;
	}
	
	public Task getTaskByName(String tName) {
		for(Task t: Tasks) {
			if(t.getName().equalsIgnoreCase(tName)) {return t;}
		} return null;
	}

	public List<Resource> getResources() {
		return Resources;
	}
	
	public List<Task> getTasks() {
		return Tasks;
	}
	
	public List<Meeting> getMeetings() {
		return Meetings;
	}
	
	public RulesEngine getRulesEngine() {
		return rulesEngine;
	}
	
	
	public boolean isTaskListFull() {
		if(this.Tasks.size() >= this.taskCapacity) {return true;}
		else {return false;}
	}
	
	public boolean assignAvailableTaskById(int tId, int rId) {
		for(Task t : Tasks) {
			if(t.getTaskId() == tId) {
				for(Resource r : Resources) {
					if(r.getId() == rId) {
						r.addTask(t);
						return true;
					}
				}
			}
		} System.out.println("Task or Resource not Found");
		return false;
	}
	
	public boolean assignTask(Resource r, Task t) {
		if(r.addTask(t)) {
			return true;
		}
		return false;
	}
	
	public boolean unassignTasksById(int rId){
		int[] taskIds = getResourceById(rId).getTaskIds();
		for(int i = 0; i < taskIds.length; i++) {
			for(Task t : Tasks) {
				if(t.getTaskId() == taskIds[i]) {
					t.unassign(); 
					t.setResourceId(-1);
				}
			}
		} return true;
	}
	
	public void printResourcesAndTasks() {
		for(Resource r : Resources) {
			System.out.println("-----------------------------------------------------");
			System.out.println("Employee Id:   		   " + r.getId());
			System.out.println("Employee Name: 		   " + r.getName());
			System.out.println("Employee Availability:     " + (r.getAvailable() ? "Available":"Schedule Full"));
			System.out.println("Assigned Jobs: 		   ");
			r.printTasks();
			System.out.println("-----------------------------------------------------");
		}
	}

	public void printAllTasks() {
		for(Task t : Tasks) {
			System.out.println("-----------------------------------------------------");
			System.out.println("Job Name: 	       " + t.getName());
			System.out.println("Date of Job:           " + t.getStartDT().ToStringDate());
			System.out.println("Start Time:            " + t.getStartDT().ToStringTime());
			System.out.println("End Time: 	       " + t.getEndDT().ToStringTime() );
			System.out.println("Assigned: 	       " + (t.getAssigned() ? "yes" : "no"));
			if(t.getAssigned()) { System.out.println("Assigned Employee's Id:" + t.getResourceId()); } 
			System.out.println("-----------------------------------------------------");
		}
	}
	
	public String getStringResourcesAndTasks() {
	    StringBuilder sb = new StringBuilder();
	    for(Resource r : Resources) {
	        sb.append("-----------------------------------------------------\n");
	        sb.append("  Employee Id:                    " + r.getId() + "\n");
	        sb.append("  Employee Name:            " + r.getName() + "\n");
	        sb.append("  Employee Availability:     " + (r.getAvailable() ? "Available":"Schedule Full") + "\n");
	        sb.append("  Assigned Jobs: 		   \n");
	        sb.append(r.getStringTasks() + "\n");
	        sb.append("-----------------------------------------------------\n");
	    }
	    return sb.toString();
	}
	
	public String getStringAllTasks() {
	    StringBuilder sb = new StringBuilder();
	    for(Task t : Tasks) {
	        sb.append("-----------------------------------------------------\n");
	        sb.append("  Job Name: 	         " + t.getName() + "\n");
	        sb.append("  Date of Job:              " + t.getStartDT().ToStringDate() + "\n");
	        sb.append("  Start Time:                " + t.getStartDT().ToStringTime() + "\n");
	        sb.append("  End Time: 	         " + t.getEndDT().ToStringTime() + "\n");
	        sb.append("  Assigned: 	         " + (t.getAssigned() ? "yes" : "no") + "\n");
	        if(t.getAssigned()) { sb.append("  Assigned \n  Employee's Id:         " + t.getResourceId() + "\n"); } 
	        sb.append("-----------------------------------------------------\n");
	    }
	    return sb.toString();
	}
}