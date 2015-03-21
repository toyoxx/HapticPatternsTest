package jp.ac.tohoku.mech.irs.hapticpatterntest;

/**
 * Created by toyo on 10/03/2015.
 */
public class HapticPatternsController {
    int numPatterns = 20;



    private static HapticPatternsController ourInstance = new HapticPatternsController();

    public static HapticPatternsController getInstance() {
        return ourInstance;
    }

    private HapticPatternsController() {

    }

    public int getNumPatterns() {
        return numPatterns;
    }

    public void setNumPatterns(int numPatterns) {
        this.numPatterns = numPatterns;
    }
}
