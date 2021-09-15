/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Course ID: 70605 CSE360 Tu 9:00-10:15
 *Assignment: Final 360 Project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * ClassInfo is an interface that will be implemented by the Roster
 * and Attendance classes in order to access shared information in
 * both children classes.
 * @author Austin Gilmore, Brandon McMillin
 */
public interface ClassInfo{
	ArrayList<String[]> roster = new ArrayList<String[]>();
	public abstract boolean rosterCreated();
	public abstract void create() throws FileNotFoundException;
	public abstract void readFile(File file);
}
