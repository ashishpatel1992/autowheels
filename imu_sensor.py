#!/usr/bin/env python

import logging
import sys
import time
import rospy
from std_msgs.msg import String

from Adafruit_BNO055 import BNO055

bno = BNO055.BNO055(serial_port='/dev/serial0', rst=18)

# Enable verbose debug logging if -v is passed as a parameter.
if len(sys.argv) == 2 and sys.argv[1].lower() == '-v':
    logging.basicConfig(level=logging.DEBUG)
    
while True:
    try:
        # Initialize the BNO055 and stop if something went wrong.
        if not bno.begin():
            raise RuntimeError('Failed to initialize BNO055! Is the sensor connected?')

        # Print system status and self test result.
        status, self_test, error = bno.get_system_status()
        break
    except Exception as e:
        print("Got error: {}".format(e))
        print("Sleeping is before retrying")
        time.sleep(1)

print('System status: {0}'.format(status))
print('Self test result (0x0F is normal): 0x{0:02X}'.format(self_test))
# Print out an error if system status is in error mode.
if status == 0x01:
    print('System error: {0}'.format(error))
    print('See datasheet section 4.3.59 for the meaning.')

# Print BNO055 software revision and other diagnostic data.
sw, bl, accel, mag, gyro = bno.get_revision()
print('Software version:   {0}'.format(sw))
print('Bootloader version: {0}'.format(bl))
print('Accelerometer ID:   0x{0:02X}'.format(accel))
print('Magnetometer ID:    0x{0:02X}'.format(mag))
print('Gyroscope ID:       0x{0:02X}\n'.format(gyro))
print('Reading BNO055 data, press Ctrl-C to quit...')

def loop():
    tt = 1
    while True:
        pub = rospy.Publisher('imu_in', String, queue_size=10)
        rospy.init_node('angle_sensor', anonymous=True)
        rate = rospy.Rate(10) # 10hz
        # Read the Euler angles for heading, roll, pitch (all in degrees).
        heading, roll, pitch = bno.read_euler()
        # Read the calibration status, 0=uncalibrated and 3=fully calibrated.
        sys, gyro, accel, mag = bno.get_calibration_status()
        # Print everything out.
        print('Heading={0:0.2F}'.format(heading))
        # Sleep for a second until the next reading.
        message = str(heading)
        rospy.loginfo(message)
        pub.publish(message)
        rate.sleep()
        #time.sleep(tt)
        
def destroy():
	GPIO.cleanup()             # Release resource

if __name__ == '__main__':     # Program start from here
	try:
		loop()
	except KeyboardInterrupt:  # When 'Ctrl+C' is pressed, the child program destroy() will be  executed.
		destroy()
