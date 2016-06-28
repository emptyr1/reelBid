package com.zdatainc.rts.storm;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Map;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class FirstFilterBolt extends BaseBasicBolt
{
    private static final long serialVersionUID = 42L;
    private static final Logger LOGGER =
        Logger.getLogger(FirstFilterBolt.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet_id", "tweet_text"));
    }

    public void execute(Tuple input, BasicOutputCollector collector)
    {
        LOGGER.info("filttering incoming tweets");
        String json = input.getString(0);
        try
        {
            JsonNode root = mapper.readValue(json, JsonNode.class);
            long id;
            String text;
            if (root.get("lang") != null &&
                "en".equals(root.get("lang").textValue()))
            {
                if (root.get("id") != null && root.get("text") != null)
                {
                    id = root.get("id").longValue();
                    text = root.get("text").textValue();
                    collector.emit(new Values(id, text));
                }
                else
                    LOGGER.debug("tweet id and/ or text was null");
            }
            else
                LOGGER.debug("Ignoring non-english tweet");
        }
        catch (IOException ex)
        {
            LOGGER.error("IO error while filtering tweets", ex);
            LOGGER.trace(null, ex);
        }
    }

    public Map<String, Object> getComponenetConfiguration() { return null; }
}
