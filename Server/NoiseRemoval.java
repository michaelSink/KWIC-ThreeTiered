import java.util.ArrayList;

public class NoiseRemoval implements iNoise{

	private ArrayList<String> noiseLines;
	private ArrayList<String> noiseWords;

	public NoiseRemoval(){
		noiseWords = new ArrayList<String>();
		noiseLines = new ArrayList<String>();
	}

	@Override
	public boolean hasNext() {
		return !noiseLines.isEmpty();
	}

	@Override
	public String getLine() {
		return noiseLines.remove(0);
	}

	@Override
	public void addNoiseWord(String word) {
		if(word != null && !word.isEmpty()){
			noiseWords.add(word);
		}
	}

	@Override
	public void setup(iModule mod) {
		while(mod.hasNext()){
			String data = mod.getLine();
			if(noiseWords.size() == 0 || !noiseWords.contains(data.substring(0, data.indexOf(" ")).toLowerCase())){
				noiseLines.add(data);
			}
		}
	}

	@Override
	public void resetNoiseWords() {
		noiseWords.clear();
	}

}