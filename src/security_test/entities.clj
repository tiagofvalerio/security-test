(ns security-test.entities
  (:use korma.core security-test.db))

(declare games)

(defentity games
  (pk :uuid)
  (table :games)
  (entity-fields :uuid :name :description :year))

(defentity users
  (pk :uuid)
  (table :users)
  (entity-fields :uuid :name :email :type :password_digest :token))

(defentity permissions
  (pk :uuid)
  (table :permissions)
  (entity-fields :uuid :action :object :user_type))
