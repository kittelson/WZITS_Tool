/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class GoalNeedsMatrix {

    private final ObservableList<QuestionYN> qList;

    private final ObservableList<Need> needsList;

    private final LinkedHashMap<Question, Integer> qToRowMap = new LinkedHashMap();

    private final LinkedHashMap<Need, Integer> needToColMap = new LinkedHashMap();

    private int[][] matrix;

    public GoalNeedsMatrix(ObservableList<QuestionYN> qList, ObservableList<Need> needsList) {
        this.qList = qList;
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            qToRowMap.put(qList.get(qIdx), qIdx);
        }
        this.needsList = needsList;
        for (int needIdx = 0; needIdx < qList.size(); needIdx++) {
            needToColMap.put(needsList.get(needIdx), needIdx);
        }

        matrix = new int[qList.size()][needsList.size()];

        loadDefault();

    }

    private void loadDefault() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/goalNeedsDefaultMatrix.csv")));
            String line = br.readLine();
            String[] tokens;
            int rowIdx = 0;
            while (line != null) {
                tokens = line.split(",");
                for (int colIdx = 0; colIdx < tokens.length; colIdx++) {
                    matrix[rowIdx][colIdx] = Integer.parseInt(tokens[colIdx]);
                }
                br.readLine();
                rowIdx++;
            }
        } catch (IOException e) {

        }
    }

}
