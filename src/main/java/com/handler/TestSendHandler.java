package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import com.common.msg.BaseBean;
import com.common.msg.BaseBean.BaseMessage;
import com.common.msg.BuildBean.CGCreatBuild;
import com.common.msg.MissionBean.MissionInfo;
import com.common.msg.PlayerBean.CGCreateRole;
import com.common.msg.PlayerBean.CGGetRoleList;
import com.common.msg.PlayerBean.CGPlayerCheckLogin;
import com.common.msg.PlayerBean.CGRoleReName;
import com.common.msg.PlayerBean.CGSelectRole;

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
				cgPlayerCheckLogin.setPlayerId(Long.parseLong(infos[1]));
				cgPlayerCheckLogin.setUserCode("");
				myMessage.setMessageCode(BaseMessage.MessageCode.CGPLAYERCHECKLOGIN);
				myMessage.setType(BaseMessage.Type.GLOBALMESSAGE);
				myMessage.setExtension(BaseBean.cgPlayerCheckLogin, cgPlayerCheckLogin.build());
	//			gcPlayerCheckLogin.get
			}else if(infos[0].equals("getRole")){
				CGGetRoleList.Builder cgGetRoleList=CGGetRoleList.newBuilder();
				cgGetRoleList.setPlayerId(Long.parseLong(infos[1]));
				myMessage.setMessageCode(BaseMessage.MessageCode.CGGETROLELIST);
				myMessage.setType(BaseMessage.Type.GLOBALMESSAGE);
				myMessage.setExtension(BaseBean.cgGetRoleList, cgGetRoleList.build());
			}else if(infos[0].equals("creatRole")){
				CGCreateRole.Builder cgCreatRole=CGCreateRole.newBuilder();
				cgCreatRole.setPlayerId(Long.parseLong(infos[1]));
				cgCreatRole.setTemplateId(Integer.parseInt(infos[2]));
				myMessage.setMessageCode(BaseMessage.MessageCode.CGCREATEROLE);
				myMessage.setType(BaseMessage.Type.GLOBALMESSAGE);
				myMessage.setExtension(BaseBean.cgCreateRole, cgCreatRole.build());
			}else if(infos[0].equals("selectRole")){
				CGSelectRole.Builder cgSelectRole =CGSelectRole.newBuilder();
				cgSelectRole.setPlayerId(Long.parseLong(infos[1]));
				cgSelectRole.setRoleId(Long.parseLong(infos[2]));
				myMessage.setMessageCode(BaseMessage.MessageCode.CGSELECTROLE);
				myMessage.setType(BaseMessage.Type.GLOBALMESSAGE);
				myMessage.setExtension(BaseBean.cgSelectRole, cgSelectRole.build());
			}else if(infos[0].equals("roleraname")){
				CGRoleReName.Builder cgRoleReName =CGRoleReName.newBuilder();
				cgRoleReName.setRoleId(Long.parseLong(infos[1]));
				cgRoleReName.setRoleName(infos[2]);
				myMessage.setMessageCode(BaseMessage.MessageCode.CGROLERENAME);
				myMessage.setType(BaseMessage.Type.PLAYERMESSAGE);
				myMessage.setExtension(BaseBean.cgRoleReName, cgRoleReName.build());
				
			}else if(infos[0].equals("creatBuild")){
				CGCreatBuild.Builder cgCreatBuild=CGCreatBuild.newBuilder();
				cgCreatBuild.setRoleId(Long.parseLong(infos[1]));
				cgCreatBuild.setTemplateId(Integer.parseInt(infos[2]));
				myMessage.setMessageCode(BaseMessage.MessageCode.CGCREATBUILD);
				myMessage.setType(BaseMessage.Type.PLAYERMESSAGE);
				myMessage.setExtension(BaseBean.cgCreatBuild, cgCreatBuild.build());
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
