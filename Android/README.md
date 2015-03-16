#APMRabbit Android Application

Software created for the APMRover Project located (http://rover.ardupilot.com/). 

This is a GUI Website interface coded in HTML5 and JavaScript used to stream and control the GoPro Hero 3+ camera.
The application was created to view the mounted camera on the APM Rover, Drone or another APM-Based device. The
Website has currently been tested on Hero 3+ only, most test results will be done. This will support only
GoPro cameras with WiFi.

##Requirements for Android

Here is a list of what needs to be there for this to work.

1. GoPro Hero 2 or greater
2. 3DR Adapters (one connect to rover, one for device)
3. 3DR Services Application (you will be linked to it if you don't have it)
4. Micro-USB Male to Male Connector
5. GoPro WiFi Password for camera modes
6. Android Device that supports USB on-the-go connections

##Setup Process

Here is a list of what needs to be done in order to get this to work

1. Download the APK file here.
2. Upload the APK on your device and install your APK
3. Connect to your GoPro WiFi Network for streaming
4. Connect your 3DR connector to your android device using the male to male USB

##How to Use

The website is divided into multiple sections

1. GoPro Page - The following display will show the live stream from your GoPro Device.(coming soon)
		Under the streaming window will show basic controls (Video, Photo, Burst, Timelapse)
		Once a mode is selected you can take a picture, video and stop a video below
		Before using this page MAKE SURE YOU SET YOUR GOPRO PASSWORD

2. Rover Page - The rover page will give you option to connect to your rover using the 3DR connector
		You will need to connect to your device first. Once you are connected you can currently
		Change the mode of your Rover
		View current status infomation

3. History Page - History has not been implemented yet.


4. Settings - This page is used to change all the settings of your GoPro camera and Rover. Currently
              you can only change the settings of the GoPro. You can change all the settings you would
              find on the GoPro menu in this application


5. FAQ - Will be displaying FAQ that was asked into the page. 

##F.A.Q
    
    Q: On the GoPro page, nothing is showing in streaming
    A: Currently the Stream is not working at this moment.

    Q: On the GoPro Page, the modes are not changing on the camera
    A:  1) Make sure you are connect to your GoPro via WiFi
	2) Go into the Settings menu, and make sure your GoPro Password has been set! (the same password as your GoPro WiFi Network

    Q: On the Stream Page, I am unable to connect to the Rover
    A: 1) Make sure there is lights on the device appering on the adapter (if not then your device cannot use this)
       2) Make sure 3DR Services have been installed from the Play Store
       3) Make sure your Rover's adpater is on and has power via battery
       4) Check if you can connect to the rover via APM PC Software






