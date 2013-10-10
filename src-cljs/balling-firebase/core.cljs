(ns balling-firebase.core
  (:require [domina :refer [by-id value append!]]
            [goog.dom :as dom]
            [goog.events.KeyHandler :as key-handler]
            [goog.events :as events]
            [hiccups.runtime :as hiccupsrt])
  (:require-macros [hiccups.core :as hiccups]))

(def messages (js/Firebase. "YOUR FIREBASE URL"))

(defn display-message
  [new-data]
  (let [mc (-> new-data
               .val
               js->clj)]
    (append! (by-id "messages")
             (hiccups/html [:div (str  (get mc "name")
                                       " : " 
                                       (get mc "message"))]))))

(defn init-populate-messages-handler
  []
  (let [messages-query (.limit messages 10)]
    (.on messages-query "child_added"
         #(display-message %))))

(defn add-message
  []
  (.push messages (clj->js {"name"  (value (by-id "nameInput"))
                            "message" (value (by-id "messageInput"))})))

(defn init-submit-message-handler
 []
 (events/listen (goog.events.KeyHandler. (dom/getDocument) true) "key"
                #((let [id (.-id (.-target %))]
                    (if (and (= id "messageInput")
                             (= 13 (.-keyCode %)))
               (add-message))))))

(defn init
  []
  (init-submit-message-handler)
  (init-populate-messages-handler))

(set! (.-onload js/window) init)
