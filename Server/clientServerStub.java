import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class clientServerStub {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
	private BufferedReader in;
	private MasterController con;
	private serverDBStub dbStub;

	public clientServerStub(int port){

		try{
			con = new MasterController();
			dbStub = new serverDBStub("127.0.0.1", 4371);
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void handleRequest(){
		try{
			String line = in.readLine();
			while(line != null){
				System.out.println(line);
				if(line.equals("NOISE")){
					line = in.readLine();
					con.setNoiseWords(line);
					break;
				}else if(line.equals("DB")){
					String response = "";
					line = in.readLine();
					while(line != null && !line.isEmpty()){
						System.out.println(line);
						String[] text = line.split("[ \t]{2,}");
						response += text[1] + "\n";
						con.compute(text[0]);
						String computation = con.getOutput();
						dbStub.send(text[1] + "\n" + computation);
						response += computation;
						line = in.readLine();
					}
					out.flush();
					out.println(response);
					break;
				}else if(line.equals("KEYWORDS")){
					line = in.readLine();
					dbStub.send("KEYWORDS" + "\n" + line);
					String a = dbStub.getResponse();
					a += dbStub.getResponse();
					System.out.println("Resp: " + a);
					out.println(a);
					break;
				}
				line = in.readLine();
			}
		}catch(Exception e){
			System.out.println(e);
		}
		// String response = "";
		// try{
		// 	String line = in.readLine();
		// 	if(line.equals("KEYWORDS")){
		// 		line = in.readLine();
		// 		dbStub.send("KEYWORDS" + "\n" + line);
		// 		response += dbStub.getResponse();
		// 		//System.out.println("Here 2");
		// 		//System.out.println("Resp: " + response);
		// 		//out.println("Yes");
		// 	}else if(line.equals("DB")){
		// 		line = in.readLine();

		// 		while(line != null && !line.isEmpty()){
		// 			String[] text = line.split("[ \t]{2,}");
		// 			response += text[1] + "\n";
		// 			con.compute(text[0]);
		// 			String computation = con.getOutput();
		// 			//dbStub.send(text[1] + "\n" + computation);
		// 			//System.out.println("DB Output");
		// 			//System.out.println(dbStub.getResponse());
		// 			//System.out.println("reading");
		// 			//response += computation;
		// 			line = in.readLine();
		// 		}
		// 	}else if(line.equals("NOISE")){
		// 		line = in.readLine();
		// 		con.setNoiseWords(line);
		// 		//line = in.readLine();
		// 		//System.out.println(line);
		// 	}
		// 	//in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		// }catch(Exception e){
		// 	System.out.println(e);
		// 	this.close();
		// }

		// System.out.println("Oi: " + response);
		// out.println(response);
		// out.flush();
		
	}

	public void close(){
		try{
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		}catch(Exception e){}
	}

	public static void main(String[] args){

		clientServerStub s = new clientServerStub(4370);
		while(true){
			s.handleRequest();
		}

	}

}