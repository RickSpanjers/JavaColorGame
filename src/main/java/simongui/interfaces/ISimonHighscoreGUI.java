package simongui.interfaces;

import domain.Highscore;
import domain.Player;
import simongame.ISimonGUI;
import simongame.ISimonGame;
import simongame.SimonGameGUILogic;

import java.util.ArrayList;

public interface ISimonHighscoreGUI {
    void showMessage(final String message);

    void setGUILogic(ISimonGUI guiLogic);

    void setHighScoresForTable(ArrayList<Player> highscoreArrayList);
}
