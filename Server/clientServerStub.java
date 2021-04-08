import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class clientServerStub implements IReceiver{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
	private BufferedReader in;
	private iController con;
	private ISender dbStub;

	public clientServerStub(int port){

		try{
			con = new MasterController();
			dbStub = new serverDBStub("127.0.0.1", 4371);
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
	public void handleExchange(){
		try{
			String line = in.readLine();
			while(line != null){
				if(line.equals("NOISE")){
					line = in.readLine();
					con.setNoiseWords(line);
					break;
				}else if(line.equals("DB")){
					String response = "";
					line = in.readLine();
					while(line != null && !line.isEmpty()){
						String[] text = line.split("[ \t]{2,}");
						response += text[1] + "\n";
						con.compute(text[0]);
						String computation = con.getOutput();
						dbStub.send(text[1] + "\n" + computation);
						response += computation;
						line = in.readLine();
					}
					out.flush();
					send(response);
					break;
				}else if(line.equals("KEYWORDS")){
					line = in.readLine();
					dbStub.send("KEYWORDS" + "\n" + line);
					String a = dbStub.getResponse();
					a += dbStub.getResponse();
					send(a);
					break;
				}
				line = in.readLine();
			}
		}catch(Exception e){
			System.out.println(e);
			this.closeSocket();
		}
		
	}

	@Override
	public void closeSocket(){
		try{
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void main(String[] args){

		clientServerStub s = new clientServerStub(4370);
		while(true){
			s.handleExchange();
		}

	}

}