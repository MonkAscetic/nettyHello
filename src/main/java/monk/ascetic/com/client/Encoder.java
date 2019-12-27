package monk.ascetic.com.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * @author lizy
 * @date 2019/12/26
 */
public class Encoder extends MessageToMessageEncoder<byte[]>{
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        out.add( new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg)));
    }
}
