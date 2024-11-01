
Overview:
The fifth part of a multi-project series, this Java program adds a graphical user interface (GUI) for visualizing the GPS trip data. Using previous project data, it plots the trip points on a map and animates the route, providing an interactive and visual representation of the trip.

In this project, we implement a map GUI using Java, allowing users to view the trip path with an animation feature that tracks the trip’s progression. Users can select animation speed, include or exclude stop points, and view the route as it plays out on the map. This project integrates a mapping library to facilitate visualization.

Features:
1. Interactive Map: Displays GPS coordinates from the trip data on a map, showing the entire trip path.
2. Animation Controls:
  - Animation Speed: Offers options to animate the route over 15, 30, 60, or 90 seconds.
  - Stop Inclusion: Provides a checkbox to toggle display of stops on the route.
  - Play/Reset Button: Begins the animation or resets it to start anew, clearing previous animations from the map.
3. Current Position Tracking: Shows the current trip point with an icon and leaves a trail of the path.|

Required GUI Components:
1. JComboBox: Allows selection of the animation duration.
2. JCheckBox: Toggles inclusion of stops in the route visualization.
3. JButton: Starts or resets the animation, clearing prior tracks if re-played.

Map and Animation Details:
  - Map Centering: Centers the map over the area of the trip for optimal view.
  - Position Tracking: Uses an icon to show the current position on the route.
  - Trail Display: Leaves a line showing the route behind the position icon, highlighting the path taken.

Getting Started:
1. Setup: Clone the repository, import the triplog.csv file from previous projects, and add the required mapping library (e.g., JMapViewer).
2. Run: Start the GUI and use the controls to animate the trip route.
3. Customize Animation: Adjust animation speed and include or exclude stops for varied visualization.