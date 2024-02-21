package HomeLearning;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Goods {
    int id;
    String name;
    float price;
    public Goods(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

 class LambdaStream {

    // create an object of list using ArrayList
    static List<String> places = new ArrayList<>();

    // preparing our data
    public static List getPlaces() {
        // add places and country to the list
        places.add("Nepal, Kathmandu");
        places.add("Nepal, Pokhara");
        places.add("India, Delhi");
        places.add("USA, New York");
        places.add("Africa, Nigeria");
        places.add("Nepal, Tibbet");
        places.add("Nepal, Himalayas");

        return places;
    }
}


public class StreamLearning {
    public static void main(String[] args) {


        List<String> myPlaces = LambdaStream.getPlaces();

        myPlaces.stream()
                .filter((p)->p.startsWith("Nepal"))
                .map((p)->p.toUpperCase())
                .sorted()
                .forEach((p)-> System.out.println(p));





        List<Goods> productsList = new ArrayList<>();
        //Adding Products
        productsList.add(new Goods(1,"HP Laptop",25000f));
        productsList.add(new Goods(2,"Dell Laptop",30000f));
        productsList.add(new Goods(3,"Lenevo Laptop",28000f));
        productsList.add(new Goods(4,"Sony Laptop",28000f));
        productsList.add(new Goods(5,"Apple Laptop",90000f));


        List<Float> productPriceList = productsList.stream()
                .filter(p -> p.price > 30000)
                .map(p->p.price)
                .collect(Collectors.toList());

        System.out.println(productPriceList);



        List<String> productPriceList1 = productsList.stream()
                .filter(p -> p.price > 30000)
                .map(p->p.name)
                .collect(Collectors.toList());

        System.out.println(productPriceList1);


        List<Integer> numbers = Arrays.asList(10,20,30,34,27,78,39);

        Stream<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0);

        evenNumbers.forEach((n)-> System.out.println(n));

        List<String> names = Arrays.asList("John", "Sarah", "Tom", "Jim");

        Stream<Integer> nameLength = names.stream().map(name->name.length());


        nameLength.forEach((n)-> System.out.println(n));

        List<List<Integer>> numbers1 = Arrays.asList(Arrays.asList(1, 2),
                Arrays.asList(3, 4), Arrays.asList(5, 6));
        Stream<Integer> flattenedNumbers = numbers1.stream().flatMap(list ->list.stream());

        flattenedNumbers.forEach((n)-> System.out.println(n));

        List<Integer> numbers2 = Arrays.asList(1,1,1,2,2,3,3,4,5,1,1,1);

        List<Integer> distinctNumbers = numbers2.stream().distinct().toList();

        System.out.println(distinctNumbers);

        List<Integer> numbers3 = Arrays.asList(3, 1, 4, 2, 5);
        List<Integer> sortedNumbers = numbers3.stream().sorted().toList();

        System.out.println(sortedNumbers);

        List<String> names1 = Arrays.asList("John", "Sarah", "Tom", "Jim");
        List<String> capitalizedNames = names1.stream()
                .peek(name -> System.out.println("Processing name: " + name))
                .map(name -> name.toUpperCase()).toList();

        System.out.println(capitalizedNames);

        List<Integer> numbers4 = Arrays.asList(1, 2, 3, 4, 5, 5);
        Set<Integer> uniqueNumbers = numbers4.stream().collect(Collectors.toSet());

        System.out.println(uniqueNumbers);

        List<String> names2 = Arrays.asList("John", "Sarah", "Tom", "Jim");
        Map<String, Integer> nameLengthMap = names2.stream()
                .collect(Collectors.toMap(name -> name, name -> name.length()));
     // The resulting Map will contain the length of each name with the name as the key - {John=4, Sarah=5, Tom=3, Jim=3}

        List<String> names3 = Arrays.asList("John", "Sarah", "Tom", "Jim");
        String concatenatedNames = names3.stream().collect(Collectors.joining(", "));
// The resulting String will contain all the names concatenated with a comma and space - "John, Sarah, Tom, Jim"

        System.out.println(concatenatedNames);


        List<Integer> numbers5 = Arrays.asList(1, 2, 3, 4, 5);
        Double average = numbers5.stream().collect(Collectors.averagingInt(n -> n));
// The resulting average of the numbers in the list will be 3.0

        System.out.println(average);

        List<Integer> numbers6 = Arrays.asList(1, 2, 3, 4, 5);
        IntSummaryStatistics statistics = numbers6.stream().collect(Collectors.summarizingInt(n -> n));
// The resulting statistics object will contain the count, sum, min, max, and average of the numbers in the list

        System.out.println(statistics);

//        Stream<Date> stream = Stream.generate(()->{
//            return new Date();
//        });
//
//        stream.forEach(p-> System.out.println(p));



        List<String> memberNames = new ArrayList<>();
        memberNames.add("Amir Khan");
        memberNames.add("Sohel Rana");
        memberNames.add("Shakib Khan");
        memberNames.add("Rajib");
        memberNames.add("Salman Shah");
        memberNames.add("Ferdous");
        memberNames.add("Riaz");
        memberNames.add("Jashim");
        memberNames.add("Humayun Faridi");
        memberNames.add("Bappa Raj");
        memberNames.add("Misha");
        memberNames.add("Jamboo");

        memberNames.stream()
                .filter((p)->p.startsWith("S"))
                .forEach((s)-> System.out.println(s));

        memberNames.stream()
                .filter((p)->p.startsWith("S"))
                .map(String::toUpperCase)
                .forEach((s)-> System.out.println(s));

        memberNames.stream()
                .filter((p)->p.startsWith("S"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        boolean matchedResult = memberNames.stream()
                .anyMatch((s) -> s.startsWith("J"));
        System.out.println(matchedResult);


        matchedResult = memberNames.stream()
                .allMatch((s) -> s.startsWith("A"));
        System.out.println(matchedResult);

        matchedResult = memberNames.stream()
                .noneMatch((s) -> s.startsWith("X"));
        System.out.println(matchedResult);

        long totMatched = memberNames.stream()
                .filter((a)->a.startsWith("S"))
                .count();

        System.out.println(totMatched);


        Optional<String> reduced = memberNames.stream()
                .reduce((s1,s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);

        String newStr = memberNames.stream()
                .collect(Collectors.joining("#"));

        System.out.println(newStr);


        String actors = memberNames.stream()
                .limit(3)
                .collect(Collectors.joining());

        System.out.println(actors);


        Optional<Integer> canbeempty1 = Optional.of(5);

        System.out.println(canbeempty1.isEmpty());
        System.out.println(canbeempty1.isPresent());
        System.out.println(canbeempty1.get());

        Optional<Integer> canBeEmpty2 = Optional.empty();
        System.out.println(canBeEmpty2.isPresent());
        System.out.println(canBeEmpty2.isEmpty());




        Optional<String> optional1 = Optional.of("hello");
        System.out.println(optional1.isPresent()); // prints "true"
        System.out.println(optional1.get()); // prints "hello"

        // create an optional that does not contain a value
        Optional<String> optional2 = Optional.empty();
        System.out.println(optional2.isPresent()); // prints "false"

        // use the orElse method to provide a default value if the optional is empty
        String value = optional2.orElse("world");
        System.out.println(value); // prints "world"


        // use the map method to perform an operation on the contained value
        Optional<String> optional3 = Optional.of("123");
        Optional<Integer> optional4 = optional3.map(Integer::parseInt);
        System.out.println(optional4.isPresent()); // prints "true"
        System.out.println(optional4.get()); // prints 123

    }
}








