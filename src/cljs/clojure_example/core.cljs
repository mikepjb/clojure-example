(ns clojure-example.core
  (:require [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

(println "hello there!")

(defonce state
  (r/atom {:example "simplestring"}))

(defn app [state]
  [:div
   "Hello clojure-example, my state is " (str @state)])

(rdom/render [app state] (gdom/getElement "app"))
