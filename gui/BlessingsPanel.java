package gui;

import data.Attribute;
import data.ToFCharacter;
import data.Totem;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

    VBox cntBlessings = new VBox();


    grid.addRow(1, totemBox, cntBlessings);

  }

  public void update(ToFCharacter character) {
    Totem totem = character.getTotem();
    for (Attribute attribute: Attribute.values()) {
      int bonus = totem.getAttributeBonus(attribute);
      totemMods.get(attribute).setText(fmt.format(bonus));
    }
  }


  public Node getPanel() {
    return grid;
  }
}
