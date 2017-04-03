# ReelBid
---

ReelBid is a real time ad bidding / *programmatic* framework, which can be used to bid on website advertisements in real time(100 ms). Currently, it works with openrtb[]-protocol-complaint Ad exchanges like Google Adx, AppNexus Smaato etc. 
It was a part of [Insight data engineering fellowship](http://insightdatascience.com/).

Find related slides for more details: [https://goo.gl/3Bl15V](https://goo.gl/3Bl15V)

### Getting started
---

(Due to few security concerns and credentials the code is still being uploaded and somewhat incomplete)

What is real time bidding? Simply put, imagine a ebay auction where auction lasts for 100 milliseconds. There's a seller side(SSP) and buying side(DSP). In this case, an AdExchange would be `ebay`, hosting the auction and `ReelBid`[this project] would be the buying side, used by any advertiser. Depending on the business logic, registered/interested bidders/companies bid for a spot on a webpage for **every user impression** before the page fully loads -- hence, more targeted advertising. To know more, I recommend reading [this](https://www.youtube.com/watch?v=NoGgLxky1FE), [this](https://www.youtube.com/watch?v=rTg9l4d8MU4) or watch this 60 second video. (Remember you are bidding on every user impression on that website)
Or check out my slides [here](https://goo.gl/3Bl15V). 

To get started, you need:





### Methods & Technologies used to acheive high throughput
---

All technologies used were supposed to be programmed in a highly asynchronous + using probabilistic data structures like Bloom filter and Hyperloglog -- the key to make the system more effective and acheive sub-second latency. The clients (and ideally should) use non-blocking IO to implement request pipelining and achieve higher throughput. i.e., clients can send requests even while awaiting responses for preceding requests since the outstanding requests will be buffered in the underlying OS socket buffer.
(Java did not turn out to be the best language for this, due to global lock because of Java garbage collection. Go/golang would have been perfect for this and would be my next step)


### Architecture 2.0
---

![imagetxt](https://github.com/modqhx/reelBid/blob/master/frontend/images/Arch2.png)

A total of 14 nodes were used and I tried using Amabari from Hortonworks for cluster mgmt. 

Other tools include Vagrant & Docker.. (inside processing folder) -- so you need to install Vagrant (>= 1.6) and VirtualBox, then run:

![imagetxt2](https://github.com/modqhx/reelBid/blob/master/frontend/images/frontend_form.png)
![imagetxt3](https://github.com/modqhx/reelBid/blob/master/frontend/images/dashbrd1.png)
![imagetxt4](https://github.com/modqhx/reelBid/blob/master/frontend/images/bids.png)




### Analysis and Networking challenges
---

Q1: Why use redshift and what analysis is being done? 

> We are bidding on online ads based on probablistic models. We would like to know anything about what are paying for ads at this moment -not just how much I have spent in the past hour, but what percent of ads have I spent more than a penny on, for example. Or what's the 90th or 5th percentile- which involves alot of aggregations and groupings, and relational db seemed like a good choice for this. Redshift being an OLAP is pretty fast and support extremely large data sizes. 

Q2: How do you handle 2 million hits per second? 

> Sampling! Using Ziggurat Algorithm to sample some random values following gaussian or gamma distribution. Check around slide 10 [here](https://goo.gl/3Bl15V). Reservoir sampling or VIRB's(Variable incoming rate biased samplers) are another good techniques which I tried & worked well when I need a biased sample. Check out my [recent post](https://medium.com/@muppal) on medium.  

Q3: What strategy were used to accept so many requests per second? 

> There's this interesting paper published in IEEE on Data mining on Scaling RTB which was a good and sole inspiration for this project. Find it [here](http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=7373421&url=http%3A%2F%2Fieeexplore.ieee.org%2Fxpls%2Fabs_all.jsp%3Farnumber%3D7373421).  



### Testing
---

How did I test the system? 
> I tested the system by creating a mock exchange, which sent random valid(or invalid) bid request on port 12336 and WINS on port 12339. Validation was checked at the receiving side. 
> Unit testing was done with Smaato Ad Exchange. 
> I also tried using this [parallec.io](https://github.com/eBay/parallec) to basically DDos attack my system. 


### QA
---


### What does a bid request look like? How did I deserialize the object?
---
![imagetxt](https://github.com/modqhx/reelBid/blob/master/frontend/images/page_view.png)

A typical `bid request` looks like: 

```
{
	"id": "32a69c6ba388f110487f9d1e63f77b22d86e916b",
	"imp": [{
		"id": "1",
		"banner": {
			"h": 250,
			"w": 300,
			"battr": [2, 3],
			"btype": [1, 3]
		}
	}],
	"site": {
		"id": "102855",
		"name": "mashable.com",
		"domain": "http://www.example.com",
		"cat": ["IAB15", "IAB15-10"],
		"page": "http://easy.example.com/easy?cu=13824;cre=mu;target=_blank",
		"ref": "http://refer+url",
		"publisher": {
			"id": "qqwer1234xgfd",
			"name": "site_name",
			"domain": "my.site.com"
		}
	},
	"device": {
		"ua": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13  (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
		"ip": "192.168.5.5",
		"geo": {
			"lat": 37.789,
			"lon": -122.394,
			"country": "USA",
			"city": "San Francisco",
			"region": "CA",
			"zip": "94105",
			"type": 2
		}
	},
	"user": {
		"buyeruid": "89776897686798fwe87rtryt8976fsd7869678",
		"id": "55816b39711f9b5acf3b90e313ed29e51665623f",
		"gender": "M",
		"yob": 1975,
		"customdata": "Data-asdfdwerewr",
		"data": [{
			"id": "pub-demographics",
			"name": "data_name",
			"segment": [{
				"id": "345qw245wfrtgwertrt56765wert",
				"name": "segment_name",
				"value": "segment_value"
			}]
		}]
	}
}

```
and Bid Response which ReelBid sends back to some Ad exchange includes the id and the `price`.


openRTB is the standard protocol used in real time bidding. I'm using 2.2 version with nodejs, which you can find on npm.



List of major adexchanges: AppNexus, google AdX, Facebook 


### Contribution
---


### Contact
---

I do not have alot of background in advertisement or real time bidding. If I made a mistake or something does not make sense, please let me know. Feel free to reach me at: mudituppal247[at]gmail[dot]com






