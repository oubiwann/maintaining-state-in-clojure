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

If you don't have ``lein`` installed, you'll need to `download it`_.


Examples
========

TBD


State via Closures
------------------

This example uses nested closures to 1) dispatch based upon a passed keyword,
and 2) return a dispatched function that has access to the top-level function's
variables as well as variables that are passed in to the nested functions.

This sort of construction provides some of the basic functionality of an object
system (mostly just state data).

Here is a link to the `state-via-closures example code`_ . Take a look, then
let's set up a working namespace in the REPL, and then we can see how this
works in action:

.. code:: clojure

    user=> (ns fake-obs (:require [state-examples.fake-objects :refer :all]))
    nil

If you want to play with the code and made changes, you can use the ``:reload``
keyword:

.. code:: clojure

    user=> (ns fake-obs (:require [state-examples.fake-objects :refer :all] :reload))
    nil

We've got a REPL namespace, with all of the functions available in this
namespace (thanks to ``:refer :all``), so let's get started with creating a
new account object, and poke it a bit:

.. code:: clojure

    fake-obs=> (def acc (new-account "savings" 1000 0.05))
    #'fake-obs/acc
    fake-obs=> (get-name acc)
    "savings"
    fake-obs=> (get-balance acc)
    1000

If we call any functions that make any changes to state data, a new account
object gets returned. As such, in those cases we'll need to reasign the new
object to our account variable:

.. code:: clojure

    fake-obs=> (def acc (deposit acc 150.50))
    #'fake-obs/acc
    fake-obs=> (get-balance acc)
    1150.5
    fake-obs=> (def acc (apply-interest acc))
    #'fake-obs/acc
    fake-obs=> (get-balance acc)
    1208.025
    fake-obs=> (def acc (withdraw acc 25.25))
    #'fake-obs/acc
    fake-obs=> (get-balance acc)
    1182.775
    fake-obs=> (withdraw acc 2000)
    Exception : Insufficient funds.  state-examples.fake-objects/new-account/fn--970/fn--974 (fake_objects.clj:19)


Using the object system in Clojure
----------------------------------

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
.. _download it: https://github.com/technomancy/leiningen#installation
.. _state-via-closures example code: src/state_examples/fake_objects.clj
