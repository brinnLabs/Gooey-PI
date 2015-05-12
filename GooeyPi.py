#==============================================================================
#
#				Gooey PI a simple Gui for GPIO Control
#					Written by Dom Amato 4/28/2015
#
#==============================================================================

#==============================================================================
# import all our modules

from time import sleep
import RPi.GPIO as GPIO
from sys import exit
import xml.etree.ElementTree as ET
import time
from threading import Timer

#==============================================================================
# our board numbering, we should use BCM to preserve things across hardware
GPIO.setmode(GPIO.BCM)

#==============================================================================
# heres all the XML settings lets break them out into parts

tree = ET.parse('/boot/settings.xml')
root = tree.getroot()
setup = root.find('setup_IO_pins')
input = setup.find('input')
output = setup.find('output')
control = root.find('Control')


#==============================================================================
# all our dictionaries and lists
input_pins = [] #we dont have to search these so lists are good
output_pins = []

controls = {}

#==============================================================================
# take the settings and make it usable 

for pins in input.findall('pin'):
	#this is ugly but it gives us a list of tuples of the pin and its state
	input_pins.append((int(pins.text), pins.attrib.values()[0]))

for pins in output.findall('pin'):
	#this is ugly but it gives us a list of tuples of the pin and its state
	output_pins.append((int(pins.text), pins.attrib.values()[0]))
	
for outputs in control.findall('Output'):
	#a dictionary that holds a tuple/pair 
	controls[int(outputs.attrib.values()[0])] = (int(outputs.find('pin').text),(int(outputs.find('duration').text) if int(outputs.find('duration').text) > 0 else 0))

#=============================================================================   
# we need to handle the threaded callbacks here   

def input_callback(channel):
	#check if we can start a new audio stream
	if(channel in controls):
		#get the current state of the pin and invert it
		state = GPIO.input(channel)
		GPIO.output(channel, Not(state))
		if(controls[channel][1] > 0):
			#check if the pin isn't a toggle
			Timer(controls[channel][1]/1000.0, reset_pin, [channel, state]).start()


def reset_pin(channel, state):
	GPIO.output(channel, state)

#==============================================================================
#setup pins here
for pins in input_pins:
   if (pins[1].upper() == 'PULLUP'):
		GPIO.setup(pins[0], GPIO.IN, pull_up_down=GPIO.PUD_UP)
		GPIO.add_event_detect(pins[0], GPIO.FALLING, callback=input_callback, bouncetime=200) 
		
   else: #we just assume if not declared its a pull down pin 
		GPIO.setup(pins[0], GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
		GPIO.add_event_detect(pins[0], GPIO.RISING, callback=input_callback, bouncetime=200) 

for pins in output_pins:
	if (pins[1].upper() == 'HIGH'):
		GPIO.setup(pins[0], GPIO.OUT, initial=GPIO.HIGH)
	else:
		GPIO.setup(pins[0], GPIO.OUT, initial=GPIO.LOW)
   
			
#==============================================================================
#our infinite loop to run everything

while True:
	try:   
		#button presses are threaded so not handled in main loop
		sleep(.01)
	except KeyboardInterrupt:
		GPIO.cleanup()
		exit()