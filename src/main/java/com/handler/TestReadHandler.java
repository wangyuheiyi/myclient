package com.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.common.msg.BaseBean;
import com.common.msg.BaseBean.BaseMessage;
import com.common.msg.BuildBean.GCGetBuildList;
import com.common.msg.DataBean.HumanInfo;
import com.common.msg.PlayerBean.GCCreateRole;
import com.common.msg.PlayerBean.GCEnterScene;
import com.common.msg.PlayerBean.GCGetRoleList;
import com.common.msg.PlayerBean.GCPlayerCheckLogin;
import com.common.msg.PlayerBean.GCRoleReName;

public class TestReadHandler extends SimpleChannelInboundHandler<Object>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("end");
		HumanInfo humanInfo=null;
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
			if(gcGetRoleList.getHumanInfoList()!=null&&gcGetRoleList.getHumanInfoList().size()!=0){
				System.out.println(gcGetRoleList.getHumanInfoCount());
				humanInfo=gcGetRoleList.getHumanInfo(0);
				System.out.println("roleId:"+humanInfo.getRoleId());
				ctx.channel().writeAndFlush("selectRole,"+gcGetRoleList.getPlayerId()+","+humanInfo.getRoleId());
			}else{
				ctx.channel().writeAndFlush("creatRole,"+gcGetRoleList.getPlayerId()+",1");
			}
			break;
		case GCCREATEROLE:
			GCCreateRole gcCreateRole=baseBean.getExtension(BaseBean.gcCreateRole);
			System.out.println("createRole playId:" + gcCreateRole.getPlayerId());
			humanInfo=gcCreateRole.getHumanInfo();
			System.out.println("createRole HumanId:" + humanInfo.getRoleId());
			break;
		case GCENTERSCENE:
			GCEnterScene gcEnterScene=baseBean.getExtension(BaseBean.gcEnterScene);
			int sceneId=gcEnterScene.getSceneId();
			long roleId=gcEnterScene.getRoleId();
			System.out.println("role enterSence ["+sceneId+"] roleId ["+roleId+"]");
//			ctx.channel().writeAndFlush("roleraname,"+roleId+",wangyuheiyi");
			break;
		case GCROLERENAME:
			GCRoleReName gcRoleReName=baseBean.getExtension(BaseBean.gcRoleReName);
			System.out.println("role rename ["+gcRoleReName.getRoleName()+"]");
			break;
		case GCGETBUILDLIST:
			GCGetBuildList gcGetBuildList=baseBean.getExtension(BaseBean.gcGetBuildList);
			System.out.println("build list is"+gcGetBuildList.getBuildDataCount());
			System.out.println("building list is"+gcGetBuildList.getBuildIngDataCount());
			if(gcGetBuildList.getBuildDataCount()==0&&gcGetBuildList.getBuildIngDataCount()==0){
				//¥¥Ω®”¢–€º¿Ã≥
				ctx.channel().writeAndFlush("creatBuild,"+gcGetBuildList.getRoleId()+",100001");
			}
			break;
		}
	}
	
}
