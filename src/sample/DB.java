package sample;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Хускар on 24.01.2016.
 */
public class DB {
    static String log;
    static String ip;
    static String pass;

    //addUser Добавление нового пользователя первоночально обращается к searchtoadd для того чтобы проверить не занят ли логин
    //возвращает true если пользователь добавлен и false если не добавлен так как ник занят
    public static boolean addUser( String login, String password, String ip) {
        if (!serachtoadd(login)) {
            //System.out.print("Пользователь с таким ником уже есть");
            return false;
        } else {
            PreparedStatement stat = null;
            Connection conn = null;
            ResultSet r = null;
            try {
                Class.forName("org.h2.Driver");
                conn = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");

                String sql = "INSERT INTO USER( LOGIN, PASSWORD, IP) VALUES (?,?,?)";
                stat = conn.prepareStatement(sql);
                //stat.setInt(1, id);
                stat.setString(1, login);
                stat.setString(2, password);
                stat.setString(3, ip);
                stat.executeUpdate();
                createTableUser(login,password,ip);
                return true;
                //stat = conn.createStatement();
                // r = stat.executeQuery("SELECT * FROM USER");

                //  while (r.next()) {
                //     System.out.print(r.getString("Login"));
                // }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) conn.close();
                    if (stat != null) stat.close();
                } catch (SQLException e) {
                }

            }
        }
        return false;
    }

    //search Поиск пользователя в базе данных по нику.возвращает IP пользователя
    public static String search(String login) {
        log = null;
        ip = null;
        Statement stat = null;
        Connection connect = null;
        ResultSet r = null;
        try {
            connect = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");
            stat = connect.createStatement();
            r = stat.executeQuery("SELECT * FROM USER WHERE LOGIN ='" + login + "';");
            while (r.next()) {
                log = r.getString("Login");
                ip = r.getString("IP");
                System.out.println(r.getString("Login"));
            }
            if (log == null) {
                System.out.println(" Такого логина нет в базе данных!" + log);
                return null;
            } else if (log != null) {
                System.out.println("Такой логин есть в базе данных:" + log);
                return ip;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null) r.close();
                if (connect != null) connect.close();
                if (stat != null) stat.close();
            } catch (SQLException e) {
            }

        }
        return null;
    }

    //Поиск пользователя для проверки при добавлении в методе addUser
    public static boolean serachtoadd(String login) {
        log = null;
        Statement stat = null;
        Connection connect = null;
        ResultSet r = null;
        try {
            connect = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");
            stat = connect.createStatement();
            r = stat.executeQuery("SELECT * FROM USER WHERE LOGIN ='" + login + "';");
            while (r.next()) {
                log = r.getString("Login");
                //System.out.println(r.getString("Login"));
            }
            if (log == null) {
                //System.out.println(" Такого логина нет!" + log);
                return true;
            } else if (log != null) {
                //System.out.println("Такой логин есть:" + log);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) connect.close();
                if (stat != null) stat.close();
                if (r != null) r.close();
            } catch (SQLException e) {
            }
        }
        return false;
    }

    //Проверка соотвествия логина и пароля по переданному пользователем ID; возвращает True если соответствует.
    public static boolean auten( String login, String password, String ipp) {

        log = null;
        pass = null;
        ip = null;
        Statement stat = null;
        Connection connection = null;
        ResultSet r = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");
            stat = connection.createStatement();
            r = stat.executeQuery("SELECT * FROM USER where LOGIN='" + login + "';");
            while (r.next()) {
                log = r.getString("Login");
                pass = r.getString("Password");
                ip = r.getString("ip");
            }
            System.out.print(!log.equals(""));
            if ((!log.equals(""))&&(log.equals(login)) && (password.equals(pass))) {
                if (!ip.equals(ipp)) {
                    obnov(login, ipp);
                }
                return true;
            } else return false;
            //stat = conn.createStatement();
            // r = stat.executeQuery("SELECT * FROM USER");

            //  while (r.next()) {
            //     System.out.print(r.getString("Login"));
            // }
        } catch (ClassNotFoundException | SQLException e) {
           // e.printStackTrace();
            System.out.print("Такого логина не найдено");
        }
        catch (NullPointerException e){return false;}
        finally {
            try {
                if (connection != null) connection.close();
                if (stat != null) stat.close();
            } catch (SQLException e) {
            }

        }

        return false;
    }

    //Обновление IP пользователя в базе данных вызывается из auten
    public static void obnov(String login, String ip) {
        PreparedStatement stat = null;
        Connection connection = null;
        ResultSet r = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");
            stat = connection.prepareStatement("UPDATE user SET IP='" + ip + "' WHERE LOGIN='" + login + "';");
            stat.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stat != null) stat.close();
            } catch (SQLException e) {
            }

        }
    }

    public static Boolean createTableUser(String login,String password,String ip){

            Connection connection = null;
            ResultSet r = null;
        Statement stat =null;
            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");
                //stat = connection.prepareStatement("CREATE TABLE "+login+" (Friends CHAR not null );");
                //stat.executeUpdate();
                stat= connection.createStatement();
                stat.execute("CREATE TABLE "+login+" (Friends CHAR not null,FOREIGN KEY(Friends) REFERENCES USER(LOGIN));");
                //r = stat.executeQuery("CREATE TABLE '"+login+"' (Friends CHAR not null );");

                return true;

            }
            //stat = conn.createStatement();
            // r = stat.executeQuery("SELECT * FROM USER");

            //  while (r.next()) {
            //     System.out.print(r.getString("Login"));
            // }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (connection != null) connection.close();
                    if (stat != null) stat.close();

                } catch (SQLException e) {
                    return false;
                }

            }


    }//Создание базы данных для нового пользователя

    public static String  addFriends(String loginUser,String loginVspisok){
        PreparedStatement stat = null;
        Connection conn = null;
        ResultSet r = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");

            String sql = "INSERT INTO "+loginUser+"(Friends) VALUES (?)";
            stat = conn.prepareStatement(sql);
            //stat.setInt(1, id);
            stat.setString(1, loginVspisok);
            stat.executeUpdate();
            return loginVspisok;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (stat != null) stat.close();
            } catch (SQLException e) {
            }

        }
        return null;
    }//Добавление клиента в друзья пользователя

    public static String  addFriends2(String loginUser,String loginVspisok){
        PreparedStatement stat = null;
        Connection conn = null;
        ResultSet r = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");

            String sql = "INSERT INTO "+loginVspisok+"(Friends) VALUES (?)";
            stat = conn.prepareStatement(sql);
            //stat.setInt(1, id);
            stat.setString(1, loginUser);
            stat.executeUpdate();
            return loginVspisok;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (stat != null) stat.close();
            } catch (SQLException e) {
            }

        }
        return null;
    }//Добавление клиента в друзья пользователя

    public static ArrayList<Object> showFriendList(String login){
    System.out.println("Логин:"+login);

        ArrayList<Object> arr= new ArrayList<Object>();
        arr.add("2021");
        Statement stat = null;
        Connection connect = null;
        ResultSet r = null;
        try {
            connect = DriverManager.getConnection("jdbc:h2:file://F:\\JAVA\\Messenger with DB\\TetServer\\db\\users", "", "");
            stat = connect.createStatement();
            r = stat.executeQuery("SELECT Friends FROM "+login+";");
            while (r.next()) {
                arr.add(r.getString("Friends"));
                //System.out.println(r.getString("Login"));
            }
            for(int i=0;i<arr.size();i++){
            }
            return arr;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) connect.close();
                if (stat != null) stat.close();
                if (r != null) r.close();
            } catch (SQLException e) {
            }
        }
        return arr;
    }//Выгрузка списка контактов пользователя

    public static void test() {


        String s = "Alex";
        // if (addUser(2, "Тимофей Мозгоф", "qwert", "127.0.0.1")) {
        //   System.out.println("Пользователь успешно добавлен в базу");
        //  } else System.out.println("Пользователь с таким Login уже существует");
        if (auten( "Тимофей Мозгоф", "qwert", "127.0.0.5")) {
            System.out.print("Прошел авторизацию");
        } else System.out.print("Не прошел авторизацию");
    }
}
//CREATE table USER (Name CHAR PRIMARY KEY NOT NULL ,Password CHAR NOT NULL ,IP CHAR NOT NULL );