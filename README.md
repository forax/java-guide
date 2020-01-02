# java-guide
A guide to modern Java (Java 17)

## Content

0. [genesis.md](guide/chapter00-genesis.md)
1. [basic_types.md](guide/chapter01-basic_types.md)
2. [methods.md](guide/chapter02-methods.md)
3. [jshell_vs_java.md](guide/chapter03-jshell_vs_java.md)
4. [numbers.md](guide/chapter04-numbers.md)
5. [control_flow.md](guide/chapter05-control_flow.md)
6. [interface.md](guide/chapter07-interface.md)
7. [lambda.md](guide/chapter08-lambda.md)
8. [list_and_map.md](guide/chapter09-list_and_map.md)
9. [string_formatting.md](guide/chapter10-string_formatting.md)
10. [encapsulation.md](guide/chapter11-encapsulation.md)
11. [equals_hashCode_toString.md](guide/chapter12-equals_hashCode_toString.md)
12. [contract.md](guide/chapter13-contract.md)
13. [modifable_vs_mutalble.md](guide/chapter13-modifable_vs_mutalble.md)
14. [null_and_optional.md](guide/chapter14-null_and_optional.md)
15. [inheritance.md](guide/chapter15-inheritance.md)
16. [exception.md](guide/chapter16-exception.md)
17. [enum.md](guide/chapter17-enum.md)
18. [internal_classes.md](guide/chapter18-internal_classes.md)
19. [implementing_interface.md](guide/chapter19-implementing_interface.md)
20. [generics.md](guide/chapter20-generics.md)
21. [wrapper.md](guide/chapter21-wrapper.md)
22. [variance.md](guide/chapter22-variance.md)
23. [limitation_of_generics.md](guide/chapter23-limitation_of_generics.md)
24. [stream.md](guide/chapter25-stream.md)
25. [collector.md](guide/chapter26-collector.md)
26. [data_structure.md](guide/chapter30-data_structure.md)
27. [sort.md](guide/chapter31-sort.md)


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

To quit use '/exit', to enable verbose error messages '/set feedback verbose', otherwise to get the help type '/help'


## Work in progress

This is a work in progress, don't hesitate to contribute, i'm waiting your pull request


### Build markdown files from jshell files

Using java 14
```
  java --enable-preview build/build.java
```
