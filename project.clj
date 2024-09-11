(defproject learndatalogtoday "0.1.0"
  :description "Interactive Datalog Tutorial"
  :url "https://datomic.learn-some.com"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/clojurescript "1.11.132"]
                 [compojure "1.7.1"]
                 [ring/ring-jetty-adapter "1.12.2"]
                 [com.datomic/peer "1.0.7187"]
                 [datomic-query-helpers "0.1.1"]
                 [hiccup "1.0.5"]
                 [markdown-clj "1.12.1"]
                 [fipp "0.6.26"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/core.rrb-vector "0.1.2"]
                 ;; cljs
                 [hylla "0.2.0"]
                 [hiccups "0.3.0"]
                 [domina "1.0.3"]]
  :plugins [[lein-ring "0.12.6"]
            [lein-cljsbuild "1.1.8"]]
  :source-paths ["src/clj"]
  :ring {:handler learndatalogtoday.handler/app}
  :main learndatalogtoday.handler
  :aot :all
  :uberjar-name "learndatalogtoday-standalone.jar"
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}}
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:output-to "resources/public/app.js"
                                   :optimizations :advanced
                                   :externs ["externs.js"]
                                   :static-fns true}}]})
