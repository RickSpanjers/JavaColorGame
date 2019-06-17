package simonalgorithm;

import domain.Color;
import domain.GameMode;
import simongame.ISimonGame;
import simongame.SimonGameLogic;
import domain.SimonColor;
import java.util.ArrayList;
import java.util.Random;

public class SequenceGenerator implements ISequenceGenerator {

    private int round = 0;
    private ISimonGame game;
    private Random random;
    private static int sequenceLengthClassic;
    private static int increaseLength = 4;
    private ArrayList<Color> lastSequence;
    private ArrayList<Color> listOfChoices;

    public SequenceGenerator(ISimonGame game) {
        this.game = game;
    }

    @Override
    public void initialize() {
        random = new Random();
        round = 0;
        sequenceLengthClassic = 4;
        increaseLength = 0;

        lastSequence = new ArrayList<>();
        listOfChoices = new ArrayList<>();
        listOfChoices.add(new Color(SimonColor.RED, "#8c1607", "#ff3232"));
        listOfChoices.add(new Color(SimonColor.BLUE, "#081189", "#4128ff"));
        listOfChoices.add(new Color(SimonColor.GREEN, "#275603", "#2bba13"));
        listOfChoices.add(new Color(SimonColor.YELLOW, "#bf9d04", "#ffe054"));
    }

    @Override
    public void processRound() {
        if (round != 13) {
            round++;
            lastSequence = createSequenceOfColors();
        } else {
            game.setGameVictor();
        }
    }

    private ArrayList<Color> createSequenceOfColors() {
        ArrayList<Color> sequenceToSend = new ArrayList<>();

        if(game.getCurrentGame().getGameMode() == GameMode.CLASSIC){
            int count = createSequenceLength();
            for (int a = 1; a <= count; a++) {
                sequenceToSend.add(listOfChoices.get(random.nextInt(listOfChoices.size())));
            }
        }
        else if(game.getCurrentGame().getGameMode() == GameMode.LENGTHBATTLE){
            sequenceToSend = lastSequence;
            Color colorToAdd = listOfChoices.get(random.nextInt(listOfChoices.size()));
            if(round != 1){
                Color lastColor = lastSequence.get(lastSequence.size()-1);
                while(colorToAdd == lastColor){
                    colorToAdd = listOfChoices.get(random.nextInt(listOfChoices.size()));
                }
            }
            sequenceToSend.add(colorToAdd);
        }
        return sequenceToSend;
    }


    private int createSequenceLength(){
        if(round>2){
            if(increaseLength == 2){
                sequenceLengthClassic++;
                increaseLength=0;
            }
            increaseLength++;
        }
        return sequenceLengthClassic;
    }

    @Override
    public boolean validateSequence(ArrayList<Color> sequence) {
        boolean result = false;
        for (int i = 0; i < lastSequence.size(); i++) {
            if (lastSequence.get(i).getColorCode() == sequence.get(i).getColorCode()) {
                result = true;
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Color> getLastSequence() {
        return lastSequence;
    }

}
