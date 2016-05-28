(ns clj-todos.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/clj-todos"})

(defn add-todo-to-db
  [description priority]
  (let [results (sql/with-connection db-spec
                  (sql/insert-record :todos
                                     {:description description :priority priority}))]
    (assert (= (count results) 1))
    (first (vals results))))

(defn get-todo
  [todo-id]
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["SELECT description, priority FROM todos WHERE id = ?" todo-id]
                    (doall res)))]
    (assert (= (count results) 1))
    (first results)))

(defn get-all-todos
  []
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["SELECT id, description, priority FROM todos"]
                    (doall res)))]
    results))
