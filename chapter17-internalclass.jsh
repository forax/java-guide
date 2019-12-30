// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// We have seen in the previous chapter that we can use classes to
// hide information

// There 4 kinds of internal classses
// - internal classes of classes, static or not
// - internal classes of methods, anonymous or not
//

// # Static internal class

public class Utils {
  public record Result(int index, int sum) {}
  
  public static Result findFirstSumGreaterThan100(int[] array) {
    var sum = 0;
    for(var index = 0; index < array.length; index++) {
      sum = sum + array[index];
      if (sum >= 100) {
        return new Result(index, sum);
      }
    }
    return new Result(-1, sum);
  }
}
var array = IntStream.range(0, 20).toArray();
System.out.println(Utils.findFirstSumGreaterThan100(array));


// # Inner class (non static)


// # Anonymous class of method




// ## 


// # Named class of method





