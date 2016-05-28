(require '[clojure.java.jdbc :as sql])

(sql/with-connection
  {:classname "org.h2.Driver"
   :subprotocol "h2:file"
   :subname "db/clj-todos"}

  (sql/create-table :todos
                    [:id "bigint primary key auto_increment"]
                    [:description "varchar"]
                    [:priority "integer"]))
