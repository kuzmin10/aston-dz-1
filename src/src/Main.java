import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(0, "zero");
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        System.out.println(map.get(0));
        System.out.println(map.remove(1));
        System.out.println(Arrays.toString(map.table));
    }
}