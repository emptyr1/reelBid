from flask import jsonify
#jsonify creates a json representation of the response
from app import app
from cassandra.cluster import Cluster
#importing Cassandra modules from the driver we just installed

# setting up connections to cassandra

cluster = Cluster(['172.31.1.168'])

session = cluster.connect('rtb')

@app.route('/')

@app.route('/index')
def index():
	return "Hello, World!"


@app.route('/api/<pageviews>/<ts>')
def get_email(pageviews, ts):
       stmt = "SELECT * FROM demopages WHERE pageviews=%s and ts=%s"
       response = session.execute(stmt, parameters=[pageviews, ts])
       response_list = []
       for val in response:
            response_list.append(val)
       return 'test' 
       #jsonresponse = [{"first name": x.fname, "last name": x.lname, "id": x.id, "message": x.message, "time": x.time} for x in response_list]
       #return jsonify(emails=jsonresponse)
