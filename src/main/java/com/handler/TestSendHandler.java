package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import com.common.msg.BaseBean;
import com.common.msg.BaseBean.BaseMessage;
import com.common.msg.MissionBean.MissionInfo;
import com.common.msg.PlayerBean.CGCreatRole;
import com.common.msg.PlayerBean.CGGetRoleList;
import com.common.msg.PlayerBean.CGPlayerCheckLogin;

public class TestSendHandler extends ChannelOutboundHandlerAdapter{
	@Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof FullHttpRequest)) {
			String info=String.valueOf(msg);
			String[] infos=info.split(",");
			BaseMessage.Builder myMessage=BaseMessage.newBuilder();
			if(infos[0].equals("mission")){
				MissionInfo.Builder missionInfo=MissionInfo.newBuilder();
				missionInfo.setMissionId(1);
				missionInfo.setStar(1);
				missionInfo.setSelfRecord(2);
				myMessage.setMessageCode(BaseMessage.MessageCode.MISSIONINFO);
				myMessage.setType(BaseMessage.Type.PLAYERMESSAGE);
				myMessage.setExtension(BaseBean.missionInfo, missionInfo.build());
			}else if(infos[0].equals("login")){
				CGPlayerCheckLogin.Builder cgPlayerCheckLogin=CGPlayerCheckLogin.newBuilder();
				cgPlayerCheckLogin.setPlayerId(Integer.parseInt(infos[1]));
				cgPlayerCheckLogin.setUserCode("");
				myMessage.setMessageCode(BaseMessage.MessageCode.CGPLAYERCHECKLOGIN);
				myMessage.setType(BaseMessage.Type.GLOBALMESSAGE);
				myMessage.setExtension(BaseBean.cgPlayerCheckLogin, cgPlayerCheckLogin.build());
	//			gcPlayerCheckLogin.get
			}else if(infos[0].equals("getRole")){
				CGGetRoleList.Builder cgGetRoleList=CGGetRoleList.newBuilder();
				cgGetRoleList.setPlayerId(Integer.parseInt(infos[1]));
				myMessage.setMessageCode(BaseMessage.MessageCode.CGGETROLELIST);
				myMessage.setType(BaseMessage.Type.GLOBALMESSAGE);
				myMessage.setExtension(BaseBean.cgGetRoleList, cgGetRoleList.build());
			}else if(infos[0].equals("creatRole")){
				CGCreatRole.Builder cgCreatRole=CGCreatRole.newBuilder();
				
			}else if(infos[0].equals("send")){
				TextWebSocketFrame tmessage=new TextWebSocketFrame((String)msg);
				ctx.writeAndFlush(tmessage);
			}

	        ctx.writeAndFlush(myMessage.build());
        }else{
        	ctx.writeAndFlush(msg);
        }
    }
}
