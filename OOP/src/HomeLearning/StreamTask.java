package HomeLearning;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTask {

    public static void main(String[] args) {

       List<String> names = Arrays.asList("aaaa" , "abccba" , "11211" , "honesty");
        Stream<String> strs = names.stream()
                .filter((p)->p.equals(new StringBuilder(p).reverse().toString()))
                .map((p)->p.toUpperCase());

        strs.forEach((n)-> System.out.println(n));


        int []numbers = {2,4,6,8,10};
        int sum1 = IntStream.of(numbers).sum();
        int sum = IntStream.of(numbers).reduce(0 , Integer::sum);

        System.out.println(sum);
        OptionalDouble average = IntStream.of(numbers).average();

        System.out.println("Average: " + (average.isPresent() ? average.getAsDouble() : "n/a"));


        List<Integer> printIntegers = IntStream.range(1,101).boxed().collect(Collectors.toList());

        System.out.println(printIntegers);

        List<Integer> evenIntegers = IntStream.rangeClosed(1,100)
                .filter((n)->n%2==0)
                .boxed()
                .collect(Collectors.toList());

        System.out.println(evenIntegers);

        IntStream.rangeClosed(1,100)
                .filter((n)->n%2 != 0)
                .map(i->i*i)
                .limit(20)
                .forEach(System.out::println);

    }
}
