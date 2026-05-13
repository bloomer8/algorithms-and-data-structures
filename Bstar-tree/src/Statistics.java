import java.util.List;

public class Statistics {

    public static void print(String title, List<OperationResult> stats) {
        long totalOperations = 0;
        long totalTime = 0;
        for (OperationResult result : stats) {
            totalOperations += result.getOperations();
            totalTime += result.getTimeNs();
        }
        double avgOperations = (double) totalOperations / stats.size();

        double avgTime = (double) totalTime / stats.size();
        System.out.println("========== " + title + " ==========");
        System.out.println("Количество операций: " + stats.size());
        System.out.println("Среднее число операций: " + avgOperations);
        System.out.println("Среднее время (нс): " + avgTime);
        System.out.println();
    }
}