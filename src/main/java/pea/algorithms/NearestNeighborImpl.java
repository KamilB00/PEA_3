package pea.algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class NearestNeighborImpl {

    private static List<List<Integer>> allPairs;
    private static List<Integer> distances;

    public static void run(int[][] graph) {

        allPairs = new ArrayList<>();
        distances = new ArrayList<>();
        boolean[] visited = new boolean[graph.length];
        int currentVertex = 0;
        int destinationVertex = 0;
        int min;

        Arrays.fill(visited, false);

        for (int i = 0; i < graph.length - 1; i++) {
            visited[currentVertex] = true;
            min = Integer.MAX_VALUE;
            for (int j = 0; j < graph.length; j++) {
                if (i != j && !visited[j] && graph[currentVertex][j] < min) {
                    min = graph[currentVertex][j];
                    destinationVertex = j;
                }
            }
            allPairs.add(List.of(currentVertex, destinationVertex));
            distances.add(min);
            currentVertex = destinationVertex;
        }

        int lastIndex = allPairs.get(allPairs.size() - 1).get(1);
        distances.add(graph[lastIndex][0]);
    }

    public static List<Integer> getPath() {
        List<Integer> path = allPairs.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream()
                .distinct()
                .collect(Collectors.toList());
        path.add(0);
        return path;
    }

    public static Integer getCost() {
        return distances.stream()
                .reduce(Integer::sum)
                .orElseThrow();
    }
}


