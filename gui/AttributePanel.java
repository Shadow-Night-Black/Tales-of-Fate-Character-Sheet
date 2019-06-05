package gui;

import data.Attribute;
import data.ToFCharacter;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class AttributePanel {
  private final Map<Attribute, Label> baseValues;
  private final Map<Attribute, Label> attributeModifier;
  private final Map<Attribute, Label> modifiedValues;
  private final Map<Attribute, DicePoolDisplay> dicePools;
  private final DecimalFormat fmt;
  private final GridPane pane;

  public AttributePanel(MainFrame mainFrame, ToFCharacter character){
    baseValues = new TreeMap<>();
    attributeModifier = new TreeMap<>();
    modifiedValues = new TreeMap<>();
    dicePools = new TreeMap<>();
    fmt = new DecimalFormat("+#0;-#");

    for(Attribute attribute: Attribute.values()) {
      int baseValue = character.getBaseAttribute(attribute);
      Label lblAttribute = new Label(String.valueOf(baseValue));
      baseValues.put(attribute, lblAttribute);

      int modifier = character.getModifiedAttribute(attribute) - character.getBaseAttribute(attribute);
      Label lblBaseMod = new Label(fmt.format(modifier));
      attributeModifier.put(attribute, lblBaseMod);

      int moddedValue = character.getModifiedAttribute(attribute);
      Label lblModValues = new Label(String.valueOf(moddedValue));
      modifiedValues.put(attribute, lblModValues);

      DicePoolDisplay dicePool = new DicePoolDisplay(character.getDicePool(attribute));
      dicePools.put(attribute, dicePool);
    }

    pane = MainFrame.getGridPane();
    pane.add(new Label("Base Attributes"), 0, 0, 3, 1);
    pane.add(new Label("Current Attributes"), 3, 0, 3, 1);
    for (Attribute attribute: Attribute.values()) {
      pane.addRow(attribute.index(), new Label(attribute.getAbbreviation()),
        baseValues.get(attribute),
        attributeModifier.get(attribute),
        new Separator(),
        modifiedValues.get(attribute),
        new Separator(),
        dicePools.get(attribute).getFrame());
    }
    ColumnConstraints attributeBoxes = new ColumnConstraints(60);
    pane.getColumnConstraints().addAll(new ColumnConstraints(), attributeBoxes);

  }

  public void update(ToFCharacter character) {
    for (Attribute attribute: Attribute.values()) {
      int value = character.getBaseAttribute(attribute);
      int moddedValue = character.getModifiedAttribute(attribute);
      baseValues.get(attribute).setText(String.valueOf(value));
      attributeModifier.get(attribute).setText(fmt.format(moddedValue - value));
      modifiedValues.get(attribute).setText(String.valueOf(moddedValue));
      dicePools.get(attribute).update(character.getDicePool(attribute));
    }
  }

  public Pane getPanel() {
    return pane;
  }
}
