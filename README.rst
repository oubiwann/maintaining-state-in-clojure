############################
Maintaining State in Clojure
############################

Background
==========

TBD


Getting the Code
================

To play with the examples in this repo, you'll need to clone it to the machine
that you're working on:

.. code:: shell

  $ git clone https://github.com/oubiwann/maintaining-state-in-clojure.git
  $ cd maintaining-state-in-clojure


Firing up the REPL
==================

.. code:: shell

``lein`` is fantastic. We should all be using more of it :-) With the ``repl``
command, you will be put into a shell where the code in your source files will
be available:

.. code:: shell

    $ lein repl

If you don't have it installed, you'll need to `download lein`_.


Examples
========

All of the examples below are adapted from an example given by Peter Norvig in
Chapter 13 of his famous Lisp book, `PAIP`_. The `Chapter 13 PAIP source code`_
is available online at the `Peter Norvig site`_.


Using Closures
--------------

Once upon a time (before CLOS), if you wanted to maintain state in Lisp, you
used closures. As a nod to this savory history, we start with a closure example.

This example uses nested closures to:

#. dispatch based upon a passed keyword, and

#. return a dispatched function that has access to the top-level function's
   variables as well as variables that are passed in to the nested functions.

This sort of construction provides some of the basic functionality of an object
system (mostly just state data).

Here is a link to the `state-via-closures example code`_ . Take a look, then
let's set up a working namespace in the REPL, and then we can see how this
works in action:

.. code:: clojure

    user=> (ns closures (:require [state-examples.closures :refer :all]))
    nil

If you want to play with the code and made changes, you can use the ``:reload``
keyword:

.. code:: clojure

    user=> (ns closures (:require [state-examples.closures :refer :all] :reload))
    nil

We've got a REPL namespace, with all of the functions available in this
namespace (thanks to ``:refer :all``), so let's get started with creating a
new account object, and poke it a bit:

.. code:: clojure

    closures=> (def acc (new-account "savings" 1000 0.05))
    #'closures/acc
    closures=> (get-name acc)
    "savings"
    closures=> (get-balance acc)
    1000

If we call any functions that make any changes to state data, a new account
object gets returned. As such, in those cases we'll need to reasign the new
object to our account variable:

.. code:: clojure

    closures=> (def acc (deposit acc 150.50))
    #'closures/acc
    closures=> (get-balance acc)
    1150.5
    closures=> (def acc (apply-interest acc))
    #'closures/acc
    closures=> (get-balance acc)
    1208.025
    closures=> (def acc (withdraw acc 25.25))
    #'closures/acc
    closures=> (get-balance acc)
    1182.775
    closures=> (withdraw acc 2000)
    Exception : Insufficient funds.  state-examples.closures/new-account/fn--1253/fn--1263 (closures.clj:29)


Using Protocols and Records
---------------------------

TBD


Using Agents
------------

TBD


Using Light-weight Processes
----------------------------

TBD

.. Links
.. -----
..
.. _download lein: https://github.com/technomancy/leiningen#installation
.. _state-via-closures example code: src/state_examples/fake_objects.clj
.. _PAIP: http://www.amazon.com/dp/B003VWBY1I/
.. _Chapter 13 PAIP source code: http://norvig.com/paip/clos.lisp
.. _Peter Norvig site: http://norvig.com/
