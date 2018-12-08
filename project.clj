(defproject pokedex-fast-api "0.1.0-SNAPSHOT"
  :description "Pokedex Simple API"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/java.jdbc "0.6.0"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.3.2"]]
  :main pokedex-fast-api.handler
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler pokedex-fast-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [proto-repl "0.3.1"]
                        [org.clojure/test.check "0.9.0"]
                        [ring/ring-mock "0.3.2"]]}})
