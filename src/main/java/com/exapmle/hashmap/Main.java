package main.java.com.exapmle.hashmap;

public class Main {
    public static void main(String[] args) {
        CustomHashMap<Integer, String> myMap = new CustomHashMap<>();
        myMap.put(1, "one");
        myMap.put(2, "two");
        myMap.put(3, "three");

        System.out.println("myMap.size() = " + myMap.size());
        System.out.println(myMap);

        myMap.remove(1);
        System.out.println(myMap);

        String value = myMap.get(2);
        System.out.println("For kye = 2 value = " + value);

        myMap.put(2, "another two");
        System.out.println("For kye = 2 value = " + myMap.get(2));

        System.out.println("Is myMap empty? - " + myMap.isEmpty());
        System.out.println("\n");
        System.out.println("------");

        CustomHashMap<String, String> myMap2 = new CustomHashMap<>();
        System.out.println("Is myMap2 empty? - " + myMap2.isEmpty());

        myMap2.put("one", "one");
        myMap2.put("two", "two");
        myMap2.put("three", "three");
        System.out.println(myMap2);
        System.out.println("myMap2.size() = " + myMap2.size());

        myMap2.remove("one");
        System.out.println(myMap2);

        String value2 = myMap2.get("two");
        System.out.println("For kye = 2 value = " + value2);

        myMap2.put("two", "another two");
        System.out.println("For kye = 2 value = " + myMap2.get("two"));

        System.out.println("Is myMap empty? - " + myMap2.isEmpty());
        System.out.println("\n");
        System.out.println("------");

        CustomHashMap<Float, Integer> myMap3 = new CustomHashMap<>();
        System.out.println("Is myMap2 empty? - " + myMap3.isEmpty());

        myMap3.put(25F, 15);
        myMap3.put(3.5F, 34);
        myMap3.put(8.2F, 45);
        System.out.println(myMap3);
        System.out.println("myMap3.size() = " + myMap3.size());

        myMap2.remove("one");
        System.out.println(myMap3);

        Integer value3 = myMap3.get(8.2F);
        System.out.println("For kye = 8.2F value = " + value3);

        myMap3.put(8.2F, 935);
        System.out.println("For kye = 8.2F value = " + myMap3.get(8.2F));

        System.out.println("Is myMap empty? - " + myMap3.isEmpty());
        System.out.println("\n");
        System.out.println("------");



    }
}
