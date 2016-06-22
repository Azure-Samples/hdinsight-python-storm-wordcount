---
services: hdinsight
platforms: java,python
author: blackmist
---
# hdinsight-python-storm-wordcount
How to use Python components in an Apache Storm topology on HDInsight

There are two samples included in this repository, both of which use Python components

* A Java topology - The topology definition is written in Java. Additionally, there are wrapper Java components that invoke the Python components at runtime.

* A Clojure topology - The topology definition is written in Clojure. It directly invokes Python components at runtime.

##Prerequisites

* Python 2.7 or higher

* Java JDK 1.7 or higher

* [Leiningen](http://leiningen.org/)

## Build and run

###Java topology

The Java-based topology and Python components are located in the `JavaTopology` directory. To run this project locally, just use the following Maven command to build and run in local mode:

    mvn compile exec:java -Dstorm.topology=com.microsoft.example.WordCount

Use __Ctrl+C__ to kill the process.

To deploy the project to an HDInsight cluster running Apache Storm, use the following steps:

1. Build an uber jar:

        mvn package

    This will create a file named __WordCount--1.0-SNAPSHOT.jar__ in the `/target` directory for this project.

2. Upload the jar file to the Hadoop cluster using one of the following methods:

    * For __Linux-based__ HDInsight clusters: Use `scp WordCount-1.0-SNAPSHOT.jar USERNAME@CLUSTERNAME-ssh.azurehdinsight.net:WordCount-1.0-SNAPSHOT.jar` to copy the jar file to the cluster, replacing USERNAME with your SSH user name and CLUSTERNAME with the HDInsight cluster name.

        Once the file has finished uploading, connect to the cluster using SSH and start the topology using `storm jar WordCount-1.0-SNAPSHOT.jar com.microsoft.example.WordCount wordcount`

    * For __Windows-based__ HDInsight clusters: Connect to the Storm Dashboard by going to HTTPS://CLUSTERNAME.azurehdinsight.net/ in your browser. Replace CLUSTERNAME with your HDInsight cluster name and provide the admin name and password when prompted.

        Using the form, perform the following actions:

        * __Jar File__: Select __Browse__, then select the __WordCount-1.0-SNAPSHOT.jar__ file
        * __Class Name__: Enter `com.microsoft.example.WordCount`
        * __Additional Paramters__: Enter a friendly name such as `wordcount` to identify the topology

        Finally, select __Submit__ to start the topology.

Once started, a Storm topology runs until stopped (killed.) To stop the topology, use either the `storm kill TOPOLOGYNAME` command from the command-line (SSH session to a Linux cluster for example,) or by using the Storm UI, select the topology, and then select the __Kill__ button.

###Clojure topology

The Clojure topology is located in the `ClojureTopology` directory. To build and run the project locally, use the following command:

    lein do clean, run

To stop the topology, use __Ctrl+C__.

To build an uberjar and deploy to HDInsight, use the following steps:

1. Create an uberjar containing the topology and required dependencies:

        lein uberjar

    This will create a new file named `wordcount-1.0-SNAPSHOT.jar` in the `target\uberjar+uberjar` directory.
    
2. Use one of the following methods to deploy and run the topology to an HDInsight cluster:

    * __Linux-based HDInsight__
    
        1. Copy the file to the HDInsight cluster head node using `scp`. For example:
        
                scp wordcount-1.0-SNAPSHOT.jar USERNAME@CLUSTERNAME-ssh.azurehdinsight.net:wordcount-1.0-SNAPSHOT.jar
                
            Replace USERNAME with an SSH user for your cluster, and CLUSTERNAME with your HDInsight cluster name.
            
        2. Once the file has been copied to the cluster, use SSH to connect to the cluster and submit the job. For information on using SSH with HDInsight, see one of the following:
        
            * [Use SSH with Linux-based HDInsight from Linux, Unix, or OS X](hdinsight-hadoop-linux-use-ssh-unix.md)
            * [Use SSH with Linux-based HDInsight from Windows](hdinsight-hadoop-linux-use-ssh-windows.md)
            
        3. Once connected, use the following to start the topology:
        
                storm jar wordcount-1.0-SNAPSHOT.jar wordcount.core wordcount
    
    * __Windows-based HDInsight__
    
        1. Connect to the Storm Dashboard by going to HTTPS://CLUSTERNAME.azurehdinsight.net/ in your browser. Replace CLUSTERNAME with your HDInsight cluster name and provide the admin name and password when prompted.

        2. Using the form, perform the following actions:

            * __Jar File__: Select __Browse__, then select the __wordcount-1.0-SNAPSHOT.jar__ file
            * __Class Name__: Enter `wordcount.core`
            * __Additional Paramters__: Enter a friendly name such as `wordcount` to identify the topology

            Finally, select __Submit__ to start the topology.

Once started, a Storm topology runs until stopped (killed.) To stop the topology, use either the `storm kill TOPOLOGYNAME` command from the command-line (SSH session to a Linux cluster,) or by using the Storm UI, select the topology, and then select the __Kill__ button.

##More information

For more information on how Python components are used in a Storm topology, see [Develop Storm topologies using Python](https://azure.microsoft.com/en-us/documentation/articles/hdinsight-storm-develop-python-topology/).

## Project code of conduct

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.