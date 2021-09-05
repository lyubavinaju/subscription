package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {


    public static List<String> getSql(final String resourceName) {
        return Arrays.stream(new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                Utils.class.getClassLoader().getResourceAsStream(resourceName))))
                .lines()
                .collect(Collectors.joining("\n")).split(";")).filter(s -> !s.isBlank()).collect(Collectors.toList());
    }



}
