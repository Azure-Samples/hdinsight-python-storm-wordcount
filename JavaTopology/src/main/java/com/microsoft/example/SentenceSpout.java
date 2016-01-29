package com.microsoft.example;

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.spout.ShellSpout;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

/*
This is basically just defining some JVM things for Storm, such as
the output fields, or passing around configuration. Then it invokes the
sentencespout.py using Python.
*/
public class SentenceSpout extends ShellSpout implements IRichSpout {
    // Invoke the python spout
    public SentenceSpout() {
        super("python", "sentencespout.py");
    }

    // Declare that we emit a 'sentence' field
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }

    // No real configuration going on
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}