We have seen in a previous chapter that we can use classes to hide information. 
We can even declare classes inside a class.

Several examples are using `range()`, so we are importing it first
```java
import static java.util.stream.IntStream.range;
```

# Nested classes

There 4 kinds of nested classses
- nested classes of classes, _static_ or _inner_
- local classes of methods, _anonymous_ or _named_

## Static nested class
A static nested class is a class declared inside another one.
Inside a class, like with fields and method, a nested class
can be private, public or package visible.
Using a nested class is a way to tidy things up.
Here the name of the class is `Utils.Result`,
```java
public class Utils {
  public static class Result {
    private final int count;
    private final double sum; 
    private Result(int count, double sum) {
      this.count = count;
      this.sum = sum;
    }
    public double average() {
      return sum / count;
    }
  }
  public static Result findSumAndAverage(double[] array) {
    return new Result(array.length, Arrays.stream(array).sum());
  }
}
var array = range(0, 20).mapToDouble(x -> x).toArray();
System.out.println(Utils.findSumAndAverage(array).average());
```


### Always static
A class inside an interface is always static.
A record inside a class is always static.

So the following code has mostly the same behavior
```java
public class Utils {
  public record Result(int count, double sum) {
    public double average() {
      return sum / count;
    }
  }
  public static Result findSumAndAverage(double[] array) {
    return new Result(array.length, Arrays.stream(array).sum());
  }
}
System.out.println(Utils.findSumAndAverage(array).average());
```


## Inner class (non static)
An inner class is a non-static nested class.
An inner class can access to the instance fields of the enclosing
class because an inner class is created on an instance of the
enclosing class.
In the method `subList()`, you can notice that `RangeList` is
created using `this.new RangeList(...)`. The new instance of
`RangeList` is created on an instance of `Range`.
```java
record Range(int start, int end) {
  private class RangeList extends AbstractList<Integer> implements RandomAccess {
    private final int from;
    private final int to;
    private RangeList(int from, int to) {
      this.from = from;
      this.to = to;
    }
    public int size() {
      return to - from;
    }
    public Integer get(int index) {
      Objects.checkIndex(index, size());
      return start + from + index;
    }
  }
  public Range {
    Objects.checkIndex(start, end);
  }
  public List<Integer> subList(int from, int to) {
    Objects.checkFromToIndex(from, to, end - start);
    return this.new RangeList(from, to);
  }
}
var list = new Range(2, 10).subList(2, 6);
System.out.println(list);
```

In the code above, we are inheriting from `AbstractList` that
allows to implement an unmodifiable List given two methods
`size()` and `get(index)`.
Implanting `RandomAccess` means that `get(index)` is implemented
in constant time. For more info, see the chapter 'collection'.


### Accessing the outer instance
Inside the inner class, you may want to the instance of
the enclosing class on which the current instance was created.
There is a special syntax for that, in the example below,
it's `A.this`. 

The syntax looks like accessing a static field named `this`
of the enclosing class but there is nothing static here.
But it's accessing to the instance of A at the time B was created.
It's just a weird syntax.
```java
class A {
  class B {
    void print() {
      System.out.println("this " + this);
      System.out.println("A.this " + A.this);
    }
  }
}
var a = new A();
System.out.println("a " + a);
var b = a.new B();
System.out.println("b " + b);
b.print();
```


## Local anonymous class of method
Java allows to create class inside methods, with the twist
that the class can access the local variables of the method if
the variable is initialized once (the compiler named those
variables _effectively final_).
In the code below, `from` and `to` are the two effectively
final variables (here parameters) that are used inside the
anonymous class.

An anonymous class is a class that is not named in the user code
(the compiler will give it a name) so there is a special syntax
for creating an anonymous class
In the code below, it's `new AbstractList<Integer>() { ... }`,
it means create a class with no name that inherits from
`AbstractList<Integer>`.
```java
record Range(int start, int end) {
  public Range {
    Objects.checkIndex(start, end);
  }
  public List<Integer> subList(int from, int to) {
    Objects.checkFromToIndex(from, to, end - start);
    return new AbstractList<Integer>() {
      public int size() {
        return to - from;
      }
      public Integer get(int index) {
        Objects.checkIndex(index, size());
        return start + from + index;
      }
    };
  }
}
var list = new Range(2, 10).subList(2, 6);
System.out.println(list);
```


### interaction with var
While an anonymous class has no name from the user point of view,
it has a name given by the compiler so we can use `var` to
ask the compiler to declare a variable of that type.

In the code below, if we try to type `box` as `Object`,
`box.sum` doesn't compile (Object has no field sum).
```java
Object box = new Object() {
  int sum;
};
System.out.println(box.sum);  // doesn't compile
```

using `var`, it works !
```java
var box = new Object() {
  int sum;
};
range(0, 10).forEach(value -> box.sum += value);
System.out.println(box.sum);
```


## Local named class of method
The anonymous class syntax has a restriction, you can not
inherits/implements more than one type, if you want to both
inherits from a class and implement an interface like
for `RangeList`, you can name the class inside the method. 

In the code below, `RangeList` is a named class that is only
available inside the method `subList`. Like an anonymous
class it can access effectively final variables.
```java
record Range(int start, int end) {
  public Range {
    Objects.checkIndex(start, end);
  }
  public List<Integer> subList(int from, int to) {
    Objects.checkFromToIndex(from, to, end - start);
    class RangeList extends AbstractList<Integer> implements RandomAccess {
      public int size() {
        return to - from;
      }
      public Integer get(int index) {
        Objects.checkIndex(index, size());
        return start + from + index;
      }
    }
    return new RangeList();
  }
}
var list = new Range(2, 10).subList(2, 6);
System.out.println(list);
```

