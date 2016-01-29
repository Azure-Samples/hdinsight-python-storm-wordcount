(defproject wordcount "0.1.0-SNAPSHOT"
  :description "A basic example of a Storm topology using Python components, where the topology is defined using clojure."
  :license {:name "Apache License 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.apache.storm/storm-core "0.9.3"]]
  :jar-exclusions     [#"log4j\.properties" #"backtype" #"trident" #"META-INF" #"meta-inf" #"\.yaml"]
  :uberjar-exclusions [#"log4j\.properties" #"backtype" #"trident" #"META-INF" #"meta-inf" #"\.yaml"]

  :main ^:skip-aot wordcount.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
