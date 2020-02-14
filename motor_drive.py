#!/usr/bin/env python
import rospy
from std_msgs.msg import String
import RPi.GPIO as GPIO

in1 = 24
in2 = 8
en1 = 25		#PWM pin to run left rear motor
in3 = 7
in4 = 11
en2 = 9		#PWM pin to run right rear motor

GPIO.setmode(GPIO.BCM)

def callback(data):
    
    rospy.loginfo(rospy.get_caller_id() + "I heard %s", data.data)
    x = data.data
    print(x)
    if x == "forward":
		GPIO.output(in1, GPIO.LOW)
		GPIO.output(in2, GPIO.HIGH)
		GPIO.output(in3, GPIO.LOW)
		GPIO.output(in4, GPIO.HIGH)
    elif x == "right":
	    GPIO.output(in1, GPIO.HIGH)
	    GPIO.output(in2, GPIO.LOW)
	    GPIO.output(in3, GPIO.LOW)
	    GPIO.output(in4, GPIO.HIGH)
    elif x == "left":
        GPIO.output(in1, GPIO.LOW)
        GPIO.output(in2, GPIO.HIGH)
        GPIO.output(in3, GPIO.HIGH)
        GPIO.output(in4, GPIO.LOW)
    
def listener():

    # In ROS, nodes are uniquely named. If two nodes with the same
    # name are launched, the previous one is kicked off. The
    # anonymous=True flag means that rospy will choose a unique
    # name for our 'listener' node so that multiple listeners can
    # run simultaneously.
    rospy.init_node('listener', anonymous=True)

    rospy.Subscriber("output", String, callback)

    # spin() simply keeps python from exiting until this node is stopped
    rospy.spin()

if __name__ == '__main__':
    listener()
