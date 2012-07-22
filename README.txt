joos1bench
==========

This is a small collection of (spaghetti-code) Joos 1 benchmark programs
currently including

 * textutils: Implementations of wc, tr, and echo.

 * joos1lexer: A lexer for Joos 1.

 * joos1syntax: A terribly written syntax checker for Joos 1 that
   outputs poor error messages and stops on the first error it
   encounters.

Joos 1 is a subset of Java 1.3; it's used as the source language in
the compilers course at Aarhus University. (The course currently uses
Ocaml as the implementation language.)


Usage
-----

Each of the top-level directories contains an Ant build.xml file. To
compile one of the programs in this collection, `cd` into the
directory containing it and run `ant -Djoosc=/path/to/your/joos1-compiler`.
For instance, to compile `textutils` one might do as follows:

    $ cd textutils
    $ ant -Djoosc=/home/$USER/bin/joos


License
-------

Public domain: http://creativecommons.org/publicdomain/zero/1.0/
