package context;

import domain.Player;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import repository.IUser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserTestContext implements IUser {

    private ArrayList<Player> listofUsers = new ArrayList<Player>();
    private String passwordConstant = "Test123";
    private ILogger logger = Logger.getInstance();

    public UserTestContext(){
        listofUsers.add(new Player(1, "User01", "user01@outlook.com", passwordConstant));
        listofUsers.add(new Player(2, "User02", "user02@outlook.com", passwordConstant));
        listofUsers.add(new Player(3, "user03", "user03@outlook.com", passwordConstant));
        listofUsers.add(new Player(4, "user04", "user04@outlook.com", passwordConstant));
    }

    private boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }


    public Boolean registerUser(Player u) {
        boolean result = false;
        List<String> errorList = new ArrayList<>();
        if(isValid(u.getPlayerMail())){
            if(!checkIfUsernameExists(u)){
                if(!checkIfMailExists(u)){
                    if(validateUsername(u.getPlayerName(), errorList)){
                        if(validatePassword(u.getPlayerPassword(), errorList)){
                            listofUsers.add(u);
                            result = true;
                        }
                    }
                }
            }
        }
        for(String error : errorList){
            logger.log(error, LogLevel.ERROR);
        }

        return result;
    }

    public Boolean loginUser(String username, String mail, String password) {
        boolean result = false;
        for(Player p : listofUsers){
            if(mail == null){
                if(username.equals(p.getPlayerName()) && password.equals(p.getPlayerPassword())){
                    result = true;
                }
            }
            else{
                if(mail.equals(p.getPlayerMail()) && password.equals(p.getPlayerPassword())){
                    result = true;
                }
            }

        }
        return result;
    }

    public Player getUserById(int id) {
        Player p = null;
        for(Player u: listofUsers){
            if(u.getPlayerId() == id){
               p = u;
            }
        }
        return p;
    }

    public Player getUserByUsername(String name) {
        Player player = null;
        for(Player p : listofUsers){
            if(p.getPlayerName().equals(name)){
                player = p;
            }
        }
        return player;
    }

    public Player getUserByMail(String mail) {
        Player player = null;
        for(Player p : listofUsers){
            if(p.getPlayerMail().equals(mail)){
                player = p;
            }
        }
        return player;
    }

    public Boolean checkIfUsernameExists(Player p) {
        boolean result = false;
        for(Player player : listofUsers){
            if(player.getPlayerName().equals(p.getPlayerName())){
                result = true;
            }
        }
        return result;
    }

    public Boolean checkIfMailExists(Player p) {
        boolean result = false;
        for(Player player : listofUsers){
            if(player.getPlayerMail().equals(p.getPlayerMail())){
                result = true;
            }
        }
        return result;
    }

    public ArrayList<Player> getAllPlayers() {
        return listofUsers;
    }

    @Override
    public String toSHA256(String base) {
        return null;
    }


    @Override
    public Boolean validateUsername(String username, List<String> errorList) {
        boolean result = true;
        if(username.length() < 8){
            result = false;
            errorList.add("Username length must be at least 8 letters long!");
        }
        return result;
    }

    @Override
    public Boolean validatePassword(String password, List<String> errorList) {
        boolean result = true;

        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern upperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (password.length() < 8) {
            errorList.add("Password length must have at least 8 characters!");
            result=false;
        }
        if (!specialCharPatten.matcher(password).find()) {
            errorList.add("Password must have at least one special character!");
            result=false;
        }
        if (!upperCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one uppercase character!");
            result=false;
        }
        if (!lowerCasePatten.matcher(password).find()) {
            errorList.add("Password must have at least one lowercase character!");
            result=false;
        }
        if (!digitCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one digit character!");
            result=false;
        }

        return result;
    }
}
