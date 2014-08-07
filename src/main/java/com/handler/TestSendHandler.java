package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import com.bean.BaseBean;
import com.bean.BaseBean.BaseMessage;
import com.bean.MissionBean.MissionInfo;

public class TestSendHandler extends ChannelOutboundHandlerAdapter{
	@Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		MissionInfo.Builder missionInfo=MissionInfo.newBuilder();
		missionInfo.setMissionId(1);
		missionInfo.setStar(1);
		missionInfo.setSelfRecord(2);
		BaseMessage.Builder myMessage=BaseMessage.newBuilder();
		myMessage.setType(BaseMessage.Type.MISSIONINFO);
		myMessage.setExtension(BaseBean.missionInfo, missionInfo.build());
        ctx.writeAndFlush(myMessage.build());
    }
}
