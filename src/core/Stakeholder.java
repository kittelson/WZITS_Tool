/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author ltrask
 */
public class Stakeholder {

    private final IntegerProperty idx = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final BooleanProperty coreTeamMember = new SimpleBooleanProperty();
    private final BooleanProperty stakeholder = new SimpleBooleanProperty();
    private final BooleanProperty notApplicable = new SimpleBooleanProperty();

    public Stakeholder(int idx, String name, int score) {
        this.idx.set(idx);
        this.name.set(name);
        this.score.set(score);

        coreTeamMember.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    stakeholder.set(false);
                    notApplicable.set(false);
                }
            }
        });
        stakeholder.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    coreTeamMember.set(false);
                    notApplicable.set(false);
                }
            }
        });

        notApplicable.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    coreTeamMember.set(false);
                    stakeholder.set(false);
                }
            }
        });
    }

    public int getIdx() {
        return idx.get();
    }

    public void setIdx(int value) {
        idx.set(value);
    }

    public IntegerProperty idxProperty() {
        return idx;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int value) {
        score.set(value);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public boolean isCoreTeamMember() {
        return coreTeamMember.get();
    }

    public void setCoreTeamMember(boolean value) {
        coreTeamMember.set(value);
    }

    public BooleanProperty coreTeamMemberProperty() {
        return coreTeamMember;
    }

    public boolean isStakeholder() {
        return stakeholder.get();
    }

    public void setStakeholder(boolean value) {
        stakeholder.set(value);
    }

    public BooleanProperty stakeholderProperty() {
        return stakeholder;
    }

    public boolean isNotApplicable() {
        return notApplicable.get();
    }

    public void setNotApplicable(boolean value) {
        notApplicable.set(value);
    }

    public BooleanProperty notApplicableProperty() {
        return notApplicable;
    }
    private final StringProperty email = new SimpleStringProperty();

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
    private final StringProperty phone = new SimpleStringProperty();

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public static final int CORE_TEAM = 0;
    public static final int STAKEHOLDER = 1;
    public static final int NA = 2;

}
