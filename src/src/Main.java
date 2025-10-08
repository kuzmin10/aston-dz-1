import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();

        System.out.println("Проверка put");
        for (int i = 0; i < 10; i++) {
            System.out.print(map.put(i, String.valueOf(i)) + " ");
        }
        System.out.println();
        System.out.println(Arrays.toString(map.table));
        System.out.println();

        System.out.println("Проверка перезаписи значения");
        for (int i = 0; i < 10; i++) {
            System.out.print(map.put(i, String.valueOf(i+10)) + " ");
        }
        System.out.println();
        System.out.println(Arrays.toString(map.table));
        System.out.println();

        System.out.println("Проверка get");
        for (int i = 0; i < map.table.length; i++) {
            System.out.print(map.get(i) + " ");
        }
        System.out.println();
        System.out.println(Arrays.toString(map.table));
        System.out.println();

        System.out.println("Проверка remove");
        for (int i = 0; i < map.table.length; i++) {
            System.out.print(map.remove(i) + " ");
        }
        System.out.println();
        System.out.println(Arrays.toString(map.table));
        System.out.println();

        System.out.println("Проверка resize");
        for (int i = 0; i < 20; i++) {
            map.put(i, String.valueOf(i));
        }
        System.out.println(Arrays.toString(map.table));
    }
}