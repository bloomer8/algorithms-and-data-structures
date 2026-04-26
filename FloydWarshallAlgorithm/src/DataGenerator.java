
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {

    static final int INF = FloydWarshall.INF;

    public static void generate(String folder, int datasets) throws IOException {
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();

        Random rand = new Random();

        for (int d = 1; d <= datasets; d++) {

            int size = 100 + rand.nextInt(9901); // 100–10000
            String filename = folder + "/graph_" + size + "_" + d + ".txt";

            try (FileWriter fw = new FileWriter(filename)) {

                fw.write(size + "\n");

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {

                        if (i == j) {
                            fw.write("0 ");
                        } else {
                            // 30% рёбер отсутствуют
                            if (rand.nextDouble() < 0.3) {
                                fw.write("INF ");
                            } else {
                                int weight = rand.nextInt(20) + 1;
                                fw.write(weight + " ");
                            }
                        }
                    }
                    fw.write("\n");
                }
            }
        }
    }
}