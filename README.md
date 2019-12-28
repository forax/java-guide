# java-guide
A guide to modern Java (Java 17)

## Content

0. [genesis.md](guide/chapter00-genesis.md)
1. [basictypes.md](guide/chapter01-basictypes.md)
2. [methods.md](guide/chapter02-methods.md)
3. [jshellvsjava.md](guide/chapter03-jshellvsjava.md)
4. [controlflow.md](guide/chapter04-controlflow.md)
5. [interface.md](guide/chapter05-interface.md)
6. [lambda.md](guide/chapter06-lambda.md)
7. [listandmap.md](guide/chapter07-listandmap.md)
8. [encapsulation.md](guide/chapter09-encapsulation.md)
9. [internalclass.md](guide/chapter10-internalclass.md)
10. [inheritance.md](guide/chapter12-inheritance.md)
11. [generics.md](guide/chapter15-generics.md)
12. [stream.md](guide/chapter18-stream.md)
13. [datastructure.md](guide/chapter20-datastructure.md)
14. [sort.md](guide/chapter21-sort.md)


## Using Java Shell (jshell)

Each chapter comes with executable examples that you can run using jshell.

To get the examples, just clone this repository
```
  git clone http://github.com/forax/java-guide
```

Then run jshell (at least Java 14 version)
```
   jshell --enable-preview
```

Then you can copy paste the examples inside jshell and see by yourself.

To quit use '/exit', to have have verbose error message '/set feedback verbose', otherwise to get the help '/help'


## Work in progress

This is a work in progress, don't hesitate to contribute, i'm waiting your pull request


### Build markdown files from jshell files

Using java 14
```
  java --enable-preview build/build.java
```
