# Limitations of generics

## No reification

In Java, generics are purely a compiler construct, the type arguments of a generics
are not available at runtime. 
- for a type variable (`T`, `E`, etc), the actual value at the execution, the type argument is not present at runtime. 
- for a parameterized type (`List&gt;String&lt;`) the type argument (`String` here) is not available too

### No type argument

Which means that all operations that requires the type argument at runtime doesn't work.
So the compiler doesn't allow you to write
- `new T(...)` 
- `new T[5]`
- `instanceof T` and `instanceof Foo<String>`
- `catch(T ..)` and `catch(Foo<String>)`, moreover 

### Unchecked Warnings

You can still write a cast like `(T)` or `(Foo<String>)`,
but the complier will warn you that this is not a safe cast.

An example of cast that works
```java
List<Object> objectList = List.of("foo", "bar");
List<String> stringList = (List<String>) (List<?>) objectList; // warning, this is dangerous
                                                               // but it works in practice
```

An example of cast that doesn't work
```java
List<String> stringList = new ArrayList<>();
stringList.add("foo");
List<Object> objectList = (List<Object>) (List<?>) stringList; // dangerous, don't work !
objectList.add(4321);
System.out.println(stringList.get(1));               // fail at runtime
```

Hopefully, those casts are rare in practice because casts in general are rare
mostly because if you have a cast it means that you have lost the type at some point.


## Array of parameterized type
Arrays are covariant in Java, it means that you can always consider an
array of a subtype as an array of supertype,
By example, see an array of String as an array of Object
```java
String[] stringArray = new String[] { "hello", "array" };
Object[] objectArray = stringArray;
```

This is the same reference seen as a String[] or Object[]
```java
System.out.println(objectArray == stringArray);
```

At compile time, objectArray is a Object[] but at runtime it's a String[]
```java
System.out.println(objectArray[0]);
```

In fact, this is a nonsense, because you can store an object inside a Object[]
but not inside a String[], so the Java compiler let you write code that will
fail at runtime

The code below compiles because objectArray[0] is an Object, and 3 can be
seen as an Integer. But at runtime, objectArray is a String[] and
you can not assign an Integer to a String, so you get an ArrayStoreException
```java
objectArray[0] = 3;   // ArrayStoreException
```

In practice, this code is occurs rarely, if you consider a String[] as a Object[],
usually, it's for getting the value out of the array not to change the value of
the array. Anyway, it's unsafe.


### varargs









