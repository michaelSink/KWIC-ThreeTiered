import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.io.FileWriter;
import java.util.Vector;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dbServerStub{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
	private BufferedReader in;
	private HashMap<String, String> map;
	private FileWriter myWriter;
	private BufferedReader myReader;

	public dbServerStub(int port){

		try{
			map = new HashMap<String, String>();
			myWriter = new FileWriter("db.txt");
			myWriter.write("");
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void handleRequest(){
		Vector<String> v = new Vector<String>();

		try{
			String line = in.readLine();
			if(line.equals("KEYWORDS")){
				String response = "";

				line = in.readLine();
				System.out.println(line);

				String[] keywords = line.split(" ");
				myReader = new BufferedReader(new FileReader("db.txt"));
				
				String st;
				while((st = myReader.readLine()) != null){
					String desc = st.substring(st.indexOf("descriptor=") + 11, st.indexOf(",", st.indexOf("descriptor=") + 1));
					boolean match = true;
					for(int i = 0; i < keywords.length; i++){
						if(!desc.toLowerCase().contains(keywords[i].toLowerCase())){
							match = false;
							break;
						}
					}
					if(match){
						response += desc + " " + st.substring(st.indexOf("address=") + 8, st.indexOf(",")) + "\n";
					}
				}

				if(response.trim().isEmpty()){
					response = "Empty\n";
				}

				out.flush();
				out.println(response);
				out.println("");

			}else{

				if(line != null && !line.isEmpty()){
					map.put("address", line);
					line = in.readLine();
					//System.out.println(line);
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
					System.out.println(map.toString());
					myWriter.write(map.toString() + "\n");
					myWriter.flush();
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
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

		dbServerStub s = new dbServerStub(4371);
		while(true){
			s.handleRequest();
		}

	}
}