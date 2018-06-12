(ns security-test.core
  (:gen-class)
  (:require [io.pedestal.http :as server]
            [security-test.service :as service]))

(defonce runnable-service (server/create-server service/service))

(defn -main
  [& args]
  (println "\nCreating server...")
  (server/start runnable-service))
