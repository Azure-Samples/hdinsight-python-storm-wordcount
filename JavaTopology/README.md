This demonstrates a very basic use of Python in a Storm topology. This is based off of/similar to what is included in the Storm-Starter examples.

##Prerequisites

* Python 2.7

* Hadoop with Storm 0.9.3 (tested with both a Linux and Windows based HDInsight cluster.)

* Java 1.7 or higher

* Maven (tested with 3.2.5)

##Architecture

JVM

 Topology

  Spout -> spawns python spout

  Bolt(s) -> spawns python bolts

The topology is Java, and at first glance you might think that all the bolts and spouts are Java. But if you look in each of the Java spouts/bolts, you'll see something similar to the following:

        public SplitBolt() {
            super("python", "countbolt.py");
        }

This is where the Java bits are invoking python and running the script that contains the actual bolt logic. For this example, the only thing the Java pieces are actually doing is declaring the fields in the tuple that will be emitted by the component.

Data sent between components is serialized as JSON, because you might use a topology that uses a mix of Python and Java (or Ruby, or Node.js, etc.) components. For this example, everything is just Python.

##Developing Python components

- Python components must `import storm`. The storm module is provided at [https://github.com/apache/storm/blob/master/storm-multilang/python/src/main/resources/resources/storm.py](https://github.com/apache/storm/blob/master/storm-multilang/python/src/main/resources/resources/storm.py), and provides the bits needed to interact with the JVM Storm bits. For convenience, I've included a copy in the `/multilang/resources` directory.

    This provides things like `storm.emit` to emit tuples, and `storm.logInfo` to write to the logs. I would encourage you to read over this file and understand what it provides.

- Python files are stored in `/multilang/resources` in this example. In the `pom.xml`, there is the following entry:

        <resources>
            <resource>
                <!-- Where the Python bits are kept -->
                <directory>${basedir}/multilang</directory>
            </resource>
        </resources>

    Notice that this only specifies the `/multilang` directory and not `/multilang/resources`. Storm seems to expect non-JVM resources in a `resources` directory, so it is looked for internally already. Placing components in this folder allows you to just reference by name in the Java code.

- Python will obviously need to be on all the worker nodes in your cluster. Additionally, if you have modules that your Python code depends on, this must also be installed on all the worker nodes. Storm doesn't provide anything to do this. There are some frameworks that have been created (such as [Streamparse] and [Pyleus],) that handle this for you, in addition to making it generally easier to work with Python and Storm.

##Running this example

To run locally, just use the following:

    mvn compile exec:java -Dstorm.topology=com.microsoft.example.WordCount

Use ctrl+c to kill it. NOTE: The Python processes might stick around, so you should check processes and kill those if they don't stop with the topology.

To run it on a Hadoop cluster, use the following steps:

1. Build an uber jar:

        mvn package

2. Upload the jar file to the Hadoop cluster:

        scp WordCount-1.0-SNAPSHOT.jar USERNAME@CLUSTERNAME-ssh.azurehdinsight.net:WordCount-1.0-SNAPSHOT.jar

3. SSH to the cluster and run via the Storm command:

        storm jar WordCount-1.0-SNAPSHOT.jar com.microsoft.example.WordCount wordcount

It doesn't do anything useful or fun, but it should run and you can look in the Storm UI to see that it's actually doing stuff.
