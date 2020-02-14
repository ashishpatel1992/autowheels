#!/usr/bin/env python

import struct, os
#import turtle
import rospy
from std_msgs.msg import String

file = open("/dev/input/mice" , "rb");

point_x = 0
point_y = 0
#t = turtle.Turtle()

class Point:
    x=0.0
    y=0.0
    
def getMouseEvent():
    buf = file.read(3);
    x,y = struct.unpack("bb",buf[1:]);
    dis = Point();
    dis.x = x;
    dis.y = y;
    return dis;
    
def loop(point_x,point_y):   
    while(1):
        dis = getMouseEvent();
        pub = rospy.Publisher('mouse_in', String, queue_size=10)
        rospy.init_node('pos_sensor', anonymous=True)
        rate = rospy.Rate(10) # 10hz
        point_x = point_x + dis.x ;
        point_y = point_y + dis.y;
        p1 = point_x * 0.00277777777
        p2 = point_y * 0.00277777777
        #t.setposition(point_x, point_y)
        print ("%d %d" % (point_x,point_y));
        print( p1 , p2 );
        message = str(p1) + "_" + str(p2)
        rospy.loginfo(message)
        pub.publish(message)
        rate.sleep()
    
def destroy():
	GPIO.cleanup()             # Release resource
	file.close();

if __name__ == '__main__':     # Program start from here
	try:
		loop(point_x,point_y)
	except KeyboardInterrupt:  # When 'Ctrl+C' is pressed, the child program destroy() will be  executed.
		destroy()

