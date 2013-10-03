(ns balling-firebase.core
  (:require [firebase.core :as firebase]
            [domina :refer [by-id value listen!]]))

(def fb-ref (firebase.Firebase. "https://balling-firebase.firebaseio.com"))

(defn add-message
  [message]
  (.set fb-ref (clj-js {:message message})))

(defn init
  []
  (js/alert "initing")
  (if (and js/document
           (.-getElementById js/document))
    (let [submit-button (by-id "submitMessage")
          message-input (by-id "message")]
      (listen! submit-button :click
               (fn 
                 [evt] 
                 (add-message (value message-input)))))))

(set! (.-onload js/window) init)
