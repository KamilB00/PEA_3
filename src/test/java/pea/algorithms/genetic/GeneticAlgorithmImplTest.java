package pea.algorithms.genetic;


import org.junit.jupiter.api.Test;
import pea.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class GeneticAlgorithmImplTest {
    Graph graph = Graph.getInstance();

    @Test
   public void should_generate_population() {
        List<Integer> initialData = List.of(0,1,2,3,4,5,6,7,8,9,0);
        int [][] tempGraph =
        {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };
        int populationSize = 10;
        graph.setGraph(tempGraph);

        GeneticAlgorithmImpl algorithm = new GeneticAlgorithmImpl(50,0,0);
        List<Chromosome> population = algorithm.generatePopulation(initialData, populationSize);

        population.forEach(chromosome -> System.out.println(chromosome.getPath()));
        System.out.println(population.stream().map(Chromosome::getFitnessFunctionValue).collect(Collectors.toList()));
    }

    @Test
    void should_cross_two_chromosomes() {
        List<Integer> parent1 = new ArrayList<>(List.of(0,1,2,3,4,5,6,7,8,9,0));
        List<Integer> parent2 = new ArrayList<>(List.of(0,1,3,5,7,2,4,6,8,9,0));
        List<Integer> expected1 = List.of(0,1,3,5,7,2,4,6,8,9,0);
        List<Integer> expected2 = List.of(0,1,3,2,4,5,6,7,8,9,0);
        Chromosome ch1 = new Chromosome(parent1,0);
        Chromosome ch2 = new Chromosome(parent2, 0);
        GeneticAlgorithmImpl algorithm = new GeneticAlgorithmImpl(50,0,0);

        List<Chromosome> actual = algorithm.crossoverMethodOX(ch1,ch2);

        assertEquals(expected1,actual.get(0).getPath());
        assertEquals(expected2,actual.get(1).getPath());
    }

}