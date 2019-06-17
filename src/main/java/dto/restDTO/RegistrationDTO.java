package dto.restDTO;

public class RegistrationDTO {

    private String username;
    private String mail;
    private String password;

    public RegistrationDTO(){

    }

    public RegistrationDTO(String username, String mail, String password){
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
