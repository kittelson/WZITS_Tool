/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.MainController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ltrask
 */
public class Stakeholder implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private IntegerProperty idx = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty score = new SimpleIntegerProperty();
    private BooleanProperty coreTeamMember = new SimpleBooleanProperty();
    private BooleanProperty stakeholder = new SimpleBooleanProperty();
    private BooleanProperty notApplicable = new SimpleBooleanProperty();

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
    private StringProperty email = new SimpleStringProperty();

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
    private StringProperty phone = new SimpleStringProperty();

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }
    private StringProperty contactName = new SimpleStringProperty();

    public String getContactName() {
        return contactName.get();
    }

    public void setContactName(String value) {
        contactName.set(value);
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }
    private StringProperty comment = new SimpleStringProperty();

    public String getComment() {
        return comment.get();
    }

    public void setComment(String value) {
        comment.set(value);
    }

    public StringProperty commentProperty() {
        return comment;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeBoolean(coreTeamMember.get());
        s.writeObject(getEmail());
        s.writeInt(getIdx());
        s.writeObject(getName());
        s.writeBoolean(notApplicable.get());
        s.writeObject(getPhone());
        s.writeInt(getScore());
        s.writeBoolean(stakeholder.get());
        s.writeObject(getContactName());
        s.writeObject(getComment());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        coreTeamMember = new SimpleBooleanProperty(s.readBoolean());
        email = new SimpleStringProperty((String) s.readObject());
        idx = new SimpleIntegerProperty(s.readInt());
        name = new SimpleStringProperty((String) s.readObject());
        notApplicable = new SimpleBooleanProperty(s.readBoolean());
        phone = new SimpleStringProperty((String) s.readObject());
        score = new SimpleIntegerProperty(s.readInt());
        stakeholder = new SimpleBooleanProperty(s.readBoolean());
        try {
            contactName = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            contactName = new SimpleStringProperty("");
        }
        try {
            comment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            comment = new SimpleStringProperty("");
        }
    }

    public static void editInfo(Stakeholder sh) {
        GridPane gp = new GridPane();
        Label l1 = new Label("Email: ");
        TextField emailTF = new TextField(sh.getEmail());
        emailTF.setPadding(new Insets(0, 0, 0, 5));
        Label l2 = new Label("Phone #: ");
        TextField phoneTF = new TextField(sh.getPhone());
        phoneTF.setPadding(new Insets(0, 0, 0, 5));
        Label l3 = new Label("Contact Name: ");
        TextField contactNameTF = new TextField(sh.getContactName());
        contactNameTF.setPadding(new Insets(0, 0, 0, 5));
        Label l4 = new Label("Comment: ");
        TextField commentTF = new TextField(sh.getComment());
        commentTF.setPadding(new Insets(0, 0, 0, 5));
        gp.add(new Label(sh.getName()), 0, 0, 2, 1);
        gp.add(l1, 0, 1);
        gp.add(emailTF, 1, 1);
        gp.add(l2, 0, 2);
        gp.add(phoneTF, 1, 2);
        gp.add(l3, 0, 3);
        gp.add(contactNameTF, 1, 3);
        gp.add(l4, 0, 4);
        gp.add(commentTF, 1, 4);
        Alert al = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK, ButtonType.CANCEL);
        al.initOwner(MainController.getWindow());
        al.setTitle("Enter/Edit Stakeholder Contact Information");
        al.setHeaderText("Enter/Edit Stakeholder Contact Information");
        al.getDialogPane().setContent(gp);
        Optional<ButtonType> result = al.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sh.setEmail(emailTF.getText());
            sh.setPhone(phoneTF.getText());
            sh.setContactName(contactNameTF.getText());
            sh.setComment(commentTF.getText());
        }
    }

    public static final int CORE_TEAM = 0;
    public static final int STAKEHOLDER = 1;
    public static final int NA = 2;

}
