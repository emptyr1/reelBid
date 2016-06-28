
package org.apache.storm.kafka.spout.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.kafka.spout.KafkaSpoutTupleBuilder;
import org.apache.storm.tuple.Values;

import java.util.List;

public class TopicsTest0Test1TupleBuilder<K, V> extends KafkaSpoutTupleBuilder<K,V> {
    /**
     * @param topics list of topics that use this implementation to build tuples
     */
    public TopicsTest0Test1TupleBuilder(String... topics) {
        super(topics);
    }
    //new TopicsTest0Test1TupleBuilder<String, String>(TOPICS[0], TOPICS[1]),

    @Override
    public List<Object> buildTuple(ConsumerRecord<K, V> consumerRecord) {
        return new Values(consumerRecord.topic(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                consumerRecord.key(),
                consumerRecord.value());
    }
}