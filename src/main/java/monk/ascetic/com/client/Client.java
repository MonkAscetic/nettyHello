package monk.ascetic.com.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import monk.ascetic.com.server.handler.ServerHandler;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @author lizy
 * @date 2019/12/26
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new OutBoundHandler());
                    ch.pipeline().addLast(new ServerHandler());

                    System.out.println("channel:"+ch.id());
                }
            });



            
            // Start the client.
            ChannelFuture future = b.connect(host, port).sync(); // (5)

            ByteBuf byteBuf = Unpooled.copiedBuffer("你好".getBytes());
            future = future.channel().writeAndFlush(byteBuf);

            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
