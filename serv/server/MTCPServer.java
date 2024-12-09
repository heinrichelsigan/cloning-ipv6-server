import java.net.*;
import java.io.*;
import java.util.*;

public class MTCPServer {

	final static int MAXCONNECT=256;			// MAXIMALE ANZAHL DER CONNECTIONS
	public static Integer Port;				// ServerPortn als Integer dient zum Einlesen
   	public static int i_port;				// int port
   	public String Hostname;					// Hostname des Servers oder IP
								// dient fuer Rechner mit mehreren DNS entries
   	stdConsole std = new stdConsole();			// Stdout und Stderr
	public static InetAddress inetadr;			// 
	public ServerSocket server;				// Socket und Internetadresse
	static Integer MailNumber;			
	static int i_mailnumber;
	public static volatile boolean SEMAPHORE;		// SEMAPHORE zur Sync
    public static volatile long EVENTCOUNTER;
	public static volatile boolean IS_AFTER_YOU_DEADLOCK;
	MTCPServer serverA;					// Server wird als uebergabeparameter 
    								// mitgegeben
    public static void main(String args[]){
	    MTCPServer serverA = new MTCPServer();
        serverA.handleArgs(args);
	    SEMAPHORE=true;					// SEMAPHORE = TRUE, wenn Zugriff auf
	    EVENTCOUNTER=0;					// das Mailfile freigegeben.
        serverA.run();						// FALSE, wenn ein Thread auf das
    }								// das File gerade zugreift
    
    public MTCPServer() {
        super();						// Konstruktor
    }

    public void run() {
        try {
	    inetadr = inetadr.getByName(Hostname);		// 
		} catch (UnknownHostException ex) {
	    	std.Err(0,"UnknownHostException: "+Hostname);
		}
		try {						// MAXIMALE ANZAHL AN SOCKETS
	    	ServerSocket server = new ServerSocket(i_port,MAXCONNECT,inetadr);
	    	int localPort = server.getLocalPort();
       	std.Out("Default Port: "+i_port);
       	std.Out("Host: "+inetadr.toString()+" listenonport"+localPort+".");
    	   	std.Out("Hostname: "+inetadr.toString()); 
          	do {						// FUER JEDEN NEUEN CLIENT DER ZUGREIFT
            	Socket client = server.accept();		// WIRD Neuer THREAD aufgemacht.
            	(new MTCPThread(serverA,client,std)).start();
       	} while(true);
     	} catch(IOException ex) {
       	std.Err(0,inetadr.toString()+" failed to listenon:"+Port.toString()+".");
    	}
   	}
    
	public void handleArgs(String args[]) {			// ARGUMENT HANDELING
   		if (args.length!=2) {
       	std.Err(1,"");
        }
        try {
            Port = new Integer(args[0]);
            Hostname = new String(args[1]);
            if (Hostname.length() > 255) {
                std.Err(2,"Hostname: 255 Characters maximum");    
            }
            i_port = Port.intValue();
            if (i_port<=1900 || i_port >=2300) {
                std.Err(2,"Port: 1900 + <gc>");
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException uebergabeEx) {
             std.Err(1,"");
        } catch (NumberFormatException uebergabeEx) {
            std.Err(3,"Port must be a numeric value: " + uebergabeEx.getMessage());
        }
	}
		
}

