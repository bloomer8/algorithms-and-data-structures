
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

    public static int[][] readMatrix(String path) throws IOException {
        try (Scanner sc = new Scanner(new File(path))) {

            int V = sc.nextInt();
            int[][] matrix = new int[V][V];

            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    String val = sc.next();

                    if (val.equals("INF"))
                        matrix[i][j] = FloydWarshall.INF;
                    else
                        matrix[i][j] = Integer.parseInt(val);
                }
            }

            return matrix;
        }
    }
}