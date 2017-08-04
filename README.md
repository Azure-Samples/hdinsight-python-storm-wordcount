---
services: hdinsight
platforms: java,python
author: blackmist
---
# hdinsight-python-storm-wordcount

How to use Python components in an Apache Storm topology on HDInsight

This topology uses the Flux framework to define a Storm topology using YAML. The components (spout and bolts) that process the data are written in Python.

This example has been tested with HDInsight 3.6 (Storm 1.1.0).

## Prerequisites

* Python 2.7 or higher

* Java JDK 1.8 or higher

* Maven

* (Optional) A local Storm development environment. This is only needed if you want to run the topology locally. For more information, see [Setting up a development environment](http://storm.apache.org/releases/1.0.1/Setting-up-development-environment.html).

## How it works

* `/resources/topology.yaml` - defines what components are in the topology and how data flows between them.

* `/multilang/resources` - contains the Python components.

* `/pom.xml` - dependencies and how to build the project.

## Build the project

From the root of the project, use the following command:

```bash
mvn clean compile package
```

This command creates a `target/WordCount-1.0-SNAPSHOT.jar` file.

## Run the topology locally

To run the topology locally, use the following command:

```bash
storm jar WordCount-1.0-SNAPSHOT.jar org.apache.storm.flux.Flux -l -R /topology.yaml
```

Once the topology starts, it emits information to the local console similar to the following text:

```
24302 [Thread-25-sentence-spout-executor[4 4]] INFO  o.a.s.s.ShellSpout - ShellLog pid:2436, name:sentence-spout Emiting the cow jumped over the moon
24302 [Thread-30] INFO  o.a.s.t.ShellBolt - ShellLog pid:2438, name:splitter-bolt Emitting the
24302 [Thread-28] INFO  o.a.s.t.ShellBolt - ShellLog pid:2437, name:counter-bolt Emitting years:160
24302 [Thread-17-log-executor[3 3]] INFO  o.a.s.f.w.b.LogInfoBolt - {word=the, count=599}
24303 [Thread-17-log-executor[3 3]] INFO  o.a.s.f.w.b.LogInfoBolt - {word=seven, count=302}
24303 [Thread-17-log-executor[3 3]] INFO  o.a.s.f.w.b.LogInfoBolt - {word=dwarfs, count=143}
24303 [Thread-25-sentence-spout-executor[4 4]] INFO  o.a.s.s.ShellSpout - ShellLog pid:2436, name:sentence-spout Emiting the cow jumped over the moon
24303 [Thread-30] INFO  o.a.s.t.ShellBolt - ShellLog pid:2438, name:splitter-bolt Emitting cow
^C24303 [Thread-17-log-executor[3 3]] INFO  o.a.s.f.w.b.LogInfoBolt - {word=four, count=160}
```

Use Ctrl+c to stop the topology.

## Run the topology on HDInsight

1. Use the following command to copy the `WordCount-1.0-SNAPSHOT.jar` file to your Storm on HDInsight cluster:

    ```bash
    scp target\WordCount-1.0-SNAPSHOT.jar sshuser@mycluster-ssh.azurehdinsight.net
    ```

    Replace `sshuser` with the SSH user for your cluster. Replace `mycluster` with the cluster name.

2. Once the file has been uploaded, connect to the cluster using SSH and use the following command to start the topology on the cluster:

    ```bash
    storm jar WordCount-1.0-SNAPSHOT.jar org.apache.storm.flux.Flux -r -R /topology.yaml
    ```

3. You can use the Storm UI to view the topology on the cluster. The Storm UI is located at https://mycluster.azurehdinsight.net/stormui. Replace `mycluster` with your cluster name.

Once started, a Storm topology runs until stopped (killed.) To stop the topology, use either the `storm kill TOPOLOGYNAME` command from the command-line (SSH session to a Linux cluster,) or by using the Storm UI, select the topology, and then select the __Kill__ button.

## Project code of conduct

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
