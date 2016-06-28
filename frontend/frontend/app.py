from flask import Flask, render_template, request, url_for
from flask_bootstrap import Bootstrap

# Initialize the Flask application
app = Flask(__name__)

# Define a route for the default URL, which loads the form
@app.route('/')
def create_app():
    #Bootstrap(app)
    #return app
    return render_template('form_submit.html')

@app.route('/hello/', methods=['POST'])
def hello():
    name=request.form['yourname']
    email=request.form['youremail']
    return render_template('form_action.html', name=name, email=email)


@app.route('/test', methods=['GET','POST'])
def bids():
    print 'checkkkk'
    print request.values
    return 'Checking.. working fine'



# Run the app :)
if __name__ == '__main__':
	app.run( 
        	host="0.0.0.0",
        	port=int("80")
  		)
