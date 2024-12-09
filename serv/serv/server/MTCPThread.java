import java.net.*;
import java.io.*;
import java.util.*;


class MTCPThread extends Thread {   
    Socket client;					// Socket des Clients
    MTCPServer serverA;					// Server wird als Parameter uebergeben
							// wegen der gemeinsamen SEMAPHORE 
    stdConsole std;					// stdCOnsole fuer stdout, stderr
	final static String FILENAME="userx.html";	// NAME DES MAILFILES	
	mvFile xFile;

	BufferedWriter writer;				// Writer um gesendete Nachrichten
    
    public MTCPThread(MTCPServer serverA, Socket client, stdConsole std) {
        this.client = client;
        this.std = std;
    }
    public void run() {
        try {
            if (serverA.EVENTCOUNTER>=65535)		// nicht gescheit ausprogrammierter
                serverA.EVENTCOUNTER=0;			// EVENTCOUNTER ;(
            describeConnection(client);
            MTCPOutputStream outStream = new MTCPOutputStream(
                new BufferedOutputStream(client.getOutputStream()));
            MTCPInputStream inStream = new MTCPInputStream(client.getInputStream());
            
	    MTCPRequest request = inStream.getRequest();
            request.log();
/* 	SEND REQUEST	
*/
            if(request.isSendRequest()) {
                processSendRequest(request,outStream);
                std.Out("SEND Request completed. Closing connection.");
            }            
/* Thread is tot hier, Semaphore wird freigegeben
*/
            if (isAlive()==false) {
                // Free Semaphore
                serverA.SEMAPHORE=true;
            }
        }catch(IOException ex) {
	        serverA.SEMAPHORE=true;
            std.Err(0,"IOException occured when processing request.");
        }
        try {
            client.close();
        }catch(IOException ex) {
	        serverA.SEMAPHORE=true;
            std.Err(0,"IOException occured when closing socket.");
        }
    }

/*	 Methode die die Connection beschreibt 
*/
    void describeConnection(Socket client) {
        String destName = client.getInetAddress().getHostName();
        String destAddr = client.getInetAddress().getHostAddress();
        int destPort = client.getPort();
        std.Out("Accepted connection to "+destName+" ("+destAddr+")"+" on port "+destPort+".");
    }
/*	Methode die auf den OutputStream ein spezifisches ACK oder NACK 
	schreibt, sodass der Client erfaehrt ob der Request vom Server erfuellt
	werden konnte, bzw. wo der Fehler war
*/    
    void sendCK(String ACKNACK, MTCPOutputStream outStream) {
        try {
            outStream.println(ACKNACK);
            outStream.flush();
            outStream.close();
        } catch (IOException ex) {
	        serverA.SEMAPHORE=true;
            std.Err(0,"IOError writing Socket.");
        }
    }
/*	Methode, die den Sendrequest 
*/
    void processSendRequest(MTCPRequest request,MTCPOutputStream outStream) {
	    while(serverA.SEMAPHORE==false) {
	        try {
	            sleep(100);
	        } catch (InterruptedException IntEx) {
	        }
	    }
	    serverA.SEMAPHORE=false; serverA.EVENTCOUNTER++;
	    System.err.println("Lock Semaphore");
	    try { // OPEN MAILFILE
	        writer = new BufferedWriter(new FileWriter(FILENAME,true));
        } catch(IOException ex) { 
            sendCK("NACK",outStream);
	        serverA.SEMAPHORE=true;
            std.Err(0,"Unable to open Mailfile!");
        }      
        try { //WRITE +SNDTO=$USERNAME
            writer.write((String) request.lines.elementAt(0));
            writer.newLine();
        } catch(IOException ex){
            sendCK("NCKWRF",outStream);
	        serverA.SEMAPHORE=true;
            std.Err(0,"Unable to write to Mailfile!");
        }
    	try { //WRITE TIMESTAMP
		    writer.write("<p>Time: <u>"+new Date().toString()+"</u></p>");
		    writer.newLine();
	    } catch(IOException ex) {
		    sendCK("NCKSTP",outStream);
	        serverA.SEMAPHORE=true;
		    std.Err(0,"Unable to write Timestamp");
	    }
        for (int ijk = 1; ijk < request.lines.size(); ijk++) {
            try { // WRITE REST OF MAIL
               writer.write((String) request.lines.elementAt(ijk));
               writer.newLine();
            } catch(IOException ex){
                sendCK("NCKWRF",outStream);
	            serverA.SEMAPHORE=true;
                std.Err(0,"Unable to write to Mailfile!");
            }
        }        
        try { // FLUSH OUT && CLOSE FILE
            writer.flush();
            writer.close();
        } catch(IOException ex){
            sendCK("NCKCLF",outStream);
	        serverA.SEMAPHORE=true;
            std.Err(0,"Unable to write to Mailfile!");
        }
        sendCK("SNDACK",outStream);   
	xFile = new mvFile();
	xFile.processFile();
	serverA.SEMAPHORE=true;
    }

}



