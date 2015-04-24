package jp.ac.tohoku.mech.irs.hapticpatterntest;

import android.util.Log;

import java.util.Random;

/**
 * Created by toyo on 10/03/2015.
 */
public class HapticPatternsController {
    private static final String TAG = HapticPatternsController.class.getSimpleName();
    static int numPatterns = 24;
    static int numQuestions = 10;
    int currentQuestion=0;
    PatternQuestion[] questions ;

    private static HapticPatternsController ourInstance = new HapticPatternsController();

    public static HapticPatternsController getInstance() {
        return ourInstance;
    }

    private HapticPatternsController() {
        questions = new PatternQuestion[numQuestions];
        for (int i=0;i<numQuestions; i++){
            questions[i] = new PatternQuestion();
        }
        currentQuestion = 0;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public PatternQuestion[] getQuestions() {
        return questions;
    }

    public void setQuestions(PatternQuestion[] questions) {
        this.questions = questions;
    }

    public void advanceQuestion() {
        this.currentQuestion++;
    }

    public int getNumPatterns() {
        return numPatterns;
    }

    public void setNumPatterns(int numPatterns) {
        this.numPatterns = numPatterns;
    }

    public void initializeQuestions(){
        boolean[] usedPatterns=new boolean[numPatterns];
        for(int i=0;i<numPatterns;i++){
            usedPatterns[i]=false;
        }

        for (int i=0; i<numQuestions;i++){
            int position;
            Random rand = new Random();
            do{
                position =rand.nextInt(numPatterns);
            }while(usedPatterns[position]==true);
            usedPatterns[position]=true;
            questions[i].setRightAnswer(position);
            int wrongPos;

            for (int j=0; j<PatternQuestion.numOptions; j++){
                do{
                    wrongPos =rand.nextInt(numPatterns);
                }while(questions[i].patternIsInOptions(position) || wrongPos == position);
                questions[i].setOptionAt(j,wrongPos);
            }
        }
        Log.d(TAG, "ans ; right ans ; opts" );
        for (int i=0; i<numQuestions;i++){
            Log.d(TAG, questions[i].writableAnswer());
        }
    }

}
