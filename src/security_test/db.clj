(ns security-test.db
    (:use korma.db))

(defdb db (postgres {:db "security-test"
                     :user "postgres"
                     :password "postgres"
                     :host "localhost"
                     :port 5432}))
