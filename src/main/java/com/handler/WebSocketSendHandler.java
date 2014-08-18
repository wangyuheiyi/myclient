package com.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketSendHandler extends ChannelOutboundHandlerAdapter{
	@Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof FullHttpRequest)) {
			if(msg instanceof ByteBuf){
				BinaryWebSocketFrame message=new BinaryWebSocketFrame((ByteBuf)msg);
		        ctx.writeAndFlush(message);
			}else{
				WebSocketFrame frame =(WebSocketFrame) msg;
				if (frame instanceof TextWebSocketFrame) {
					ctx.writeAndFlush(frame);
				}
			}
        }else{
        	ctx.writeAndFlush(msg);
        }
    }
}
