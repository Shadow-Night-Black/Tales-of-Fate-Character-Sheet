package gui;

/**
 * Created by shado on 11/02/2017.
 */


import data.*;
import gui.models.FigmentModel;
import gui.models.ItemModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class FigmentsPanel {
  private TableView<ItemModel> inventory;
  private TableView<FigmentModel> figmentEditor;
  private Figment selected;
  private final GridPane mainGrid;
  private boolean advantageMode;
  private Label slotsOpen;

  public FigmentsPanel(MainFrame mainFrame, ToFCharacter character) {
    mainGrid = MainFrame.getGridPane();

    VBox invBox = getInvBox(mainFrame, character);

    VBox figmentsBox = getFigmentsBox(mainFrame, character);



    GridPane.setHgrow(invBox, Priority.ALWAYS);
    GridPane.setHgrow(figmentsBox, Priority.ALWAYS);
    mainGrid.add(invBox, 0, 0);
    mainGrid.add(figmentsBox, 1, 0);

  }

  private VBox getInvBox(MainFrame mainFrame, ToFCharacter character) {
    VBox invBox = new VBox();

    inventory = new TableView<>();
    inventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    inventory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    TableColumn<ItemModel, String> colName = new TableColumn<>("Name");
    TableColumn<ItemModel, Integer> colMv = new TableColumn<>("MV");
    TableColumn<ItemModel, Integer> colCost = new TableColumn<>("Cost");
    TableColumn<ItemModel, String> colDesc = new TableColumn<>("Description");

    inventory.getColumns().addAll(colName, colMv, colCost, colDesc);
    inventory.setRowFactory(param -> new TableRow<ItemModel>() {
      public void updateItem(ItemModel model, boolean empty) {
        super.updateItem(model, empty);
        if (model != null && !empty) {
          if (model.isEquipped()) {
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

    final int TOTAL_SIZE = 550;
    colName.prefWidthProperty().bind(inventory.widthProperty().multiply(100).divide(TOTAL_SIZE));
    colName.setMinWidth(100);
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    colMv.prefWidthProperty().bind(inventory.widthProperty().multiply(75).divide(TOTAL_SIZE));
    colMv.setMinWidth(75);
    colMv.setCellValueFactory(new PropertyValueFactory<>("Mv"));

    colCost.prefWidthProperty().bind(inventory.widthProperty().multiply(75).divide(TOTAL_SIZE));
    colCost.setMinWidth(75);
    colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

    colDesc.prefWidthProperty().bind(inventory.widthProperty().multiply(300).divide(TOTAL_SIZE));
    colDesc.setMinWidth(300);
    colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
    colDesc.setCellFactory(param -> {
      TableCell<ItemModel, String> cell = new TableCell<>();
      Text text = new Text();
      cell.setGraphic(text);
      cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
      text.wrappingWidthProperty().bind(colDesc.widthProperty());
      text.textProperty().bind(cell.itemProperty());
      return cell;
    });

    for (Figment figment: character.getFigments()) {
      ItemModel model = new ItemModel(figment);
      inventory.getItems().add(model);
      selected = figment;
    }

    TextField txtName = new TextField();
    txtName.setPromptText("Name");
    txtName.prefWidthProperty().bind(colName.widthProperty());
    txtName.minWidthProperty().bind(colName.minWidthProperty());

    SpinnerAutoCommit<Integer> nsMv = new SpinnerAutoCommit<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99));
    nsMv.setEditable(true);
    //NumberSpinner nsMv = new NumberSpinner();
    //NumberTextField txtMv = nsMv.getNumberField();
    nsMv.getEditor().setPromptText("MV");
    nsMv.prefWidthProperty().bind(colMv.widthProperty());
    nsMv.minWidthProperty().bind(colMv.minWidthProperty());

    SpinnerAutoCommit<Integer> nsCost = new SpinnerAutoCommit<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99));
    nsCost.setEditable(true);
    //NumberSpinner nsCost = new NumberSpinner();
    //NumberTextField txtCost = nsCost.getNumberField();
    nsCost.getEditor().setPromptText("Cost");
    nsCost.prefWidthProperty().bind(colCost.widthProperty());
    nsCost.minWidthProperty().bind(colCost.minWidthProperty());

    TextField txtDesc = new TextField();
    txtDesc.setPromptText("Description");
    txtDesc.prefWidthProperty().bind(colDesc.widthProperty());
    txtDesc.minWidthProperty().bind(colDesc.minWidthProperty());

    inputFields.getChildren().addAll(txtName, nsMv, nsCost, txtDesc);

    HBox controls = new HBox();

    Button btnAddItem = new Button("Add");
    btnAddItem.setOnAction(actionEvent -> {
      character.addFigment( new Figment(
        txtName.getText(),
        txtDesc.getText(),
        nsMv.getValue(),
        nsCost.getValue(),
        true));
      mainFrame.update(character);
    });

    Button btnEditForm= new Button("Edit");
    btnEditForm.setOnAction(actionEvent -> {
      ItemModel model = inventory.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setName(txtName.getText());
        model.setDesc(txtDesc.getText());
        model.setMv(nsMv.getValue());
        model.setCost(nsCost.getValue());
        mainFrame.update(character);
      }
    });

    Button btnRemoveForm = new Button("Delete");

    btnRemoveForm.setOnAction(actionEvent -> {
      for (ItemModel model: inventory.getSelectionModel().getSelectedItems()) {
        Figment figment = model.getFigment();
        character.removeFigment(figment);
      }
      mainFrame.update(character);
    });

    Button btnSetForm = new Button("(Un)Equip Selected Item(s)");

    btnSetForm.setOnAction(event -> {
      for (ItemModel model: inventory.getSelectionModel().getSelectedItems()) {
        Figment figment = model.getFigment();
        figment.setEquipped(!figment.isEquipped());
      }
      mainFrame.update(character);
    });

    inventory.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, itemModel) -> {
      if (itemModel != null) {
        Figment figment = itemModel.getFigment();
        selected = figment;
        txtName.setText(figment.getName());
        txtDesc.setText(figment.getDesc());
        nsCost.getValueFactory().setValue(figment.getCost());
        nsMv.getValueFactory().setValue(figment.getMv());
        this.updateFigmentTable();
      }
    });

    controls.getChildren().addAll(btnAddItem, btnEditForm, btnSetForm, btnRemoveForm);
    controls.setAlignment(Pos.CENTER);

    invBox.getChildren().addAll(inventory, inputFields, controls);
    return invBox;
  }

  private VBox getFigmentsBox(MainFrame mainFrame, ToFCharacter character) {
    VBox figmentsBox = new VBox();

    figmentEditor = new TableView<>();
    figmentEditor.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<FigmentModel, Integer> colBonus = new TableColumn<>("Bonus");
    TableColumn<FigmentModel, String> colType = new TableColumn<>("Type");
    TableColumn<FigmentModel, String> colDesc = new TableColumn<>("Description");

    figmentEditor.getColumns().addAll( colBonus, colType, colDesc);

    HBox inputFields = new HBox();

    final int TOTALSIZE = 600;

    colBonus.prefWidthProperty().bind(figmentEditor.widthProperty().multiply(75).divide(TOTALSIZE));
    colBonus.setMinWidth(75);
    colBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));

    colType.prefWidthProperty().bind(figmentEditor.widthProperty().multiply(125).divide(TOTALSIZE));
    colType.setMinWidth(125);
    colType.setCellValueFactory(new PropertyValueFactory<>("name"));

    colDesc.prefWidthProperty().bind(figmentEditor.widthProperty().multiply(300).divide(TOTALSIZE));
    colDesc.setMinWidth(300);
    colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
    colDesc.setCellFactory(param -> {
      TableCell<FigmentModel, String> cell = new TableCell<>();
      Text text = new Text();
      cell.setGraphic(text);
      cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
      text.wrappingWidthProperty().bind(colDesc.widthProperty());
      text.textProperty().bind(cell.itemProperty());
      return cell;
    });

    if (this.selected != null) {
      for (Feat feat : this.selected.getFeatBonuses()){
        FigmentModel model = new FigmentModel(feat, selected);
        figmentEditor.getItems().add(model);
      }
    }

    SpinnerAutoCommit<Integer> txtBonus = new SpinnerAutoCommit<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
    txtBonus.setEditable(true);
    txtBonus.prefWidthProperty().bind(colBonus.widthProperty());
    txtBonus.minWidthProperty().bind(colBonus.minWidthProperty());
    txtBonus.getEditor().setPromptText("Bonus");
    txtBonus.managedProperty().bind(txtBonus.visibleProperty());

    ComboBox<Advantage> comboAdvantages = new ComboBox<>();
    comboAdvantages.prefWidthProperty().bind(colBonus.widthProperty());
    comboAdvantages.minWidthProperty().bind(colBonus.minWidthProperty());

    comboAdvantages.getItems().addAll(Advantage.values());
    comboAdvantages.getSelectionModel().select(Advantage.ADVANTAGE);

    comboAdvantages.managedProperty().bind(comboAdvantages.visibleProperty());
    comboAdvantages.setVisible(false);

    TextField txtType = new TextField();
    txtType.setPromptText("Bonus Type");
    txtType.prefWidthProperty().bind(colType.widthProperty());
    txtType.minWidthProperty().bind(colType.minWidthProperty());

    TextField txtDesc = new TextField();
    txtDesc.setPromptText("Description");
    txtDesc.prefWidthProperty().bind(colDesc.widthProperty());
    txtDesc.minWidthProperty().bind(colDesc.minWidthProperty());

    inputFields.getChildren().addAll(txtBonus,comboAdvantages, txtType, txtDesc);

    HBox controls = new HBox();

    Button btnAddForm = new Button("Add");
    btnAddForm.setOnAction(actionEvent -> {
      if (advantageMode) {
        selected.addFeatBonus(new Feat(
          txtType.getText(),
          txtDesc.getText(),
          Attribute.POWER, //NEVER USED
          comboAdvantages.getValue(),
          true));
      }else {
        selected.addFeatBonus(new Feat(
          txtType.getText(),
          txtDesc.getText(),
          Attribute.POWER, //NEVER USED
          txtBonus.getValue(),
          true));
      }
      mainFrame.update(character);
    });

    Button btnEditForm= new Button("Edit");
    btnEditForm.setOnAction(actionEvent -> {
      FigmentModel model = figmentEditor.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setName(txtType.getText());
        model.setDesc(txtDesc.getText());
        model.setAdvantageMode(advantageMode);
        if (advantageMode) {
          model.setBonus(comboAdvantages.getValue().toInt());
        }else {
          model.setBonus(txtBonus.getValue().intValue());
        }
        mainFrame.update(character);
      }
    });

    Button btnRemoveForm = new Button("Delete");

    btnRemoveForm.setOnAction(actionEvent -> {
      FigmentModel model = figmentEditor.getSelectionModel().getSelectedItem();
      if (model != null) {
        Figment figment = model.getFigment();
        figment.removeFeatBonus(model.getFeat());
        mainFrame.update(character);
      }
    });

    Button btnToggleAdvantageMode = new Button("Use advantages");
    btnToggleAdvantageMode.setOnAction(event -> {
      if (advantageMode) {
        advantageMode = false;
        btnToggleAdvantageMode.setText("Use advantages");
        comboAdvantages.setVisible(false);
        txtBonus.setVisible(true);
      }else {
        advantageMode = true;
        btnToggleAdvantageMode.setText("Use stat bonuses");
        comboAdvantages.setVisible(true);
        txtBonus.setVisible(false);
      }
    });


    figmentEditor.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, featModel) -> {
      if (featModel != null) {
        txtType.setText(featModel.getName());
        txtDesc.setText(featModel.getDesc());
        txtBonus.getValueFactory().setValue(featModel.getBonus());
        comboAdvantages.getSelectionModel().select(Advantage.fromInt(featModel.getBonus()));
        if (featModel.isAdvantageMode() != advantageMode) {
          btnToggleAdvantageMode.fire();
        }
      }
    });

    controls.getChildren().addAll(btnAddForm, btnEditForm, btnToggleAdvantageMode, btnRemoveForm);
    controls.setAlignment(Pos.CENTER);

    HBox memoryDetails = new HBox();

    slotsOpen = new Label();
    //slotsOpen.setText(character.getCurrentForm().getNumberOfSlots() + " slots Open");

    memoryDetails.getChildren().addAll(slotsOpen);
    memoryDetails.setAlignment(Pos.CENTER);

    figmentsBox.getChildren().addAll(figmentEditor, inputFields, controls, memoryDetails);
    return figmentsBox;
  }




  public Node getPanel() {

    return mainGrid;
  }

  public void update(ToFCharacter character) {
    inventory.getItems().clear();
    inventory.setStyle("");

    for (Figment figment: character.getFigments()) {
      ItemModel model = new ItemModel(figment);
      inventory.getItems().add(model);
    }

    updateFigmentTable();
  }

  private void updateFigmentTable() {
    figmentEditor.setStyle("");
    figmentEditor.getItems().clear();
    if (selected != null) {
      for (Feat feat: selected.getFeatBonuses()) {
        figmentEditor.getItems().add(new FigmentModel(feat, selected));
      }
      slotsOpen.setText(selected.getNumberOfSlots() + " slots Open");
    }
  }

}
