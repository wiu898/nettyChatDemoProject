package com.netty.chat.processor;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 主要用于自定义协议内容的逻辑处理
 *
 * @author lichao chao.li07@hand-china.com 5/27/21 6:05 PM
 */
public class MsgProcessor {

    //记录在线用户
    private static ChannelGroup onlineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
