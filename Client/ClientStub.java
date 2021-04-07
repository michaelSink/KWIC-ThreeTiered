import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientStub {

	private Socket socket;
	private PrintStream out;
	private BufferedReader in;
	

	public ClientStub(String ip, int port){
		try{
			Socket socket = new Socket(ip, port);

			out = new PrintStream( socket.getOutputStream() );
			in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

		}catch(Exception e){}
	}

	public void send(String data){

		System.out.println("Sending: " + data);
		out.println(data);

	}

	public String getResponse(){

		String response = "";
		try{
			
			String line = in.readLine();
			while(line != null && !line.isEmpty()){
				response += line + "\n";
				line = in.readLine();
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return response;
	}

	public void close(){

		try{
            in.close();
            out.close();
            socket.close();
		}catch(IOException e){
			
		}

	}

}