# Basic Queries

The data model in Datomic is based on atomic facts called
**datoms**. A datom is a 4-tuple of:

* Entity ID
* Attribute
* Value
* Transaction ID

Think of the database as a flat **set of datoms**. Our *movies* database looks like this:

    [<e-id>  <attribute>      <value>          <tx-id>]
    ...
    [ 167    :person/name     "James Cameron"    102  ]
    [ 234    :movie/title     "Die Hard"         102  ]
    [ 234    :movie/year      1987               102  ]
    [ 235    :movie/title     "Terminator"       102  ]
    [ 235    :movie/director  167                102  ]
    ...

A few things we can learn from the data:

* The last two datoms have the same entity ID (235), which means they are facts about the same movie.
* The last datom's value points to the entity of first datom (167).
* All datoms above have the same transaction ID (tx-id), so we know they were added to the database in the same transaction.

## Writing a query

The query below returns all entity-ids that have the attribute
`:person/name` with the value `"Ridley Scott"`:

    [:find ?e
     :where
     [?e :person/name "Ridley Scott"]]

A query is represented as a vector starting with the keyword `:find`
followed by one or more **pattern variables** (symbols starting with `?`,
e.g. `?title`).

The `:where` clause restricts the query to datoms that match the given **data patterns**.
A data pattern is a datom with some parts replaced with pattern
variables.

It is the job of the query engine to figure out every
possible value of each of the pattern variables and return the ones that are
specified in the `:find` clause.

The symbol `_` can be used to ignore parts of the data pattern.
You can also ignore trailing values. So, the previous query
is equivalent to this next query.

    [:find ?e
     :where
     [?e :person/name "Ridley Scott" _]]
