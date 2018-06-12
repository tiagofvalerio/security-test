(ns security-test.service.auth
  (:use korma.core)
  (:require [clj-time.core :refer [hours from-now]]
            [buddy.auth.backends.token :refer [jws-backend]]
            [buddy.auth.protocols :as proto]
            [buddy.auth :refer [authenticated?]]
            [buddy.sign.jwt :as jwt]
            [clj-time.core :as t]
            [buddy.hashers :as hashers]
            [buddy.sign.util :as util]
            [security-test.service.users :as u]
            [security-test.service.permissions :as p]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.interceptor.chain :refer [terminate]]))

(defonce secret "4%&6yu##5s")

(def encryption {:alg :hs512})

(def auth-backend
  (jws-backend {:secret secret :options encryption}))

(def check-auth
  (interceptor/interceptor
    {:name ::check-auth
     :enter
     (fn [{:keys [request] :as context}]
       (let [req (try (some->> (proto/-parse auth-backend request)
                               (proto/-authenticate auth-backend request))
                   (catch Exception _))]
         (if req
           (assoc context :request (-> request
                                       (assoc :identity (:user req))))
           (-> context
               terminate
               (assoc :response {:status 401 :body {:message "Unauthorized"}})))))}))


(defn auth-user
  [{:keys [username password]}]
  (let [user (u/find-user username)
        unauthed [false {:message "Invalid username or password"}]]
    (if user
      (if (u/password-matches? username password)
        [true {:user (-> user
                         (dissoc :password-digest)
                         (dissoc :uuid))}]
        unauthed)
      unauthed)))

(defn create-auth-token
  [credentials]
  (let [[ok? res] (auth-user credentials)
        exp (-> (t/plus (t/now) (t/hours 1)) (util/to-timestamp))]
    (if ok?
      [true {:token (jwt/sign res secret {:alg :hs512 :exp exp})}]
      [false res])))

(def check-permissions
  (interceptor/interceptor
    {:name ::check-permissions
     :enter
     (fn [{:keys [request] :as context}]
       (let [{:keys [identity request-method]} request
             user identity
             allowed? (p/has-permission? user request-method)]
         (if allowed?
           context
           (-> context
               terminate
               (assoc :response {:status 403 :body {:message "Not allowed"}})))))}))
