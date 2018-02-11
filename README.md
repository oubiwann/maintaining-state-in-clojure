# Maintaining State in Clojure


## Caveat

These examples are here to help those new to Clojure in establishing
analogies with other languages (or Lisp dialects). Please keep in mind
the travesties committed in the majority of object-oriented software over
the decades since the 80s: data and behaviour should not be conflated.

To put a finer point on this, if you are looking for proper design patterns
for working with state in Clojure applications, be sure to purchase any
number of the Clojure books dedicated to real-world applications. Please do
not treat this project as an application reference.

One note on real-world applications: I highly recommend Stuart Sierra's
[component library][component] for separating your apps into components
and sharing only that state which each needs between components.

This is an old repo, but since it comes up in search results, I try to keep
it updated.


## Background

Learning how to work with state in such a way as fits the language of one's
choice is critical for building production-ready applications.

Learning how to _play_ with state in different ways is actually a fun topic
of exploration. Different languages often have widely diverging features
that allow one to maintain state in all sorts of unique ways.

The examples in this repo explore some of this, from the perspective of
Clojure. They include:

* Using closures
* Using a data structure
* Using Clojure protocols
* Using `core.async` channels

Enjoy!


## Preparations


### Getting the Code

To play with the examples in this repo, you'll need to clone it to the
machine that you're working on:

```shell
$ git clone https://github.com/oubiwann/maintaining-state-in-clojure.git
$ cd maintaining-state-in-clojure
```


### Firing up the REPL

```
$ lein repl
```

If you don't have `lein` installed, you'll need to [download it][lein-dl].


## Examples

The examples below are adapted from an example given by Peter Norvig in
Chapter 13 of his famous Lisp/AI book, [PAIP][paip]. The
[Chapter 13 PAIP source code][paip-ch13] is available online at the
[Peter Norvig site][norvig-site].


### Using Closures

Once upon a time (before CLOS), if you wanted to maintain state in Lisp, you
used closures. As a nod to this savory history, we start with a closure
example.

This example uses nested closures to:

1. dispatch based upon a passed keyword, and

1. return a dispatched function that has access to the top-level function's
   variables as well as variables that are passed in to the nested functions.

This sort of construction provides some of the basic functionality of an
object system (mostly just state data).

Here is a link to the [state-via-closures example code][closures].
Take a look, then let's see how this works in action:


```clj
state-examples.dev=> (def acc (closures/new-account "savings" 1000 0.05))
#'state-examples.dev/acc
state-examples.dev=> (closures/get-name acc)
"savings"
state-examples.dev=> (closures/get-balance acc)
1000
```

If we call any functions that make any changes to state data, a new account
object gets returned. As such, in those cases we'll need to reasign the new
object to our account variable:

```clj
state-examples.dev=> (def acc (closures/deposit acc 150.50))
#'state-examples.dev/acc
state-examples.dev=> (closures/get-balance acc)
1150.5
state-examples.dev=> (def acc (closures/apply-interest acc))
#'state-examples.dev/acc
state-examples.dev=> (closures/get-balance acc)
1208.025
state-examples.dev=> (def acc (closures/withdraw acc 25.25))
#'state-examples.dev/acc
state-examples.dev=> (closures/get-balance acc)
1182.775
state-examples.dev=> (closures/withdraw acc 2000)

Exception : Insufficient funds.  state-examples.closures/new-account/fn--28/fn--38 (closures.clj:28)
```

### Data Structures as a Counter Example

Most of these examples are using fairly elaborate means of doing something
quite simple: tracking data. What simpler way to do that than a data
structure? None, that's what way.

This example provides a convenience function which creates a simple map. This
allows us to use it just like we did the previous example. The functions,
instead of extracting info from nested closures, simply operate on the
provided data structure.

Note that this approach is not thread-safe.

Here is a link to the [state-via-data-structures example code][data].

For this example, we've used an identical set of functions as the closures
example, with no fancy-pants. Just data. We'll start it off like we did
before:

```clj
state-examples.dev=> (def acc (data/new-account "savings" 1000 0.05))
#'state-examples.dev/acc
state-examples.dev=> (data/get-name acc)
"savings"
state-examples.dev=> (data/get-balance acc)
1000
```

Let's walk through the same steps:

```clj
state-examples.dev=> (def acc (data/deposit acc 150.50))
#'state-examples.dev/acc
state-examples.dev=> (data/get-balance acc)
1150.5
state-examples.dev=> (def acc (data/apply-interest acc))
#'state-examples.dev/acc
state-examples.dev=> (data/get-balance acc)
1208.025
state-examples.dev=> (def acc (data/withdraw acc 25.25))
#'state-examples.dev/acc
state-examples.dev=> (data/get-balance acc)
1182.775
state-examples.dev=> (data/withdraw acc 2000)

Exception : Insufficient funds.  state-examples.data/withdraw (data.clj:27)
```

### Using Protocols and Records

We now take a look at Clojure's wrapping around Java interfaces and classes
as a means of maintaining state.

Again, we've set things up so that the usage is almost identical to the
previous examples.

Here is a link to the [state-via-protocols example code][protocols].

```clj
state-examples.dev=> (def acc (protocols/new-account "savings" 1000 0.05))
#'state-examples.dev/acc
state-examples.dev=> (protocols/get-name acc)
"savings"
state-examples.dev=> (protocols/get-balance acc)
1000
```

And now for some operations on our data:

```clj
state-examples.dev=> (def acc (protocols/new-account "savings" 1000 0.05))
#'state-examples.dev/acc
state-examples.dev=> (protocols/get-name acc)
"savings"
state-examples.dev=> (protocols/get-balance acc)
1000
state-examples.dev=> (def acc (protocols/deposit acc 150.50))
#'state-examples.dev/acc
state-examples.dev=> (protocols/get-balance acc)
1150.5
state-examples.dev=> (def acc (protocols/apply-interest acc))
#'state-examples.dev/acc
state-examples.dev=> (protocols/get-balance acc)
1208.025
state-examples.dev=> (def acc (protocols/withdraw acc 25.25))
#'state-examples.dev/acc
state-examples.dev=> (protocols/get-balance acc)
1182.775
state-examples.dev=> (protocols/withdraw acc 2000)

Exception : Insufficient funds.  state-examples.protocols.Account (protocols.clj:22)
```


### Using `core.async` Channels

This example is a completely different animal ... on the surface.
Generalizing to core concepts, this is very simiilar to using closures.

Here is a link to the [state-via-channels example code][channels].

```clj
state-examples.dev=> (def acc (channels/new-account "savings" 1000 0.05))
#'state-examples.dev/acc
state-examples.dev=> (channels/get-name acc)
"savings"
state-examples.dev=> (channels/get-balance acc)
1000
```

Let's walk through the same steps as the other examples:

```clj
state-examples.dev=> (channels/deposit acc 150.50)
:ok
state-examples.dev=> (channels/get-balance acc)
1150.5
state-examples.dev=> (channels/apply-interest acc)
:ok
state-examples.dev=> (channels/get-balance acc)
1208.025
state-examples.dev=> (channels/withdraw acc 25.25)
:ok
state-examples.dev=> (channels/get-balance acc)
1182.775
state-examples.dev=> (channels/withdraw acc 2000)
:insufficient-funds
```


[lein-dl]: https://github.com/technomancy/leiningen#installation
[closures]: src/state_examples/closures.clj
[data]: src/state_examples/data.clj
[protocols]: src/state_examples/protocols.clj
[channels]: src/state_examples/channels.clj
[paip]: http://www.amazon.com/dp/B003VWBY1I/
[paip-ch13]: http://norvig.com/paip/clos.lisp
[norvig-site]: http://norvig.com/
[component]: https://github.com/stuartsierra/component
