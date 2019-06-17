package rest;

import domain.GameMode;
import domain.Highscore;
import domain.Player;
import dto.restDTO.AuthenticationDTO;
import dto.restDTO.RegistrationDTO;
import dto.restDTO.RestResultDTO;
import dto.restDTO.SetHighscoreDTO;


public class SimonRESTClient extends BaseRESTClient implements IRESTClient {

    public SimonRESTClient(){

    }

    @Override
    public Player login(String username, String password) {
        AuthenticationDTO loginRequest = new AuthenticationDTO(username, password);
        String query = "/loginWithUsername";
        if(username.contains("@")){
            query = "/loginWithMail";
        }
        Player result =  executeQueryPost(loginRequest, query, Player.class);
        return result;
    }

    @Override
    public boolean register(String username, String mail, String password){
        RegistrationDTO registrationRequest = new RegistrationDTO(username, mail, password);
        boolean result = false;
        String query = "/register";
        RestResultDTO resultDTO =  executeQueryPost(registrationRequest, query, RestResultDTO.class);
        if(resultDTO.getSuccess()){
            result = true;
        }
        return result;
    }

    @Override
    public Highscore getHighscore(int playerId){
        Highscore h = null;
        String query = "highscore?playerId="+playerId;
        h =  executeQueryGet(query, Highscore.class);
        return h;
    }

    @Override
    public boolean setHighscore(int playerId, int difficulty, int turn, GameMode mode){
        Boolean result = false;
        SetHighscoreDTO setHighscoreRequest = new SetHighscoreDTO(playerId, turn, difficulty, mode);
        String query = "sethighscore";
        RestResultDTO resultDTO =  executeQueryPost(setHighscoreRequest, query, RestResultDTO.class);
        if(resultDTO.getSuccess()){
            result = true;
        }
        return result;
    }

    @Override
    public Player getHighestScore(GameMode gameMode){
        Player finalResult = null;
        String query = "highestscore?gameMode="+gameMode;
        finalResult = executeQueryGet(query, Player.class);
        return finalResult;
    }

    @Override
    public String getAllHighscores(GameMode filter){
        String result;
        String query = "allhighscores?gameMode="+filter;
        result = executeQueryGet(query, String.class);
        return result;
    }

}
