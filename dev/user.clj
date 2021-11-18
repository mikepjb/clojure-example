(ns user
  (:require [figwheel.main.api :as figwheel]
            [clojure-example.core :as app]
            [sail.core :as sail]))

(defonce server (atom nil))

(defn start-figwheel! []
  (figwheel/start
    {:id "dev"
     :options {:output-to "target/public/cljs-out/main.js"
               :main 'clojure-example.core}
     :config {:watch-dirs ["src/cljs" "src/cljc"]
              :open-url false
              :mode :serve}}))

(defn stop-figwheel![]
  (figwheel/stop-all))

(defn start-server []
  (reset! server (app/-main)))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn dev []
  (sail/generate-styles "target/public/styles.gen.css")
  (start-figwheel!)
  (start-server))

(def start dev)

(defn stop []
  (stop-figwheel!)
  (stop-server))

(defn reset []
  (stop)
  (start))

(defn repl []
  (figwheel/cljs-repl "dev"))
