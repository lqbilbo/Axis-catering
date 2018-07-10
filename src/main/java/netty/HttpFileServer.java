package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.TimeUnit;

public class HttpFileServer {

    private static final String DEFAULT_URL = "/Users/luoqi/Document/netty/";

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
//                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",
                                    new HttpRequestDecoder());
                            /**
                             * HTTP解码器会在解码过程中生成多个消息对象，
                             * （1）HttpRequest/HttpResponse
                             * （2）HttpContent
                             * （3）LastHttpContent
                             * 作用是将多个消息转换为单一的FullHttpRequest或FullHttpResponse
                             */
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65535));
                            ch.pipeline().addLast("http-encoder",
                                    new HttpResponseEncoder());
                            /**
                             * 支持异步发送大的码流，不占用过多内存，防止发生内存溢出
                             */
                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler",
                                    new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = b.bind("192.168.99.239", port).sync();
            System.out.println("HTTP文件目录服务器启动，网址是： "+ "http://192.168.99.239:" + port + url);

//            future.awaitUninterruptibly();
            //ChannelFuture配置超时时间
            /**
             * 如果没有显式关闭连接，则超时会导致严重问题
             */
            future.awaitUninterruptibly(10, TimeUnit.SECONDS);

            assert future.isDone();

            if (future.isCancelled()) {
                //被客户端取消了的连接尝试
            } else if (!future.isSuccess()) {
                future.cause().printStackTrace();
            } else {
                //成功连接建立
            }

            future.channel().closeFuture().sync();
        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if (args.length > 1 ) {
            url = args[1];
            new HttpFileServer().run(port, url);
        }
    }

}
