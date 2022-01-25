package pea.algorithms.genetic;

import java.util.List;

public class Chromosome {

    List<Integer> path;
    Integer fitnessFunctionValue;

    public Chromosome(List<Integer> path, Integer fitnessFunctionValue) {
        this.path = path;
        this.fitnessFunctionValue = fitnessFunctionValue;
    }

    public Integer getFitnessFunctionValue() {
        return fitnessFunctionValue;
    }

    public void setFitnessFunctionValue(Integer fitnessFunctionValue) {
        this.fitnessFunctionValue = fitnessFunctionValue;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public List<Integer> getPath() {
        return path;
    }
}
