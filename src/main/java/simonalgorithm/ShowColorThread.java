package simonalgorithm;

import domain.Color;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongui.interfaces.ISimonGameGUI;
import java.util.ArrayList;

public class ShowColorThread implements Runnable{

    private ISimonGameGUI gameGUI;
    private ArrayList<Color> sequence;
    private int difficulty;
    private boolean lost;
    private int time;
    private ILogger logger = Logger.getInstance();

    public ShowColorThread(ISimonGameGUI gameGUI, ArrayList<Color> sequence, int difficulty, boolean lost, int time){
        this.gameGUI = gameGUI;
        this.sequence = sequence;
        this.difficulty = difficulty;
        this.lost = lost;
        this.time = time;
    }

    @Override
    public void run() {
        int restBetweenTransition = 0;
        try {
            showNextRound();
            Thread.sleep(time);
            switch (difficulty) {
                case 1:
                    restBetweenTransition = 2000;
                    break;
                case 2:
                   restBetweenTransition = 1000;
                    break;
                case 3:
                   restBetweenTransition = 800;
                    break;
                default:
                    break;
            }
            for(Color c: sequence) {
                gameGUI.showSquareToPress(c, 700);
                System.out.println("Shown color: " + c.getColorCode());
                Thread.sleep(restBetweenTransition);
            }
            if(!lost){
                gameGUI.allowPlayerToEnterSequence(true);
            }
        }
        catch (Exception e) {
            logger.log(e.getMessage(), LogLevel.ERROR);
        }
    }

    private void showNextRound(){
        gameGUI.showNextRoundAnimation();
    }
}
