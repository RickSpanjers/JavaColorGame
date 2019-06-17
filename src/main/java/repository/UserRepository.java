package repository;

import domain.Player;
import java.util.ArrayList;

public class UserRepository {

    private IUser userContext;

    public UserRepository(IUser context) {
        userContext = context;
    }

    public Boolean registerUser(Player u) {
        return userContext.registerUser(u);
    }

    public Boolean loginUser(String username, String mail, String password) {
        return userContext.loginUser(username, mail, password);
    }

    public Player getUserById(int id) {
        return userContext.getUserById(id);
    }

    public Player getUserByUsername(String name) {
        return userContext.getUserByUsername(name);
    }

    public Player getUserByMail(String mail) {
        return userContext.getUserByMail(mail);
    }

    public Boolean checkIfUsernameExists(Player p) {
        return userContext.checkIfUsernameExists(p);
    }

    public Boolean checkIfMailExists(Player p) {
        return userContext.checkIfMailExists(p);
    }

    public ArrayList<Player> getAllPlayers() {
        return userContext.getAllPlayers();
    }

    public String toSHA256(String base){
        return userContext.toSHA256(base);
    }
}
