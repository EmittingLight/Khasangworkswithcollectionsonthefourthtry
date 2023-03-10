import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDuplicateFinder {

    private List<Product> list1;
    private List<Product> list2;

    public void initData() {
        // эмулируем получение данных из базы данных или файловой системы
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setCategory("Category " + (i % 10));
            product.setInternalCode(new byte[]{(byte) i, (byte) (i + 1), (byte) (i + 2)});
            list1.add(product);
        }
        System.out.println(list1.size());

        // добавляем дубликаты второй коллекции из первой коллекции
        Set<Integer> duplicateIndexes = new HashSet<>();
        while (duplicateIndexes.size() < 1000) {
            int index = (int) (Math.random() * 100000);
            duplicateIndexes.add(index);
        }
        for (Integer index : duplicateIndexes) {
            list2.add(list1.get(index));
        }
        for (int i = 0; i < 100000 - 1000; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setCategory("Category " + (i % 10));
            product.setInternalCode(new byte[]{(byte) i, (byte) (i + 1), (byte) (i + 2)});
            list2.add(product);
        }
        System.out.println(list2.size());
    }

    public List<Product> findDuplicates1() {
        List<Product> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < list1.size(); i++) {
            Product product1 = list1.get(i);
            for (int j = 0; j < list2.size(); j++) {
                Product product2 = list2.get(j);
                if (product1.equals(product2)) {
                    result.add(product1);
                    break;
                }
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("затраченное время findDuplicates1(): " + (endTime - startTime) + " ms");

        return result;
    }

    public List<Product> findDuplicates2() {
        List<Product> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        Set<Product> set1 = new HashSet<>(list1);

        for (Product product2 : list2) {
            if (set1.contains(product2)) {
                result.add(product2);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("затраченное время findDuplicates2(): " + (endTime - startTime) + " ms");

        return result;
    }

    public static void main(String[] args) {
        ProductDuplicateFinder finder = new ProductDuplicateFinder();
        finder.initData();

        List<Product> duplicates1 = finder.findDuplicates1();
        System.out.println("Количество дубликатов, найденных с помощью findDuplicates1(): " + duplicates1.size());

        List<Product> duplicates2 = finder.findDuplicates2();
        System.out.println("Количество дубликатов, найденных с помощью findDuplicates2(): " + duplicates2.size());
    }

}
