import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamClass {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        Stream<String> namesStream = names.stream();

        namesStream.forEach(
                (s)-> System.out.println(s)
        );

        names.stream()
                .forEach(
                        (s)-> System.out.println(s)
                );

        //Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);

        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        evenNumbers.forEach(
                (n)-> System.out.println(n)
        );

        System.out.println(evenNumbers);


        List<String> upperCaseNames = names.stream()
                .map(s->s.toUpperCase())
                .collect(Collectors.toList());

        System.out.println(upperCaseNames);


        List<Integer> reverseSortedNumbers = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(reverseSortedNumbers);



        String joinedNames = names.stream()
                .collect(Collectors.joining(", "));

        System.out.println(joinedNames);


        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        System.out.println(sum);

        Optional<Integer> max = numbers.stream()
                .reduce(Integer::max);

        System.out.println(max.get());

        List<Integer> parallelSum = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println(parallelSum);







    }
}
