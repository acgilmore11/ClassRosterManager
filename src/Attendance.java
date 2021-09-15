/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Assignment: Final Project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * The Attendance class is an Observable class that reads in an attendance file
 * selected by the user and creates an ArrayList of string arrays that contains the
 * attendance info. It also checks for duplicate attendee IDs and combines their
 * minutes attended.
 * @author Austin Gilmore, Brandon McMillin
 */
public class Attendance extends Observable implements ClassInfo {
	/**
	 * These variables represent the information read from the attendance file.
	 */
	private ArrayList<String[]> attendance = new ArrayList<String[]>();
	private String[][] uniqueIds;
	private int numUnique;
	private String date;
	
	/**
	 * This is the constructor method for the Attendance class that sets the date variable
	 * based on user input from the date picker. 
	 * @param date
	 */
	Attendance(String date){
		this.date = date;
	}
	
	/**
	 * The create method initializes a JFileChooser and calls the readFile method
	 * once a file is selected.
	 */
	public void create() {
		final JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			readFile(file);
		}
	}
	
	/**
	 * The readFile method takes in a File and writes the file information into an
	 * ArrayList of string arrays that represents the attendance info. 
	 * @param file Represents the file that was selected by the user. 
	 */
	public void readFile(File file) {
		Scanner readFile;
		try {
			readFile = new Scanner(file);
			while (readFile.hasNext()) {
				String newStudent = readFile.nextLine(); 
				String[] student = newStudent.split(",",2);
				attendance.add(student);
			}
			checkAttendance();
			readFile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Notifies the observers of the Attendance class.
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * The checkAttendance method searches the attendance ArrayList and combines the 
	 * minutes of duplicate attendee IDs.
	 */
	private void checkAttendance() {
		uniqueIds = new String[attendance.size()][2];
		numUnique = 0;
		//Iterates through attendance ArrayList and adds every unique student id to uniqueIds.
		for (int i = 0; i < attendance.size(); i++) {
			int j = 0;
			boolean found = false;
			while(j < numUnique && !found) {
				if (attendance.get(i)[0].equals(uniqueIds[j][0]))
					found = true;
				j++;
			}
			if (!found) {
				uniqueIds[numUnique][0] = attendance.get(i)[0];
				numUnique++;
			}
		}
		
		//Iterates through uniqueIds and attendance and combines minutes for each unique ID.
		for(int i = 0; i < numUnique; i++) {
			String id = uniqueIds[i][0];
			int time = 0;
			for(int j = 0; j < attendance.size(); j++) {
				if (attendance.get(j)[0].equals(id))
					time += Integer.parseInt(attendance.get(j)[1]);
			}
			String timeString = Integer.toString(time);
			uniqueIds[i][1] = timeString;
		}	
	}
	
	/**
	 * The rosterCreated method always returns true because an Attendance object can only
	 * be initialized once a roster has been created.
	 */
	public boolean rosterCreated() {
		return true;
	}
	
	/**
	 * The getUniqueIds method is an accessor method that returns uniqueIds;
	 * @return uniqueIds Two-dimensional array that represents every unique
	 * 					 attendee and their total minutes attended.
	 */
	public String[][] getUniqueIds(){
		return uniqueIds;
	}

	/**
	 * The getNumUnique method is an accessor method that returns numUnique.
	 * @return numUnique Integer that represents the number of unique attendees.
	 */
	public int getNumUnique() {
		return numUnique;
	}
	
	/**
	 * The getDate method is an accessor method that returns date.
	 * @return date String that represents the date in which the user selected.
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * The getRosterSize method is an accessor method that returns the size of the roster.
	 * @return roster.size() Integer that represents the size of the roster variable of the
	 * 							parent interface.
	 */
	public int getRosterSize() {
		return roster.size();
	}
}
