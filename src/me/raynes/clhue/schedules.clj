(ns me.raynes.clhue.schedules
  (:require [me.raynes.clhue.request :refer [req handle-weird-error]]))

(defn schedules
  "Get ALL the schedules."
  [config]
  (:body (req :get "schedules" config)))

(defn create-schedule
  "Create a new schedule."
  [config settings]
  (:body (req :post "schedules" config settings)))

(defn schedule
  "Get or update a schedule."
  ([config id]
     (handle-weird-error (req :get (format "schedules/%s" id) config)))
  ([config id settings]
     (:body (req :put (format "schedules/%s" id) config settings))))

(defn delete-schedule
  "Delete a schedule."
  [config id]
  (:body (req :delete (format "schedules/%s" id) config)))
