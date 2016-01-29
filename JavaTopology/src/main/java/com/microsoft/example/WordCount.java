package com.microsoft.example;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

// The topology
public class WordCount 
{
    public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		// Spout emits random sentences
		builder.setSpout("SentenceSpout", new SentenceSpout(), 1);
		// Split bolt splits sentences and emits words
		builder.setBolt("SplitBolt", new SplitBolt(), 4).shuffleGrouping("SentenceSpout");
		// Counter consumes words and emits words and counts
		// FieldsGrouping is used so the same words get routed
		//  to the same bolt instance
		builder.setBolt("CountBolt", new CountBolt(), 4).fieldsGrouping("SplitBolt", new Fields("word")); 
		
		//New configuration
		Config conf = new Config();
		
		// If there are arguments, we must be on a cluster
		if(args != null && args.length > 0) {
			conf.setNumWorkers(3);
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			}
		} else {
			// Otherwise, we are running locally
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("test", conf, builder.createTopology());
		}
    }
}
