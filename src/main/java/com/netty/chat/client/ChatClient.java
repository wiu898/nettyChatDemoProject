package com.netty.chat.client;

import com.netty.chat.client.handler.ChatClientHandler;
import com.netty.chat.protocol.IMDecoder;
import com.netty.chat.protocol.IMEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * 聊天客户端
 *
 * @author lichao chao.li07@hand-china.com 5/27/21 6:03 PM
 */
public class ChatClient {

    private ChatClientHandler clientHandler;

    private String host;

    private int port;

    public ChatClient(String nickName){
        this.clientHandler = new ChatClientHandler(nickName);
    }

    private void connect(String host, int port) {
        this.host = host;
        this.port = port;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new IMDecoder());
                    pipeline.addLast(new IMEncoder());
                    pipeline.addLast(clientHandler);
                }
            });
            ChannelFuture future = bootstrap.connect(this.host,this.port).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        //启动一个客户端 Cover为客户端默认名称
        new ChatClient("Cover").connect("127.0.0.1",8080);
//        String url = "http://localhost:8080/images/a.png";
//        System.out.println(url.toLowerCase().matches(".*\\.(gif|png|jpg)$"));
    }

}
