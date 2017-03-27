/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author jlake
 */
public class QuestionOption extends Question {

    private final String[] options;

    private final int[] scores;

    public QuestionOption(int idx, String category, String text, String[] options) {
        this(idx, category, text, options, new int[options.length]);
    }

    public QuestionOption(int idx, String category, String text, String[] options, int[] scores) {
        super(idx, category, text);
        this.options = options;
        this.scores = scores;
    }

    @Override
    public String getAnswerString() {
        return options[this.responseIdx.get()];
    }

    @Override
    public void setAnswer(String ans) {
        for (int opIdx = 0; opIdx < options.length; opIdx++) {
            if (options[opIdx].equalsIgnoreCase(ans)) {
                this.responseIdx.set(opIdx);
                return;
            }
        }
        // If the answer is not found, set to -1 (unanswered)
        this.responseIdx.set(-1);
    }

    @Override
    public boolean isYes() {
        return false;
    }

    @Override
    public boolean isTypeYesNo() {
        return false;
    }

    public String getOption(int optIdx) {
        if (optIdx < 0 || optIdx >= options.length) {
            return "Select Answer";
        } else {
            return options[optIdx];
        }
    }

    public int getOptionScore() {
        if (responseIdx.get() >= 0 && scores != null) {
            return scores[responseIdx.get()];
        }
        return 0;
    }

    public String[] getOptions() {
        return options;
    }
}
