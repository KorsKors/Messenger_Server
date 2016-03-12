package sample.Otvet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Хускар on 22.02.2016.
 */
public class Otvet {
    Channel channel = null;
    EventLoopGroup group = new NioEventLoopGroup();
    String ip;
    String newUser;

    public Otvet(String IP,String newUser) {
        this.ip = IP;
        this.newUser=newUser;
    }

    public void runs(Integer code) {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler((new OtvetInitalizer()));
            channel = null;
            System.out.println("Пытаюсь подключиться к серверу!");
            channel = b.connect(ip, 9990).sync().channel();//88.201.204.21
            System.out.println("Подключение удалось!");
            switch(code){
                case(404):{
                    new OtvetHandler().PostNewUser(channel,newUser);
                }
            }
        } catch (InterruptedException e) {
        }
    }
}