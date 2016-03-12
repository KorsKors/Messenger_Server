package sample.Otvet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;

/**
 * Created by Хускар on 22.02.2016.
 */
public class OtvetHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    }

    public void PostNewUser(Channel channel , String newUSer) {
        ArrayList<Object> otpr= new ArrayList<Object>();
        otpr.add("404");
        otpr.add(newUSer);
        System.out.println("Отправляю данные клиенту 404 ");
        channel.writeAndFlush(otpr);
        System.out.println("Данные отправлены 404");
    }

    public void PostNewMessege() {
    }
}
