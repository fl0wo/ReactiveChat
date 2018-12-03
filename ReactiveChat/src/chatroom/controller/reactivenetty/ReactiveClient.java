package chatroom.controller.reactivenetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;

/**
 *
 * @author saban
 */
public class ReactiveClient {

    private final String host;
    private final int port;

    private Channel canale;
    
    private DatagramSocket sock;

    public ReactiveClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException, Exception {

        sock = new DatagramSocket();
        
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap boot = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ReactiveClientInitializer());

            canale = boot.connect(host, port).sync().channel();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            while(true){
                String t = br.readLine();
                
                System.out.println("SEND >> "+t);
                
                send(t);
            }
            
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(String s) throws Exception {
        canale.writeAndFlush(s+"\r\n");
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        new ReactiveClient("localhost", 8000).run();
    }

}
