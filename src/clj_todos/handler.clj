(ns clj-todos.handler
  (:require [clj-todos.views :as views]
            [compojure.core :as cc]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(cc/defroutes app-routes
  (cc/GET "/"
          []
          (views/home-page))
  (cc/GET "/add-todo"
          []
          (views/add-todo-page))
  (cc/POST "/add-todo"
           {params :params}
           (views/add-todo-results-page params))
  (cc/GET "/todos/:todo-id"
          [todo-id]
          (views/todo-page todo-id))
  (cc/GET "/todos"
          []
          (views/all-todos-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
