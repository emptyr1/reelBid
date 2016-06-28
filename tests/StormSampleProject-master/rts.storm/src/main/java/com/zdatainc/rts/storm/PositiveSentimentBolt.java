package com.zdatainc.rts.storm;

import org.apache.log4j.Logger;
import java.util.Set;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PositiveSentimentBolt extends BaseBasicBolt
{
    private static final long serialVersionUID = 42L;
    private static final Logger LOGGER =
        Logger.getLogger(PositiveSentimentBolt.class);

    public void execute(Tuple input, BasicOutputCollector collector)
    {
        LOGGER.debug("Calculating positive score");
        Long id = input.getLong(input.fieldIndex("tweet_id"));
        String text = input.getString(input.fieldIndex("tweet_text"));
        Set<String> posWords = PositiveWords.getWords();
        String[] words = text.split(" ");
        int numWords = words.length;
        int numPosWords = 0;
        for (String word : words)
        {
            if (posWords.contains(word))
                numPosWords++;
        }
        collector.emit(new Values(id, (float) numPosWords / numWords, text));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet_id", "pos_score", "tweet_text"));
    }
}
