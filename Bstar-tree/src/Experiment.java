import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiment {

    public static void main(String[] args) throws IOException {

        Random random = new Random();

        int[] data = new int[10000];

        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(100000);
        }

        BStarTree tree = new BStarTree(3);

        List<OperationResult> insertResults = new ArrayList<>();

        List<OperationResult> searchResults = new ArrayList<>();

        List<OperationResult> deleteResults = new ArrayList<>();

        for (int value : data) {
            long start = System.nanoTime();
            tree.insert(value);
            long end = System.nanoTime();
            insertResults.add(new OperationResult(tree.getOperations(), end - start));
        }

        for (int i = 0; i < 100; i++) {
            int value = data[random.nextInt(data.length)];
            long start = System.nanoTime();
            tree.search(value);
            long end = System.nanoTime();
            searchResults.add(new OperationResult(tree.getOperations(), end - start));
        }

        for (int i = 0; i < 1000; i++) {
            int value = data[random.nextInt(data.length)];
            long start = System.nanoTime();
            tree.remove(value);
            long end = System.nanoTime();
            deleteResults.add(new OperationResult(tree.getOperations(), end - start));
        }

        Statistics.print("INSERT", insertResults);

        Statistics.print("SEARCH", searchResults);

        Statistics.print("DELETE", deleteResults);
        ResultWriter.writeResults("insert_results.csv", insertResults);

        ResultWriter.writeResults("search_results.csv", searchResults);

        ResultWriter.writeResults("delete_results.csv", deleteResults);

        System.out.println("CSV files generated successfully.");
    }
}