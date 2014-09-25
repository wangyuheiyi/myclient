package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.common.msg.BaseBean;
import com.common.msg.BaseBean.BaseMessage;
import com.common.msg.PlayerBean.GCGetRoleList;
import com.common.msg.PlayerBean.GCPlayerCheckLogin;

public class TestReadHandler extends SimpleChannelInboundHandler<Object>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("end");
		BaseMessage baseBean=(BaseMessage)msg;
		switch (baseBean.getMessageCode()) {
		case GCPLAYERCHECKLOGIN:
			GCPlayerCheckLogin gcPlayerCheckLogin=baseBean.getExtension(BaseBean.gcPlayerCheckLogin);
			System.out.println(gcPlayerCheckLogin.getPlayerId());
			ctx.channel().writeAndFlush("getRole,"+gcPlayerCheckLogin.getPlayerId());
			break;
		case GCGETROLELIST:
			GCGetRoleList gcGetRoleList=baseBean.getExtension(BaseBean.gcGetRoleList);
			System.out.println(gcGetRoleList.getPlayerId());
			if(gcGetRoleList.getHumanInfoList()!=null){
				System.out.println(gcGetRoleList.getHumanInfoCount());
				ctx.channel().writeAndFlush("creatRole,"+gcGetRoleList.getPlayerId()+",1");
			}else{
				System.out.println("null");
			}
			
			break;
		}
	}
	
}
