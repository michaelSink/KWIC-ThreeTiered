import java.util.ArrayList;

public class CircularShift implements iOutput{

	private ArrayList<String> csLines;
	private String circularOutput;

	private void shiftString(String[] inputArray){
		String temp = inputArray[0];
		for(int i = 0; i < inputArray.length - 1; i++){
			inputArray[i] = inputArray[i + 1];
		}
		inputArray[inputArray.length - 1] = temp;
	}

	public CircularShift(){
		csLines = new ArrayList<String>();
	}

	@Override
	public String getOutput() {
		return circularOutput;
	}

	@Override
	public void setup(iModule mod) {
		circularOutput = "";
		while(mod.hasNext()){
			String data = mod.getLine();
			csLines.add(data);
			circularOutput += data + "\n";
			String out = "";
			String[] dataArray = data.split(" ");
			for(int i = 1; i < dataArray.length; i++){
				shiftString(dataArray);
				out = String.join(" ", dataArray);
				csLines.add(out);
				circularOutput += out + "\n";
			}
		}
	}

	@Override
	public boolean hasNext() {
		return !csLines.isEmpty();
	}

	@Override
	public String getLine() {
		return csLines.remove(0);
	}

}