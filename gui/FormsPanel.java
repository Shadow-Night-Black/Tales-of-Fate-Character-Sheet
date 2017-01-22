package gui;

import data.Attribute;
import data.Feat;
import data.Form;
import data.ToFCharacter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.math.BigDecimal;

/**
 * Created by AQU-mark on 22/12/16.
 */
public class FormsPanel {
  private TableView<FormModel> formTable;
  private  TableView<FeatModel> featTable;
  private final GridPane mainGrid;

  public FormsPanel(MainFrame mainFrame, ToFCharacter character) {
    mainGrid = MainFrame.getGridPane();

    VBox formBox = getFormBox(mainFrame, character);

    VBox featsBox = getFeatBox(mainFrame, character);



    mainGrid.setHgrow(formBox, Priority.ALWAYS);
    mainGrid.setHgrow(featsBox, Priority.ALWAYS);
    mainGrid.add(formBox, 0, 0);
    mainGrid.add(featsBox, 1, 0);

  }

  private VBox getFormBox(MainFrame mainFrame, ToFCharacter character) {
    VBox formBox = new VBox();

    formTable = new TableView<>();

    TableColumn<FormModel, String> colName = new TableColumn<>("Name");
    TableColumn<FormModel, Integer> colClass = new TableColumn<>("Class");
    TableColumn<FormModel, String> colDesc = new TableColumn<>("Description");

    formTable.getColumns().addAll(colName, colClass, colDesc);

    HBox inputFields = new HBox();

    colName.prefWidthProperty().bind(formTable.widthProperty().multiply(2).divide(9));
    colName.setMinWidth(100);
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    colClass.prefWidthProperty().bind(formTable.widthProperty().divide(9));
    colClass.setMinWidth(75);
    colClass.setCellValueFactory(new PropertyValueFactory<>("formClass"));

    colDesc.prefWidthProperty().bind(formTable.widthProperty().multiply(6).divide(9));
    colDesc.setMinWidth(300);
    colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));

    for (Form form: character.getForms()) {
      FormModel model = new FormModel(form);
      formTable.getItems().add(model);
    }

    TextField txtName = new TextField();
    txtName.setPromptText("Name");
    txtName.prefWidthProperty().bind(colName.widthProperty());
    txtName.minWidthProperty().bind(colName.minWidthProperty());


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

    inputFields.getChildren().addAll(txtName, comboClass, txtDesc);

    HBox controls = new HBox();

    Button btnAddForm = new Button("Add");
    btnAddForm.setOnAction(actionEvent -> {
      character.addForm( new Form(
        txtName.getText(),
        comboClass.getValue(),
        txtDesc.getText()));
      mainFrame.update(character);
    });

    Button btnEditForm= new Button("Edit");
    btnEditForm.setOnAction(actionEvent -> {
      FormModel model = formTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setName(txtName.getText());
        model.setFormClass(comboClass.getValue());
        model.setDesc(txtDesc.getText());
        update(character);
      }
    });

    Button btnRemoveForm = new Button("Delete");

    btnRemoveForm.setOnAction(actionEvent -> {
      FormModel model = formTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        Form form = model.getForm();
        character.removeForm(form);
        update(character);
      }
    });

    Button btnSetForm = new Button("Set Current Form");

    btnSetForm.setOnAction(event -> {
      FormModel model = formTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        character.setCurrentForm(model.getForm());
        update(character);
      }
    });

    formTable.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, formModel) -> {
      if (formModel != null) {
        txtName.setText(formModel.getName());
        comboClass.getSelectionModel().select(formModel.getFormClass()-1);
        txtDesc.setText(formModel.getDesc());
      }
    });

    controls.getChildren().addAll(btnAddForm, btnEditForm, btnSetForm, btnRemoveForm);
    controls.setAlignment(Pos.CENTER);

    formBox.getChildren().addAll(formTable, inputFields, controls);
    return formBox;
  }

  private VBox getFeatBox(MainFrame mainFrame, ToFCharacter character) {
    VBox featBox = new VBox();

    featTable = new TableView<>();

    TableColumn<FeatModel, String> colName = new TableColumn<>("Name");
    TableColumn<FeatModel, Integer> colBonus = new TableColumn<>("Bonus");
    TableColumn<FeatModel, String> colAttribute = new TableColumn<>("Attribute");
    TableColumn<FeatModel, String> colDesc = new TableColumn<>("Description");

    featTable.getColumns().addAll(colName, colBonus, colAttribute, colDesc);

    HBox inputFields = new HBox();

    colName.prefWidthProperty().bind(featTable.widthProperty().multiply(2).divide(9));
    colName.setMinWidth(100);
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    colBonus.prefWidthProperty().bind(featTable.widthProperty().divide(9));
    colBonus.setMinWidth(75);
    colBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));

    colAttribute.prefWidthProperty().bind(featTable.widthProperty().divide(9));
    colAttribute.setMinWidth(150);
    colAttribute.setCellValueFactory(cellData -> cellData.getValue().attributeProperty());

    colDesc.prefWidthProperty().bind(formTable.widthProperty().multiply(6).divide(9));
    colDesc.setMinWidth(300);
    colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));

    for (Feat feat: character.getCurrentForm().getFeats()) {
      FeatModel model = new FeatModel(feat);
      featTable.getItems().add(model);
    }

    TextField txtName = new TextField();
    txtName.setPromptText("Name");
    txtName.prefWidthProperty().bind(colName.widthProperty());
    txtName.minWidthProperty().bind(colName.minWidthProperty());

    ComboBox<Attribute> comboAttributes = new ComboBox<>();
    comboAttributes.prefWidthProperty().bind(colAttribute.widthProperty());
    comboAttributes.minWidthProperty().bind(colAttribute.minWidthProperty());

    comboAttributes.getItems().addAll(Attribute.values());
    comboAttributes.getSelectionModel().selectFirst();

    NumberTextField txtBonus = new NumberTextField();
    txtBonus.prefWidthProperty().bind(colBonus.widthProperty());
    txtBonus.minWidthProperty().bind(colBonus.minWidthProperty());
    txtBonus.setPromptText("Bonus");

    TextField txtDesc = new TextField();
    txtDesc.setPromptText("Description");
    txtDesc.prefWidthProperty().bind(colDesc.widthProperty());
    txtDesc.minWidthProperty().bind(colDesc.minWidthProperty());

    inputFields.getChildren().addAll(txtName, txtBonus, comboAttributes, txtDesc);

    HBox controls = new HBox();

    Button btnAddForm = new Button("Add");
    btnAddForm.setOnAction(actionEvent -> {
      character.getCurrentForm().addFeat( new Feat(
        txtName.getText(),
        txtDesc.getText(),
        comboAttributes.getValue(),
        txtBonus.getNumber().intValue(),
        true));
      System.out.println(txtBonus.getNumber().intValue());
      mainFrame.update(character);
    });

    Button btnEditForm= new Button("Edit");
    btnEditForm.setOnAction(actionEvent -> {
      FeatModel model = featTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setName(txtName.getText());
        model.setDesc(txtDesc.getText());
        model.setBonus(txtBonus.getNumber().intValue());
        model.setAttribute(comboAttributes.getValue().name());
        update(character);
      }
    });

    Button btnRemoveForm = new Button("Delete");

    btnRemoveForm.setOnAction(actionEvent -> {
      FeatModel model = featTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        Feat feat = model.getFeat();
        character.getCurrentForm().removeFeat(feat);
        update(character);
      }
    });

    Button btnToggleActive = new Button("Toggle Ability");

    btnToggleActive.setOnAction(event -> {
      FeatModel model = featTable.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setActive(!model.isActive());
        update(character);
      }
    });

    featTable.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, featModel) -> {
      if (featModel != null) {
        txtName.setText(featModel.getName());
        txtDesc.setText(featModel.getDesc());
        txtBonus.setNumber(new BigDecimal(featModel.getBonus()));
        comboAttributes.getSelectionModel().select(Attribute.valueOf(featModel.getAttribute()));
      }
    });

    controls.getChildren().addAll(btnAddForm, btnEditForm, btnToggleActive, btnRemoveForm);
    controls.setAlignment(Pos.CENTER);

    featBox.getChildren().addAll(featTable, inputFields, controls);
    return featBox;
  }


  public Node getPanel() {

    return mainGrid;
  }

  public void update(ToFCharacter character) {
    formTable.getItems().clear();

    for (Form form: character.getForms()) {
      FormModel model = new FormModel(form);
      formTable.getItems().add(model);
    }

    featTable.getItems().clear();
    for (Feat feat: character.getCurrentForm().getFeats()) {
      FeatModel model = new FeatModel(feat);
      featTable.getItems().add(model);
    }
  }

  public class FormModel {
    private Form form;
    private SimpleIntegerProperty formClass;
    private SimpleStringProperty name, desc;

    public FormModel(Form form) {
      init(form, new SimpleIntegerProperty(form.getFormClass()),
        new SimpleStringProperty(form.getName()),
        new SimpleStringProperty(form.getDesc()));
    }

    public FormModel(SimpleIntegerProperty formClass, SimpleStringProperty name, SimpleStringProperty desc) {
      Form form = new Form(name.getValue(), formClass.getValue(), desc.getValue());
      init(form, formClass, name, desc);
    }

    public void init(Form form, SimpleIntegerProperty formClass, SimpleStringProperty name, SimpleStringProperty desc) {
      this.form = form;
      this.formClass = formClass;
      this.name = name;
      this.desc = desc;
    }

    public Form getForm() {
      return form;
    }

    public void setForm(Form form) {
      this.form = form;
    }

    public int getFormClass() {
      return formClass.get();
    }

    public SimpleIntegerProperty formClassProperty() {
      return formClass;
    }

    public void setFormClass(int formClass) {
      this.formClass.set(formClass);
    }

    public String getName() {
      return name.get();
    }

    public SimpleStringProperty nameProperty() {
      return name;
    }

    public void setName(String name) {
      this.name.set(name);
    }

    public String getDesc() {
      return desc.get();
    }

    public SimpleStringProperty descProperty() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc.set(desc);
    }
  }
}
