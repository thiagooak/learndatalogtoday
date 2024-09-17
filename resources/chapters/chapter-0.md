# Learn Datalog Today

An interactive tutorial that will teach you how to query a [Datomic](https://datomic.com) database with [Datalog](https://en.wikipedia.org/wiki/Datalog).

Here is an example query that finds all movie titles in our example database:

```clojure
[:find ?title
 :where
 [_ :movie/title ?title]]
```

Use the text box to your right to test Datalog queries against our movies database.
