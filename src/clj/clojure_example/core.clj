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

;; (defn handler [req]
;;   (or
;;     (when (= "/" (:uri req))
;;       (-> (resource-response "index.html" {:root "public"})
;;           (content-type "text/html")))
;;     {:status 404
;;      :headers {"Content-Type" "text/html"}
;;      :body "404 Not Found"}))

;; (defn -main
;;   [& args]
;;   (run-jetty
;;     (wrap-defaults handler site-defaults)
;;     {:port 8080
;;      :join? false}))
