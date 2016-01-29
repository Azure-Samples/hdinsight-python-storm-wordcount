(ns wordcount.core
  (:gen-class)
  (:import [backtype.storm StormSubmitter LocalCluster])
  (:use [backtype.storm clojure config]))

; Define the topology
(defn mk-topology []
  (topology
   ; Use the sentencespout.py with Python.
   ; Emit a 'sentence' field
   ; Parallelization hint of 1
   ; Spout name of 'spout'
   {"spout" (shell-spout-spec "python"
                         "sentencespout.py"
                         ["sentence"]
                         :p 1)}
   ; Use the splitbolt.py
   ; Emit a 'word' field
   ; Parallelization hint of 4
   ; input comes from 'spout', shuffle grouping
   {"splitbolt" (shell-bolt-spec {"spout" :shuffle}
                         "python"
                         "splitbolt.py"
                         ["word"]
                         :p 4
                )
    ; Use the counterbolt.py
    ; Emit a 'word' and 'count' field
    ; Parallelization hint of 4
    ; input comes from 'splitbolt', and groups on the 'word' field
    "countbolt" (shell-bolt-spec {"splitbolt" ["word"]}
                         "python"
                         "countbolt.py"
                         ["word" "count"]
                         :p 4
                )
    }))

; For running local (no parameters)
(defn run-local! []
 (let [cluster (LocalCluster.)]
   (.submitTopology cluster "word-count" {TOPOLOGY-DEBUG true} (mk-topology))
   (Thread/sleep 30000)
   (.shutdown cluster)
   ))

; For running on a cluster (when you pass a friendly name as a parameter)
(defn submit-topology! [name]
 (StormSubmitter/submitTopology
  name
  {TOPOLOGY-DEBUG true
   TOPOLOGY-WORKERS 3}
  (mk-topology)))

; Main, which calls either run-local or submit-topology
(defn -main
  ([]
   (run-local!))
  ([name]
   (submit-topology! name)))
