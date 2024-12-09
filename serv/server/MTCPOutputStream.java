/* 
    class MTCPOutputStream
    Socket OutputStream
*/
import java.io.*;
import java.util.*;
import java.net.*;

class MTCPOutputStream extends FilterOutputStream {

    final static int T_CR  = 13;
    final static int T_LF  = 10;

    public MTCPOutputStream(OutputStream out) {
        super(out);
    }
    public void println() throws IOException {
        write(T_CR);
        write(T_LF);
    }
    public void println(String s) throws IOException {
        for(int i=0;i<s.length();++i) write(s.charAt(i));
        println();
    }
}

