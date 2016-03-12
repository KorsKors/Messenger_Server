package sample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.InternetProtocolFamily;
import sample.Otvet.Otvet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Хускар on 18.01.2016.
 */
public class SmartHouseServerHandler extends SimpleChannelInboundHandler<Object>{


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o)   {
       // String s= (String) o; РАБОТАЕТ
        //System.out.print(s);РАБОТАЕТ
       // ctx.write(o+"1234");РАБОТАЕТ

       // String s[]=(String[])o;то что отключил
        //System.out.print(s[2]);то что отключил
        //ctx.write(s);то что отключил

        System.out.print("Получаю"+"\n");
        ArrayList<Object> k=(ArrayList)o;
        String t=(String)k.get(0);

        Integer r= Integer.valueOf(t);
        System.out.println ("Номер операции от клиента:"+r);
        switch(r){
            case(101):{
                User user= new User(k.get(1),k.get(2),k.get(3));
                boolean rez=DB.addUser(user.getLogin(),user.getPassword(),user.getIP());
        //System.out.print(k.get(0));
        //System.out.print(k.get(1));
        //System.out.print(k.get(2));
                if (rez)        System.out.print("Запись успешно создана");
                else { System.out.print("Такой логин уже есть");}
                ArrayList<Object> arr=new ArrayList<Object>();
                arr.add("101");
                arr.add(rez);
                ctx.write(arr);
            return;}
            case (202):{
                User user1= new User(k.get(1),k.get(2),k.get(3));
                System.out.println("Полученные данные на аутентификацию:"+k.get(1)+k.get(2)+k.get(3));
                boolean rez=DB.auten( user1.getLogin(), user1.getPassword(),user1.getIP());
                ArrayList<Object> arr=new ArrayList<Object>();
                arr.add("202");
                System.out.println("Данные об аутетификакции отправляю клинету: "+rez);
                arr.add(rez);
                ctx.write(arr);
                return;
            }
            case (2021):{//Отправить список пользователю

               ArrayList<Object>obj= DB.showFriendList((String)k.get(1));
                for(int i=0;i<obj.size();i++){
                System.out.println("Массив:"+obj.get(i));}
                ctx.write(obj);
                return;
            }
            case(303):{//Добавление пользователя в друзья
                System.out.print((String)k.get(2));
                String login=DB.search((String)k.get(2));
                System.out.print(login);
                if((login !=null)){
                    login = DB.addFriends((String)k.get(1),(String)k.get(2));
                    DB.addFriends2((String)k.get(1),(String)k.get(2));

                    new Otvet(DB.search((String)k.get(2)),(String)k.get(1)).runs(404);//В данный момент времени я пытаюсь отправить добавленного пользователя человеку которого добавили
                    System.out.println(login);
                    ArrayList<Object> arr=new ArrayList<Object>();
                    arr.add("303");
                    arr.add(login);
                    ctx.write(arr);
                    System.out.println ("Отослал!  "+arr.get(0)+""+arr.get(1));
                    return;
                }
              else {
                    System.out.println(login);
                ArrayList<Object> arr=new ArrayList<Object>();
                arr.add("303");
                arr.add(login);
                ctx.write(arr);
                System.out.println ("Отослал!");
                return;}
            }
        }

    }
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
