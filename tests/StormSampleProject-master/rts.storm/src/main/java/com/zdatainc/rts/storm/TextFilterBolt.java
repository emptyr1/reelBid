package com.zdatainc.rts.storm;

import org.apache.log4j.Logger;
import java.util.Map;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TextFilterBolt extends BaseBasicBolt
{
    private static final long serialVersionUID = 42L;
    private static Logger LOGGER = Logger.getLogger(TextFilterBolt.class);

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet_id", "tweet_text"));
    }

    public void execute(Tuple input, BasicOutputCollector collector)
    {
        LOGGER.debug("removing ugly characters");
        Long id = input.getLong(input.fieldIndex("tweet_id"));
        String text = input.getString(input.fieldIndex("tweet_text"));
        text = text.replaceAll("[^a-zA-Z\\s]", "").trim().toLowerCase();
        
        collector.emit(new Values(id, text));
    }

    public Map<String, Object> getComponentConfiguration() { return null; }
}
