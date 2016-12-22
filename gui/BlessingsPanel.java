package gui;

import data.Attribute;
import data.Blessing;
import data.ToFCharacter;
import data.Totem;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shado on 10/12/2016.
 */
public class BlessingsPanel {


  private final GridPane grid;
  private Map<Attribute, TextField> totemMods;
  private final DecimalFormat fmt;
  private final TableView<BlessingModel> tblBlessings;

  public BlessingsPanel(MainFrame mainFrame, ToFCharacter character) {
    grid = MainFrame.getGridPane();
    totemMods = new TreeMap<>();
    fmt = new DecimalFormat("+#0;-#");

    VBox totemBox = new VBox();
    GridPane totemGrid = MainFrame.getGridPane();

    Label lblTotem = new Label("Totem Bonuses");
    grid.add(lblTotem, 0, 0, 2, 1);

    Totem totem = character.getTotem();
    for (Attribute a : Attribute.values()) {
      TextField modifier = new TextField(fmt.format(totem.getAttributeBonus(a)));
      modifier.setPrefColumnCount(3);
      totemMods.put(a, modifier);

      modifier.textProperty().addListener(
              (observable, oldValue, newValue) -> {
                if (!newValue.matches("^[+\\-]?\\d*$")) {
                  Platform.runLater(() -> {
                    modifier.setText(newValue.replaceAll("[^\\d*]", ""));
                    modifier.positionCaret(modifier.getLength());
                  });
                } else {
                  try {
                    int value = Integer.parseInt(newValue);
                    character.getTotem().setAttributeBonus(a, value);
                    mainFrame.update(character);
                  } catch (NumberFormatException e) {
                  }
                }
              });


      totemGrid.addRow(a.index(), new Label(a.getAbbrevation()), modifier);
    }


    totemBox.getChildren().addAll(totemGrid);
    totemBox.setAlignment(Pos.CENTER);

    VBox cntBlessings = new VBox();
    Label lblBlessings = new Label("Blessings");

    tblBlessings = new TableView();
    tblBlessings.setEditable(true);



    TableColumn<BlessingModel, String> name = new TableColumn("Name");
    TableColumn<BlessingModel, String> god = new TableColumn("God");
    TableColumn<BlessingModel, String> cost = new TableColumn("Cost");
    TableColumn<BlessingModel, String> desc = new TableColumn("Description");
    tblBlessings.getColumns().addAll(name, god, cost, desc);

    name.setPrefWidth(100);
    name.setCellValueFactory(new PropertyValueFactory<>("name"));

    god.setPrefWidth(150);
    god.setCellValueFactory(cellData -> cellData.getValue().godProperty());

    cost.setPrefWidth(50);
    cost.setCellValueFactory(new PropertyValueFactory<>("cost"));

    desc.setPrefWidth(300);
    desc.setCellValueFactory(new PropertyValueFactory<>("desc"));



    HBox addBlessing = new HBox();

    TextField txtAddName = new TextField();
    txtAddName.setPromptText("Name");
    txtAddName.setPrefWidth(name.getPrefWidth());

    ComboBox<Attribute> comboAddGod = new ComboBox<Attribute>();
    comboAddGod.setPrefWidth(god.getPrefWidth());
    comboAddGod.setCellFactory(
            new Callback<ListView<Attribute>, ListCell<Attribute>>() {
              public ListCell<Attribute > call(ListView<Attribute> p) {
                ListCell cell = new ListCell<Attribute>() {
                  protected void updateItem(Attribute item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setText("");
                    } else {
                      setText(item.getGod());
                    }
                  }
                };
                return cell;
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

    TextField txtAddCost = new TextField();
    txtAddCost.setPromptText("Cost");
    txtAddCost.setPrefWidth(cost.getPrefWidth());
    txtAddCost.textProperty().addListener((observable, oldValue, newValue) -> {
          if (!newValue.matches("\\d*")) {
            Platform.runLater(() -> {
              txtAddCost.setText(newValue.replaceAll("[^\\d*]", ""));
              txtAddCost.positionCaret(txtAddCost.getLength());
            });}});

    TextField txtAddDesc = new TextField();
    txtAddDesc.setPromptText("Blessing's Desc");
    txtAddDesc.setPrefWidth(desc.getPrefWidth());



    for (Blessing blessing: character.getTotem().getBlessings()) {
      BlessingModel model = new BlessingModel(blessing);
      tblBlessings.getItems().add(model);
    }
    addBlessing.getChildren().addAll(txtAddName, comboAddGod, txtAddCost, txtAddDesc);

    HBox controls = new HBox();


    Button btnAddBless = new Button("Add");
    btnAddBless.setOnAction(actionEvent -> {
      character.getTotem().addBlessing(new Blessing(
              txtAddName.getText(), comboAddGod.getValue(), Integer.parseInt(txtAddCost.getText()), txtAddDesc.getText()));
      mainFrame.update(character);
    });

    Button btnEditBless = new Button("Edit");
    btnEditBless.setOnAction(actionEvent -> {
      BlessingModel model = tblBlessings.getSelectionModel().getSelectedItem();
      model.setName(txtAddName.getText());
      model.setGod(String.valueOf(comboAddGod.getSelectionModel().getSelectedItem()));
      model.setCost(txtAddCost.getText());
      model.setDesc(txtAddDesc.getText());
      update(character);
    });

    Button btnRemoveBless = new Button("Delete");

    btnRemoveBless.setOnAction(actionEvent -> {
      BlessingModel model = tblBlessings.getSelectionModel().getSelectedItem();
      Blessing blessing = model.getBlessing();
      character.getTotem().deleteBlessing(blessing);
      update(character);
    });

    tblBlessings.getSelectionModel().selectedItemProperty().addListener((observableValue, t1, blessingModel) -> {
      txtAddName.setText(blessingModel.getName());
      comboAddGod.getSelectionModel().select(blessingModel.getBlessing().getGod());
      txtAddCost.setText(blessingModel.getCost());
      txtAddDesc.setText(blessingModel.getDesc());
    });

    controls.getChildren().addAll(btnAddBless, btnEditBless, btnRemoveBless);
    controls.setAlignment(Pos.CENTER);

    cntBlessings.getChildren().addAll(lblBlessings, tblBlessings, addBlessing, controls);

    grid.addRow(1, totemBox, cntBlessings);

  }

  public void update(ToFCharacter character) {
    Totem totem = character.getTotem();
    for (Attribute attribute : Attribute.values()) {
      int bonus = totem.getAttributeBonus(attribute);
      totemMods.get(attribute).setText(fmt.format(bonus));
    }

    tblBlessings.getItems().clear();
    for (Blessing blessing: character.getTotem().getBlessings()) {
      BlessingModel model = new BlessingModel(blessing);
      tblBlessings.getItems().add(model);
    }
  }


  public Node getPanel() {
    return grid;
  }

  public class BlessingModel {
    private Blessing blessing;
    private SimpleStringProperty name, god, desc;
    private SimpleStringProperty cost;

    public BlessingModel(Blessing blessing) {
      this.init(new SimpleStringProperty(blessing.getName()),
              new SimpleStringProperty(String.valueOf(blessing.getGod().getGod())),
              new SimpleStringProperty(blessing.getDescription()),
              new SimpleStringProperty(String.valueOf(blessing.getLevel())),
              blessing);
    }

    public BlessingModel(SimpleStringProperty name, SimpleStringProperty god, SimpleStringProperty desc, SimpleStringProperty cost) {
      int intCost = Integer.parseInt(cost.getValue());
      Attribute attribute = Attribute.valueOf(god.getValue());
      Blessing blessing = new Blessing(name.getValue(), attribute, intCost, desc.getValue());
      this.init(name, god, desc, cost, blessing);
    }

    private void init(SimpleStringProperty name, SimpleStringProperty god, SimpleStringProperty desc, SimpleStringProperty cost, Blessing blessing) {
      this.name = name;
      this.god = god;
      this.desc = desc;
      this.cost = cost;
      this.blessing = blessing;
    }

    public Blessing getBlessing() {

      return blessing;
    }

    public String getName() {

      return name.get();
    }

    public SimpleStringProperty nameProperty() {
      return name;
    }

    public void setName(String name) {
      this.name.set(name);
      this.blessing.setName(name);
    }

    public String getGod() {
      return god.get();
    }

    public SimpleStringProperty godProperty() {
      return god;
    }

    public void setGod(String god) {
      this.blessing.setGod(Attribute.valueOf(god));
      this.god.set(this.blessing.getGod().getGod());
    }

    public String getDesc() {
      return desc.get();
    }

    public SimpleStringProperty descProperty() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc.set(desc);
      this.blessing.setDescription(desc);
    }

    public String getCost() {
      return cost.get();
    }

    public SimpleStringProperty costProperty() {
      return cost;
    }

    public void setCost(String cost) {
      this.cost.set(cost);
      this.blessing.setLevel(Integer.parseInt(cost));
    }
  }


}
