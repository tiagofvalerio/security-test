(ns security-test.api.game-api
  (:require [security-test.service.game :as g]))

(def json-content-type {"content-type" "application/json"})

(defn add-game
  [{:keys [json-params] :as request}]
  (g/save-game json-params)
  {:status  200
   :headers json-content-type})

(defn get-game
  [{:keys [path-params] :as request}]
  {:status  200
   :headers json-content-type
   :body (g/get-game (:id path-params))})

(defn get-all-games
  [r]
  {:status  200
   :headers json-content-type
   :body (g/get-all-games)})

(defn delete-game
  [{:keys [path-params] :as request}]
  (g/delete-game (:id path-params))
  {:status  200
   :headers json-content-type})
