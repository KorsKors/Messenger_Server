package sample.Otvet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Хускар on 22.02.2016.
 */
public class OtvetInitalizer extends ChannelInitializer<SocketChannel> {
    public OtvetInitalizer(){
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline p = socketChannel.pipeline();
        //p.addLast(new StringDecoder());
        // p.addLast(new StringEncoder());
        p.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        p.addLast(new ObjectEncoder());
        p.addLast(new OtvetHandler());

    }
}
