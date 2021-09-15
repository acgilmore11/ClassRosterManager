/** 
 *Name: Austin Gilmore 1216396051, Brandon McMillin 1216859943
 *Course ID: 70605 CSE360 Tu 9:00-10:15
 *Assignment: Final 360 Project
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * ScatterPlot class implements the Observer interface and creates a scatterplot from
 * attendance info.
 * @author Austin Gilmore, Brandon McMillin
 */
public class ScatterPlot implements Observer{
	/**
	 * These variables represent attendance data that will be used to construct scatterplot.
	 */
	private ArrayList<XYSeries> seriesOfData = new ArrayList<XYSeries>();
	public XYSeriesCollection seriesCollection = new XYSeriesCollection();
	private JFreeChart chart;
	
	/**
	 * The update method creates a series of data every time a new attendance file is selected.
	 * @param o Represents Observable object in which the ScatterPlot class observes.
	 * @param arg
	 */
	public void update(Observable o, Object arg){
		seriesOfData.add(createNewSeries(o));
		seriesCollection.addSeries(seriesOfData.get(seriesOfData.size() - 1));
		createChart(o);
	}
	
	/**
	 * The createChart method initializes a JFreeChart with the attendance data.
	 * @param o Represents Observable object in which the ScatterPlot class observes.
	 */
	public void createChart(Observable o) {
		chart = ChartFactory.createScatterPlot("Attendance Chart", "% of Total Minutes Attended","# of Students", createDataset(o), PlotOrientation.VERTICAL, true, true, true);
	}
	
	/**
	 * The createDataset method converts an XYSeriesCollection to an XYDataset to be used in the JFreeChart.
	 * @param o Represents Observable object in which the ScatterPlot class observes.
	 * @return seriesCollection Represents series of data from a day of attendance.
	 */
	private XYDataset createDataset(Observable o) {
		return seriesCollection;
	}
	
	/**
	 * The createNewSeries method creates a new series of data from the attendance info that will
	 * eventually be added to the scatterplot.
	 * @param o Represents Observable object in which the ScatterPlot class observes.
	 * @return attendanceSeries XYSeries object that represents attendance info for particular day.
	 */
	private XYSeries createNewSeries(Observable o) {
		String[][] attendanceInfo =  ((Attendance)o).getUniqueIds();
		XYSeries attendanceSeries = new XYSeries(((Attendance)o).getDate());
		ArrayList<Integer> uniqueMinutes = new ArrayList<Integer>();
		int numNull = ((Attendance)o).getRosterSize() - ((Attendance)o).getNumUnique();
		
		if(numNull > 0)
			uniqueMinutes.add(0);
		
		//Iterates through attendanceInfo 2D array and adds any unique minute 
		//entries to uniqueMinutes array
		for (int i = 0; i < ((Attendance)o).getNumUnique(); i++) {
			int minutes = Integer.parseInt(attendanceInfo[i][1]);
			boolean found = false;
			int j = 0;
			while (j < uniqueMinutes.size() && !found) {
				if (uniqueMinutes.get(j) == minutes)
					found = true;
				j++;
			}
			if (!found) {
				uniqueMinutes.add(minutes);
			}
		}
	
		//Iterates through uniqueMinutes. For every unique Minutes, iterates through
		//attendanceInfo and keeps track of number of attendees who attended for
		//that number of minutes.
		double[][] values = new double[uniqueMinutes.size()][2];
		for (int i = 0; i < uniqueMinutes.size(); i++) {
			values[i][0] = uniqueMinutes.get(i);
			if (uniqueMinutes.get(i) == 0)
				values[i][1] = numNull;
			else {
				int minutes = uniqueMinutes.get(i);
				int count = 0;
				for (int j = 0; j < ((Attendance)o).getNumUnique(); j++) {
					if (minutes == Integer.parseInt(attendanceInfo[j][1]))
						count++;
				}
				values[i][1] = count;
			}
		}
		
		//Iterates through values and adds entries to attendanceSeries.
		int over75 = 0;
		for(int i = 0; i < values.length; i++) {
			if (values[i][0] >= 75) {
				over75 += values[i][1];
			} else { 
				attendanceSeries.add(((values[i][0]/75) * 100), values[i][1]);
			}
		}
		if (over75 > 0)
			attendanceSeries.add(100, over75);
		
		return attendanceSeries;
	}
	
	/**
	 * The getChart method is an accessor method that returns chart.
	 * @return chart JFreeChart that represents scatterplot.
	 */
	public JFreeChart getChart() {
		return chart;
	}

}
