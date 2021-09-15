/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Assignment: Final Project
 */

import javax.swing.*;
import javax.swing.filechooser.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartPanel;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * The World class represents the frame that contains the GUI for the
 * attendance/roster application. 
 * @author Austin Gilmore, Brandon McMillin
 *
 */
public class World extends JFrame{
	/**
	 * These variables are components of the GUI that hold and display
	 * attendance/roster information.
	 */
	private Roster classRoster = new Roster();
	private ScrollPane rosterPane;
	private TableSaver tableSaver = new TableSaver();
	private ChartPanel chartPanel;
	private ScatterPlot scatterPlot;
	private JPanel plotPanel;
	
	/**
	 * This is the main method that initializes the world frame by calling its
	 * constructor
	 * @param args Unused
	 * @return Nothing
	 */
	public static void main(String[] args) {
		World world = new World();
		world.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		world.setSize(1000,1000);
		world.setVisible(true);
	}
	
	/**
	 * This is the constructor method that initializes the variables within
	 * the world class. It also creates the GUI and sets up the action listener
	 * events.
	 * @return Nothing
	 */
	@SuppressWarnings("deprecation")
	World(){
		//Creates the GUI:
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenuItem loadRoster, addAttend, save, plotData, aboutMenu;
		
		//Creates the menu bar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		aboutMenu = new JMenuItem("About");
		menuBar.add(aboutMenu);
		
		loadRoster = new JMenuItem("Load a Roster");
		fileMenu.add(loadRoster);
		
		addAttend = new JMenuItem("Add Attendance");
		fileMenu.add(addAttend);
		
		save = new JMenuItem("Save");
		fileMenu.add(save);
		
		plotData = new JMenuItem("Plot Data");
		fileMenu.add(plotData);
		
		setJMenuBar(menuBar);
		
		setLayout(new GridLayout(2,1));
		
		scatterPlot = new ScatterPlot();
		plotPanel = new JPanel();
		
		rosterPane = new ScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rosterPane.setVisible(false);
		classRoster.addObserver(rosterPane);
		
		//Initializes date picker for attendance option
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
		datePicker.setVisible(false);
		
		//adds datePicker and rosterPane to frame
		add(datePicker);
		add(rosterPane);
	
		//sets up action listeners for menu options
		aboutMenu.addActionListener(new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "CSE360 Final Project\n"
						+ "Austin Gilmore, ID: 1216396051\n"
						+ "Brandon McMillin, ID: 1216859943");
	        }
		});
		
		loadRoster.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				classRoster.create();
			}
		});
		
		addAttend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				datePicker.setVisible(true);
			}
		});
		
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				datePicker.setVisible(false);
				Date selectedDate = (Date) datePicker.getModel().getValue();
				String[] dateArray = selectedDate.toString().split(" ", 6);
				String date = dateArray[1] + " " + dateArray[2];
				Attendance attendance = new Attendance(date);
				attendance.addObserver(rosterPane);
				attendance.addObserver(scatterPlot);
				attendance.create();
				
			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				try {
					tableSaver.saveTable(rosterPane.getRosterTable());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		plotData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				chartPanel = new ChartPanel(scatterPlot.getChart());
				
				plotPanel.add(chartPanel);
				plotPanel.validate();
				JOptionPane pane = new JOptionPane();
				JDialog dialog = pane.createDialog("Attendance");
				dialog.setSize(1000,1000);
				dialog.setContentPane(plotPanel);
				dialog.setVisible(true);	
			}
		});
	}
}
