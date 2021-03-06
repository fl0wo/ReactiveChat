package chatroom.controller.reactivenetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 * @author saban
 */
public class ReactiveServerInitializer extends ChannelInitializer<SocketChannel> {

    public ReactiveServerInitializer() {
    }

    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        ChannelPipeline pipeline = c.pipeline();
        
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder" , new StringEncoder());
        
        pipeline.addLast("handler", new ReactiveNotifyServer());
    }
    
}
