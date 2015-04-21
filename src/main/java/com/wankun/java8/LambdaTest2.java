package com.wankun.java8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LambdaTest2 {
	 public static List<String> parse(Path path) throws Exception{
         return Files.lines(path)
                 .parallel()
                 .flatMap(line -> Arrays.asList(line.split("\\b")).stream())
                 .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                 .entrySet().stream()
                 .sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed())
                 .limit(20)
                 .map(Map.Entry::getKey)
                 .collect(Collectors.toList());
    }

    public static void main(String... args) throws Exception{
        System.out.println(parse(Paths.get(args[0])));
    }
}
