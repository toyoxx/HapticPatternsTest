package jp.ac.tohoku.mech.irs.hapticpatterntest;

/**
 * Created by toyo on 05/04/2015.
 */
public class PatternQuestion {


    public static int numOptions = 3;
    int rightAnswer;
    int[] wrongOptions = new int[numOptions];
    int userAnswer;
    public PatternQuestion(){
        rightAnswer = -1;
        userAnswer = -1;
        for(int i=0;i<numOptions;i++){
            wrongOptions[i]=-1;
        }
    }


    public PatternQuestion(int rightAnswer, int[] options, int userAnswer) {
        this.rightAnswer = rightAnswer;
        this.wrongOptions = options;
        this.userAnswer = userAnswer;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int[] getOptions() {
        return wrongOptions;
    }

    public void setOptions(int[] options) {
        this.wrongOptions = options;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public void setOptionAt(int n, int value){
        if (n<numOptions)
            this.wrongOptions[n]= value;
    }
    public String writableAnswer(){
        StringBuilder sb = new StringBuilder(userAnswer+";");
        sb.append(rightAnswer+";");
        for(int i=0;i<numOptions;i++){
            sb.append(wrongOptions[i]+";");
        }
        return (sb.toString());
    }

    boolean patternIsInOptions(int n){
        boolean isIn = false;
        for(int i=0;i<numOptions;i++){
            if(wrongOptions[i]==n){
                isIn = true;
                break;
            }
        }
        return isIn;
    }
}
