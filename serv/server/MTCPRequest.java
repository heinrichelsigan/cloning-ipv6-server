/* MTCPRequest.java
   class who handels several TCP-Requests
*/ 
import java.io.*;
import java.util.*;
import java.lang.*;

class MTCPRequest {
    Vector lines = new Vector();
    final static String SNDRQ = "<p><b>"; 
    
    public MTCPRequest() {
    }
    
    public void addLine(String line) {
        lines.addElement(line);
    }

    public boolean isSendRequest()  {		
// Stellt fest, ob der Request ein SendRequest ist 
        String firstLine = (String) lines.elementAt(0);
        if (firstLine.substring(0,6).equals(SNDRQ)){
		    System.out.println("SEND REQUEST TRUE");
	        return true;
	    }
        return false;
    }


    String getmName(int mNameIndex) {
// gibt den Substring nach dem "=" des Element 'mNameIndex' des Vektors zurueck
        String XName=new String("");
        if(lines.size()>(mNameIndex)) {
            String indexLine = (String) lines.elementAt(mNameIndex);
            XName = indexLine.substring(indexLine.indexOf("=")+1);
	 	    System.err.println("XNAME: "+XName);	
        }            
        return XName;
    }
    
    String getData() {
        return "";
    }
    
    void log() {
        System.err.println("Received the following request:");
        for(int i=0;i<lines.size();++i)
            System.out.println((String) lines.elementAt(i));
    }
}

