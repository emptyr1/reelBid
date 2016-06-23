package com.zdatainc.rts.storm;

import org.apache.log4j.Logger;
import java.util.Map;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import static com.github.fhuss.storm.cassandra.DynamicStatementBuilder.*;


public class CassandraBolt extends BaseBasicBolt
{

	private static final long serialVersionUUID = 42L;

	 public void declareOutputFields(OutputFieldsDeclarer declarer)
    	{
        	declarer.declare(new Fields("tweet_id", "tweet_text"));
    	}


	public void execute(Tuple input, BasicOutputCollector collector) {

	}
}

