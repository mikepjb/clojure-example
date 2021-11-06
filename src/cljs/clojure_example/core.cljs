(ns clojure-example.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [reitit.core :as reitit]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [clojure-example.routes :as routes]))

(println "hello there!")

(def router
  (reitit/router routes/routes))

(defonce state
  (r/atom {:example "simplestring"}))

(js/setInterval
  (fn []
    (go
      (let [response (<! (http/get "/api/test" {:with-credentials? false}))]
        (prn "got /api/test response")
        (prn (:status response))
        (prn (:body response))
        (swap! state assoc :test (:body response)))))
  3000)

(defn app [state]
  [:div
   "Hello clojure-example, my state is " (str @state)])

(rdom/render [app state] (gdom/getElement "app"))
