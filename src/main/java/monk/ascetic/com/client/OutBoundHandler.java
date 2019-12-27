package monk.ascetic.com.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;

/**
 * @author lizy
 * @date 2019/12/26
 */
public class OutBoundHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String o) throws Exception {
        System.out.println(o);
        //此处使该方法中管道和信息往下传播到下一个处理类
        ctx.fireChannelRead(o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //此处使该方法中管道和信息往下传播到下一个处理类
        ctx.fireChannelActive();
    }

}
