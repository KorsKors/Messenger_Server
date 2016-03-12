package sample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * Created by ������ on 18.01.2016.
 */
public class SmartHouseServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.remoteAddress();
        ChannelPipeline p = socketChannel.pipeline();

       // p.addLast(new StringDecoder());
        //p.addLast(new StringEncoder());
        p.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        p.addLast(new ObjectEncoder());
        p.addLast( new SmartHouseServerHandler());
    }
}
