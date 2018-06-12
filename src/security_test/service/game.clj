(ns security-test.service.game
  (:use korma.core)
  (:require [security-test.entities :as e]))

(defn save-game
  [game]
  (insert e/games (values (-> game
                            (assoc :uuid (java.util.UUID/randomUUID))))))

(defn get-game
  [uuid]
  (first (select e/games
                 (where {:uuid (java.util.UUID/fromString uuid)}))))

(defn get-all-games
  []
  (select e/games))

(defn delete-game
  [uuid]
  (try
    (delete e/games
      (where {:uuid (java.util.UUID/fromString uuid)}))
    (catch Exception e)))
