package gui;

import data.*;
import gui.models.FeatModel;
import gui.models.SkillModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;

public class SkillsPanel {

  private TableView<FeatModel> profsTable;
  private TableView<SkillModel> skillTable;
  private final GridPane mainGrid;

  public SkillsPanel(MainFrame mainFrame, ToFCharacter character) {
    mainGrid = MainFrame.getGridPane();

    //VBox formBox = getFormBox(mainFrame, character);

    VBox featsBox = getSkillsBox(mainFrame, character);



    //GridPane.setHgrow(formBox, Priority.ALWAYS);
    GridPane.setHgrow(featsBox, Priority.ALWAYS);
    //mainGrid.add(formBox, 0, 0);
    mainGrid.add(featsBox, 0, 0);

  }
/*
  private VBox getFormBox(MainFrame mainFrame, ToFCharacter character) {
    VBox formBox = new VBox();

    profsTable = new TableView<>();

    TableColumn<FormModel, String> colName = new TableColumn<>("Name");
    TableColumn<FormModel, Integer> colMv = new TableColumn<>("MV");
    TableColumn<FormModel, Integer> colClass = new TableColumn<>("Class");
    TableColumn<FormModel, String> colDesc = new TableColumn<>("Description");

    profsTable.getColumns().addAll(colName, colMv, colClass, colDesc);
    profsTable.setRowFactory(param -> new TableRow<FormModel>() {
      public void updateItem(FormModel model, boolean empty) {
        super.updateItem(model, empty);
        if (model != null && !empty) {
          if (model.getName() == character.getCurrentForm().getName()) {
            setStyle("-fx-control-inner-background: green");
          } else {
            setStyle("");
          }
        }else {
          setStyle("");
        }
      }
    });

    HBox inputFields = new HBox();

    final int TOTALSIZE = 500;
    colName.prefWidthProperty().bind(profsTable.widthProperty().multiply(100).divide(TOTALSIZE));
    colName.setMinWidth(100);
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    colMv.prefWidthProperty().bind(profsTable.widthProperty().multiply(50).divide(TOTALSIZE));
    colMv.setMinWidth(50);
    colMv.setCellValueFactory(new PropertyValueFactory<>("Mv"));

    colClass.prefWidthProperty().bind(profsTable.widthProperty().multiply(50).divide(TOTALSIZE));
    colClass.setMinWidth(50);
    colClass.setCellValueFactory(new PropertyValueFactory<>("formClass"));

    colDesc.prefWidthProperty().bind(profsTable.widthProperty().multiply(300).divide(TOTALSIZE));
    colDesc.setMinWidth(300);
    colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
    colDesc.setCellFactory(param -> {
      TableCell<FormModel, String> cell = new TableCell<>();
      Text text = new Text();
      cell.setGraphic(text);
      cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
      text.wrappingWidthProperty().bind(colDesc.widthProperty());
      text.textProperty().bind(cell.itemProperty());
      return cell ;
    });

    for (Form form: character.getForms()) {
      FormModel model = new FormModel(form);
      profsTable.getItems().add(model);
    }

    TextField txtName = new TextField();
    txtName.setPromptText("Name");
    txtName.prefWidthProperty().bind(colName.widthProperty());
    txtName.minWidthProperty().bind(colName.minWidthProperty());

    NumberSpinner nSMv = new NumberSpinner();
    NumberTextField txtMv = nSMv.getNumberField();
    txtMv.setPromptText("MV");
    txtMv.prefWidthProperty().bind(colMv.widthProperty().subtract(nSMv.getIncrementButton().widthProperty()));
    txtMv.minWidthProperty().bind(colMv.minWidthProperty().subtract(nSMv.getIncrementButton().minWidthProperty()));


    ComboBox<Integer> comboClass = new ComboBox<>();
    comboClass.setPrefWidth(colClass.getPrefWidth());
    comboClass.prefWidthProperty().bind(colClass.widthProperty());
    comboClass.minWidthProperty().bind(colClass.minWidthProperty());
    for (int i = 1; i <= 6; i++) {
      comboClass.getItems().add(i);
    }
    comboClass.getSelectionModel().select(2);

    TextField txtDesc = new TextField();
    txtDesc.setPromptText("Description");
    txtDesc.prefWidthProperty().bind(colDesc.widthProperty());
    txtDesc.minWidthProperty().bind(colDesc.minWidthProperty());

    inputFields.getChildren().addAll(txtName, nSMv, comboClass, txtDesc);

    HBox controls = new HBox();

    Button btnAddForm = new Button("Add");
    btnAddForm.setOnAction(actionEvent -> {
      character.addForm( new Form(
        txtName.getText(),
        comboClass.getValue(),
        txtDesc.getText(),
        nSMv.getNumber().intValue(),
        6));
      mainFrame.update(character);
    });

    Button btnEditForm= new Button("Edit");
    btnEditForm.setOnAction(actionEvent -> {
      FormModel model = profsTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setName(txtName.getText());
        model.setFormClass(comboClass.getValue());
        model.setDesc(txtDesc.getText());
        mainFrame.update(character);
      }
    });

    Button btnRemoveForm = new Button("Delete");

    btnRemoveForm.setOnAction(actionEvent -> {
      FormModel model = profsTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        Form form = model.getForm();
        character.removeForm(form);
        mainFrame.update(character);
      }
    });

    Button btnSetForm = new Button("Set Current Form");

    btnSetForm.setOnAction(event -> {
      FormModel model = profsTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        character.setCurrentForm(model.getForm());
        mainFrame.update(character);
      }
    });

    profsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, formModel) -> {
      if (formModel != null) {
        txtName.setText(formModel.getName());
        comboClass.getSelectionModel().select(formModel.getFormClass()-1);
        txtDesc.setText(formModel.getDesc());
      }
    });

    controls.getChildren().addAll(btnAddForm, btnEditForm, btnSetForm, btnRemoveForm);
    controls.setAlignment(Pos.CENTER);

    formBox.getChildren().addAll(profsTable, inputFields, controls);
    return formBox;
  }
*/
  private VBox getSkillsBox(MainFrame mainFrame, ToFCharacter character) {
    VBox featBox = new VBox();

    skillTable = new TableView<>();

    TableColumn<SkillModel, String> colName = new TableColumn<>("Name");
    TableColumn<SkillModel, Integer> colLevel = new TableColumn<>("Level");
    TableColumn<SkillModel, String> colAttribute = new TableColumn<>("Attributes");

    skillTable.getColumns().addAll(colName, colLevel, colAttribute);

    HBox inputFields = new HBox();

    final int TOTALSIZE = 600;

    colName.prefWidthProperty().bind(skillTable.widthProperty().multiply(100).divide(TOTALSIZE));
    colName.setMinWidth(100);
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    colLevel.prefWidthProperty().bind(skillTable.widthProperty().multiply(75).divide(TOTALSIZE));
    colLevel.setMinWidth(75);
    colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

    colAttribute.prefWidthProperty().bind(skillTable.widthProperty().multiply(125).divide(TOTALSIZE));
    colAttribute.setMinWidth(125);
    colAttribute.setCellValueFactory(cellData -> cellData.getValue().attributesProperty());


    for (Skill skill: character.getSkills()) {
      SkillModel model = new SkillModel(skill);
      skillTable.getItems().add(model);
    }

    TextField txtName = new TextField();
    txtName.setPromptText("Name");
    txtName.prefWidthProperty().bind(colName.widthProperty());
    txtName.minWidthProperty().bind(colName.minWidthProperty());

    CheckComboBox<Attribute> comboAttributes = new CheckComboBox<>();
    comboAttributes.prefWidthProperty().bind(colAttribute.widthProperty());
    comboAttributes.minWidthProperty().bind(colAttribute.minWidthProperty());

    comboAttributes.getItems().addAll(Attribute.values());
    comboAttributes.getCheckModel().check(Attribute.POWER);


    ComboBox<SkillLevel> comboLevel = new ComboBox<>();
    comboLevel.prefWidthProperty().bind(colLevel.widthProperty());
    comboLevel.minWidthProperty().bind(colLevel.minWidthProperty());

    comboLevel.getItems().addAll(SkillLevel.values());
    comboLevel.getSelectionModel().select(SkillLevel.PROFICENT);


    inputFields.getChildren().addAll(txtName, comboLevel, comboAttributes);

    HBox controls = new HBox();

    Button btnAddSkill = new Button("Add");
    btnAddSkill.setOnAction(actionEvent -> {
      List<Attribute> attributes = new ArrayList<>();
      comboAttributes.getCheckModel().getCheckedItems().forEach(attributes::add);
      Skill skill = new Skill(
        txtName.getText(),
        comboLevel.getValue(),
        attributes);
      character.addAbility(skill);
      mainFrame.update(character);
    });

    Button btnEditSkill= new Button("Edit");
    btnEditSkill.setOnAction(actionEvent -> {
      SkillModel model = skillTable.getSelectionModel().getSelectedItem();
      List<Attribute> attributes = new ArrayList<>();
      comboAttributes.getCheckModel().getCheckedItems().forEach(attributes::add);
      if (model != null) {
        model.setName(txtName.getText());
        model.setLevel(comboLevel.getValue().toString());
        model.setAttribute(attributes);
        mainFrame.update(character);
      }
    });

    Button btnRemoveSkill = new Button("Delete");

    btnRemoveSkill.setOnAction(actionEvent -> {
      SkillModel model = skillTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        Skill skill = model.getSkill();
        character.removeAbility(skill);
        mainFrame.update(character);
      }
    });


    skillTable.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, skillModel) -> {
      if (skillModel != null) {
        txtName.setText(skillModel.getName());
        comboLevel.getSelectionModel().select(SkillLevel.valueOf(skillModel.getLevel()));
        comboAttributes.getCheckModel().clearChecks();
        for(Attribute attribute: skillModel.getSkill().getAttributes()) {
          comboAttributes.getCheckModel().check(attribute);
        }
      }
    });

    controls.getChildren().addAll(btnAddSkill, btnEditSkill, btnRemoveSkill);
    controls.setAlignment(Pos.CENTER);

    featBox.getChildren().addAll(skillTable, inputFields, controls);
    return featBox;
  }




  public Node getPanel() {

    return mainGrid;
  }

  public void update(ToFCharacter character) {
    /*
    profsTable.getItems().clear();
    profsTable.setStyle("");

    for (Form form: character.getForms()) {
      FormModel model = new FormModel(form);
      profsTable.getItems().add(model);
    }
    */

    skillTable.setStyle("");
    skillTable.getItems().clear();
    for (Skill skill: character.getSkills()) {
      SkillModel model = new SkillModel(skill);
      skillTable.getItems().add(model);
    }
  }

}
