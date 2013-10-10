(defproject balling-firebase "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1909"]
                 [compojure "1.1.5" :exclusions [ring/ring-core]]
                 [hiccup "1.0.4"]
                 [domina "1.0.2-SNAPSHOT"]
                 [hiccups "0.2.0"]]
  :plugins [[lein-ring "0.8.5"]
            [lein-cljsbuild "0.3.3"]]
  :ring {:handler balling-firebase.handler/app}
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}}
  :cljsbuild {:builds [{:source-paths ["src-cljs/balling-firebase/"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :optimizations :simple
                                   :pretty-print true}}]} )
