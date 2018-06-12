(ns security-test.service.permissions
  (:use korma.core)
  (:require [security-test.entities :as e]
            [clojure.string :as s]))

(defn has-permission?
  [user http-method]
  (let [permissions (select e/permissions
                            (where {:user_type (s/upper-case (:type user))
                                    :action (s/upper-case (name http-method))}))]
    (if (or (nil? permissions) (empty? permissions))
      false
      true)))
