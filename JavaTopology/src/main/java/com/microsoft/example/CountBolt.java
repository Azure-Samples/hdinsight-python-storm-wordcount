package com.microsoft.example;

import java.util.Map;

import backtype.storm.task.ShellBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

/*
This is basically just defining some JVM things for Storm, such as
the output fields, or passing around configuration. Then it invokes the
countbolt.py using Python.
*/
public class CountBolt extends ShellBolt implements IRichBolt {
	// Call the countbolt.py using Python
	public CountBolt() {
		super("python", "countbolt.py");
	}
	
	// Declare that we emit a 'word' and 'count'
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word", "count"));
	}

	// Nothing to do for configuration
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}