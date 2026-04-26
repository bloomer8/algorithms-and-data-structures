
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {

    public static void writeHeader(String path) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("Size,Iterations,Time(ns)\n");
        }
    }

    public static void append(String path, FloydWarshall.Result r) throws IOException {
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write(r.size + "," + r.iterations + "," + r.time + "\n");
        }
    }
}