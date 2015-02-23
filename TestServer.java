Enter file contents herepackage com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Created by Kunal Chowdhury on 2/21/15.
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        SocketChannel c = SocketChannel.open();
        c.connect(new InetSocketAddress(5001));
        String newData = "Welcome earthlings.. here we come at " + System.currentTimeMillis();

        CharBuffer cb = CharBuffer.allocate(1024);
        cb.clear();
        cb.put(newData.toCharArray());
       /* ByteBuffer buf = ByteBuffer.allocate(48).order(ByteOrder.BIG_ENDIAN);
        buf.clear();
        buf.put(newData.getBytes());
*/
       cb.flip();
       ByteBuffer buf = StandardCharsets.UTF_8.encode(cb);


       // buf.flip();
        while(buf.hasRemaining()) {
            c.write(buf);
        }
        /*ByteBuffer buffer = ByteBuffer.wrap("Here we come earthlings.. ".getBytes());
        buffer.flip();
        c.write(buffer);
        System.out.println("from here "+c);*/

        ByteBuffer b1 = ByteBuffer.allocate(1024);
        c.read(b1);
        b1.flip();
        StringBuilder sb = new StringBuilder();
        for(byte b : b1.array()) {
            sb.append((char)b);
        }

        System.out.println("hhh "+ sb.toString());
    }
}
