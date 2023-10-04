# Learn Datalog Today

An interactive tutorial that will teach you the [Datomic](https://datomic.com) dialect of [Datalog](https://en.wikipedia.org/wiki/Datalog).
Datalog is a declarative database query language with roots in logic programming.

## Table of Contents

- [Basic Queries](/chapter/1)
- [Data Patterns](/chapter/2)
- [Parameterized Queries](/chapter/3)
- [More Queries](/chapter/4)
- [Predicates](/chapter/5)
- [Transformation Functions](/chapter/6)
- [Aggregates](/chapter/7)
- [Rules](/chapter/8)

In Datomic, a Datalog query is written in [EDN (Extensible Data Notation)](http://edn-format.org).

Here is an example query that finds all movie titles in our example database:

    [:find ?title
     :where
     [_ :movie/title ?title]]

Note that the query is a vector with four elements:

* the keyword `:find`
* the symbol `?title`
* the keyword `:where`
* the vector `[_ :movie/title ?title]`

Use the text box to your right to test Datalog queries against our movies database.
