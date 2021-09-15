/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Course ID: 70605 CSE360 Tu 9:00-10:15
 *Assignment: Final 360 Project
 */

import java.util.Observable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * The Roster class is an Observable class that reads in a file and adds the information
 * into an ArrayList of string arrays. 
 * @author Austin Gilmore, Brandon McMillin
 */
public class Roster extends Observable implements ClassInfo {
	/**
	 * rosterCreated is initially set to false and changed to true once
	 * a file has been read in.
	 */
	private boolean rosterCreated = false;

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
	 * ArrayList of string arrays that represents the roster info. 
	 * @param file Represents the file that was selected by the user. 
	 */
	public void readFile(File file) {
		Scanner readFile;
		try {
			readFile = new Scanner(file);
			while (readFile.hasNext()) {
				String newStudent = readFile.nextLine(); 
				String[] student = newStudent.split(",",6);
				roster.add(student);
			}
			readFile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Notifies the observers of the Roster class.
		setChanged();
		notifyObservers();
		rosterCreated = true;
	}
	
	/**
	 * The getRoster method is an accessor method that returns the roster variable.
	 * @return roster An ArrayList of string arrays that contains the roster information.
	 */
	public ArrayList<String[]> getRoster(){
		return roster;
	}
	
	/**
	 * The rosterCreated method is an accessor method that returns the rosterCreated variable.
	 * @return rosterCreated Boolean value that represents if the roster has been created or not. 
	 */
	public boolean rosterCreated() {
		return rosterCreated;
	}
		
}