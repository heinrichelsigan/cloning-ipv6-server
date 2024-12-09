/*
    class stdConsole
    Debugging Messages
*/
public class stdConsole {
        
    public stdConsole() {
    }

    public void Err (int exitCode, String errMes) {
        if ((exitCode>0)&& (exitCode<10))
            System.err.println("Usage: mtcpServer [port] [hostname]");
        System.err.println(errMes);           
        if (exitCode>=0) {
	    System.err.println("Exit: "+exitCode);
            System.exit(exitCode);
	    }
	}

    public void Out(String outMes) {
        System.out.println(outMes);           
	}
}
