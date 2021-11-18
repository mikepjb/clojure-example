(ns clojure-example.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :refer [resource-response content-type]]
            [reitit.ring :as ring]
            [reitit.core :as r]
            [clojure-example.routes :as routes]))

(defn my-expand [registry]
  (fn [data opts]
    (if (keyword? data)
      (some-> data
              registry
              (r/expand opts)
              (assoc :name data))
      (r/expand data opts))))

(def app
  (ring/ring-handler
    (ring/router
      routes/routes)))

(defn -main
  [& args]
  (run-jetty
    (wrap-defaults app site-defaults)
    {:port 8080
     :join? false}))
