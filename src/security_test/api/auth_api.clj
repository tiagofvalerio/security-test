(ns security-test.api.auth-api
  (:use korma.core)
  (:require [clj-time.core :refer [hours from-now]]
            [buddy.auth.backends.token :refer [jws-backend]]
            [buddy.auth.protocols :as proto]
            [buddy.sign.jwt :as jwt]
            [buddy.hashers :as hashers]
            [security-test.service.auth :as a]
            [security-test.service.users :as u]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.interceptor.chain :refer [terminate]]))

(defn login
  [{:keys [json-params] :as request}]
  (let [auth-response (a/create-auth-token json-params)]
    (if (some true? auth-response)
      (do
        {:status 200
         :headers {"content-type" "application/json;charset=utf-8" "token" (:token (second auth-response))}})
      {:status 401
       :headers {"content-type" "application/json;charset=utf-8"}
       :body {:message (:message (second auth-response))}})))
