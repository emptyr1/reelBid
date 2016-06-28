package org.apache.storm.kafka.spout.test;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
//import backtype.storm.Tuple.Fields;
//import backtype.storm.Tuple.Tuple;
//import backtype.storm.Tuple.Values;


	public class FilterText extends BaseRichBolt {
	    protected static final Logger LOG = LoggerFactory.getLogger(FilterText.class);
	    private OutputCollector collector;
	    private static final long serialVersionUID = 42L;
	    //private String webserver = Properties.getString("rts.storm.webserv");
	   private static final ObjectMapper mapper = new ObjectMapper();
	    private HttpClient client;


    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        LOG.debug("input = TESTINGGGGGGG" + input + "]");
        String json = input.getString(0);
        try {
        	JsonNode root = mapper.readValue(json, JsonNode.class);
        	long id;
        	String text;

        	if(root.get("id") != null) {
        		id = root.get("id").longValue();
        		System.out.println(id);
        		collector.emit(new Values(id));
        	} else {
        		System.out.println("bid was empty");
        	} 

        } catch(IOException ex) {
        	LOG.error("IO error while filtering bid request", ex);
            //LOGGER.trace(null, ex);
        } 

        
        //String value = input.getString(0);
        //System.out.println("Hellllloooooo STREAM 11111 :" + value);
        //int index = value.indexOf(" ");
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
}

