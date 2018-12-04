package chatroom.controller.reactivenetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author saban
 */
public class ReactiveNotifyServer extends SimpleChannelInboundHandler<String> {

    // non funziona ...
    //private ChannelGroup all = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // neanche questo funziona ...
    //private Set<Channel> set = new HashSet<>();
    // 
    //private HashMap<String,Channel> map = new HashMap<String,Channel>();
    @Override
    public void handlerAdded(ChannelHandlerContext chc) {
        Channel me = chc.channel();

        //notifico tutti (io sto per entrare)
        /*
        for (Channel other : all) {
            other.writeAndFlush(" >> [" + me.remoteAddress() + "]");
        }
         */
        //ora io faccio parte di "tutti"
        //map.put(me.toString(),me);
        DC.list.add(me);

        System.out.println("added " + me.toString() + DC.list.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext chc) {
        Channel me = chc.channel();

        //notifico tutti (io sto per uscire)
        /*
        for (Channel other : map) {
            other.writeAndFlush(" >> [" + me.remoteAddress() + "]");
        }
         */
        //ora io faccio parte di "tutti"
        //map.remove(me);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String i) throws Exception {
        Channel me = chc.channel();
        
        //chc.writeAndFlush("siii\r\n");
        
        System.out.println(" >>" + i + "<< ");

        //notifico tutti tranne me stesso
        for (int j = 0; j < DC.list.size(); j++) {

            Channel other = DC.list.get(j);

            if (other != me) {
                System.out.println(" send to ->" + other.toString());
                other.writeAndFlush("[" + i + "]" + "\r\n");
            }
        }

        /*
        Set<String> keys = map.keySet();

        for (String k : keys) {
            
            Channel other = map.get(k);
            
            System.out.println("entra");
            
            if (other != me) {
                
                System.out.println("INVIA");
                other.writeAndFlush("[" + i + "]" + "\r\n");
            }
        }

         */
    }
}

class DC {

    public static ArrayList<Channel> list = new ArrayList<>();

}
