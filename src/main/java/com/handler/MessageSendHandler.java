package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocket08FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class MessageSendHandler extends SimpleChannelInboundHandler<Object>{
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.pipeline().addLast(new WebSocket13FrameEncoder(true));
		ctx.pipeline().addLast(new WebSocketSendHandler());
        ctx.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        ctx.pipeline().addLast(new ProtobufEncoder());
        ctx.pipeline().addLast(new TestSendHandler());
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
