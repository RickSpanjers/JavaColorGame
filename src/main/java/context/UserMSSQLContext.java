package context;

import domain.GameMode;
import domain.Player;
import helper.ConnectionHelper;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import repository.IUser;
import java.security.MessageDigest;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserMSSQLContext implements IUser {

    private ConnectionHelper helper = new ConnectionHelper();
    private ILogger logger = Logger.getInstance();
    private String usernameConstant = "username";
    private String mailConstant = "mail";
    private String passwordConstant = "password";


    public Boolean registerUser(Player u) {
        Boolean result = false;
        List<String> errorList = new ArrayList<>();
        String sql = "INSERT INTO BigIdea.[User] (Username, Mail, Password) VALUES (?, ?, ?)";
        if (!checkIfUsernameExists(u) && !checkIfMailExists(u)) {
            if (validateUsername(u.getPlayerName(), errorList) && isValid(u.getPlayerMail()) && validatePassword(u.getPlayerPassword(), errorList)) {
                try {
                    Connection conn = helper.dbConnect();
                    PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    {
                        pstmt.setString(1, u.getPlayerName());
                        pstmt.setString(2, u.getPlayerMail());
                        pstmt.setString(3, toSHA256(u.getPlayerPassword()));
                        pstmt.executeUpdate();

                        ResultSet rs = pstmt.getGeneratedKeys();
                        if (rs.next()) {
                            initializeHighscoreNewUser(rs.getInt(1), GameMode.CLASSIC);
                            initializeHighscoreNewUser(rs.getInt(1), GameMode.LENGTHBATTLE);
                            initializeHighscoreNewUser(rs.getInt(1), GameMode.TIMEBATTLE);
                        }
                        result = true;
                    }
                } catch (Exception e) {
                    logger.log(e);
                }

            }
        }
        for (String error : errorList) {
            logger.log(error, LogLevel.INFORMATION);
        }
        return result;
    }

    private void initializeHighscoreNewUser(int userId, GameMode gameMode) {
        String sql = "INSERT INTO BigIdea.[Highscore] (UserId, Difficulty, Round, HighscoreAchieved, GameMode) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, LocalDateTime.now().toString());
                pstmt.setString(5, gameMode.toString());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(e);
        }
    }


    public Boolean loginUser(String username, String mail, String password) {
        Boolean result = false;
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User] WHERE BigIdea.[User].username = ?");
            myStmt.setString(1, username);
            ResultSet myResults = myStmt.executeQuery();

            while (myResults.next()) {
                String uname = myResults.getString(usernameConstant);
                String upassword = myResults.getString(passwordConstant);

                if (mail == null) {
                    if (password.equals(upassword)) {
                        result = true;
                    }
                } else {
                    if (username.equals(uname) && password.equals(upassword)) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    public Player getUserById(int id) {
        Player result = new Player(0, null, null, null);
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User] WHERE BigIdea.[User].id = ?");
            myStmt.setInt(1, id);
            ResultSet myResults = myStmt.executeQuery();

            while (myResults.next()) {
                String username = myResults.getString(usernameConstant);
                String mail = myResults.getString(mailConstant);
                String password = myResults.getString(passwordConstant);
                result = new Player(id, username, mail, password);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    public Player getUserByUsername(String name) {
        Player result = new Player(0, null, null, null);

        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User] WHERE BigIdea.[User].username = ?");
            myStmt.setString(1, name);
            ResultSet myResults = myStmt.executeQuery();
            while (myResults.next()) {
                result = doUserResults(myResults);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    public Player getUserByMail(String mail) {
        Player result = new Player(0, null, null, null);

        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User] WHERE BigIdea.[User].mail = ?");
            myStmt.setString(1, mail);
            ResultSet myResults = myStmt.executeQuery();
            while (myResults.next()) {
                result = doUserResults(myResults);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    private Player doUserResults(ResultSet myResults) throws SQLException{
        Player result;
        String username = myResults.getString(usernameConstant);
        String mail = myResults.getString(mailConstant);
        String password = myResults.getString(passwordConstant);
        int id = myResults.getInt("id");
        result = new Player(id, username, mail, password);
        return result;
    }


    public Boolean checkIfUsernameExists(Player p) {
        boolean result = false;
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User] WHERE BigIdea.[User].username =?");
            myStmt.setString(1, p.getPlayerName());
            ResultSet myResults = myStmt.executeQuery();
            if (myResults.next()) {
                result = true;
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    public Boolean checkIfMailExists(Player p) {
        boolean result = false;
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User] WHERE BigIdea.[User].mail =?");
            myStmt.setString(1, p.getPlayerMail());
            ResultSet myResults = myStmt.executeQuery();

            if (myResults.next()) {
                result = true;
            }
        } catch (SQLException e) {
            logger.log(e);
        }
        return result;
    }

    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[User]");
            ResultSet myResults = myStmt.executeQuery();

            while (myResults.next()) {
                String username = myResults.getString("username");
                String mail = myResults.getString("mail");
                String password = myResults.getString("password");
                int id = myResults.getInt("id");
                Player p = new Player(id, username, mail, password);
                listOfPlayers.add(p);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return listOfPlayers;
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

    public String toSHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public Boolean validateUsername(String username, List<String> errorList) {
        boolean result = true;
        if (username.length() < 8) {
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
            errorList.add("Password length must have at least 8 characters");
            result = false;
        } else if (!specialCharPatten.matcher(password).find()) {
            errorList.add("Password must have at least one special character");
            result = false;
        } else if (!upperCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one uppercase character");
            result = false;
        } else if (!lowerCasePatten.matcher(password).find()) {
            errorList.add("Password must have at least one lowercase character");
            result = false;
        } else if (!digitCasePatten.matcher(password).find()) {
            errorList.add("Password must have atleast one digit character");
            result = false;
        }
        return result;
    }
}
