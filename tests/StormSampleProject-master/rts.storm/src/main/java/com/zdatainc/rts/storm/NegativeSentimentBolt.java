package com.zdatainc.rts.storm;

import org.apache.log4j.Logger;
import java.util.Set;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class NegativeSentimentBolt extends BaseBasicBolt
{
    private static final long serialVersionUID = 42L;
    private static final Logger LOGGER =
        Logger.getLogger(NegativeSentimentBolt.class);

    public void execute(Tuple input, BasicOutputCollector collector)
    {
        LOGGER.debug("Calculating negitive score");
        Long id = input.getLong(input.fieldIndex("tweet_id"));
        String text = input.getString(input.fieldIndex("tweet_text"));
        Set<String> negWords = NegativeWords.getWords();
        String[] words = text.split(" ");
        int numWords = words.length;
        int numNegWords = 0;
        for (String word : words)
        {
            if (negWords.contains(word))
                numNegWords++;
        }
        collector.emit(new Values(id, (float)numNegWords / numWords, text));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet_id", "neg_score", "tweet_text"));
    }
}
