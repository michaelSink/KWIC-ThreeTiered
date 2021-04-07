public class MasterController implements iController{

	private iLines lineStorage;
	private iOutput shifts;
	private iNoise noiseRemoval;
	private iOutput alph;

	public MasterController(){
		lineStorage = new LineStorage();
		shifts = new CircularShift();
		noiseRemoval = new NoiseRemoval();
		alph = new Alphebetize();
	}

	private void computePrivate(){
		shifts.setup(lineStorage);
		noiseRemoval.setup(shifts);
		alph.setup(noiseRemoval);
	}

	public void singleComputer(String line){
		if(line != null && !line.isEmpty()){
			lineStorage.addLine(line);
			computePrivate();
		}
	}

	@Override
	public void compute(String lines) {
		String[] inputData = lines.split("\n");
		for(int i = 0; i < inputData.length; i++){
			if(inputData[i] != null && !inputData[i].isEmpty()){
				lineStorage.addLine(inputData[i]);
			}
		}
		if(inputData.length > 0){
			computePrivate();
		}
	}

	@Override
	public String getOutput() {
		return alph.getOutput();
	}

	@Override
	public void setNoiseWords(String words) {
		String[] noiseWords = words.split(",");
		for(int i = 0; i < noiseWords.length; i++){
			noiseRemoval.addNoiseWord(noiseWords[i].toLowerCase().trim());
		}
	}

	@Override
	public String getCircularOutput() {
		return shifts.getOutput();
	}

	@Override
	public void resetNoiseWords() {
		noiseRemoval.resetNoiseWords();
	}

}