package sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Хускар on 18.01.2016.
 */
public class Server {
    EventLoopGroup group = new NioEventLoopGroup();
    ServerBootstrap b = new ServerBootstrap();

    public void run() throws InterruptedException {
    b.group(group)
    .channel(NioServerSocketChannel.class)
    .childHandler(new SmartHouseServerInitializer());

        ChannelFuture future= b.bind(9989).sync();
        future.channel().closeFuture().sync();
        group.shutdownGracefully();
}
}
