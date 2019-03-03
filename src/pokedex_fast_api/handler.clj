(ns pokedex-fast-api.handler
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [pokedex-fast-api.config :as config]
            [pokedex-fast-api.db :as db]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-params wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]))

(defroutes endpoints
  (GET "/pokemon/previews" []
       (response (db/get-previews)))
  (GET "/pokemon/stats" []
       (response (db/get-stats)))
  (route/not-found "Not Found"))

(def app
  (-> endpoints
    (wrap-json-response)
    (wrap-keyword-params)
    (wrap-params)
    (wrap-cors :access-control-allow-origin [#".*"]
               :access-control-allow-methods [:get])))

(defn -main [& args]
  (let [port (get-in config/properties [:app-server :port])]
    (run-jetty app {:port port})))
