package simonalgorithm;

import domain.Color;

import java.util.ArrayList;

public interface ISequenceGenerator {
    void initialize();

    void processRound();

    boolean validateSequence(ArrayList<Color> sequence);

    ArrayList<Color> getLastSequence();
}
