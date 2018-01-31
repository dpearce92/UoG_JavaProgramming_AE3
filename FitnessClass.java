import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
	
	// Number of attendances recorded for each class
	final int ATTENDANCES_RECORDED = 5; 
	
	// Declare instance variables
	private String classID, className, tutorName;
	private int startTime, attend1, attend2, attend3, attend4, attend5; 
	private double aveAttendance;
	
	
	/**
	 * Default constructor
	 */
	public FitnessClass() {
		classID = "";
		className = "";
		tutorName = "";
		startTime = 0;
		attend1 = 0;
		attend2 = 0;
		attend3 = 0;
		attend4 = 0;
		attend5 = 0;
	}

	
	/**
	 * Non-default constructor
	 * @param input - a single String containing classID, className, tutorName, and startTime
	 */
	public FitnessClass(String input) {
		String line = input;
		String [] tokens = line.split(" ");
		
		classID = tokens[0];
		className = tokens[1];
		tutorName = tokens[2];
		startTime = Integer.parseInt(tokens[3]);
		
		setAttendances();
	}

	
	/**
	 * Non-default constructor - used to create new objects from data entered into GUI
	 * @param a - classID
	 * @param b - className
	 * @param c - tutorName
	 * @param d - startTime
	 */
	public FitnessClass(String a, String b, String c, int d) {
		classID = a;
		className = b;
		tutorName = c;
		startTime = d;
		
		// Initialise all attendances to zero
		attend1 = 0;
		attend2 = 0;
		attend3 = 0;
		attend4 = 0;
		attend5 = 0;
	}
		
	
	/**
	 * Method which assigns attendance data to FitnessClass objects.
	 * Searches attendancesIn.txt for the corresponding classID and
	 * initialises the attendance variables
	 */
	public void setAttendances() {
		String inputFileName = "AttendancesIn.txt";
	 		
		try {
			FileReader reader = new FileReader(inputFileName);
	 		Scanner in = new Scanner(reader);
	 			
	 		while (in.hasNextLine()) { // More lines to read
	 			// read next line from input file
	 			String line = in.nextLine();
	 			String [] tokens = line.split(" ");
	 			   
	 			// Assuming file is in correct format, tokens[0] will be the classID for that line of data.
	 			// Attendance data is assigned to objects with the corresponding classID
	 			if(tokens[0].equals(classID)) {
	 				attend1 = Integer.parseInt(tokens[1]);
	 				attend2 = Integer.parseInt(tokens[2]);
	 				attend3 = Integer.parseInt(tokens[3]);
	 				attend4 = Integer.parseInt(tokens[4]);
	 				attend5 = Integer.parseInt(tokens[5]);
	 			}
	 		}   
	 		reader.close();
	 	}
	 	// No action required since it is assumed the file definitely exists
	 	catch (IOException e) { } 
	 	}
	
	
	/**
	 * Method which returns the average attendance
	 * @return aveAttendance - the average attendance for a FitnessClass object
	 */
	public double getAveAttendance() {
		aveAttendance = (double)(attend1 + attend2 + attend3 + attend4 + attend5) / ATTENDANCES_RECORDED;
		return aveAttendance;
	}
	
	
	/**
	 * Method which determines criteria for comparing FitnessClasses
	 */
    public int compareTo(FitnessClass other) {
    	double thisAverage = this.getAveAttendance();
    	double otherAverage = other.getAveAttendance();
    	
    	// Allows for sorting in descending order of average attendance
    	if(thisAverage > otherAverage)
    		return -1;
    	else if(thisAverage == otherAverage)
    		return 0;
    	else
    		return 1;
    }
    
        
    /**
     * Method which returns a formatted String for use in attendance report
     * @return reportLine - a formatted String
     */
    public String getAttendReportString() {
    	String reportLine = String.format(" %-5s%-20s%-17s%3d%5d%5d%5d%5d%15.2f", getClassID(), getClassName(), getTutorName(),
    									getAttend1(), getAttend2(), getAttend3(), getAttend4(), getAttend5(), getAveAttendance());
    	
    	return reportLine;
    }

    
    
    /****************************************************************
	 * 				ACCESSOR AND MUTATOR METHODS
	 ****************************************************************/
	
	public void setClassID(String id) {
		classID = id;
	}
	
	public String getClassID() {
		return classID;
	}
	
	public void setClassName(String name) {
		className = name;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setTutorName(String tutor) {
		tutorName = tutor;
	}
	
	public String getTutorName() {
		return tutorName;
	}
	
	public void setStartTime(int time) {
		startTime = time;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
    public int getAttend1() {
    	return attend1;
    }
    
    public int getAttend2() {
    	return attend2;
    }
    
    public int getAttend3() {
    	return attend3;
    }
    
    public int getAttend4() {
    	return attend4;
    }
    
    public int getAttend5() {
    	return attend5;
    }
  
}
