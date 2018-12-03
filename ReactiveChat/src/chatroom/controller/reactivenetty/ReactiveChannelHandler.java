
package chatroom.controller.reactivenetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author saban
 */
public class ReactiveChannelHandler extends SimpleChannelInboundHandler<String> {

    //private Set<InetSocketAddress> set = new HashSet<>();

    //private ExecutorService ex = Executors.newFixedThreadPool(5);

    private Job task;

    public ReactiveChannelHandler(Job task) {
        this.task = task;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String i) throws Exception {
        System.out.println(i);
    }

    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf) msg;
        //System.out.println(new String(bb.array()));
        
        task.exec(bb.array());
    }
    
    
}
/*
public class ReactiveChannelHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private Set<InetSocketAddress> set = new HashSet<>();

    private ExecutorService ex = Executors.newFixedThreadPool(5);

    private Job task;

    public ReactiveChannelHandler(Job task) {
        this.task = task;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext chc, DatagramPacket packet) throws Exception {
         final InetSocketAddress srcAddr = packet.sender();

        System.out.println(set.add(srcAddr));

        final ByteBuf buf = packet.content();
        final int len = buf.readableBytes();
        final byte[] rcvbuff = new byte[len];

        buf.readBytes(rcvbuff);

        task.exec(rcvbuff);

        for (Iterator<InetSocketAddress> it = set.iterator(); it.hasNext();) {

            InetSocketAddress isa = it.next();
            
            if(isa.getAddress()!=InetAddress.getLocalHost()){
                UDPClient.sendTo(isa.getAddress(), rcvbuff);
            }
        }

    }
    
}
*/