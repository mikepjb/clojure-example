(ns clojure-example.routes
  #?(:clj (:require
            [ring.util.response :refer [resource-response content-type]])))

#?(:clj
   (defn index [_]
     (-> (resource-response "index.html" {:root "public"})
         (content-type "text/html"))))

#?(:clj
   (defn test-data [_]
     (-> {:status 200 :body "{\"hello\": \"test data\"}"}
         (content-type "text/json"))))

(def routes
  [["/" {:name ::index
         #?@(:clj [:get {:handler index}])}]
   ["/api/test" {:name ::test
                 #?@(:clj [:get {:handler test-data}])}]])
