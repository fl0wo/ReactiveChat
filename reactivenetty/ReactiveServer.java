package chatroom.controller.reactivenetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saban
 */
public class ReactiveServer {

    private final int port;

    public ReactiveServer(int port) {
        this.port = port;
    }

    public void run() throws UnknownHostException, SocketException, InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    
                    .option(ChannelOption.SO_BROADCAST, true)
                    
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ReactiveServerInitializer());

            bootstrap.bind(port).sync().channel().closeFuture().sync();

        } catch (InterruptedException ex) {
            Logger.getLogger(ReactiveServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws UnknownHostException, SocketException, InterruptedException {
        new ReactiveServer(8000).run();
    }

    //new ReactiveClient("localhost", 8081);
}
