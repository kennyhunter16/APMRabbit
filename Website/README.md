#APMRabbit

Software created for the APMRover Project located (http://rover.ardupilot.com/). 

This is a GUI Website interface coded in HTML5 and JavaScript used to stream and control the GoPro Hero 3+ camera.
The application was created to view the mounted camera on the APM Rover, Drone or another APM-Based device. The
Website has currently been tested on Hero 3+ only, most test results will be done. This will support only
GoPro cameras with WiFi.

##Requirements for Website

Here is a list of what needs to be there for this to work.

1. GoPro Hero 3+
2. VLC Web Plugin
3. GoPro WiFi Password to change mode settings
4. Internet Browser (Tested on Firefox/Chrome)


##Setup Process

Here is a list of what needs to be done in order to get this to work

1. Download the package file here.
2. Extract the ZIP file, and open the folder containing "index.html"
3. Open your WiFi Manager and connect to your GoPro Cameras WiFi Hotspot (Check GoPro Manual for help)
4. Once connected, open the index.html in your browser

##How to Use

The website is divided into multiple pages

1. Home - Displays basic introduction information about the developers. Applications and copyright
2. About - Information about the developers
3. Stream - This page will display a window that will display the current stream of the camera. The page
        Is using VLC Web Plugin to perform the streaming functions.
        Below the window, will display a password field, the password is the same password you use when logging into
        Your device via WiFi. This is required before changing any mode functions.
        Once your password has been set. You may change the camera modes of the camera by selecting the 4 icons below
        (Video, Photo, Burst, Timelapse) - read GoPro docs for more information about the modes
        Then when you are ready you can apply the shutter functions below the mode!.
4. Settings -The page is used to change all the advance settings of your GoPro. You will need to type in the password
        That you used for your WiFi again in the password box above before changing settings. 
        The current settings can be changed by selecting something from the drop-down menus
5. View Content - Still in development, need help from the community
6. FAQ - Will be displaying FAQ that was asked into the page. 

##F.A.Q
    
    Q: On the Stream page, it says the plugin is not supported
    A: Currently the VLC Web plugin is only supported on Windows and Mac OS systems

    Q: When I started recording a video, the streaming stopped?
    A: You cannot record a video and stream a video at the same time. GoPro doesn't allow that.

    Q: The Streaming works, but nothing is happening when I am changing the settings
    A: Make sure the password is typed and set, before changing camera settings. The password is the same one used for your WiFi






