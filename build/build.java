import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.writeString;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("preview")
class build {
  static class TextBuilder {
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
    
    String clean(String line) {
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
  
  interface EventHandler {
    default void startDocument(@SuppressWarnings("unused") TextBuilder builder) { /*empty*/ }
    default void endDocument(@SuppressWarnings("unused") TextBuilder builder) { /*empty*/ }
    default void startCode(@SuppressWarnings("unused") TextBuilder builder) { /*empty*/ }
    default void endCode(@SuppressWarnings("unused") TextBuilder builder) { /*empty*/ }
    default void startText(@SuppressWarnings("unused") TextBuilder builder) { /*empty*/ }
    default void endText(@SuppressWarnings("unused") TextBuilder builder) { /*empty*/ }
    void line(TextBuilder builder, LineKind kind, String line);
  }
  
  private static String transformTo(List<String> lines, EventHandler handler) {
    var builder = new TextBuilder();
    handler.startDocument(builder);
    
    var inside = LineKind.BLANK;
    var skipFirst = true;
    for(var line: lines) {
      var kind = LineKind.kind(line);   
      
      //System.out.println(kind + " " + line);
      
      inside = switch(kind) {
        case BLANK -> {
          if (inside == LineKind.CODE) {
            handler.endCode(builder);
          }
          yield (inside == LineKind.TEXT)? LineKind.TEXT: LineKind.BLANK;
        }
        case TEXT -> {
          if (inside == LineKind.CODE) {
            handler.endCode(builder);
          }
          if (inside != LineKind.TEXT) {
            handler.startText(builder);
          }
          yield kind;
        }
        case CODE -> {
          if (inside == LineKind.TEXT) {
            handler.endText(builder);
          }
          if (inside != LineKind.CODE) {
            handler.startCode(builder);
          }
          yield kind;
        }
      };
      
      skipFirst = skipFirst && kind == LineKind.TEXT;
      if (!skipFirst) {
        handler.line(builder, kind, kind.clean(line));
      }
    }
    
    if (inside == LineKind.CODE) {
      handler.endCode(builder);
    } else if (inside == LineKind.TEXT) {
      handler.endText(builder);
    }
    handler.endDocument(builder);
    return builder.toString();
  }
  
  private static void writeMarkDown(List<String> lines, Path to) throws IOException {
    writeString(to, transformTo(lines, new EventHandler() {
      @Override
      public void startCode(TextBuilder text) {
        text.append("```java");
      }
      @Override
      public void endCode(TextBuilder text) {
        text.append("```");
      }
      @Override
      public void line(TextBuilder text, LineKind kind, String line) {
        text.append(line);
      }
    }));
  }
  
  /*private static void writeJupyter(List<String> lines, Path to) throws IOException {
    writeString(to, transformTo(lines, new EventHandler() {
      @Override
      public void line(TextBuilder text, LineKind kind, String line) {
        
      }
    }));
  }*/
  
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
  
  private static void generate(List<Path> paths, Path markdownFolder, Path jupyterFolder) throws IOException {
    createDirectories(markdownFolder);
    createDirectories(jupyterFolder);
    
    for(var path: paths) {
      var rawFilename = removeExtension(path.getFileName().toString());

      var lines = readAllLines(path);
      writeMarkDown(lines, markdownFolder.resolve(rawFilename + ".md"));
      //writeJupyter(lines, jupyterFolder.resolve(rawFilename + ".ipynb"));
    }
  }
  
  private static void generateIndex(List<Path> paths, Path destination) {
    var index = 0;
    for(var path: paths) {
      var filename = removeExtension(path.getFileName().toString()) + ".md";
      System.out.println(index + ". [" + shortName(filename) + "](" + destination.getFileName() + "/" + filename + ")");
      index++;
    }
  }
  
  private static List<Path> gatherFiles(Path directory, Predicate<String> filter) throws IOException {
    try(var list = Files.list(directory)) {
      return list.filter(path -> filter.test(path.toString())).sorted(Comparator.comparing(Path::toString)).collect(toList());
    }
  }
  
  public static void main(String[] args) throws IOException {
    var markdownFolder = Path.of("guide");
    var jupyterFolder = Path.of("jupyter");
    
    var files = gatherFiles(Path.of("."), name -> name.endsWith(".jsh"));
    generate(files, markdownFolder, jupyterFolder);
    generateIndex(files, markdownFolder);
  }
}