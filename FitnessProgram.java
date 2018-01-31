import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
	
	final int MAX_CLASSES = 7; // Maximum number of potential classes
	
	// Instance variables
	private FitnessClass [] classList, noGaps;
	private int numClasses;
	
	// Default constructor of empty list
	public FitnessProgram() {
		classList = new FitnessClass[MAX_CLASSES];
		numClasses = 0;
	}
	
	
	/**
	 * Method to add a FitnessClass object to the array in order of start time
	 * @param line - a String containing Class ID, Class Name, Tutor Name, and Start Time
	 * @return session - a FitnessClass object
	 */
	public FitnessClass addClass(String line) {
		// Instantiates new FitnessClass object, passing String of data to
		// a default constructor in FitnessClass class
		FitnessClass session = new FitnessClass(line);
		
		// Obtain start time for each class 
		String [] tokens = line.split(" ");
		int time = Integer.parseInt(tokens[3]);
		
		// Order classes in array by start time
		int index = time - 9;
		classList[index] = session;
		numClasses++;
		
		return session;
	}
	
	
	/**
	 * Method which returns a single String containing all the time table information
	 * @return timeTableContent - a single String with all time table data
	 */
	public String getTimeTableContent() {
    	// Create header of timetable
		String timeTableContent = String.format("  %-13s%-13s%-13s%-13s%-13s%-13s%-13s\n ", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16");
    	
		// Add next line of timetable - Class names ('Available' if null)
    	for(int index = 0; index < MAX_CLASSES; index++) {
    		if(classList[index] != null)
    			timeTableContent += String.format("%-13s", classList[index].getClassName());
    		else
    			timeTableContent += String.format("%-13s", "Available");
    	}
    	// New line
    	timeTableContent += "\n ";
    	
    	// Add next line of timetable - Tutor names (blank if null)
    	for(int index = 0; index < MAX_CLASSES; index++) {
    		if(classList[index] != null)
    			timeTableContent += String.format("%-13s", classList[index].getTutorName());
    		else
    			timeTableContent += String.format("%-13s", "");
    	}
  
    	return timeTableContent;
    }
	
	
	/****************************************************************
	 * 						ProcessAdding
	 ****************************************************************/
	
	/**
	 * Method to check whether the time table is full
	 * @return a boolean value - true if full, false if not
	 */
	public boolean isTimeTableFull() {
		if(numClasses == MAX_CLASSES)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Method to check if ID is already in use
	 * @param id - String containing class ID of new class to be added
	 * @return a boolean value - true if ID already exists, false if not
	 */	
	public boolean idInUse(String id) {
		String classID = id;
		boolean result = true;
		
		// Check if any FitnessClass objects in the array have the class ID being checked
		for(int index = 0; index < MAX_CLASSES; index++) {
			if(classList[index] != null) {
				if ((classList[index].getClassID()).equals(classID)) {   // A class in the list already uses the ID
					result = true;
					break;
				}
				else		// No classes in the list use the ID
					result = false;
			}
		}
		return result;
	}
	
	
	/**
	 * Method to add a new FitnessClass object to the array
	 * @param 3 Strings - ClassID, Class Name, Tutor Name
	 */
	public void addNewClass(String a, String b, String c) {
		String classID = a;
		String className = b;
		String tutorName = c;
		
		// Identify time slot for new class
		int timeSlot = 9;	// 9 represents the time slot without adding the array index
		for(int index = 0; index < MAX_CLASSES; index++) {
			if(classList[index] == null) {
				timeSlot += index;	
				break;
			}
		}
		
		// Create FitnessClass object and add it to the array in the correct position
		FitnessClass newClass = new FitnessClass(classID, className, tutorName, timeSlot);
		classList[timeSlot - 9] = newClass;
		numClasses++;
	}

	
	/****************************************************************
	 * 						ProcessDeletion
	 ****************************************************************/
	
	/**
	 * Method to remove a desired class from the array
	 * @param id - the ClassID of the class that is to be removed
	 */
	public void removeClass(String id) {
		String classID = id;
		
		// Search array for class with specific classID and remove it from array (make the index null)
		for(int index = 0; index < MAX_CLASSES; index++) {
			if(classList[index] != null) {
				if((classList[index].getClassID()).equals(classID)) {
					classList[index] = null;
					break;
				}
			}
		}
		// Decrement number of classes counted
		numClasses--;
	}
	
	
	/****************************************************************
	 * 						DisplayReport
	 ****************************************************************/
	
	/**
	 * Method to generate a single String containing the data 
	 * for the attendance report
	 * @return reportContent - a single String containing the attendance report data
	 */
	public String getAttendReportContent() {
		// Generate new array with all null entries removed
		getNoGapsArray();
		// Order the new array by average attendance
		Arrays.sort(noGaps, 0, numClasses);
		
		// Generate header of attendance report
		String reportContent = String.format(" %-5s%-20s%-20s%-25s%-19s\n", "ID", "Class", "Tutor", "Attendances", "Average Attendance");
		for(int i = 0; i < 90; i++) {
			reportContent += "=";
		}
		
		// Add lines of attendance data for each class
		for(int index = 0; index < numClasses; index++) {
			reportContent += "\n"+noGaps[index].getAttendReportString();
		}
		
		// Add line with overall attendance
		reportContent += String.format("\n\n%66s%10.2f", "Overall average:", getOverallAverage());
				
		return reportContent;
	}
	
	
	/**
	 * Method which creates new array with all null entries removed
	 * @return FitnessClass [] - contains no null entries
	 */
	public FitnessClass [] getNoGapsArray() {
		noGaps = new FitnessClass[numClasses];
		
		// Fill new array object by object, skipping null entries
		for(int i = 0, j = 0; i < MAX_CLASSES; i++) {
			if(classList[i] != null)
				noGaps[j++] = classList[i];
		}
		return noGaps;
	}

	
	/**
	 * Method which calculates the overall average of all classes in the array
	 * @return result - the overall average
	 */
	public double getOverallAverage() {
		double result = 0;
		
		// Sum the average attendance of all classes
		for(int index = 0; index < numClasses; index++) {
			result += noGaps[index].getAveAttendance();
		}
		
		// Calculate average by diving by number of classes
		result /= numClasses;
		
		return result;
	}
	
	
	/****************************************************************
	 * 						ProcessSaveAndClose
	 ****************************************************************/
		
	/**
	 * Method to generate a single String containing the file out content
	 * @return
	 */
	public String getFileOutContent() {
		String fileContent = "";
		
		// Add lines of data corresponding to each class in the array
		for(int index = 0; index < MAX_CLASSES; index++) {
			if(classList[index] != null) {
				fileContent += classList[index].getClassID()+" "+classList[index].getClassName()
							+" "+classList[index].getTutorName()+" "+classList[index].getStartTime()+"\n";
			}
		}
		return fileContent;
	}
	
}
