import java.io.IOException;

public interface DBInterface {

	public String searchFromDatabase(String line);
	public void writeToDatabase(String entry) throws IOException;

}