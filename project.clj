(defproject security-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.8.0"]
                 [clj-time "0.14.2"]
                 [io.pedestal/pedestal.service "0.5.2"]
                 [io.pedestal/pedestal.jetty "0.5.2"]
                 [clj-http "3.7.0"]
                 [midje "1.9.0"]
                 [ch.qos.logback/logback-classic "1.1.11"]
                 [korma "0.4.3"]
                 [org.postgresql/postgresql "9.4-1206-jdbc42"]
                 [ch.qos.logback/logback-classic "1.1.11"]
                 [buddy "1.0.0"]
                 [buddy/buddy-auth "1.2.0"]]

  :main ^{:skip-aot true} security-test.core

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
