/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.IconHelper;
import core.Project;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author ltrask
 */
public class ProjectTreeItem extends TreeItem<Project> {

    private final MainController control;

    private final Project proj;

    private final int step;

    private final int subStep;

    private final TreeItem<Project>[] steps;

    private final TreeItem<Project>[] subSteps;

    public ProjectTreeItem(MainController control, Project proj, int step) {
        this(control, proj, step, -1);
    }

    public ProjectTreeItem(MainController control, Project proj, int step, int subStep) {
        super(proj);
        this.control = control;
        this.proj = proj;
        this.step = step;
        this.subStep = subStep;
        steps = new TreeItem[6];

        subSteps = new TreeItem[(step < 0 || subStep >= 0) ? 0 : Project.NUM_SUB_STEPS[step] + 1];
        if (step < 0) {
            final ProjectTreeItem selfRef = this;
            proj.getNameProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                }
            });
            generateChildren();
        } else if (subStep < 0) {
            generateSubSteps();
        }
    }

    private void generateChildren() {
        for (int stepIdx = 0; stepIdx < 6; stepIdx++) {
            //steps[stepIdx] = new ProjectTreeItem(proj, stepIdx);
            final ProjectTreeItem selfRef = new ProjectTreeItem(control, proj, stepIdx);
            steps[stepIdx] = selfRef;
            proj.getStep(stepIdx).stepStartedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                    selfRef.setExpanded(newVal);
                }
            });
            proj.getStep(stepIdx).stepFinishedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                    selfRef.setExpanded(!newVal);
                }
            });
            control.activeStepProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                }
            });
            this.getChildren().add(steps[stepIdx]);
        }
    }

    private void generateSubSteps() {
        for (int subStepIdx = 0; subStepIdx < proj.getStep(step).getNumSubSteps(); subStepIdx++) {
            //subSteps[subStepIdx] = new ProjectTreeItem(proj, step, subStepIdx);
            final ProjectTreeItem selfRef = new ProjectTreeItem(control, proj, step, subStepIdx);
            subSteps[subStepIdx] = selfRef;
            proj.getStep(step).getSubStep(subStepIdx).stepStartedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
//                    if (!steps[step].isExpanded()) {
//                        steps[step].setExpanded(true);
//                    }
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                }
            });
            proj.getStep(step).getSubStep(subStepIdx).stepFinishedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
//                    if (!steps[step].isExpanded()) {
//                        steps[step].setExpanded(true);
//                    }
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                }
            });
            control.activeStepProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                }
            });
            control.activeSubStepProperty(step).addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    Event.fireEvent(selfRef, new TreeModificationEvent<>(TreeItem.valueChangedEvent(), selfRef));
                }
            });
            this.getChildren().add(subSteps[subStepIdx]);
        }
    }

    public String getName() {
        if (step < 0) {
            return proj.getName();
        }
        if (subStep < 0) {
            return proj.getStep(step).getName();
        }
        return proj.getStep(step).getSubStep(subStep).getName();
    }

    public int getStep() {
        return step;
    }

    public boolean isStep() {
        return !(step < 0) && subStep < 0;
    }

    public int getSubStep() {
        return subStep;
    }

    public boolean isSubStep() {
        return !(subStep < 0);
    }

    public boolean isRoot() {
        return step < 0;
    }

    public boolean stepIsActive() {
        return control.getActiveStep() == step;
    }

    public boolean stepAndSubStepIsActive() {
        return control.getActiveStep() == step && control.getActiveSubStep(step) == subStep;
    }

    public static class ProjectTreeCell extends TreeCell<Project> {

        public ProjectTreeCell() {

        }

        @Override
        public void updateItem(Project item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                ProjectTreeItem pti = ((ProjectTreeItem) this.getTreeItem());
                setText(getString());
                setTooltip(MainController.getTooltip(pti.getStep(), pti.getSubStep()));
                if (pti.isRoot()) {
                    this.setContentDisplay(ContentDisplay.LEFT);
                } else {
                    this.setContentDisplay(ContentDisplay.RIGHT);
                }
                if (pti.stepAndSubStepIsActive()) {
                    this.getTreeView().getSelectionModel().select(pti);
                }
                if (pti.isRoot()) {
                    setGraphic(new ImageView(this.getTreeItem().isExpanded() ? IconHelper.TREE_NODE_PROJ_OPEN : IconHelper.TREE_NODE_PROJ_OPEN));
                } else if (pti.isStep()) {
                    if (pti.stepIsActive()) {
                        pti.setExpanded(true);
                    }
                    if (this.getItem().isStepStarted(pti.getStep())) {
                        setTextFill(Color.BLACK);
                    } else {
                        setTextFill(Color.GREY);
                    }
                    if (this.getItem().isStepComplete(pti.getStep())) {
                        setGraphic(new ImageView(IconHelper.TREE_NODE_STEP_COMPLETE));
                    } else {
                        setGraphic(null);
                    }
                } else if (pti.isSubStep()) {
                    if (this.getItem().isSubStepStarted(pti.getStep(), pti.getSubStep())) {
                        setTextFill(Color.BLACK);
                    } else {
                        setTextFill(Color.GRAY);
                    }
                    if (this.getItem().isSubStepFinished(pti.getStep(), pti.getSubStep())) {
                        setGraphic(new ImageView(IconHelper.TREE_NODE_STEP_COMPLETE));
                    } else {
                        setGraphic(null);
                    }
                }
            }
        }

        private String getString() {
            return ((ProjectTreeItem) this.getTreeItem()).getName();
        }
    }

    //private final int[] numSubSteps = {12, 7, 7, 7, 7, 7};
}
