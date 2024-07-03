import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

public class Main {
    	
	private static JFrame frame;
		
	static Schedule s = new Schedule(100);
	
	static Resource r1 = new Resource(1, "Eric");
	static Resource r2 = new Resource(2, "Garret");
	static Resource r3 = new Resource(3, "Joe");
	static Resource r4 = new Resource(4, "Sarah");
	static Resource r5 = new Resource(5, "Jeff");
	static Resource r6 = new Resource(6, "Alan");
	static Resource r7 = new Resource(7, "Ethan");
	
	static Task t1 = new Task(1, "Job 1", new DateTime(12, 4, 12, 00, 1), new DateTime(12, 4, 8, 00, 1));
	static Task t2 = new Task(2, "Job 2", new DateTime(12, 5, 12, 00, 1), new DateTime(12, 5, 8, 00, 1));
	static Task t3 = new Task(3, "Job 3", new DateTime(12, 6, 12, 00, 1), new DateTime(12, 6, 8, 00, 1));
		
	public static void main(String[] args) {
		

		s.addAvailableTask(t1);
		s.addAvailableTask(t2);
		s.addAvailableTask(t3);
		
		r1.addTask(t1);
		r2.addTask(t2);
		r3.addTask(t3);
		
		s.addResource(r1);
		s.addResource(r2);
		s.addResource(r3);
		s.addResource(r4);
		s.addResource(r5);
		s.addResource(r6);
		s.addResource(r7);
		
		s.addMeeting(new Meeting("Meeting 1", new DateTime(12, 4, 12, 00, 1), "Room 4"));
		
		s.addRule(s.rulesEngine.createCustomRule("resourceCapacity Eric > 7"));
		s.addRule(s.rulesEngine.createCustomRule("OverbookingRule"));
		s.addRule(s.rulesEngine.createCustomRule("isInSchedulingWindow 8"));
		s.addRule(s.rulesEngine.createCustomRule("workWeekCapacity 6"));
		s.addRule(s.rulesEngine.createCustomRule("checkDaysOff"));
		
		frame = new JFrame("PPL Schedule System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1550, 750);
		
		updateScreen();
		
		frame.setVisible(true);
	}

	private static void updateScreen() {
		frame.getContentPane().removeAll();
		
		JPanel panel = new JPanel(new BorderLayout());
		frame.add(panel);

		String[] columnNames = getWeekDays();
		java.util.List<Resource> resources = s.getResources();
		java.util.List<Meeting> meetings = s.getMeetings();
		Object[][] data = getData(resources, meetings);
		JTable table = new JTable(data, columnNames);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);
		int topMargin = 60;
		int leftMargin = 30;
		int bottomMargin = 30;
		int rightMargin = 30;
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

		panel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


		int topButtonMargin = 10;
		int rightButtonMargin = 200;
		int bottomButtonMargin = 200;
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(topButtonMargin, 0, bottomButtonMargin, rightButtonMargin));

		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.add(buttonPanel);
		panel.add(centerPanel, BorderLayout.SOUTH);

		for (int i = 1; i <= 10; i++) {
		    JButton customButton = new JButton("Button " + i);
		    customButton.setPreferredSize(new Dimension(250, 40)); 

		    buttonPanel.add(customButton);
		    buttonPanel.add(Box.createRigidArea(new Dimension(50, 0))); 
		    
		    switch (i) {
			    case 1:
			            customButton.setText("Add Employee");
			            customButton.addActionListener(new ActionListener() {
			                @Override
			                public void actionPerformed(ActionEvent e) {
			                    String name = JOptionPane.showInputDialog(frame, "Enter employee name:");
			                    if (name != null && !name.trim().isEmpty()) {
			                        try {
			                            int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter employee ID:"));
			                            Resource newR = new Resource(id, name);
			                            s.addResource(newR);
			                            updateScreen();
			                            JOptionPane.showMessageDialog(frame, "success.");
			                        } catch (NumberFormatException ex) {
			                            JOptionPane.showMessageDialog(frame, "Invalid employee ID. Please enter a valid integer.");
			                        }
			                    } else {
			                        JOptionPane.showMessageDialog(frame, "Employee name cannot be empty.");
			                    }
			                }
			            });
			            break;
		        case 2:
		            customButton.setText("Add a Meeting");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                	String name1 = JOptionPane.showInputDialog(frame, "Enter meeting name:");
		                    if (name1 != null && !name1.trim().isEmpty()) {
		                        try {
		                        	String startDateInput1 = JOptionPane.showInputDialog(frame, "Enter start date (MM/DD/YYYY):");
		                            String startTimeInput1 = JOptionPane.showInputDialog(frame, "Enter start time (HH:MM am/pm):");
		                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		                            Date parsedStartDate1 = dateFormat1.parse(startDateInput1 + " " + startTimeInput1);

		                            Calendar startCalendar1 = Calendar.getInstance();
		                            startCalendar1.setTime(parsedStartDate1);
		                            DateTime startDateTime1 = new DateTime(
		                                    startCalendar1.get(Calendar.YEAR),
		                                    startCalendar1.get(Calendar.MONTH) + 1,
		                                    startCalendar1.get(Calendar.DAY_OF_MONTH),
		                                    startCalendar1.get(Calendar.HOUR),
		                                    startCalendar1.get(Calendar.MINUTE),
		                                    startCalendar1.get(Calendar.AM_PM)
		                            );
		                            String loc = JOptionPane.showInputDialog(frame, "Enter meeting location:");
		                            Meeting newM = new Meeting(name1,startDateTime1,loc);
		                            s.addMeeting(newM);
		                            updateScreen();
		                            JOptionPane.showMessageDialog(frame, "success.");
		                        } catch (NumberFormatException | java.text.ParseException ex) {
		                            JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.");
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(frame, "Meeting name cannot be empty.");
		                    }
		                }
		            });
		            break; 
		        case 3:
		            customButton.setText("Add Job");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    String name = JOptionPane.showInputDialog(frame, "Enter job name:");
		                    if (name != null && !name.trim().isEmpty()) {
		                        try {
		                            int taskId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter job ID:"));

		                            String startDateInput = JOptionPane.showInputDialog(frame, "Enter start date (MM/DD/YYYY):");
		                            String startTimeInput = JOptionPane.showInputDialog(frame, "Enter start time (HH:MM am/pm):");

		                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		                            Date parsedStartDate = dateFormat.parse(startDateInput + " " + startTimeInput);

		                            Calendar startCalendar = Calendar.getInstance();
		                            startCalendar.setTime(parsedStartDate);

		                            String endDateInput = JOptionPane.showInputDialog(frame, "Enter end date (MM/DD/YYYY):");
		                            String endTimeInput = JOptionPane.showInputDialog(frame, "Enter end time (HH:MM am/pm):");

		                            Date parsedEndDate = dateFormat.parse(endDateInput + " " + endTimeInput);

		                            Calendar endCalendar = Calendar.getInstance();
		                            endCalendar.setTime(parsedEndDate);

		                            DateTime startDateTime = new DateTime(
		                                    startCalendar.get(Calendar.YEAR),
		                                    startCalendar.get(Calendar.MONTH) + 1,
		                                    startCalendar.get(Calendar.DAY_OF_MONTH),
		                                    startCalendar.get(Calendar.HOUR),
		                                    startCalendar.get(Calendar.MINUTE),
		                                    startCalendar.get(Calendar.AM_PM)
		                            );

		                            DateTime endDateTime = new DateTime(
		                                    endCalendar.get(Calendar.YEAR),
		                                    endCalendar.get(Calendar.MONTH) + 1,
		                                    endCalendar.get(Calendar.DAY_OF_MONTH),
		                                    endCalendar.get(Calendar.HOUR),
		                                    endCalendar.get(Calendar.MINUTE),
		                                    endCalendar.get(Calendar.AM_PM)
		                            );

		                            Task newTask = new Task(taskId, name, startDateTime, endDateTime);
		                            s.addAvailableTask(newTask);
		                        } catch (NumberFormatException | java.text.ParseException ex) {
		                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid values.");
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(frame, "Job name cannot be empty.");
		                    }
		                }
		                
		            });
		            break;
		        case 4:
		            customButton.setText("Assign a Job to an Employee");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    java.util.List<Resource> resources1 = s.getResources();
		                    java.util.List<String> nameList = new ArrayList<String>();
		                    for(Resource r : resources1) {
		                        nameList.add(r.getName());
		                    }

		                    if (!nameList.isEmpty()) {
		                        String selectedEmployee = (String) JOptionPane.showInputDialog(frame,
		                                "Choose an employee:", "Employee Selection",
		                                JOptionPane.QUESTION_MESSAGE, null,
		                                nameList.toArray(new String[0]), // Array of choices
		                                nameList.get(0)); // Initial selection

		                        if (selectedEmployee != null) {
		                            List<Task> tasks1 = s.getTasks();
		                            java.util.List<String> nameList1 = new ArrayList<String>();
		                            for(Task t : tasks1) {
		                                nameList1.add(t.getName());
		                            }

		                            if (!nameList1.isEmpty()) {
		                                String selectedTask = (String) JOptionPane.showInputDialog(frame,
		                                        "Choose a task:", "Task Selection",
		                                        JOptionPane.QUESTION_MESSAGE, null,
		                                        nameList1.toArray(new String[0]), // Array of choices
		                                        nameList1.get(0)); // Initial selection

		                                if (selectedTask != null) {
		                                    s.assignTask(s.getResourceByName(selectedEmployee), s.getTaskByName(selectedTask));
		                                    updateScreen();
		                                }
		                            } else {
		                                JOptionPane.showMessageDialog(frame, "No tasks available to assign.");
		                            }
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(frame, "No employees available to assign a job.");
		                    }
		                }
		                
		            });
		            break;
		        case 5:
		            customButton.setText("Remove an Employee");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                	
		                	java.util.List<Resource> resources2 = s.getResources();
		                    java.util.List<String> nameList = new ArrayList<String>();
		                    for(Resource r : resources2) {
		                        nameList.add(r.getName());
		                    }
		                    if (!nameList.isEmpty()) {
		                        String selectedEmployee = (String) JOptionPane.showInputDialog(frame,
		                                "Choose an employee:", "Employee Selection",
		                                JOptionPane.QUESTION_MESSAGE, null,
		                                nameList.toArray(new String[0]), // Array of choices
		                                nameList.get(0)); // Initial selection

		                        if (selectedEmployee != null) {
		                            s.removeResourceByObj(s.getResourceByName(selectedEmployee));
		                            updateScreen();
		                        } else {
		                                JOptionPane.showMessageDialog(frame, "System Error.");
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(frame, "No employees to remove.");
		                    }
		                }
		            });
		            break;
		        case 6:
		            customButton.setText("Remove a Job");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    java.util.List<Task> tasks2 = s.getTasks();
		                    s.printResourcesAndTasks();
		                    java.util.List<String> nameList = new ArrayList<String>();
		                    for(Task t : tasks2) {
		                        nameList.add(t.getName());
		                    }
		                    if (!nameList.isEmpty()) {
		                        String selectedTask = (String) JOptionPane.showInputDialog(frame,
		                                "Choose a Job:", "Job Selection",
		                                JOptionPane.QUESTION_MESSAGE, null,
		                                nameList.toArray(new String[0]), // Array of choices
		                                nameList.get(0)); // Initial selection

		                        if (selectedTask != null) {
		                            s.removeTask(s.getTaskByName(selectedTask).getTaskId());
		                            updateScreen();
		                        } else {
		                            JOptionPane.showMessageDialog(frame, "System Error.");
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(frame, "No Jobs to remove.");
		                    }
		                }
		                
		               
		            });
		            break;
		        case 7:
		            customButton.setText("Show All Jobs");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    JTextArea textArea = new JTextArea(30, 20);
		                    textArea.setEditable(false);
		                    textArea.setText(s.getStringAllTasks());
		                    JScrollPane scrollPane = new JScrollPane(textArea);
		                    JOptionPane.showMessageDialog(null, scrollPane, "Task Details", JOptionPane.INFORMATION_MESSAGE);
		                    
		                }
		            });
		            break;
		        case 8:
		            customButton.setText("Show Employees and Assigned Jobs");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    JTextArea textArea = new JTextArea(30, 20);
		                    textArea.setEditable(false);
		                    textArea.setText(s.getStringResourcesAndTasks());
		                    JScrollPane scrollPane = new JScrollPane(textArea);
		                    JOptionPane.showMessageDialog(null, scrollPane, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
		                }
		            });
		            break;
		        case 9:
		            customButton.setText("Create a Rule");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                	 String rulesString = "This is a help message for rules input.";
		                     String message = s.getRulesEngine().getRulesString() + "\n";

		                     Object[] options = {"Create a Rule", "Help"};
		                     int result = JOptionPane.showOptionDialog(frame, message, "Rules Input",
		                             JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		                     if (result == 0) {
		                         String newR = JOptionPane.showInputDialog(frame, message);
		                         if (newR != null) {
		                             s.addRule(s.rulesEngine.createCustomRule(newR));
		                         }
		                     } else if (result == 1) {
		                         // "Help" button clicked
		                         JOptionPane.showMessageDialog(frame, rulesString, "Help", JOptionPane.INFORMATION_MESSAGE);
		                     }
		                 }
		            });
		            break; 
		        case 10:
		            customButton.setText("Remove a Rule");
		            customButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {

		                	java.util.List<Rule> rulesList = s.getRulesEngine().getRules();
		                    java.util.List<String> nameList = new ArrayList<String>();
		                    for(Rule r : rulesList) {
		                        nameList.add(r.getRuleText());
		                    }
		                    if (!nameList.isEmpty()) {
		                        String selectedRule = (String) JOptionPane.showInputDialog(frame,
		                                "Choose a rule:", "Rule Selection",
		                                JOptionPane.QUESTION_MESSAGE, null,
		                                nameList.toArray(new String[0]), // Array of choices
		                                nameList.get(0)); // Initial selection

		                        if (selectedRule != null) {
		                            s.rulesEngine.removeRuleByText(selectedRule);
		                        } else {
		                                JOptionPane.showMessageDialog(frame, "System Error.");
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(frame, "No rules to remove.");
		                    }
		                }
		            });
		            break; 
		        default:
		            break;
		    }
		}
	}
	
	private static String[] getWeekDays() {
		String[] days = new String[8];
		days[0] = "Resource/Meeting";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("EEE dd/MM");
		for (int i = 1; i <= 7; i++) {
			days[i] = format.format(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}
		return days;
	}

	private static Object[][] getData(java.util.List<Resource> resources, java.util.List<Meeting> meetings) {
		int totalRows = resources.size() + meetings.size();
		Object[][] data = new Object[totalRows][8];
		for (int i = 0; i < resources.size(); i++) {
			data[i][0] = resources.get(i).getName();
			java.util.List<Task> tasks = resources.get(i).getTasks();
			for (Task task : tasks) {
				DateTime dateTime = task.getStartDT();
				int dayOfWeek = dateTime.getDayOfWeek(); // Adjust to 0-based indexing
				data[i][dayOfWeek - 1] = task.getName() + ": " + task.getStartDT().ToStringTime() + " - " + task.getEndDT().ToStringTime();
			}
		}

		for (int i = 0; i < meetings.size(); i++) {
			data[resources.size() + i][0] = meetings.get(i).getName();
			DateTime dateTime = meetings.get(i).getDate();
			int dayOfWeek = dateTime.getDayOfWeek(); // Adjust to 0-based indexing
			data[resources.size() + i][dayOfWeek - 1] = meetings.get(i).getLocation() + ": " + meetings.get(i).getDate().ToStringTime();
		}
		return data;
	}
	
}
