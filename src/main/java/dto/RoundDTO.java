package dto;

import domain.Color;
import java.util.ArrayList;

public class RoundDTO {

    private ArrayList<Color> lastSequence;
    private int difficulty;
    private boolean lost = false;

    public RoundDTO(ArrayList<Color> lastSequence, int difficulty){
        this.lastSequence = lastSequence;
        this.difficulty = difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public ArrayList<Color> getLastSequence() {
        return lastSequence;
    }

    public void setLastSequence(ArrayList<Color> lastSequence) {
        this.lastSequence = lastSequence;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public boolean getLost() {
        return lost;
    }
}
