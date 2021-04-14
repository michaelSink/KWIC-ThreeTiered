
public interface iController {
	public void compute(String lines);
	public String getOutput();
	public void setNoiseWords(String words);
	public boolean hasNoiseWord(String word);
	public String getCircularOutput();
	public void resetNoiseWords();
}