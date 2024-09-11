# Learn Datomic

An interactive [Datomic tutorial](https://datomic.learn-some.com/).

A fork of https://github.com/jonase/learndatalogtoday.

## Run locally

You will need [Leiningen](https://leiningen.org/) and Java installed.

    $ lein uberjar
    $ java -cp target/learndatalogtoday-standalone.jar clojure.main -m learndatalogtoday.handler

Server is now running on `$PORT` (`http://localhost:8080` by default).
