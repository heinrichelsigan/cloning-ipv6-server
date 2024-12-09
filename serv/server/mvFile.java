/*
	delFile: Klasse die das Mailfile durchbrowst 
	alles bis auf die zu löschende Mail in ein
	anderes temporaeres file hineinschreibt und
	dann aus dem file wieder -> ins Mailfile reinschr.

*/

import java.net.*;
import java.io.*;
import java.util.*;


class mvFile {
    final static String INFILENAME="userx.html";
    final static String OUTFILENAME="x.html";
    String inPut;
    String outPut;
    int INTERNALCOUNTER;
    
    public mvFile() {
    }

    public void processFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INFILENAME));
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTFILENAME,false));
            String line;
	    writer.write("<HTML><TITLE>Messageboard</TITLE></HEAD>");
	    writer.newLine();
	    writer.write("<BODY bgcolor=\"#000000\" text=\"#FFFFFF\" link=\"#6666FF\" vlink=\"#66FF66\" alink=\"#6666FF\">");
	    writer.newLine();
	    writer.write("<H1>Schwarzes Brett</H1>");
	    writer.newLine();

            while((line=reader.readLine())!=null)
            {
            	writer.write(line);
                writer.newLine();
            }
            reader.close();

	    writer.write("<hr></BODY></HTML>");
	    writer.newLine();
            writer.close();
        }catch(Exception ex) {
            System.out.println("Unable to proces file");
	    return ;
        }
    }
       
}

            
