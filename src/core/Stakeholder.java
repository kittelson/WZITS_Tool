/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Helper.NodeFactory;
import GUI.MainController;

import java.io.*;
import java.util.HashSet;
import java.util.Optional;

import com.jfoenix.controls.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

    public boolean hasHL = false;
    public Node hl = new Hyperlink("?");

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

    public Stakeholder(int idx, String name, int score, Node hl) {
        this(idx, name, score);
        this.hl = hl;
        hl.getStyleClass().add("wz-input-hyperlink");
        ((Hyperlink) hl).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                TextInputDialog tid = new TextInputDialog(getName());
                tid.setTitle("Stakeholder Name");
                tid.setHeaderText("Specify new Stakeholder Name");
                tid.showAndWait();
                String newName = tid.getResult();
                setName(newName);
            }
        });
        this.hasHL = true;
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
        try {
            hasHL = s.readBoolean();
            hl = new Hyperlink((String) s.readObject());
            hl.getStyleClass().add("wz-input-hyperlink");
            ((Hyperlink) hl).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    TextInputDialog tid = new TextInputDialog(getName());
                    tid.setTitle("Stakeholder Name");
                    tid.setHeaderText("Specify new Stakeholder Name");
                    tid.showAndWait();
                    String newName = tid.getResult();
                    setName(newName);
                }
            });
        } catch (EOFException e) {
            // Future
        }
    }

    public static void editInfo(Stakeholder sh) {
        if (sh.getScore() >= 0) {
            fireEditExistingInfo(sh);
        } else {
            editCustomInfo(sh);
        }
    }

//    public static void editExistingInfo(Stakeholder sh) {
//        GridPane gp = new GridPane();
//        Label l1 = new Label("Email: ");
//        TextField emailTF = new TextField(sh.getEmail());
//        emailTF.setPadding(new Insets(0, 0, 0, 5));
//        Label l2 = new Label("Phone #: ");
//        TextField phoneTF = new TextField(sh.getPhone());
//        phoneTF.setPadding(new Insets(0, 0, 0, 5));
//        Label l3 = new Label("Contact Name: ");
//        TextField contactNameTF = new TextField(sh.getContactName());
//        contactNameTF.setPadding(new Insets(0, 0, 0, 5));
//        Label l4 = new Label("Comment: ");
//        TextField commentTF = new TextField(sh.getComment());
//        commentTF.setPadding(new Insets(0, 0, 0, 5));
//        gp.add(new Label(sh.getName()), 0, 0, 2, 1);
//        gp.add(l1, 0, 1);
//        gp.add(emailTF, 1, 1);
//        gp.add(l2, 0, 2);
//        gp.add(phoneTF, 1, 2);
//        gp.add(l3, 0, 3);
//        gp.add(contactNameTF, 1, 3);
//        gp.add(l4, 0, 4);
//        gp.add(commentTF, 1, 4);
//        Alert al = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK, ButtonType.CANCEL);
//        al.initOwner(MainController.getWindow());
//        al.setTitle("Enter/Edit Stakeholder Contact Information");
//        al.setHeaderText("Enter/Edit Stakeholder Contact Information");
//        al.getDialogPane().setContent(gp);
//        Optional<ButtonType> result = al.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            sh.setEmail(emailTF.getText());
//            sh.setPhone(phoneTF.getText());
//            sh.setContactName(contactNameTF.getText());
//            sh.setComment(commentTF.getText());
//        }
//    }
    public static void fireEditExistingInfo(Stakeholder sh) {
        GridPane contentGrid = new GridPane();
        Label lblModalHeading = new Label();
        JFXTextField txtEmailAddr = new JFXTextField(sh.getEmail());
        JFXTextField phoneTF = new JFXTextField(sh.getPhone());
        JFXTextField contactNameTF = new JFXTextField(sh.getContactName());
        JFXTextField commentTF = new JFXTextField(sh.getComment());
        JFXButton btnOk = new JFXButton("OK");
        JFXButton btnCancel = new JFXButton("Cancel");
        Label l1 = new Label("Email: ");
        Label l2 = new Label("Phone #: ");
        Label l3 = new Label("Contact Name: ");
        Label l4 = new Label("Comment: ");

        contentGrid.add(l3,0,0);
        contentGrid.add(txtEmailAddr,1,0);
        contentGrid.add(l2,0,1);
        contentGrid.add(phoneTF,1,1);
        contentGrid.add(l1,0,2);
        contentGrid.add(contactNameTF,1,2);
        contentGrid.add(l4,0,3);
        contentGrid.add(commentTF,1,3);
        contentGrid.setHgap(15);
        contentGrid.setVgap(15);

        lblModalHeading.setText("Enter/Edit Stakeholder Contact Information");
        lblModalHeading.getStyleClass().add("stackholder-modal-title");
        l1.getStyleClass().add("stackholder-modal-labels");
        l2.getStyleClass().add("stackholder-modal-labels");
        l3.getStyleClass().add("stackholder-modal-labels");
        l4.getStyleClass().add("stackholder-modal-labels");
        btnOk.getStyleClass().add("modal-pane-button");
        btnCancel.getStyleClass().add("comment-pane-buttonClose");
        StackPane rootStackPane = MainController.getRootStackPane();

        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootStackPane,content, JFXDialog.DialogTransition.CENTER);
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sh.setEmail(txtEmailAddr.getText());
                sh.setPhone(phoneTF.getText());
                sh.setContactName(contactNameTF.getText());
                sh.setComment(commentTF.getText());
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.close();
            }
        });
        content.setHeading(lblModalHeading);
        content.setBody(contentGrid);
        content.setActions(btnCancel, btnOk);
        dialog.show();
    }

    public static void editCustomInfo(Stakeholder sh) {
        GridPane gp = new GridPane();
        Label l0 = new Label("Name: ");
        TextField nameTF = new TextField(sh.getName());
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
        gp.add(l0, 0, 0);
        gp.add(nameTF, 1, 0);
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
            sh.setName(nameTF.getText());
            sh.setEmail(emailTF.getText());
            sh.setPhone(phoneTF.getText());
            sh.setContactName(contactNameTF.getText());
            sh.setComment(commentTF.getText());
        }
    }

    public static Stakeholder createNew(int idx, int type) {
        GridPane gp = new GridPane();
        Label l0 = new Label("Name: ");
        TextField nameTF = new TextField("New Stakeholder");
        Label l1 = new Label("Email: ");
        TextField emailTF = new TextField("");
        emailTF.setPadding(new Insets(0, 0, 0, 5));
        Label l2 = new Label("Phone #: ");
        TextField phoneTF = new TextField("");
        phoneTF.setPadding(new Insets(0, 0, 0, 5));
        Label l3 = new Label("Contact Name: ");
        TextField contactNameTF = new TextField("");
        contactNameTF.setPadding(new Insets(0, 0, 0, 5));
        Label l4 = new Label("Comment: ");
        TextField commentTF = new TextField("");
        commentTF.setPadding(new Insets(0, 0, 0, 5));
        gp.add(l0, 0, 0);
        gp.add(nameTF, 1, 0);
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
            Stakeholder sh = new Stakeholder(idx, nameTF.getText(), -1);
            sh.setEmail(emailTF.getText());
            sh.setPhone(phoneTF.getText());
            sh.setContactName(contactNameTF.getText());
            sh.setComment(commentTF.getText());
            switch (type) {
                case CORE_TEAM:
                    sh.setCoreTeamMember(true);
                    break;
                case STAKEHOLDER:
                    sh.setStakeholder(true);
                    break;
                case NA:
                    sh.setNotApplicable(true);
                    break;
            }
            return sh;
        } else {
            return null;
        }
    }

    public static final int CORE_TEAM = 0;
    public static final int STAKEHOLDER = 1;
    public static final int NA = 2;
}
