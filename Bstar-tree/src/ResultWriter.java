import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResultWriter {

    public static void writeResults(String fileName, List<OperationResult> results) throws IOException {

        PrintWriter writer = new PrintWriter(new FileWriter(fileName));
        writer.println("operation_id,operations,time_ns");
        for (int i = 0; i < results.size(); i++) {
            OperationResult result = results.get(i);
            writer.println((i + 1) + "," + result.getOperations() + "," + result.getTimeNs());
        }
        writer.close();
    }
}