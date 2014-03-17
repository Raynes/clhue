(ns me.raynes.clhue.repl
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def config-path
  (io/file (System/getProperty "user.home")
           ".clhue"))

(defn setup
  "Setup a REPL for optimal Hueing."
  []
  (require '[me.raynes.clhue.lights :as lights]
           '[me.raynes.clhue.groups :as groups]
           '[me.raynes.clhue.schedules :as schedules]
           '[me.raynes.clhue.portal :as portal]
           '[me.raynes.clhue.config :as config])
  (def config (edn/read-string (slurp config-path))))
