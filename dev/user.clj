(ns user
  (:require [figwheel.main.api :as figwheel]
            [clojure-example.core :as app]
            ))

;; (def base-options
;;   {:output-to "web-target/public/cljs-out/main.js"
;;    :closure-defines {"goog.DEBUG" true}
;;    :asset-path "public/cljs-out/dev"
;;    :clean-outputs true
;;    :optimizations :none
;;    :source-map true
;;    :external-config #:devtools{:config {:features-to-install [:formatters :hints]
;;                                         :fn-symbol "F"
;;                                         :print-config-overrides true}}
;;    :pretty-print true})
;; 
;; (def main-config
;;   {:watch-dirs ["test/cljs" "src/cljs"]
;;    :target-dir "web-target"
;;    :build-inputs [:watch-dirs "dev"]
;;    :css-dirs ["resources/public/css"] ;; "web-target/public/css"
;;    :auto-testing true
;;    :ring-server-options {:port 9501}
;;    :connect-url "ws://127.0.0.1:9501/figwheel-connect" ;; TODO might need a server port
;;    :open-url false
;;    :mode :serve})

;; (defn start-figwheel! []
;;   (figwheel/start
;;     {:id "dev"
;;      :options (merge base-options {:main 'clojure-example.core})
;;      :config main-config}))

(defonce server (atom nil))

(defn start-figwheel! []
  (figwheel/start
    {:id "dev"
     :options {:output-to "target/public/cljs-out/main.js"
               :main 'clojure-example.core}
     :config {:watch-dirs ["src"]
              :open-url false
              :mode :serve}})
    )

(defn stop-figwheel![]
  (figwheel/stop-all))

(defn start []
  (reset! server (app/-main)))

(defn stop []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn dev []
  (start-figwheel!)
  (start))

(defn dev-stop []
  (stop-figwheel!)
  (stop))

(defn repl []
  (figwheel/cljs-repl "dev"))
