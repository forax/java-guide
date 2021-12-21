# Numbers
This chapter in not specific to Java but more on how integers and floating point numbers
work on CPUs like Intel 64-bit or ARM 64-bit.


## Integers

### Dividing by zero
If you try to divide by 0, you get an exception
```java
System.out.println(1 / 0);
System.out.println(1 % 0);   // remainder of the division
```

### Overflow
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


You can notice that the minimum value is one less than the maximum value
in absolute value so `Math.abs()` has an overflow issue
because -Integer.MIN_VALUE is not Integer.MAX_VALUE
```java
System.out.println(Math.abs(Integer.MIN_VALUE));
```

When trying to find the middle between two values, the calculation may also overflow
so the result becomes nagative
```java
int middle(int value1, int value2) {
  return (value1 + value2) / 2;
}
System.out.println(middle(Integer.MAX_VALUE, 1));
```

In this specific case, you can recover from the overflow by using
the triple shift operator `>>>` that doesn't consider the sign bit as a sign bit
but as a bit which is part of the value
```java
int middle(int value1, int value2) {
  return (value1 + value2) >>> 1;
}
System.out.println(middle(Integer.MAX_VALUE, 1));
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

On way to see the trick is to ask a float (32-bit), to be printed as a double (64-bit).
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
System.out.println(0.0 / 0.0);
System.out.println(Double.NaN);
```

### NaN
Not a Number is very weird, because by definition, it's the number which is not equal to itself

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

An equivalent static method to isNotANumber already exist in Double, `Double.isNaN()`
```java
System.out.println(Double.isNaN(Double.NaN));
```


### Record and NaN
To avoid the issue of a record r not equals to itself because it has a component
that contains NaN the implementation of `equals()` for a record checks
the raw bytes of the double after all NaN (yes internally there are several possible
representation of NaN) are collapsed into one so testing if two records are equals works as expected !
```java
record MagicBeer(double content) { }
var beer1 = new MagicBeer(Double.NaN);
var beer2 = new MagicBeer(Double.NaN);
System.out.println(beer1.equals(beer2));
```
