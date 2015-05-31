# Gooey-PI
Just a simple GUI to setup a Raspberry PI's GPIO pins

Built With OpenFrameworks and the PI's native libraries

For pinouts goto http://pi.gadgetoid.com/pinout though I included pictures for reference too

Note that everything is handled by the settings which the program looks for in the boot directory. This makes it easy to replace or update things without having to move files between systems. Plugging the SD card into any PC should expose the Boot partition for easy switching.

## Installation

to get this to run at boot put GooeyPI.py in the boot folder and edit rc.local which is found in /etc/ by doing

    sudo nano /etc/rc.local
	
and then replace the IP dummy script with

    sudo python /boot/GooeyPI.py

Make sure to edit the settings file to the setup you would like