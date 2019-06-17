package repository;

import domain.Player;
import java.util.ArrayList;
import java.util.List;

public interface IUser {

    Boolean registerUser(Player u);

    Boolean loginUser(String username, String mail, String password);

    Player getUserById(int id);

    Player getUserByUsername(String name);

    Player getUserByMail(String mail);

    Boolean checkIfUsernameExists(Player p);

    Boolean checkIfMailExists(Player p);

    ArrayList<Player> getAllPlayers();

    String toSHA256(String base);

    Boolean validateUsername(String username, List<String> errorList);

    Boolean validatePassword(String password, List<String> errorList);

}
