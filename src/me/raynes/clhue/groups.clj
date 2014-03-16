(ns me.raynes.clhue.groups
  (:require [me.raynes.clhue.request :refer [req]]))

(defn groups
  "Get ALL the groups."
  [config]
  (:body (req :get "groups" config)))

(defn group
  "Get a group, or set its attributes/state."
  ([config id]
     (:body (req :get (format "groups/%s" id) config)))
  ([config id settings]
     (let [attributes (select-keys settings [:name :lights])
           settings (dissoc settings :name :lights)
           responses (when (seq attributes)
                       (:body (req :put (format "groups/%s" id)
                                   config attributes)))]
       (-> (req :put (format "groups/%s/action" id) config settings)
           (:body)
           (into responses)))))
