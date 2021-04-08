import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class dbManager implements DBInterface {

	private BufferedReader myReader;
	private FileWriter myWriter;

	public dbManager(){
		try{
			myWriter = new FileWriter("db.txt");
			myWriter.write("");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public String searchFromDatabase(String line){

		String response = "";

		String[] keywords = line.split(" ");

		try{
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
		}catch(Exception e){
			System.out.println(e);
		}

		return response;
	}

	@Override
	public void writeToDatabase(String entry) throws IOException{

		myWriter.write(entry);
		myWriter.flush();
	}

}