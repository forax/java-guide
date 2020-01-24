# Wrapper types
Primitive types are not objects, but all data structures in Java (list, map, etc)
are storing objects, so we need a mechanism to see all primitive values as objects.

For that, the Java API defines the for each primitive type, a wrapper class,
in the package java.lang which is a class with one field
and the compiler as a mechanism named auto-boxing/auto-unboxing to convert
from a primitive type to the corresponding wrapper and vice-versa.

By example, to be able to type the list, we need an object type corresponding
to the primitive type int
```java
List<Integer> list = List.of(1, 2, 3);
```


## Auto-boxing
You can do the conversion explicitly calling the static method `valueOf` on the wrapper type
or implicitly if the target type is Object

### boolean -> java.lang.Boolean
```java
Object booleanBox = Boolean.valueOf(true);
Object booleanBox = true;
System.out.println(booleanBox);
```

### char -> java.lang.Character
```java
Object charBox = Character.valueOf('A');
Object charBox = 'A';
System.out.println(charBox);
```

### byte -> java.lang.Byte
```java
Object byteBox = Byte.valueOf((byte) 12);
Object byteBox = (byte) 12;
System.out.println(byteBox);
```

### short -> java.lang.Short
```java
Object shortBox = Short.valueOf((short) 712);
Object shortBox = (short) 712;
System.out.println(shortBox);
```

### int -> java.lang.Integer
```java
Object intBox = Integer.valueOf(100_000);
Object intBox = 100_000;
System.out.println(intBox);
```

### long -> java.lang.Long
```java
Object longBox = Long.valueOf(10_000_000_000L);
Object longBox = 10_000_000_000L;
System.out.println(longBox);
```

### float -> java.lang.Float
```java
Object floatBox = Float.valueOf(1.2f);
Object floatBox = 1.2f;
System.out.println(floatBox);
```

### double -> java.lang.Double
```java
Object doubleBox = Double.valueOf(42.0);
Object doubleBox = 42.0;
System.out.println(doubleBox);
```


## DON'T USE == on a wrapper, use equals()
Because the wrapper are objects, the == will test the reference, not the value.

In fact, it's worst than that, the methods `valueOf()` can use a cache,
so depending on the weather (the startup parameters of the VM),
`valueOf` may return the same object for the same value or not
so you can not predict if == will returns true of false for a wrapper.
__Never use == on a wrapper, use equals() __

```java
Object intBox1 = 8192;
Object intBox2 = 8192;
System.out.println(intBox1 == intBox2); // may return true or false ??
```


## Floating points
equals() on Float and Double works correctly with NaN,
unlike the primitive types float and double.
```java
double d = Double.NaN;
System.out.println(d == d);
Double d = Double.NaN;
System.out.println(d.equals(d));
```


## Auto-unboxing

so reusing the variable `*box` defined above, we can write

### java.lang.Boolean -> boolean
```java
boolean z = ((Boolean) booleanBox).booleanValue();
boolean z = (boolean) booleanBox;
System.out.println(z);
```

### java.lang.Character -> char
```java
char c = ((Character) charBox).charValue();
char c = (char) charBox; 
System.out.println(c);
```

### java.lang.Byte -> byte
```java
byte b = ((Byte) byteBox).byteValue();
byte b = (byte) byteBox;
System.out.println(b);
```

### java.lang.Short -> short
```java
short s = ((Short) shortBox).shortValue();
short s = (short) shortBox;
System.out.println(s);
```

### java.lang.Integer -> int
```java
int i = ((Integer) intBox).intValue();
int i = (int) intBox;
System.out.println(i);
```

### java.lang.Long -> long
```java
long l = ((Long) longBox).longValue();
long l = (long) longBox;
System.out.println(l);
```

###  java.lang.Float -> float
```java
float f = ((Float) floatBox).floatValue();
float f = (float) floatBox;
System.out.println(f);
```

### java.lang.Double -> double
```java
double d = ((Double) doubleBox).doubleValue();
double d = (double) doubleBox; 
System.out.println(d);
```


## Beware of null
Trying to unbox null throws a NullPointerException
```java
Integer box = null;
int i = (int) box;
```


## Where not to use a wrapper type
Wrapper types should only appears in between '<' and '>'
because the generics of Java doesn't support primitive type inside the '<' and '>'.

So please don't use them as type of a field or a method parameter
unless you have a very good reason.
