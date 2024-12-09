import java.net.*;
import java.io.*;
import java.util.*;

class MTCPInputStream extends FilterInputStream {

    final static int T_EOF = -1;
    final static int T_CR  = 13;
    final static int T_LF  = 10;
    final static int T_BS  = 8;
    final static int LINEMAX = 1024;

    public MTCPInputStream(InputStream in) {
        super(in);
    }
    public String readLine() throws IOException {
        StringBuffer result=new StringBuffer();
        boolean finished = false;
        boolean cr = false;
        do {
            int ch = T_EOF;
            ch = read();
            if(ch==T_EOF) 
                return result.toString();
            result.append((char) ch);
            if(cr && ch==T_LF){
                result.setLength(result.length()-2);
                return result.toString();
            }
            if(ch==T_CR) cr = true;
            else cr=false;
        } while (!finished);
        return result.toString();
    }
    
    public MTCPRequest getRequest() throws IOException {
        MTCPRequest request = new MTCPRequest();
        String line;	
		int icc=0;
        do {
            line = readLine();
			icc++;
	        if (icc>LINEMAX) return request;
			if(line.length()>0) request.addLine(line);
				else return request;
        }while(true);
    }
}
