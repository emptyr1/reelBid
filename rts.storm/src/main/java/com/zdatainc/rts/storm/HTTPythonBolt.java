
package com.zdatainc.rts.storm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.task.ShellBolt;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
//import storm.starter.spout.RandomSentenceSpout;

import java.util.HashMap;
import java.util.Map;

public class HTTPythonBolt extends ShellBolt implements IRichBolt {
    public HTTPythonBolt() {
        super("python", "sendPostresponse.py");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
    
    @Override
    public Map<String, Object> getComponentConfiguration() {
      return null;
    }
}
