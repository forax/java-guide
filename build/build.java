import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.writeString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("preview")
class build {
  static class Text {
    private final StringBuilder builder = new StringBuilder();
    
    void append(String line) {
      builder.append(line).append("\n");
    }
    
    @Override
    public String toString() {
      return builder.toString();
    }
  }
  
  enum LineKind {
    TEXT,
    BLANK,
    CODE
    ;
    
    String transform(String line) {
      return switch(this) {
      case BLANK, CODE -> line;
      case TEXT -> line.substring(3); 
      };
    }
    
    static LineKind kind(String line) {
      if (line.startsWith("// ")) {
        return TEXT;
      }
      if (line.isBlank()) {
        return BLANK;
      }
      return CODE;
    }
  }
  
  private static String transformToMarkDown(List<String> lines) {
    var text = new Text();
    
    var insideCode = false;
    var skipFirst = true;
    for(var line: lines) {
      var kind = LineKind.kind(line);   
      
      //System.out.println(kind + " " + line);
      
      insideCode = switch(kind) {
        case BLANK, TEXT -> {
          if (insideCode) {
            text.append("```");
          }
          yield false;
        }
        case CODE -> {
          if (!insideCode) {
            text.append("```java");
          }
          yield true;
        }
      };
      
      skipFirst = skipFirst && kind == LineKind.TEXT;
      if (!skipFirst) {
        text.append(kind.transform(line));
      }
    }
    
    if (insideCode) {
      text.append("```");
    }
    return text.toString();
  }
  
  private static void convertToMarkDown(Path from, Path to) throws IOException {
    createDirectories(to.getParent());
    writeString(to, transformToMarkDown(readAllLines(from)));
  }
  
  private static String removeExtension(String filename) {
    var index = filename.lastIndexOf('.');
    if (index == -1) {
      return filename;
    }
    return filename.substring(0, index);
  }
  
  private static String shortName(String filename) {
    var index = filename.indexOf('-');
    if (index == -1) {
      return filename;
    }
    return filename.substring(index + 1);
  }
  
  private static void generateMarkDown(Path directory, Predicate<String> filter, Path destination) throws IOException {
    var index = 0;
    try(var list = Files.list(directory)) {
      for(var from: (Iterable<Path>)list.filter(p -> filter.test(p.toString())).sorted(Comparator.comparing(Path::toString))::iterator) {
        var filename = removeExtension(from.getFileName().toString()) + ".md";
        var to = destination.resolve(filename);
        
        System.out.println(index + ". [" + shortName(filename) + "](" + destination.getFileName() + "/" + filename + ")");
        convertToMarkDown(from, to);
        index++;
      }
    }
  }
  
  public static void main(String[] args) throws IOException {
    generateMarkDown(Path.of("."), name -> name.endsWith(".jsh"), Path.of("guide"));
  }
} 