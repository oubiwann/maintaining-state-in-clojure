############################
Maintaining State in Clojure
############################

Background
==========

Examples
========

Getting the Code
----------------

Firing up the REPL
------------------

.. code:: shell

    $ lein repl

Oldskool
--------

First we're going to take a look at XXX

..code:: clojure

    user=> (ns fake-obs (:require [state-examples.fake-objects :refer :all]))
    nil
    fake-obs=> (def acc (new-account "savings" 1000 0.05))
    #'fake-obs/acc
    fake-obs=> (get-name acc)
    "savings"
    fake-obs=> (get-balance acc)
    1000
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