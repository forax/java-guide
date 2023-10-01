# java-guide
A guide to modern Java (Java 17)

This guide is about learning Java from scratch if you know a bit of C or JavaScript.

This is a Work in progress, it should be ready when the next LTS of Java (Java 17) will be released.
All the codes run with Java 14 with the preview features enabled.

> Note: if you are looking to only what's new in Java 14
> [I have set of slides for that](https://github.com/forax/do-synthetic-methods-dream-of-electric-switch-expressions).


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
10. [class_and_encapsulation.md](guide/chapter11-class_and_encapsulation.md)
11. [equals_hashCode_toString.md](guide/chapter12-equals_hashCode_toString.md)
12. [contract.md](guide/chapter13-contract.md)
13. [modifiable_vs_mutable.md](guide/chapter13-modifiable_vs_mutable.md)
14. [null_and_optional.md](guide/chapter14-null_and_optional.md)
15. [inheritance.md](guide/chapter15-inheritance.md)
16. [exception.md](guide/chapter16-exception.md)
17. [enum.md](guide/chapter17-enum.md)
18. [nested_classes.md](guide/chapter18-nested_classes.md)
19. [array.md](guide/chapter19-array.md)
20. [implementing_interface.md](guide/chapter19-implementing_interface.md)
21. [generics.md](guide/chapter20-generics.md)
22. [wrapper.md](guide/chapter21-wrapper.md)
23. [variance.md](guide/chapter22-variance.md)
24. [limitation_of_generics.md](guide/chapter23-limitation_of_generics.md)
25. [stream.md](guide/chapter25-stream.md)
26. [collector.md](guide/chapter26-collector.md)
27. [data_structure.md](guide/chapter30-data_structure.md)
28. [sort.md](guide/chapter31-sort.md)


## Using Java Shell (jshell)

Each chapter comes with executable examples that you can run using jshell.

To get the examples, just clone this repository
```shell
git clone http://github.com/forax/java-guide
```

Then run jshell (at least Java 14 version)
```shell
jshell --enable-preview
```

Then you can copy paste the examples inside jshell and see by yourself.

To quit use '/exit', to enable verbose error messages '/set feedback verbose', otherwise to get the help type '/help'


## Using Jupyter notebook

### on the cloud
You can run it directly in your browser
[![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/forax/java-guide/master)


### or using docker
You need to have docker already installed, then

- get the docker image from dockerhub
  ```shell
  docker pull forax/java-guide
  ```
- run the docker image in a container
  ```shell
  docker run -p 8888:8888 forax/java-guide
  ```
- open your browser using the `tokenId` printed on the console
  ```shell
  firefox http://localhost:8888/?token=tokenId
  ```


### or install everything on your laptop
You need to have python3 and Java 14 already installed, then

- clone this repository
  ```shell
  git clone http://github.com/forax/java-guide
  cd java-guide
  ```
- install [jupyter](https://jupyter.org/install)
  ```shell
  pip install notebook
  ```
- install the [ijava 1.3.0](https://github.com/SpencerPark/IJava) kernel (from Spencer Park)
  ```shell
  wget https://github.com/SpencerPark/IJava/releases/download/v1.3.0/ijava-1.3.0.zip
  python3 install.py --sys-prefix
  ```
- patch it with the repository file `kernel.json`<br>
  list all kernels to see if the java kernel is installed
  ```shell
  jupyter kernelspec list
  ```
  then copy the file `kernel.json` from the folder `docker` to the java kernel directory
  ```shell
  cp docker/kernel.json /path/to/jupyter/kernels/java
  ```
- set the env compiler option enabling the preview features
  ```shell
  export IJAVA_COMPILER_OPTS="--enable-preview -source 14"
  ```
- run the notebook
  ```shell
  cd jupyter
  jupyter notebook
  ```


### Build markdown and jupyter files from jshell files
The markdown files (.md) and the jupyter files (.ipynb) are derived/generated
from the jshell files using a small Java script.

Using java 14
```shell
java --source 14 --enable-preview build/build.java
```
