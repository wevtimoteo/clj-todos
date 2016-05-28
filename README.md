Clojure TODOs
=============

A simple web app to create TODOs using Compojure, H2 and Hiccup.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Migrations

To create `H2` database, run:

```
lein exec -p db/migrate.clj
```

## Running

To start a web server for the application, run:

```
lein ring server
```
