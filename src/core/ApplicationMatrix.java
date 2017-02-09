/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author jlake
 */
public class ApplicationMatrix {

    private LinkedHashMap<String, Integer> appToColMap;
    private LinkedHashMap<Question, Integer> questionToRowMap;
    private int[][] matrix;

    public ApplicationMatrix(ArrayList<Question> inputQuestions) {
        for (int qIdx = 0; qIdx < inputQuestions.size(); qIdx++) {
            questionToRowMap.put(inputQuestions.get(qIdx), qIdx);
        }
        matrix = new int[inputQuestions.size()][];
    }

    public ApplicationMatrix(ArrayList<Question> inputQuestions, ArrayList<Application> appTypes) {
        for (int qIdx = 0; qIdx < inputQuestions.size(); qIdx++) {
            questionToRowMap.put(inputQuestions.get(qIdx), qIdx);
        }
        for (int appIdx = 0; appIdx < inputQuestions.size(); appIdx++) {
            appToColMap.put(appTypes.get(appIdx).getName(), appIdx);
        }
        matrix = new int[inputQuestions.size()][appTypes.size()];
    }

    private int getScore(Question q, String appType) {
        return matrix[questionToRowMap.get(q)][appToColMap.get(appType)];
    }

}
