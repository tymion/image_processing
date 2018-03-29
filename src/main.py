#!/usr/bin/python

import cv2
import numpy as np
import matplotlib.pyplot as plt

import algorithm

data = [[0,1,2,3],[0,1,2,3],[1,4,5,6],[1,7,8,9]]
region = [[1,2,3],[4,5,6],[-1,8,9]]
regions = find_region(data, 4, 4, region, 3)
print regions

#img_r = cv2.imread('../resources/right.png')
#img_l = cv2.imread('../resources/left.png')
#rows,cols,ch = img_r.shape

#img_tmp = img_r[:3,:3]

#plt.subplot(121),plt.imshow(img_tmp),plt.title('Input')
#plt.subplot(122),plt.imshow(dst),plt.title('Output')
#plt.show()

#res = cv2.resize(img,None,fx=2, fy=2, interpolation = cv2.INTER_CUBIC)

#OR

#height, width = img.shape[:2]
#res = cv2.resize(img,(2*width, 2*height), interpolation = cv2.INTER_CUBIC)
