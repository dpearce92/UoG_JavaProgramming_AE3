import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
    
	private JTextArea attendDisplay;
	
	final int ROWS = 10, COLUMNS = 95;
	
	/**
	 * Constructor for attendance report.
	 * @param content - A String containing all the attendance report content
	 */
	public ReportFrame(String content) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);   		// Close window without ending the program
		setTitle("Attendance Report");
		setSize(800, 300);
		attendDisplay = new JTextArea(ROWS, COLUMNS);
		attendDisplay.setFont(new Font("Courier", Font.PLAIN, 14));
		add(attendDisplay);
		attendDisplay.setEditable(false);
		attendDisplay.setText(content);
	}
}
