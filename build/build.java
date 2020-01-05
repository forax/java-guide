import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.writeString;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
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
    default void start(TextBuilder builder, LineKind kind) {
      if (kind == LineKind.CODE) {
        startCode(builder);
      } else {
        startText(builder);
      }
    }
    default void end(TextBuilder builder, LineKind kind) {
      if (kind == LineKind.CODE) {
        endCode(builder);
      } else {
        endText(builder);
      }
    }
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
            handler.end(builder, LineKind.CODE);
          } else if (inside == LineKind.TEXT) {
            handler.end(builder, LineKind.TEXT);
          }
          yield kind;
        }
        case TEXT -> {
          if (inside == LineKind.CODE) {
            handler.end(builder, LineKind.CODE);
          }
          if (inside != LineKind.TEXT) {
            handler.start(builder, LineKind.TEXT);
          }
          yield kind;
        }
        case CODE -> {
          if (inside == LineKind.TEXT) {
            handler.end(builder, LineKind.TEXT);
          }
          if (inside != LineKind.CODE) {
            handler.start(builder, LineKind.CODE);
          }
          yield kind;
        }
      };
      
      skipFirst = skipFirst && kind == LineKind.TEXT;
      if (!skipFirst) {
        handler.line(builder, kind, kind.clean(line));
      }
    }
    
    if (inside != LineKind.BLANK) {
      handler.end(builder, inside);
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
  
  private static void writeJupyter(List<String> lines, Path to) throws IOException {
    writeString(to, transformTo(lines, new EventHandler() {
      private final StringJoiner cells = new StringJoiner(",\n", "[", "]");
      private StringJoiner content;
      
      @Override
      public void start(TextBuilder builder, LineKind kind) {
        content = new StringJoiner(", ", "[", "]");
      }
      
      @Override
      public void endCode(TextBuilder builder) {
        cells.add(String.format(
            """
            {
              "cell_type": "code",
              "execution_count": null,
              "metadata": {},
              "outputs": [],
              "source": %s
            }
            """,
            content.toString()));
        content = null;
      }
      
      @Override
      public void endText(TextBuilder builder) {
        cells.add(String.format(
            """
            {
              "cell_type": "markdown",
              "metadata": {},
              "source": %s
            }
            """,
            content.toString()));
        content = null;
      }
      
      @Override
      public void line(TextBuilder text, LineKind kind, String line) {
        if (content == null) {  // skip lines at the end of the code
          return;
        }
        
        content.add(switch(kind) {
          case BLANK -> "\"\\n\"";
          case CODE, TEXT -> "\"" + line.replace("\"", "\\\"") + "\\n\"";
        });
      }
      
      @Override
      public void endDocument(TextBuilder builder) {
        builder.append(String.format(
          """
          {
            "cells": %s,
            "metadata": {
              "kernelspec": {
                "display_name": "Java",
                "language": "java",
                "name": "java"
              },
              "language_info": {
                "codemirror_mode": "java",
                "file_extension": ".java",
                "mimetype": "text/x-java-source",
                "name": "Java",
                "pygments_lexer": "java",
                "version": "15"
              }
            },
            "nbformat": 4,
            "nbformat_minor": 2
          }    
          """, cells.toString()));
      }
    }));
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
  
  private static void generate(List<Path> paths, Path markdownFolder, Path jupyterFolder) throws IOException {
    createDirectories(markdownFolder);
    createDirectories(jupyterFolder);
    
    for(var path: paths) {
      var rawFilename = removeExtension(path.getFileName().toString());

      var lines = readAllLines(path);
      writeMarkDown(lines, markdownFolder.resolve(rawFilename + ".md"));
      writeJupyter(lines, jupyterFolder.resolve(rawFilename + ".ipynb"));
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