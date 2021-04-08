import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientStub implements ISender{

	private Socket socket;
	private PrintStream out;
	private BufferedReader in;
	

	public ClientStub(String ip, int port){
		try{
			Socket socket = new Socket(ip, port);

			out = new PrintStream( socket.getOutputStream() );
			in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

		}catch(Exception e){
			System.out.println(e);
			this.closeSocket();
		}
	}

	@Override
	public void send(String data){

		out.println(data);

	}

	@Override
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
			this.closeSocket();
		}
		return response;
	}

	@Override
	public void closeSocket(){

		try{
            in.close();
            out.close();
            socket.close();
		}catch(IOException e){
			System.out.println(e);
		}

	}

}