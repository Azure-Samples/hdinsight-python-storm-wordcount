This demonstrates a very basic use of Python in a Storm topology, using Clojure to define the project/topology.

##Prerequisites

* Python 2.7

* Hadoop with Storm 0.9.3 (tested with both a Linux and Windows based HDInsight cluster.)

* Java 1.7 or higher

* Clojure 1.5.1 (you may get errors with newer versions)

##Architecture

JVM

 Topology

  Spout -> spawns python spout

  Bolt(s) -> spawns python bolts

The topology is Clojure, which is a bit more concise than the equivalent Java. For example, we can directly specify the Python components from the topology instead of creating a Java wrapper for each component.

Data sent between components is serialized as JSON, because you might use a topology that uses a mix of Python and Java (or Ruby, or Node.js, etc.) components. For this example, everything is just Python.

##Developing Python components

- Python components must `import storm`. The storm module is provided at [https://github.com/apache/storm/blob/master/storm-multilang/python/src/main/resources/resources/storm.py](https://github.com/apache/storm/blob/master/storm-multilang/python/src/main/resources/resources/storm.py), and provides the bits needed to interact with the JVM Storm bits. For convenience, I've included a copy in the `/resources/resources` directory.

    This provides things like `storm.emit` to emit tuples, and `storm.logInfo` to write to the logs. I would encourage you to read over this file and understand what it provides.

- Python files are stored in `/resources/resources` in this example. The project automatically includes things in `/resources`, however Storm seems to expect non-JVM resources in a `resources` directory, so that is why there is a `resources` subdirectory. Placing components in this folder allows you to just reference by name in the Clojure code.

- Python will obviously need to be on all the worker nodes in your cluster. Additionally, if you have modules that your Python code depends on, this must also be installed on all the worker nodes. Storm doesn't provide anything to do this. There are some frameworks that have been created (such as [Streamparse] and [Pyleus],) that handle this for you, in addition to making it generally easier to work with Python and Storm.

##Running this example

To run locally, just use the following:

    lein do clean, run

Use ctrl+c to kill it. NOTE: The Python processes might stick around, so you should check processes and kill those if they don't stop with the topology.

To run it on a Hadoop cluster, use the following steps:

1. Build an uber jar:

        lein uberjar

    This will create a file named __WordCount-1.0-SNAPSHOT-standalone.jar__ in the `target/uberjar` directory.

2. Upload the jar file to the Hadoop cluster:

        scp WordCount-1.0-SNAPSHOT-standalone.jar USERNAME@CLUSTERNAME-ssh.azurehdinsight.net:WordCount-1.0-SNAPSHOT-standalone.jar

3. SSH to the cluster and run via the Storm command:

        storm jar WordCount-1.0-SNAPSHOT-standalone.jar wordcount.core wordcount

It doesn't do anything useful or fun, but it should run and you can look in the Storm UI to see that it's actually doing stuff.
