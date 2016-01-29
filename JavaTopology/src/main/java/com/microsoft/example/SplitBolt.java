package com.microsoft.example;

import java.util.Map;

import backtype.storm.task.ShellBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

/*
This is basically just defining some JVM things for Storm, such as
the output fields, or passing around configuration. Then it invokes the
splitbolt.py using Python.
*/
public class SplitBolt extends ShellBolt implements IRichBolt {
	// Call the splitbolt.py using Python
	public SplitBolt() {
		super("python", "countbolt.py");
	}
	
	// Declare that we emit a 'word'
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	// Nothing to do for configuration
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}