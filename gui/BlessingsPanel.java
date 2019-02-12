package gui;

import data.Attribute;
import data.Blessing;
import data.ToFCharacter;
import data.Totem;
import gui.models.BlessingModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Map;
import java.util.TreeMap;

public class BlessingsPanel {


  private final GridPane grid;
  private Map<Attribute, SpinnerAutoCommit<Integer>> totemMods;
  private final TableView<BlessingModel> tblBlessings;

  public BlessingsPanel(MainFrame mainFrame, ToFCharacter character) {
    grid = MainFrame.getGridPane();
    totemMods = new TreeMap<>();

    VBox totemBox = new VBox();
    GridPane totemGrid = MainFrame.getGridPane();

    Label lblTotem = new Label("Totem Bonuses");
    totemGrid.add(lblTotem, 0, 0, 2, 1);

    Totem totem = character.getTotem();
    for (Attribute a : Attribute.values()) {
      SpinnerAutoCommit<Integer> modifier = new SpinnerAutoCommit<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(-10, 10));
      modifier.setEditable(true);
      modifier.getValueFactory().setValue(totem.getAttributeBonus(a));
      modifier.getEditor().setPrefColumnCount(3);
      totemMods.put(a, modifier);

      modifier.valueProperty().addListener(
              (observable, oldValue, newValue) -> {
                    character.getTotem().setAttributeBonus(a, modifier.getValue());
                    mainFrame.update(character);
              });


      totemGrid.addRow(a.index(), new Label(a.getAbbreviation()), modifier);
    }


    totemBox.getChildren().addAll(totemGrid);
    totemBox.setAlignment(Pos.CENTER);

    VBox cntBlessings = new VBox();
    Label lblBlessings = new Label("Blessings");

    tblBlessings = new TableView<>();

    TableColumn<BlessingModel, String> colName = new TableColumn<>("Name");
    TableColumn<BlessingModel, String> colGod = new TableColumn<>("God");
    TableColumn<BlessingModel, Integer> colCost = new TableColumn<>("Cost");
    TableColumn<BlessingModel, String> colDesc = new TableColumn<>("Description");
    tblBlessings.getColumns().addAll(colName, colGod, colCost, colDesc);


    colName.prefWidthProperty().bind(tblBlessings.widthProperty().divide(6));
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colName.setMinWidth(100);


    colGod.prefWidthProperty().bind(tblBlessings.widthProperty().multiply(3).divide(12));
    colGod.setCellValueFactory(cellData -> cellData.getValue().godProperty());
    colGod.setMinWidth(150);

    colCost.prefWidthProperty().bind(tblBlessings.widthProperty().divide(12));
    colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    colCost.setMinWidth(50);

    colDesc.prefWidthProperty().bind(tblBlessings.widthProperty().divide(2));
    colDesc.setMinWidth(300);
    colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
    colDesc.setCellFactory(param -> {
      TableCell<BlessingModel, String> cell = new TableCell<>();
      Text text = new Text();
      cell.setGraphic(text);
      cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
      text.wrappingWidthProperty().bind(colDesc.widthProperty());
      text.textProperty().bind(cell.itemProperty());
      return cell ;
    });


    HBox addBlessing = new HBox();

    TextField txtAddName = new TextField();
    txtAddName.setPromptText("Name");
    txtAddName.prefWidthProperty().bind(colName.widthProperty());
    txtAddName.minWidthProperty().bind(colName.minWidthProperty());

    ComboBox<Attribute> comboAddGod = new ComboBox<>();
    comboAddGod.prefWidthProperty().bind(colGod.widthProperty());
    comboAddGod.minWidthProperty().bind(colGod.minWidthProperty());
    comboAddGod.setCellFactory(
      new Callback<ListView<Attribute>, ListCell<Attribute>>() {
        public ListCell<Attribute > call(ListView<Attribute> p) {
          return new ListCell<Attribute>() {
            protected void updateItem(Attribute item, boolean empty) {
              super.updateItem(item, empty);
              if (empty) {
                setText("");
              } else {
                setText(item.getGod());
              }
            }
          };
        }
      });

    comboAddGod.setButtonCell(
      new ListCell<Attribute>() {
        protected void updateItem(Attribute t, boolean bln) {
          super.updateItem(t, bln);
          if (bln) {
            setText("");
          } else {
            setText(t.getGod());
          }
        }
      });


    comboAddGod.getItems().addAll(Attribute.values());
    comboAddGod.getSelectionModel().selectFirst();

    SpinnerAutoCommit<Integer> txtAddCost = new SpinnerAutoCommit<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6));
    txtAddCost.getEditor().setPromptText("Cost");
    txtAddCost.prefWidthProperty().bind(colCost.widthProperty());
    txtAddCost.minWidthProperty().bind(colCost.minWidthProperty());

    TextField txtAddDesc = new TextField();
    txtAddDesc.setPromptText("Blessing's Desc");
    txtAddDesc.prefWidthProperty().bind(colDesc.widthProperty());
    txtAddDesc.minWidthProperty().bind(colDesc.minWidthProperty());


    for (Blessing blessing: character.getTotem().getBlessings()) {
      BlessingModel model = new BlessingModel(blessing);
      tblBlessings.getItems().add(model);
    }
    addBlessing.getChildren().addAll(txtAddName, comboAddGod, txtAddCost, txtAddDesc);

    HBox controls = new HBox();


    Button btnAddBless = new Button("Add");
    btnAddBless.setOnAction(actionEvent -> {
      try {
        character.getTotem().addBlessing(new Blessing(
          txtAddName.getText(),
          comboAddGod.getValue(),
          txtAddCost.getValue(),
          txtAddDesc.getText()));
        mainFrame.update(character);
      }catch (NumberFormatException e) {}
    });

    Button btnEditBless = new Button("Edit");
    btnEditBless.setOnAction(actionEvent -> {
      BlessingModel model = tblBlessings.getSelectionModel().getSelectedItem();
      if (model != null) {
        model.setName(txtAddName.getText());
        model.setGod(String.valueOf(comboAddGod.getSelectionModel().getSelectedItem()));
        model.setCost(txtAddCost.getValue());
        model.setDesc(txtAddDesc.getText());
        update(character);
      }
    });

    Button btnRemoveBless = new Button("Delete");

    btnRemoveBless.setOnAction(actionEvent -> {
      BlessingModel model = tblBlessings.getSelectionModel().getSelectedItem();
      if (model != null) {
        Blessing blessing = model.getBlessing();
        character.getTotem().deleteBlessing(blessing);
        update(character);
      }
    });

    tblBlessings.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, blessingModel) -> {
      if (blessingModel != null) {
        txtAddName.setText(blessingModel.getName());
        comboAddGod.getSelectionModel().select(blessingModel.getBlessing().getAttribute());
        txtAddCost.getValueFactory().setValue(blessingModel.getCost());
        txtAddDesc.setText(blessingModel.getDesc());
      }
    });

    controls.getChildren().addAll(btnAddBless, btnEditBless, btnRemoveBless);
    controls.setAlignment(Pos.CENTER);

    cntBlessings.getChildren().addAll(lblBlessings, tblBlessings, addBlessing, controls);

    GridPane.setHgrow(cntBlessings, Priority.ALWAYS);
    grid.addRow(0, totemBox, cntBlessings);

  }

  public void update(ToFCharacter character) {
    Totem totem = character.getTotem();
    for (Attribute attribute : Attribute.values()) {
      int bonus = totem.getAttributeBonus(attribute);
      totemMods.get(attribute).getValueFactory().setValue(bonus);
    }


    tblBlessings.getItems().clear();
    for (Blessing blessing: totem.getBlessings()) {
      BlessingModel model = new BlessingModel(blessing);
      tblBlessings.getItems().add(model);
    }
  }


  public Node getPanel() {
    return grid;
  }


}
