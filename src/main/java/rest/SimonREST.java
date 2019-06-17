package rest;

import com.google.gson.Gson;
import context.HighscoreMSSQLContext;
import context.UserMSSQLContext;
import domain.GameMode;
import domain.Highscore;
import domain.Player;
import dto.restDTO.AuthenticationDTO;
import dto.restDTO.RegistrationDTO;
import dto.restDTO.RestResultDTO;
import dto.restDTO.SetHighscoreDTO;
import logging.ILogger;
import logging.Logger;
import repository.HighscoreRepository;
import repository.UserRepository;
import javax.ws.rs.*;
import java.util.ArrayList;


@Path("/simongame")
public class SimonREST implements ISimonAuthentication, ISimonHighscoreServer {

    private UserRepository userRepo = new UserRepository(new UserMSSQLContext());
    private HighscoreRepository highScoreRepo = new HighscoreRepository(new HighscoreMSSQLContext());
    private ILogger logger = Logger.getInstance();
    private Gson gson = new Gson();

    @POST
    @Path("loginWithUsername/")
    @Consumes("application/json")
    @Produces("application/json")
    @Override
    public String loginUsername(AuthenticationDTO loginRequest) {
        String result = "Successful login";
        if(loginRequest == null)
        { result = "Unable to login. Did you use a wrong password?"; }
        try {
            Player retrievedPlayer = userRepo.getUserByUsername(loginRequest.getUserName());
            Player p = new Player(0, retrievedPlayer.getPlayerName());
            String password = userRepo.toSHA256(loginRequest.getHashedPassword());
            if (retrievedPlayer.getPlayerId() != 0 && retrievedPlayer.getPlayerPassword().equals(password)){
                p.setPlayerId(retrievedPlayer.getPlayerId());
                result = gson.toJson(p);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }


    @POST
    @Path("loginWithMail/")
    @Consumes("application/json")
    @Produces("application/json")
    @Override
    public String loginMail(AuthenticationDTO loginRequest) {
        String result = "Unable to login. Did you use a wrong password?";
        if(loginRequest == null){
            result = "Invalid login request";
        }
        try {
            Player retrievedPlayer = userRepo.getUserByMail(loginRequest.getUserName());
            Player p = new Player(0, retrievedPlayer.getPlayerName());
            String password = userRepo.toSHA256(loginRequest.getHashedPassword());
            if (retrievedPlayer.getPlayerId() != 0 && retrievedPlayer.getPlayerPassword().equals(password)){
                p.setPlayerId(retrievedPlayer.getPlayerId());
                result = gson.toJson(p);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }


    @POST
    @Path("register/")
    @Consumes("application/json")
    @Produces("application/json")
    @Override
    public String register(RegistrationDTO registrationRequest) {
        RestResultDTO resultDTO = new RestResultDTO(false, "REGISTER");
        Player p = new Player(0, registrationRequest.getUsername(), registrationRequest.getMail(), registrationRequest.getPassword());
        if (userRepo.registerUser(p)) {
            resultDTO.setSuccess(true);
        }
        return gson.toJson(resultDTO);
    }


    @GET
    @Path("highscore/")
    @Consumes("application/json")
    @Produces("application/json")
    @Override
    public String getHighScoreByPlayerId(@QueryParam("playerId") int playerId, @QueryParam("gameMode") String gameMode) {
        String result = "No highscores found";
        GameMode gm = gson.fromJson(gameMode, GameMode.class);
        Highscore highscore = highScoreRepo.getHighscoreByUserId(playerId, gm);
        if (highscore != null) {
            result = gson.toJson(highscore);
        }
        return result;
    }

    @POST
    @Path("sethighscore/")
    @Consumes("application/json")
    @Produces("application/json")
    @Override
    public String setHighScoreByPlayerId(SetHighscoreDTO setHighscoreRequest) {
        RestResultDTO resultDTO = new RestResultDTO(false, "SETHIGHSCORE");
        try {
            Highscore h = highScoreRepo.getHighscoreByUserId(setHighscoreRequest.getPlayerId(), setHighscoreRequest.getGameMode());
            if(h.getDifficulty() <= setHighscoreRequest.getDifficulty()){
                if(h.getRound() < setHighscoreRequest.getTurn()){
                    h = new Highscore(setHighscoreRequest.getDifficulty(), setHighscoreRequest.getTurn(), setHighscoreRequest.getGameMode());
                    if (highScoreRepo.updateHighscoreByUserId(setHighscoreRequest.getPlayerId(), h)){
                        resultDTO.setSuccess(true);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return gson.toJson(resultDTO);
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("highestscore/")
    @Override
    public String getHighestScore(@QueryParam("gameMode") String gameMode) {
        String result = "No highscore found";
        GameMode gm = gson.fromJson(gameMode, GameMode.class);
        Player highscore = highScoreRepo.getHighestScore(gm);
        if (highscore != null) {
            result = gson.toJson(highscore);
        }
        return result;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("allhighscores/")
    @Override
    public String getHighscores(@QueryParam("gameMode") String gameMode) {
        String result = "No highscore found";
        GameMode gm = gson.fromJson(gameMode, GameMode.class);
        ArrayList<Player> highScores = highScoreRepo.getAllHighscoresByFilter(gm);
        if (highScores != null) {
            result = gson.toJson(highScores);
        }
        return result;
    }
}
