package pea.algorithms.genetic;

import pea.algorithms.NearestNeighborImpl;
import pea.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pea.menu.Menu.timeInSeconds;

public class GeneticAlgorithmImpl {
    private int populationSize;
    private final double mutationRate;
    private final double crossoverRate;
    private final int[][] graph = Graph.getInstance().getGraph();
    private List<Chromosome> population = generatePopulation(NearestNeighborImpl.getPath(), populationSize);


    public GeneticAlgorithmImpl(int populationSize, double mutationRate, double crossoverRate) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
    }

    public void run() {
        long finish = System.currentTimeMillis() + timeInSeconds * 1000L;
        population = generatePopulation(NearestNeighborImpl.getPath(), populationSize);
        rankingSelection();
        do {
            crossover();
            mutatePopulation();
            rankingSelection();
            System.out.println(population.get(0).getFitnessFunctionValue());
        } while (finish - System.currentTimeMillis() > 0);

    }

    List<Chromosome> generatePopulation(List<Integer> initialPath, Integer populationSize) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize - 1; i++) {
            List<Integer> shuffledPath = shufflePath(initialPath);
            int value = getFitnessFunctionValue(shuffledPath);
            population.add(new Chromosome(shuffledPath, value));
        }
        population.add(new Chromosome(initialPath, getFitnessFunctionValue(initialPath)));
        return population;
    }

    int getFitnessFunctionValue(List<Integer> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            cost += graph[path.get(i)][path.get(i + 1)];
        }
        return cost;
    }

    void mutatePopulation() {
        int number = getNumberOfChromosomesToMutation();
        while (number > 0) {
            int index = getRandomIntegerInRange(0, population.size()-1);
            Chromosome ch = population.get(index);
            List<Integer> newPath = swapPath(ch.getPath());
            ch.setPath(newPath);
            ch.setFitnessFunctionValue(getFitnessFunctionValue(newPath));
            number--;
        }
    }

    void crossover() {
        List<Chromosome> children = new ArrayList<>();
        int number = getNumberOfChromosomesToCrossover();

        if (number % 2 != 0) {
            number--;
        }
        while (number > 0) {
            int index1 = getRandomIntegerInRange(0, population.size()-1);
            int index2 = getRandomIntegerInRange(0, population.size()-1);
            children.addAll(crossoverMethodOX(population.get(index1), population.get(index2)));
            number -= 2;
        }
        population.addAll(children);
    }

    void rankingSelection() {
        population = population.stream()
                .sorted(Comparator.comparing(Chromosome::getFitnessFunctionValue))
                .collect(Collectors.toList());
        if (population.size() > populationSize) {
            population = population.subList(0, populationSize);
        }
    }

    List<Chromosome> crossoverMethodOX(Chromosome parent1, Chromosome parent2) {
        int a;
        int b;
        List<Integer> parent1Path = parent1.getPath();
        List<Integer> parent2Path = parent2.getPath();
        List<Integer> matchingSection1;
        List<Integer> matchingSection2;
        List<Integer> child1 = new ArrayList<>();
        List<Integer> child2 = new ArrayList<>();

        do {
            a = getRandomIntegerInRange(1, parent1.getPath().size() - 2);
            b = getRandomIntegerInRange(1, parent1.getPath().size() - 2);
        } while (a == b);

        if (a < b) {
            matchingSection1 = parent1Path.subList(a, b);
            matchingSection2 = parent2Path.subList(a, b);
        } else {
            matchingSection1 = parent1Path.subList(b, a);
            matchingSection2 = parent2Path.subList(b, a);
        }

        for (int i = 0; i < parent1Path.size(); i++) {
            if (!matchingSection2.contains(parent1Path.get(i))) {
                child1.add(parent1Path.get(i));
            }
            if (!matchingSection1.contains(parent2Path.get(i))) {
                child2.add(parent2Path.get(i));
            }
        }

        if (a < b) {
            child2.addAll(a, matchingSection1);
            child1.addAll(a, matchingSection2);
        } else {
            child2.addAll(b, matchingSection1);
            child1.addAll(b, matchingSection2);
        }
        Chromosome chromosome1 = new Chromosome(child1, getFitnessFunctionValue(child1));
        Chromosome chromosome2 = new Chromosome(child2, getFitnessFunctionValue(child2));
        return new ArrayList<>(List.of(chromosome1, chromosome2));
    }

    private Integer getNumberOfChromosomesToMutation() {
        double number = mutationRate * (double) populationSize;
        return (int) number;
    }

    private Integer getNumberOfChromosomesToCrossover() {
        double number = crossoverRate * (double) populationSize;
        return (int) number;
    }

    private List<Integer> shufflePath(List<Integer> initialPath) {
        List<Integer> temp = new ArrayList<>(initialPath);
        for (int j = 0; j < initialPath.size(); j++) {
            List<Integer> otherList = swapPath(temp);
            temp = swapPath(otherList);
        }
        return temp;
    }

    private List<Integer> swapPath(List<Integer> path) {
        int a;
        int b;
        do {
            a = getRandomIntegerInRange(1, path.size() - 2);
            b = getRandomIntegerInRange(1, path.size() - 2);
        } while (a == b);

        int val = path.get(a);
        path.set(a, path.get(b));
        path.set(b, val);

        return path;
    }

    private Integer getRandomIntegerInRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

}
