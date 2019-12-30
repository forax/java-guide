
# Numbers
This chapter in not specific to Java but more on how integers and floating point numbers
works on CPUs like Intel 64 bits or ARM 64 bits.


## Integers
Given that integers are represented using a fixed number of bits,
there is a minimum/maximum number that can be represented
```java
System.out.println("max " + Integer.MAX_VALUE);
System.out.println("min " + Integer.MIN_VALUE);
```

integers can overflow, if a positive integers is too big, it becomes negative
```java
System.out.println(Integer.MAX_VALUE + 1);
```

and vice versa
```java
System.out.println(Integer.MIN_VALUE - 1);
```

In Java, you have safe alternatives that are slower but throw an exception
if the computation overflow
```java
Math.addExact(Integer.MAX_VALUE, 1);
Math.subtractExact(Integer.MIN_VALUE, 1);
```

If you try to divide by 0, you get an exception
```java
1 / 0
1 % 0   // remainder of the division
```


## Double precision floating point numbers
The computation using floating point in not precise because not all values are
directly representable so the CPU will use the closest value.
It's like using 3.14 when you ask for π

So when you do a computation, the error propagates and becomes visible
```java
System.out.println(0.1 + 0.2);
```

When you print a double, there is a trick, only some decimals will be printed
so you may think the value is fully represented that but that's just an illusion
```java
System.out.println(1.0 / 3.0);
```

On way to see the trick is to ask a float, a 32 bits, to be printed as a double, 64 bits.
```java
System.out.println(1.0f / 3.0f);
System.out.println(Float.toString(1.0f / 3.0f));
System.out.println(Double.toString(1.0f / 3.0f));  // damn i'm unmasked
```


### No exception
The computation is said `secured` so instead of having an exception thrown
when you divide by 0, you have three special values of double to represent the result
of the computation

+Infinity
```java
System.out.println(1.0 / 0.0);
System.out.println(Double.POSITIVE_INFINITY);
```

-Infinity
```java
System.out.println(-1.0 / 0.0);
System.out.println(Double.NEGATIVE_INFINITY);
```

Not A Number
```java
System.out.println(1.0 / 0.0);
System.out.println(Double.NaN);
```

### NaN
Not a Number is very weird, because by definition, it's the number which is not equals to itself
Don't use == to test NaN, it will not work
```java
System.out.println(Double.NaN == Double.NaN);
```

The only way to test is NaN is NaN is to test if it is equals to itself (by definition)
```java
boolean isNotANumber(double x) {
  return x != x;
}
System.out.println(isNotANumber(Double.NaN));
```

The method isNotANumber already exist in Double, `Double.isNaN()`
```java
System.out.println(Double.isNaN(Double.NaN));
```


### Record and NaN
When using an equals() to test if two double values are equals, the implementation
of record as a special case so if the content is two NaNs, it works ! 

```java
record MagicBeer(double content) { }
var beer1 = new MagicBeer(Double.NaN);
var beer2 = new MagicBeer(Double.NaN);
System.out.println(beer1.equals(beer2));
```
