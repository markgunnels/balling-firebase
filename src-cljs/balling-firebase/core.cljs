(ns balling-firebase.core
  (:require [domina :refer [by-id value append! set-style! listen! xpath]]
            [goog.dom :as dom]
            [goog.style :as style]
            [goog.events.KeyHandler :as key-handler]
            [goog.events :as events]
            [hiccups.runtime :as hiccupsrt])
  (:require-macros [hiccups.core :as hiccups]))

(def messages (js/Firebase. "https://balling-ironman.firebaseio.com/"))

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

(defn display-entry-and-login
  [entry-display login-display]
  (style/showElement (by-id "entry")
                     entry-display)
  (style/showElement (by-id "login")
                     login-display))

(defn handle-firebase-simple-login-user-not-present
  []
  (display-entry-and-login false true))

(defn handle-firebase-simple-login-user-present
  [user]
  (.log js/console user)
  (display-entry-and-login true false)
  (set! (.-value (dom/getElement "nameInput") ) 
        (.-name user)))

(defn handle-firebase-simple-login-error
  [error]
  (.log js/console error))

(defn simple-login-auth-handler
  [error user]
  (cond 
   error (handle-firebase-simple-login-error error)
   user  (handle-firebase-simple-login-user-present user) 
   :else (handle-firebase-simple-login-user-not-present)))

(defn init-login-auth-handler
  []
  (let [auth  (js/FirebaseSimpleLogin. messages 
                                       simple-login-auth-handler)]
    (events/listen (by-id "login") 
                    (.-CLICK events/EventType) 
                   #(.login auth "facebook"))))

(defn init
  []
  (init-submit-message-handler)
  (init-populate-messages-handler)
  (init-login-auth-handler))

(set! (.-onload js/window) init)
