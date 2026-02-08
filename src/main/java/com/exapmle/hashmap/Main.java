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





    }
}
