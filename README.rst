############################
Maintaining State in Clojure
############################

Background
==========

TBD


Examples
========

TBD


Getting the Code
----------------

TBD


Firing up the REPL
------------------

.. code:: shell

    $ lein repl


Oldskool Objects via Closures
-----------------------------

First we're going to take a look at XXX

Let's set up a working namespace in the REPL:

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
object gets returned. As such, we'll need to reasign the new object to our
account variable:

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
    Exception Insufficient funds.  state-examples.fake-objects/new-account/fn--970/fn--974 (fake_objects.clj:19)


Using the object system in Clojure
----------------------------------

TBD


Using Agents
------------

TBD


Using Lieght-weight Processes
-----------------------------

TBD