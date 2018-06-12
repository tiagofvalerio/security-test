(ns security-test.service.users
  (:use korma.core)
  (:require [security-test.entities :as e]
            [buddy.hashers :as hashers]))

(defn find-user
  [username]
  (let [user (first (select e/users (where {:email username})))]
    user))

(defn password-matches?
  [email password]
  (hashers/check password
                 (:password_digest (first
                                     (select e/users
                                             (fields :password_digest)
                                             (where {:email email}))))))

(defn save-user-token
  [user token]
  (update e/users (set-fields {:token token})
          (where {:email (:username user)})))

(defn delete-user-token
  [user]
  (update e/users (set-fields {:token nil})
          (where {:email (:email user)})))
