(ns pokedex-fast-api.db
    (:require [clojure.java.jdbc :as jdbc]
              [clojure.spec.alpha :as s]
              [clojure.spec.test.alpha :as stest]
              [pokedex-fast-api.config :as config]
              [clojure.spec.gen.alpha :as gen]))

(def db-spec (:db-spec config/properties))

;; data specs
(s/def ::preview (s/keys :req-un [::proper_name ::dex_num ::type1
                                  ::type2 ]))
(s/def ::stats (s/keys :req-un [::height ::weight ::hp ::atk ::def ::spatk ::spdef ::spd]))
(def table-name "PokemonSneakPeek")

;; helper db functions
(defn make-select-string
  "Make a valid columns query string for an SQL select query. If columns
   is empty or nil, returns the * for all columns."
  [columns]
  (let [kw->str (comp #(.substring % 1) str)
        str-cols (map kw->str columns)
        insert-comma (fn [x y] (str x ", " y))]
    (if (seq columns)
      (reduce insert-comma str-cols)
      "*")))

(defn get-rows
  "Queries the database from db-spec for all columns in the columns vector, from table,
   and transforms each row using row-fn. Returns a vector of maps matching
   the result set."
  [columns table row-fn]
  (let [select-params (make-select-string columns)
        sql-query [(str "select " select-params " from " table)]]
    (jdbc/query db-spec
                sql-query
                {:row-fn row-fn})))

;; public methods
(defn get-previews []
  (get-rows [:proper_name :dex_num :type1 :type2]
            table-name
            #(s/conform ::preview %)))

(defn get-stats []
  (get-rows [:height :weight :hp :atk :def :spatk :spdef :spd]
            table-name
            #(s/conform ::stats %)))
