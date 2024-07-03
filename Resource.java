import java.util.*;

class Resource {
	private int id;
	private int capacity = 10;
	private List<Task> Tasks = new ArrayList<Task>();
	private List<DateTime> daysOff = new ArrayList<DateTime>();
	private String name;
	private boolean available;

	public Resource(int id, String name) {
		this.id = id;
		this.name = name;
		this.available = true;
	}
	
	public Resource(int id, List<Task> tasks, String name) {
		this.id = id;
		this.Tasks = tasks;
		this.name = name;
		this.available = true;
	}

	public boolean addTask(Task t) {
		if (isFull()) {
			System.out.println("This employees schedule is full");
			return false;
		} else {
			for (Task task : Tasks) {
				if (((task.getStartDT().ToLong() < t.getStartDT().ToLong())
						&& (task.getEndDT().ToLong() > t.getStartDT().ToLong()))
						|| ((task.getStartDT().ToLong() < t.getEndDT().ToLong())
						&& (task.getEndDT().ToLong() > t.getEndDT().ToLong()))) {
					System.out.println("This employee already has a job during this time.");
					return false;
				}
			}
			Tasks.add(t);
			t.assign();
			//System.out.println("Success");
			t.setResourceId(this.id);
			if (Tasks.size() == capacity) {
				available = false;
			}
		}
		return true;
	}

	public boolean removeTaskById(int tId) {
		for(Task t : Tasks) {
			if(t.getTaskId() == tId) {
				Tasks.remove(t);
				return true;
			};
		} return false;
	}

	public boolean removeTaskByName(String tName) {
		for(Task t : Tasks) {
			if(t.getName().equalsIgnoreCase(tName)) {
				Tasks.remove(t);
				return true;
			};
		} return false;
	}
	
	
	public boolean isFull() {
		if (Tasks.size() >= capacity) {
			return true;
		} else {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean getAvailable() {
		return available;
	}

	public int[] getTaskIds() {
		int[] taskIds = new int[Tasks.size()];
		int count = 0;
		for (Task t : Tasks) {
			taskIds[count] = t.getTaskId();
			count++;
		}
		return taskIds;
	}
	
	public List<Task> getTasks(){
		return Tasks;
	}

	public List<DateTime> getDaysOff(){
		return daysOff;
	}
	
	public boolean containsTask(int tId) {
		for (Task t : Tasks) {
			if (t.getTaskId() == tId) {
				return true;
			}
		}
		return false;
	}

	public void printTasks() {
		for (Task task : Tasks) {
			if (task == null) {
				return;
			} else {
				System.out.println("   -    -    -    -");
				System.out.println("Job Name: 	 " + task.getName());
				System.out.println("Date of Job:     " + task.getStartDT().ToStringDate());
				System.out.println("Start Time:      " + task.getStartDT().ToStringTime());
				System.out.println("End Time: 	 " + task.getEndDT().ToStringTime());
			}
		}
	}
	
	public String getStringTasks() {
	    StringBuilder sb = new StringBuilder();
	    for (Task task : Tasks) {
	        if (task == null) {
	            return sb.toString();
	        } else {
	            sb.append("   -    -    -    -\n");
	            sb.append("  Job Name: 	   " + task.getName() + "\n");
	            sb.append("  Date of Job:        " + task.getStartDT().ToStringDate() + "\n");
	            sb.append("  Start Time:          " + task.getStartDT().ToStringTime() + "\n");
	            sb.append("  End Time: 	   " + task.getEndDT().ToStringTime() + "\n");
	        }
	    }
	    return sb.toString();
	}
}