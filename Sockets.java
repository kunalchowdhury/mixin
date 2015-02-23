Enter file copackage com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Sockets implements Runnable{
    private static int PORT= 5001;

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public void go() throws Exception {
        System.out.println("Starting the server... ");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(PORT));

        Selector selector  = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            int n = selector.select();
            if (n == 0){
                continue;
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                }
                if(key.isReadable()) {
                   // System.out.println("key s readable..");
                    readDataFromSocket(key);
                }
                it.remove();
            }
        }
     }

    private void readDataFromSocket(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        buffer.clear();
        int count  ;
       // System.out.println("count "+count);
        while((count =channel.read(buffer)) > 0){
            System.out.println("got data to read.. ");
            buffer.flip();
            CharBuffer result = StandardCharsets.UTF_8.decode(buffer);//buffer.asCharBuffer();
           // result.flip();
            System.out.println(new String(result.array()));
           // System.out.println(Arrays.toString(buffer.array()));
            buffer.clear();
        }
        if(count < 0){
            channel.close();
        }
    }


    private void sayHello(SocketChannel channel) throws IOException {
        System.out.println("saying hello world.. ");
        buffer.clear();
        buffer.put("Hello World".getBytes());
        buffer.flip();
        channel.write(buffer);

    }

    private void registerChannel(Selector selector, SocketChannel channel, int opRead) throws Exception {
        //System.out.println("channel is null "+channel);
        if(channel == null){
            return ;
        }
       // System.out.println("channel is null --- "+channel);
        channel.configureBlocking(false);
        channel.register(selector, opRead) ;
    }

    public static void main(String[] args) {
        new Thread(new SelectSockets()) .start();
    }


    @Override
    public void run() {
        try {
            go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
ntents here
