package com.zdatainc.rts.storm;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSBolt extends BaseRichBolt
{
    private static final long serialVersionUID = 42L;
    private static final Logger LOGGER = Logger.getLogger(HDFSBolt.class);
    private OutputCollector collector;
    private int id;
    private List<String> tweet_scores;

    @SuppressWarnings("rawtypes")
    public void prepare(
        Map stormConf,
        TopologyContext context,
        OutputCollector collector)
    {
        this.id = context.getThisTaskId();
        this.collector = collector;
        this.tweet_scores = new ArrayList<String>(1000);
    }

    public void execute(Tuple input)
    {
        Long id = input.getLong(input.fieldIndex("tweet_id"));
        String tweet = input.getString(input.fieldIndex("tweet_text"));
        Float pos = input.getFloat(input.fieldIndex("pos_score"));
        Float neg = input.getFloat(input.fieldIndex("neg_score"));
        String score = input.getString(input.fieldIndex("score"));
        String tweet_score =
            String.format("%s,%s,%f,%f,%s\n", id, tweet, pos, neg, score);
        this.tweet_scores.add(tweet_score);
        if (this.tweet_scores.size() >= 1000)
        {
            writeToHDFS();
            this.tweet_scores = new ArrayList<String>(1000);
        }
    }

    private void writeToHDFS()
    {
        FileSystem hdfs = null;
        Path file = null;
        OutputStream os = null;
        BufferedWriter wd = null;
        try
        {
            Configuration conf = new Configuration();
            conf.addResource(new Path("/opt/hadoop/etc/hadoop/core-site.xml"));
            conf.addResource(new Path("/opt/hadoop/etc/hadoop/hdfs-site.xml"));
            hdfs = FileSystem.get(conf);
            file = new Path(
                Properties.getString("rts.storm.hdfs_output_file") + this.id);
            if (hdfs.exists(file))
                os = hdfs.append(file);
            else
                os = hdfs.create(file);
            wd = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            for (String tweet_score : tweet_scores)
            {
                wd.write(tweet_score);
            }
        }
        catch (IOException ex)
        {
            LOGGER.error("Failed to write tweet score to HDFS", ex);
            LOGGER.trace(null, ex);
        }
        finally
        {
            try
            {
                if (os != null) os.close();
                if (wd != null) wd.close();
                if (hdfs != null) hdfs.close();
            }
            catch (IOException ex)
            {
                LOGGER.fatal("IO Exception thrown while closing HDFS", ex);
                LOGGER.trace(null, ex);
            }
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) { }
}
