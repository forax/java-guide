# java-guide
A guide to modern Java (Java 17)

## Content

0. [genesis.md](guide/chapter00-genesis.md)
1. [basictypes.md](guide/chapter01-basictypes.md)
2. [methods.md](guide/chapter02-methods.md)
3. [jshellvsjava.md](guide/chapter03-jshellvsjava.md)
4. [controlflow.md](guide/chapter04-controlflow.md)
5. [numbers.md](guide/chapter06-numbers.md)
6. [interface.md](guide/chapter07-interface.md)
7. [lambda.md](guide/chapter08-lambda.md)
8. [listandmap.md](guide/chapter09-listandmap.md)
9. [stringformatting.md](guide/chapter10-stringformatting.md)
10. [exception.md](guide/chapter11-exception.md)
11. [encapsulation.md](guide/chapter12-encapsulation.md)
12. [contract.md](guide/chapter13-contract.md)
13. [nullandoptional.md](guide/chapter14-nullandoptional.md)
14. [inheritance.md](guide/chapter15-inheritance.md)
15. [internalclass.md](guide/chapter17-internalclass.md)
16. [generics.md](guide/chapter20-generics.md)
17. [wrapper.md](guide/chapter21-wrapper.md)
18. [variance.md](guide/chapter22-variance.md)
19. [stream.md](guide/chapter25-stream.md)
20. [collector.md](guide/chapter26-collector.md)
21. [datastructure.md](guide/chapter30-datastructure.md)
22. [sort.md](guide/chapter31-sort.md)


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
