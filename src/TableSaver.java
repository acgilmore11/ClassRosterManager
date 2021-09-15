/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Course ID: 70605 CSE360 Tu 9:00-10:15
 *Assignment: Final 360 Project
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * The TableSaver class receives the table constructed using attendance/roster info
 * and writes its contents to a csv file.
 * @author Austin Gilmore, Brandon McMillin
 *
 */
public class TableSaver {
	/**
	 * table represents the attendance/roster table and saveCount is a counter 
	 * variable that keeps track of the number of saves.
	 */
	private JTable table;
	private int saveCount = 0;
	
	/**
	 * The saveTable method takes in a JTable and writes its contents to a csv file.
	 * @param table
	 * @throws IOException
	 */
	public void saveTable(JTable table) throws IOException {
		String saveFileName = "";
		this.table = table;
		FileWriter saveFile = null;
		
		//if statement that adds saveCount to file name if save option has been selected more than once.
		if (saveCount == 0) {
			try {
				saveFileName = "Class Roster & Attendance.csv";
				saveFile = new FileWriter(saveFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				saveFileName = "Class Roster & Attendance-" + saveCount + ".csv";
				saveFile = new FileWriter(saveFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		saveCount++;
		
		//contents represents info from attendance/roster table
		TableModel contents = table.getModel();
		
		for(int i = 0; i < contents.getColumnCount(); i++) {
			saveFile.write(contents.getColumnName(i) + ",");
		}
		saveFile.write("\n");
		
		for (int i = 0; i < contents.getRowCount(); i++) {
            for (int j = 0; j < contents.getColumnCount(); j++) {
                saveFile.write(contents.getValueAt(i,j) + ",");
            }
            saveFile.write("\n");
        }

        saveFile.close();
		
        JOptionPane.showMessageDialog(null, "File successfully saved as \"" + saveFileName + "\"");
		
	}

}
