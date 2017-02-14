/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ltrask
 */
public class Project {

    private static final int NUM_STEPS = 6;

    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty agency = new SimpleStringProperty();
    private final SimpleStringProperty analyst = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty limits = new SimpleStringProperty();
    private final SimpleStringProperty urlLink = new SimpleStringProperty();

    private File saveFile = null;

    private final Step[] steps;

    public Project() {
        this("New Project", null);
    }

    public Project(String projName) {
        this(projName, null);
    }

    public Project(String projName, File saveFile) {
        name.set(projName);
        this.saveFile = saveFile;
        steps = new Step[NUM_STEPS];
        for (int stepIdx = 0; stepIdx < NUM_STEPS; stepIdx++) {
            steps[stepIdx] = new Step("Step " + String.valueOf(stepIdx));
        }
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        this.name.set(newName);
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public SimpleStringProperty getAgencyProperty() {
        return agency;
    }

    public SimpleStringProperty getAnalystProperty() {
        return analyst;
    }

    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }

    public SimpleStringProperty getLimitsProperty() {
        return limits;
    }

    public SimpleStringProperty getUrlLinkProperty() {
        return urlLink;
    }

    public Project.Step getStep(int index) {
        return steps[index];
    }

    @Override
    public String toString() {
        return name.get();
    }

    public static class Step {

        private final String stepName;

        private String description;

        private final SimpleBooleanProperty stepStarted = new SimpleBooleanProperty(false);

        private final SimpleBooleanProperty stepFinished = new SimpleBooleanProperty(false);

        public Step(String stepName) {
            this.stepName = stepName;
        }

        public String getName() {
            return this.stepName;
        }

        @Override
        public String toString() {
            return getName();
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }

        public boolean isStepStarted() {
            return stepStarted.get();
        }

        public void setStepStarted(boolean stepStarted) {
            this.stepStarted.set(stepStarted);
        }

        public SimpleBooleanProperty stepStartedProperty() {
            return stepStarted;
        }

        public boolean isStepFinished() {
            return stepFinished.get();
        }

        public void setStepFinished(boolean stepFinished) {
            this.stepFinished.set(stepFinished);
        }

        public SimpleBooleanProperty stepFinishedProperty() {
            return stepFinished;
        }

    }

    public boolean isStepComplete(int stepIdx) {
        return steps[stepIdx].isStepFinished();
    }

    public boolean isStepStarted(int stepIdx) {
        return steps[stepIdx].isStepStarted();
    }

    public static class SubStep {

        private final String stepName;

        private String description;

        private final SimpleBooleanProperty stepStarted = new SimpleBooleanProperty(false);

        private final SimpleBooleanProperty stepFinished = new SimpleBooleanProperty(false);

        public SubStep(String stepName) {
            this.stepName = stepName;
        }

        public String getName() {
            return this.stepName;
        }

        @Override
        public String toString() {
            return getName();
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }

        public boolean isStepStarted() {
            return stepStarted.get();
        }

        public void setStepStarted(boolean stepStarted) {
            this.stepStarted.set(stepStarted);
        }

        public SimpleBooleanProperty stepStartedProperty() {
            return stepStarted;
        }

        public boolean isStepFinished() {
            return stepFinished.get();
        }

        public void setStepFinished(boolean stepFinished) {
            this.stepFinished.set(stepFinished);
        }

        public SimpleBooleanProperty stepFinishedProperty() {
            return stepFinished;
        }

    }

}
