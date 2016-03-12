package sample;

/**
 * Created by ������ on 20.01.2016.
 */
public class User {
    private Integer ID;
    private String login;
    private String password;
    private String IP;
    private String Command;

    public void setCommand(String Command){this.Command=Command;}

    public String getCommand (){return Command;}


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getIP() {
        return IP;
    }
    public User(String login,String password){
        this.login=login;
        this.password=password;
    }
    public User(Object login,Object password,Object IP){
      //  this.ID=(Integer) ID;
        this.login=(String)login;
        this.password=(String) password;
        this.IP=(String) IP;

    }
    public User(){}
}
