import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class dbServerStub implements IReceiver{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
	private BufferedReader in;
	private HashMap<String, String> map;
	private DBInterface dbManager;

	public dbServerStub(int port){

		try{
			dbManager = new dbManager();
			map = new HashMap<String, String>();
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
		Vector<String> v = new Vector<String>();

		try{
			String line = in.readLine();
			if(line.equals("KEYWORDS")){
				line = in.readLine();
				out.flush();
				send(dbManager.searchFromDatabase(line));
				send("");

			}else{

				if(line != null && !line.isEmpty()){
					map.put("address", line);
					line = in.readLine();
					while(line != null && !line.isEmpty()){
						v.add(line);
						line = in.readLine();
					}
					map.put("descriptor", v.remove(v.size() - 1));
					String a = "[";
					for(int i = v.size() - 1; i >= 0; i--){
						a += v.remove(i);
						if(i != 0){
							a += ", ";
						}
					}
					a += "]";
					map.put("lines", a);
					dbManager.writeToDatabase(map.toString() + "\n");
				}
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

		dbServerStub s = new dbServerStub(4371);
		while(true){
			s.handleExchange();
		}

	}
}