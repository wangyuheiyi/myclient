package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestReadHandler extends SimpleChannelInboundHandler<Object>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("end");
	}
	
}