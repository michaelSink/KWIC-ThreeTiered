import java.util.ArrayList;
import java.util.Collections;

import comparator.*;

public class Alphebetize implements iOutput{

	private ArrayList<String> sortedLines;

	private void sort(){
		Collections.sort(sortedLines, new stringComparator());
	}

	public Alphebetize(){
		sortedLines = new ArrayList<String>();
	}

	@Override
	public void setup(iModule mod) {
		while(mod.hasNext()){
			sortedLines.add(mod.getLine());
		}
		
		sort();
	}

	@Override
	public boolean hasNext() {
		return !sortedLines.isEmpty();
	}

	@Override
	public String getLine() {
		return sortedLines.remove(0);
	}

	@Override
	public String getOutput() {
		String data = "";
		while(hasNext()){
			data += getLine() + "\n";
		}
		return data;
	}

}