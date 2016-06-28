import os
import traceback
from collections import deque
import requests
import storm

class SendPostresp(storm.BasicBolt):
    def process(self, tup):
	#r = requests.post("http://ec2-52-41-74-224.us-west-2.compute.amazonaws.com/test", data={'number': 12524, 'type': 'issue', 'action': 'show'}), data={'number': 12524, 'type': 'issue', 'action': 'show'})
	words = tup.values[0].split("")
	#for word in words:
	#	storm.emit([word])

SendPostresp().run()
