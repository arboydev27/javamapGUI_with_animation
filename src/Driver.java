/**
 * @author Arboy Magomba
 * Version 5.0
 * Project 5
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;


public class Driver implements ActionListener {
	
	// Declare class data
	JFrame myFrame;
	JPanel topPanel;
	JPanel mapPanel;
	JButton playButton;
	JCheckBox myCheckBox;
	JComboBox myComboBox;
	String[] AnimationTimes;
	JMapViewer mapViewer;
	Timer timer;
	MapMarker marker;
	ArrayList<TripPoint> tripPoints;
	Image raccoonImage;
	
	Driver() throws FileNotFoundException, IOException  {
    	myFrame = new JFrame("Project 5 - Arboy Magomba");
    	topPanel = new JPanel();
    	mapPanel = new JPanel(new BorderLayout());

    	playButton = new JButton("Play");
    	myCheckBox = new JCheckBox("Include Stops");
    	AnimationTimes = new String[]{"Animation Time", "15", "30", "60", "90"};
    	myComboBox = new JComboBox(AnimationTimes);
    	
    	playButton.addActionListener(this);
    	myCheckBox.addActionListener(this);
    	myComboBox.addActionListener(this);
    	
    	mapViewer = new JMapViewer();
		mapViewer.setTileSource(new OsmTileSource.TransportMap());
		
		// Setting initial position and zoom level
		Coordinate usaCenter = new Coordinate(39.50, -98.35); // Approximate geographical center of the continental United States
		mapViewer.setDisplayPosition(new Point(0, 0), usaCenter, 5); 
		
		TripPoint.readFile("triplog.csv");
		tripPoints = TripPoint.getTrip();
		System.out.println("Number of trip points: " + tripPoints.size()); //Debugging purposes
		
		raccoonImage = new ImageIcon("raccoon.png").getImage();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			// Stop any ongoing animation
			if (timer != null) {
				timer.cancel();
				mapViewer.removeAllMapMarkers();
				mapViewer.removeAllMapPolygons();
			}
			
			
			// Retrieve the selected animation time from the combo box
			int animationTime = Integer.parseInt((String) myComboBox.getSelectedItem());
			System.out.println("Animation time: " + animationTime); // Debugging purposes
			
			// Calculate the delay between each execution of the TimerTask
	        int delay = animationTime * 1000 / tripPoints.size();
	        
	        
	     // Depending on the state of the "Include Stops" checkbox, I use either the entire trip or the filtered moving trip
	        //ArrayList<TripPoint> tripPoints;
	        try {
	        if (!myCheckBox.isSelected()) {
	            // Either h1 or h2 stop detection from TripPoint
	            int numStops = TripPoint.h1StopDetection();  // 
	            tripPoints = TripPoint.getMovingTrip();  //
	        } else {
	            tripPoints = TripPoint.getTrip();
	        }
	        } catch (Exception e1) {
	        	e1.printStackTrace();
	        }
	        
	        
			
			// A new timer object with the calculated delay
			timer = new Timer();	
			timer.schedule(new TimerTask() {
				int i = 0;
				MapMarker prevMarker = null;
				
				@Override
				public void run() {
					
					if (i < tripPoints.size()) {
			            // Get the current trip point
			            TripPoint point = tripPoints.get(i);
			            double lat = point.getLat();
			            double lon = point.getLon();
			            
			            // If there's a previous marker, change it to a red dot
			            if (prevMarker != null) {
			                mapViewer.removeMapMarker(prevMarker);
			                MapMarkerDot redMarker = new MapMarkerDot(prevMarker.getLat(), prevMarker.getLon());
			                redMarker.setColor(Color.RED);
			                redMarker.setBackColor(Color.RED);
			                mapViewer.addMapMarker(redMarker);
			            }
			            
			            // A new marker for the current trip point with the raccoon image
			            IconMarker marker = new IconMarker(new Coordinate(lat, lon), raccoonImage);
			            mapViewer.addMapMarker(marker);
			            
			            // Update prevMarker with the current marker
			            prevMarker = marker;
			            
			            i++;
			        } else {
			            timer.cancel();
			        }
					
					mapViewer.repaint(); 
				} 

			}, 0, delay);
		}
	}
	

    public static void main(String[] args) throws FileNotFoundException, IOException {
    	Driver driver = new Driver();

    	// Add all to myFrame. So myFrame contain 2 panels, the topPanel, and the mapPanel
    	driver.myFrame.setLayout(new BorderLayout());
		driver.myFrame.setSize(1100, 800);
		driver.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driver.myFrame.setVisible(true);
		driver.mapViewer.setVisible(true);
		
		driver.topPanel.add(driver.myComboBox);
		driver.topPanel.add(driver.myCheckBox);
		driver.topPanel.add(driver.playButton);
		
		driver.mapPanel.add(driver.mapViewer);
		
		driver.myFrame.add(driver.topPanel, BorderLayout.NORTH);
        driver.myFrame.add(driver.mapPanel, BorderLayout.CENTER);
    }
}

