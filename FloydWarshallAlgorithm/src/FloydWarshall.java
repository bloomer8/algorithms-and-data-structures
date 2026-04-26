
public class FloydWarshall {

    static final int INF = Integer.MAX_VALUE / 2;

    public Result run(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        long iterations = 0;

        for (int i = 0; i < V; i++)
            System.arraycopy(graph[i], 0, dist[i], 0, V);

        long start = System.nanoTime();

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    iterations++;

                    if (dist[i][k] != INF && dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {

                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        long end = System.nanoTime();

        return new Result(V, iterations, (end - start));
    }

    static class Result {
        int size;
        long iterations;
        long time;

        Result(int size, long iterations, long time) {
            this.size = size;
            this.iterations = iterations;
            this.time = time;
        }
    }
}