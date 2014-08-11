package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import com.bean.AddressBookProtos.Person;
import com.bean.BaseBean;
import com.bean.BaseBean.BaseMessage;
import com.bean.MissionBean.MissionInfo;
import com.bean.PlayerBean.CGPlayerCheckLogin;

public class TestSendHandler extends ChannelOutboundHandlerAdapter{
	@Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		String info=String.valueOf(msg);
		Person.Builder b=Person.newBuilder();
		b.setEmail("");
		BaseMessage.Builder myMessage=BaseMessage.newBuilder();
		if(info.equals("mission")){
			MissionInfo.Builder missionInfo=MissionInfo.newBuilder();
			missionInfo.setMissionId(1);
			missionInfo.setStar(1);
			missionInfo.setSelfRecord(2);
			myMessage.setType(BaseMessage.Type.MISSIONINFO);
			myMessage.setExtension(BaseBean.missionInfo, missionInfo.build());
		}else if(info.equals("login")){
			CGPlayerCheckLogin.Builder cgPlayerCheckLogin=CGPlayerCheckLogin.newBuilder();
			cgPlayerCheckLogin.setPlayerId(1001);
			cgPlayerCheckLogin.setUserCode("");
			myMessage.setType(BaseMessage.Type.CGPLAYERCHECKLOGIN);
			myMessage.setExtension(BaseBean.cgPlayerCheckLogin, cgPlayerCheckLogin.build());
//			gcPlayerCheckLogin.get
		}
		
        ctx.writeAndFlush(myMessage.build());
    }
}
