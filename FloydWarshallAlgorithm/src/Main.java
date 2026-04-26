
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {

        String folder = "data";
        String csv = folder + "/results.csv";

        DataGenerator.generate(folder, 50);

        CsvWriter.writeHeader(csv);

        File dir = new File(folder);

        FloydWarshall fw = new FloydWarshall();

        for (File file : dir.listFiles()) {

            if (!file.getName().endsWith(".txt")) continue;

            int[][] graph = FileUtils.readMatrix(file.getPath());

            FloydWarshall.Result result = fw.run(graph);

            CsvWriter.append(csv, result);

            System.out.println("Processed: " + file.getName());
        }

        System.out.println("Готово. Результаты в " + csv);
    }
}