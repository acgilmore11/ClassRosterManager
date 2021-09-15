/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Course ID: 70605 CSE360 Tu 9:00-10:15
 *Assignment: Final 360 Project
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Observable;
import java.util.Observer;
import java.util.*;

/**
 * The ScrollPane class observes both the Attendance and Roster classes. It 
 * also creates the roster/attendance table and displays it within the 
 * World frame.
 * @author Austin Gilmore, Brandon McMillin
 *
 */
public class ScrollPane extends JScrollPane implements Observer {
	/**
	 * These variables will be used to construct the roster/attendance
	 * table.
	 */
	private JTable rosterJTable;
	private DefaultTableModel rosterTable;
	private String[] columnNames = {"ID", "First Name", "Last Name", "Program", "Level", "ASURITE"};
	private String[][] data;
	private int dataLoaded;
	private int attendeesNotFound = 0;
	private String[][] notFound;
	
	/**
	 * This is the constructor method for the ScrollPane class. It receives two
	 * integers and passes them into the JScrollPane constructor.
	 * @param x,y Represent integers that will initialize the scroll bars.
	 */
	ScrollPane(int x, int y){
		super(x,y);
	}

	/**
	 * The update method either creates an ArrayList that represents the roster
	 * and initializes the roster table or adds an attendance column to the 
	 * existing table
	 * @param o The Observable that calls the update method.
	 * @param arg
	 */
	public void update(Observable o, Object arg) {
		if (!((ClassInfo) o).rosterCreated()) { 
			ArrayList<String[]> roster = ((Roster)o).getRoster();
			data = new String[roster.size()][6];
			for (int i = 0; i < roster.size(); i++) {
				for (int j = 0 ; j < 6; j++)
					data[i][j] = roster.get(i)[j];
			}
			
			rosterTable = new DefaultTableModel(data, columnNames);
			rosterJTable = new JTable(rosterTable);
			rosterJTable.setVisible(true);
			getViewport().add(rosterJTable);
			setVisible(true);
		} else {
			String[][] attendance = ((Attendance) o).getUniqueIds();
			int numUnique = ((Attendance) o).getNumUnique();
			String[] newColumn = compareAttendanceToRoster(attendance, numUnique);
			rosterTable.addColumn(((Attendance) o).getDate(), newColumn);
			rosterJTable.setModel(rosterTable);
			displayAttendanceInfo();
		}	
	}
	
	/**
	 * The compareAttendanceToRoster method takes the attendance info and compares
	 * the asurite id to determine the total number of minutes that each student
	 * attended on a particular day. Returns a string array that represents
	 * the attendance column that will be added to the table. 
	 * @param attendance Represents attendance info that was received from file.
	 * @param numUnique Represents number of unique ids that attended on a particular day.
	 * @return newColumn Column that will be added to table.
	 */
	private String[] compareAttendanceToRoster(String[][] attendance, int numUnique) {
		attendeesNotFound = 0;
		dataLoaded = 0;
		String[] newColumn = new String[data[0].length];
		notFound = new String[attendance[0].length][2];
		//iterates through attendance and adds students to column if they attended
		for(int i = 0; i < numUnique; i++) {
			String currId = attendance[i][0];
			boolean foundInRoster = false;
			for(int j = 0; j < data[0].length; j++) {
				if (currId.equals(data[j][5])) {
					newColumn[j] = attendance[i][1];
					foundInRoster = true;
					dataLoaded++;
				}
			}
			if (!foundInRoster) {
				notFound[attendeesNotFound][0] = attendance[i][0];
				notFound[attendeesNotFound][1] = attendance[i][1];
				attendeesNotFound++;
			}
		}
		return newColumn;
	}
	
	/**
	 * The displayAttendanceInfo method creates a dialog box that notifies the user 
	 * of the students who attended and were not on the roster. 
	 */
	private void displayAttendanceInfo() {
		String additionalList = "Data loaded for " + dataLoaded + " users in the roster.\n\n"
				+ attendeesNotFound + " additional attendee(s) was found: \n";
		for (int i = 0; i < attendeesNotFound; i++) {
			additionalList += notFound[i][0] + ", connected for " + notFound[i][1] + " minute(s)\n";
		}
		JOptionPane.showMessageDialog(null, additionalList);
	}
	
	/**
	 * The getRosterTable is an accessor method that returns the roster table. 
	 * @return rosterJTable Represents roster table.
	 */
	public JTable getRosterTable() {
		return rosterJTable;
	}
}

