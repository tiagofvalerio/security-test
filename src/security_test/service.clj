(ns security-test.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [io.pedestal.http.content-negotiation :as conneg]
            [io.pedestal.http.route :as route]
            [security-test.service.auth :refer [check-auth check-permissions]]
            [security-test.api.auth-api :refer [login]]
            [security-test.api.game-api :refer [add-game get-game get-all-games delete-game]]))

(defn home-page
  [request]
  (ring-resp/response {:message "pong"}))

(defroutes routes
  [[["/" {:get home-page}
     ^:interceptors [(body-params/body-params) http/json-body]

     ;;General api
     ["/v1/game" ^:interceptors [check-auth check-permissions] {:post add-game :get get-all-games}]
     ["/v1/game/:id" ^:interceptors [check-auth check-permissions] {:get get-game :delete delete-game}]
     ["/v1/login" ^:interceptors {:post login}]]]])

(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/port 8080
              ::http/container-options {:h2c? true
                                        :h2? false
                                        :ssl? false}})
