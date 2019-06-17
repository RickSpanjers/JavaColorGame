package rest;

import domain.GameMode;
import dto.restDTO.SetHighscoreDTO;

public interface ISimonHighscoreServer {

    String getHighScoreByPlayerId(int playerId, String gameMode);

    String setHighScoreByPlayerId(SetHighscoreDTO setHighscoreRequest);

    String getHighestScore(String gameMode);

    String getHighscores(String gameMode);
}
