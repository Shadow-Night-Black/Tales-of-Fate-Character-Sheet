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
//  private final Map<Attribute, SpinnerAutoCommit<Integer>> experianceEditors;
  private final Map<Attribute, Label> baseValues;
  private final Map<Attribute, Label> attributeModifer;
  private final Map<Attribute, Label> modifiedValues;
//  private Map<Attribute, Label> modifiedMods;
  private final Map<Attribute, DicePoolUI> dicePools;
  private final DecimalFormat fmt;
  private final GridPane pane;

  public AttributePanel(MainFrame mainFrame, ToFCharacter character){
    baseValues = new TreeMap<>();
    attributeModifer = new TreeMap<>();
    modifiedValues = new TreeMap<>();
//    modifiedMods = new TreeMap<>();
//    experianceEditors = new TreeMap<>();
    dicePools = new TreeMap<>();
    fmt = new DecimalFormat("+#0;-#");

    for(Attribute attribute: Attribute.values()) {
//      SpinnerAutoCommit<Integer> txtAttribute = new SpinnerAutoCommit<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20));
//      txtAttribute.setEditable(true);
//      txtAttribute.getEditor().setPrefColumnCount(4);
//      int baseValue = character.getBaseAttribute(attribute);
//      txtAttribute.getValueFactory().setValue(baseValue);

//      txtAttribute.valueProperty().addListener((observable, oldValue, newValue) -> {
//        character.setAttribute(attribute, newValue);
//        mainFrame.update(character);
//      });


      int baseValue = character.getBaseAttribute(attribute);
      Label lblAttribute = new Label(String.valueOf(baseValue));
      baseValues.put(attribute, lblAttribute);

      int modifier = character.getModifiedAttribute(attribute) - character.getBaseAttribute(attribute);
      Label lblBaseMod = new Label(fmt.format(modifier));
      attributeModifer.put(attribute, lblBaseMod);

      int moddedValue = character.getModifiedAttribute(attribute);
      Label lblModValues = new Label(String.valueOf(moddedValue));
      modifiedValues.put(attribute, lblModValues);

//      int moddedMod = Attribute.getModifier(moddedValue);
//      Label lblModMod = new Label(fmt.format(moddedMod));
//      modifiedMods.put(attribute, lblModMod);


      DicePoolUI dicePool = new DicePoolUI(character.getDicePool(attribute));
      dicePools.put(attribute, dicePool);

//      SpinnerAutoCommit<Integer> txtExperiance = new SpinnerAutoCommit<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999));
//      experianceEditors.put(attribute, txtExperiance);
//      txtExperiance.setEditable(true);
//      txtExperiance.getEditor().setPrefColumnCount(6);
//      txtExperiance.getValueFactory().setValue(character.getExperiance(attribute));
//      txtExperiance.valueProperty().addListener((observable, oldValue, newValue) -> {
//        character.setExperiance(attribute, newValue);
//        mainFrame.update(character);
//      });
    }

    pane = MainFrame.getGridPane();
    pane.add(new Label("Base Attributes"), 0, 0, 3, 1);
    pane.add(new Label("Current Attributes"), 3, 0, 3, 1);
//    pane.add(new Label("Experiance"), 7, 0, 3, 1);
    for (Attribute attribute: Attribute.values()) {
      pane.addRow(attribute.index(), new Label(attribute.getAbbreviation()),
        baseValues.get(attribute),
        attributeModifer.get(attribute),
        new Separator(),
        modifiedValues.get(attribute),
//        modifiedMods.get(attribute));
        new Separator(),
        dicePools.get(attribute).getFrame());
//        new Separator(),
//        experianceEditors.get(attribute));
    }
    ColumnConstraints attributeBoxs = new ColumnConstraints(60);
    pane.getColumnConstraints().addAll(new ColumnConstraints(), attributeBoxs);

  }

  public void update(ToFCharacter character) {
    for (Attribute attribute: Attribute.values()) {
      int value = character.getBaseAttribute(attribute);
      int moddedValue = character.getModifiedAttribute(attribute);
      baseValues.get(attribute).setText(String.valueOf(value));
      attributeModifer.get(attribute).setText(fmt.format(moddedValue - value));
      modifiedValues.get(attribute).setText(String.valueOf(moddedValue));
      dicePools.get(attribute).update(character.getDicePool(attribute));
//      modifiedMods.get(attribute).setText(fmt.format(Attribute.getModifier(moddedValue)));
//      experianceEditors.get(attribute).getValueFactory().setValue(character.getExperiance(attribute));
    }
  }

  public Pane getPanel() {
    return pane;
  }
}
