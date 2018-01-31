import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	
	/** Instantiate a new FitnessProgram */
	FitnessProgram classList = new FitnessProgram();
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);		// Exit program only through closeButton
		setTitle("Boyd-Orr Sports Centre");
		setSize(760, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		
		// Read-in data and update timetable
		initLadiesDay();
		updateDisplay();
	}

	
	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		// Read-in data from classesIn.txt
		try {
			FileReader reader = new FileReader(classesInFile);
			Scanner in = new Scanner(reader);
			
			while (in.hasNextLine()) { // More lines to read
			   // read next line from input file
			   String line = in.nextLine();
			   
			   // create new fitness class object from line of data
			   FitnessClass fitness = classList.addClass(line);
			}
			reader.close();
		}
		// No action required since it is assumed that the file definitely exists
		catch (IOException e) { } 		
	}

	
	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	// Method not used - attendancesIn.txt is read-in in FitnessClass class
	public void initAttendances() {	}

	
	/**
	 * Sets the text in the JTextArea with the 
	 * up-to-date timetable information
	 */
	public void updateDisplay() {
		display.setText(classList.getTimeTableContent());
	}

	
	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	
	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel(" Enter Class Id (e.g. ab1)");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel(" Enter Class Name (max. 11 characters)");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel(" Enter Tutor Name (max. 11 characters)");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	
	/**
	 * Processes adding a class
	 */
	public void processAdding() {
		// Obtain data from JtextFields
		String classID = idIn.getText().trim();
		String className = classIn.getText().trim();
		String tutorName = tutorIn.getText().trim();
		
		// Check entered data - warning message if entered data is invalid
		if(classID == "")
			JOptionPane.showMessageDialog(null, "Please enter a class ID", "No Class ID Provided", JOptionPane.ERROR_MESSAGE);
		else if(classID.length() != 3)
			JOptionPane.showMessageDialog(null, "The class ID must consist of 3 characters/numbers. Please enter a valid class ID. \nExample: ab1", "Invalid Class ID", JOptionPane.ERROR_MESSAGE);
		else if(className == "")
			JOptionPane.showMessageDialog(null, "Please enter a class name", "No Class Name Provided", JOptionPane.ERROR_MESSAGE);
		else if(className.length() > 11)
			JOptionPane.showMessageDialog(null, "Please enter a class name of 11 characters or less", "Class Name Too Long", JOptionPane.ERROR_MESSAGE);
		else if(tutorName == "")
			JOptionPane.showMessageDialog(null, "Please enter a tutor name", "No Tutor Name Provided", JOptionPane.ERROR_MESSAGE);
		else if(className.length() > 11)
			JOptionPane.showMessageDialog(null, "Please enter a tutor name of 11 characters or less", "Tutor Name Too Long", JOptionPane.ERROR_MESSAGE);
		
		// Check if time table has space to add new class - warning message if not
		else if(classList.isTimeTableFull())
			JOptionPane.showMessageDialog(null, "Sorry, the timetable is currently full", "Time Table Full", JOptionPane.ERROR_MESSAGE);
		// Check if ID is already in use - warning message if so
		else if(classList.idInUse(classID)) {
			JOptionPane.showMessageDialog(null, "Sorry, that class ID is currently in use", "Class ID in Use", JOptionPane.ERROR_MESSAGE);
			idIn.setText("");
		}
		
		// Process addition of new class
		else
			classList.addNewClass(classID, className, tutorName);
		
		updateDisplay();
		clearFields();
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
		// Obtain ID of class to be deleted
		String classID = idIn.getText().trim();
		
		// Check entered data - warning message if ID entered is empty or not in use
		if(classID == "")
			JOptionPane.showMessageDialog(null, "Please enter a valid class ID", "Invalid Class ID", JOptionPane.ERROR_MESSAGE);
		else if(!classList.idInUse(classID))
			JOptionPane.showMessageDialog(null, "The class ID entered is not in use. Please enter a valid ID for the class that you would like to remove.", "Class ID Not In Use", JOptionPane.ERROR_MESSAGE);
		
		// Process removal of class if ID entered is valid
		else
			classList.removeClass(classID);
		
		updateDisplay();
		clearFields();
	}

	
	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
	    // Retrieve content (a single String) for attendance report
		String attendReport = classList.getAttendReportContent();
		
		// Instantiate new ReportFrame object
		report = new ReportFrame(attendReport);
		report.setVisible(true);
	}

	
	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
		// Retrieve content (a single String) for file to be written
		String fileOutContent = classList.getFileOutContent();
		
		// Write to file
		FileWriter writer = null;
		try {
			try {
				writer = new FileWriter(classesOutFile);
				writer.write(fileOutContent);
			}
			finally {
				if (writer != null)
					writer.close();
			}
		}
		catch (IOException e) { }
		System.exit(0);
	}
	

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == attendanceButton)
			displayReport();
		if(ae.getSource() == addButton)
			processAdding();
		if(ae.getSource() == deleteButton)
			processDeletion();
		if(ae.getSource() == closeButton)
			processSaveAndClose();
	}
	
	
	/**
	 * Clears all textfields when called.
	 */
	public void clearFields() {
		idIn.setText("");
		classIn.setText("");
		tutorIn.setText("");
	}
	
}
