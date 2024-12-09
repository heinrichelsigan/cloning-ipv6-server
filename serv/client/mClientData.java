import java.util.*;
import java.net.*;
import java.io.*;

public class mClientData {
    final static String HNAME="212.17.83.125";
    final static int HPORT=1930;
    
    static Vector vStack = new Vector();    // Zwischenspeicher für Daten
    Socket connection;                      // Socket
    FilterOutputStream outStream;           // Socket Output
    BufferedReader inStream;                // Socket Input
    StringBuffer outPut;                    // Buffer für
    StringBuffer inPut;                     // Ein- und Ausgabe.
    int inByte;                             // dient zum Lesen von den Streams
    final static int T_EOF = -1; final static int T_CR  = 13;
    final static int T_LF  = 10; final static int T_BS  = 8;
    final static int T_TAB = ((int)('\t')); // Zeichen des 7bit ASCII-Code,
    final static int T_SP = ((int)(' '));   // die gesondert behandelt werden
        
    public mClientData() {                    // Konstruktor
    }
    
    public void addIn(String line) {        // dem Vektor ein Element hinzufügen
        vStack.addElement(line);
    }
    
    public boolean pushOut() {
        try{                                      
            connection = new Socket(HNAME,HPORT);
        } catch (UnknownHostException ex){      return false;
        } catch (IOException ex){ return false;
        }
        try{
            inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            outStream = new FilterOutputStream(connection.getOutputStream());
        }catch (IOException ex){ return false;
        }   
    
        try {
            for (Enumeration er = this.vStack.elements() ; er.hasMoreElements() ;) {
               outPut = new StringBuffer(er.nextElement().toString());
                for (int ix=0; ix<outPut.length(); ix++) {
                    switch (outPut.charAt(ix)) {
                        case T_CR:
                        case T_BS: break; // ingnore Backspace
                        case T_TAB: // Tab and ENTER -> SPACE
                        case T_LF: outStream.write((int)'\n');
                            break;
                        default: 
                            outStream.write((int)outPut.charAt(ix));                                 
                    }
                }
                outStream.write(T_CR); outStream.write(T_LF);
                outStream.flush();
            }
        }    
        catch (IOException ex) {  return false;
        }
        try {
            outStream.write(T_CR); outStream.write(T_LF);
            outStream.flush();
        } catch (IOException ex){
        }
        inPut=new StringBuffer();
        boolean finished = false;
        do {
            try {
                inByte = inStream.read();
                switch (inByte) {
                    case T_EOF:
                    case T_CR: 
                    case T_LF: finished=true; break;
                    case T_BS: ; break; //IGNORE BACKSPACE
                    default: 
                        inPut.append((char) inByte);
                }
            }catch (IOException ex){
                finished=true; // NO RETURN FALUE
            }                  // MAYBE GOT ACK 

        }while (!finished); 
        try{
            connection.close();
        }catch (IOException ex){
        }
        String uuu= new String(inPut.toString());
        if (uuu.substring(0,6).equals("SNDACK"))        
            return true;
        return false;
    }
}
