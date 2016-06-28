var express = require('express');
var app = express();
var bodyParser = require('body-parser')
app.use(bodyParser.urlencoded({ extended: false }))

// parse application/json
app.use(bodyParser.json())

var kafka = require('kafka-node'),
    HighLevelProducer = kafka.HighLevelProducer,
    client = new kafka.Client(),
    producer = new HighLevelProducer(client),
    payloads = [{ topic:'mytopic', messages:'message kafka', partition:0 }];


producer.on('ready', function () {
    producer.send(payloads, function (err, data) {
        console.log(data);
    });
});

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.post('/api', function(request, response){
  console.log(request.body);      // your JSON
  response.send(request.body);    // echo the result back
  var name = request.body.name;
  console.log(name);
  var payloads = [{ topic:'mytopic', messages:'hi', partition:0 }];
  producer.send(payloads, function (err, data) {
        console.log(data);
    });
  producer.on('ready', function() {
  	producer.send(payloads, function(err, data) {
		console.log('name senttt to kafka');
	});
   });
});

app.listen(3000, function() {
	console.log("started ");
})
