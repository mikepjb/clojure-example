(ns clojure-example.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [reitit.core :as reitit]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [clojure-example.routes :as routes]))

(println "Starting clojure example")

(def router
  (reitit/router routes/routes))

(defonce state
  (r/atom {:example "simplestring"}))

(js/setInterval
  (fn []
    (go
      (let [response (<! (http/get "/api/test" {:with-credentials? false}))]
        (swap! state assoc :test (:body response)))))
  3000)

(defn app [state]
  [:div.flex.flex-col.h-full.bg-gray-100.p-2
   [:div.font-semibold "Hello clojure-example"]
   [:div "my state is:"]
   [:ul
    (for [[k v] @state]
      ^{:key k}
      [:li (name k) " :: " v])]])

(rdom/render [app state] (gdom/getElement "app"))
