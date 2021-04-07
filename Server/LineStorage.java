import java.util.ArrayList;

public class LineStorage implements iLines{

	private ArrayList<String> lines;

	public LineStorage(){
		lines = new ArrayList<String>();
	}

	@Override
	public boolean hasNext() {
		return !lines.isEmpty();
	}

	@Override
	public String getLine() {
		return lines.remove(0);
	}

	@Override
	public void addLine(String data) {
		lines.add(data);
	}

}