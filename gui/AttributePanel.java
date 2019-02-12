package gui;

import data.Attribute;
import data.ToFCharacter;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class AttributePanel {
  private final Map<Attribute, SpinnerAutoCommit<Integer>> experianceEditors;
  private Map<Attribute, SpinnerAutoCommit<Integer>> baseValues;
  private Map<Attribute, Label> baseMods;
  private Map<Attribute, Label> modifiedValues;
  private Map<Attribute, Label> modifiedMods;
  private final DecimalFormat fmt;
  private final GridPane pane;

  public AttributePanel(MainFrame mainFrame, ToFCharacter character){
    baseValues = new TreeMap<>();
    baseMods = new TreeMap<>();
    modifiedValues = new TreeMap<>();
    modifiedMods = new TreeMap<>();
    experianceEditors = new TreeMap<>();
    fmt = new DecimalFormat("+#0;-#");

    for(Attribute attribute: Attribute.values()) {
      SpinnerAutoCommit<Integer> txtAttribute = new SpinnerAutoCommit<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20));
      txtAttribute.setEditable(true);
      txtAttribute.getEditor().setPrefColumnCount(4);
      int baseValue = character.getBaseAttribute(attribute);
      txtAttribute.getValueFactory().setValue(baseValue);

      txtAttribute.valueProperty().addListener((observable, oldValue, newValue) -> {
        character.setAttribute(attribute, newValue);
        mainFrame.update(character);
      });

      baseValues.put(attribute, txtAttribute);

      int mod = Attribute.getModifier(baseValue);
      Label lblBaseMod = new Label(fmt.format(mod));
      baseMods.put(attribute, lblBaseMod);

      int moddedValue = character.getModifiedAttribute(attribute);
      Label lblModValues = new Label(String.valueOf(moddedValue));
      modifiedValues.put(attribute, lblModValues);

      int moddedMod = Attribute.getModifier(moddedValue);
      Label lblModMod = new Label(fmt.format(moddedMod));
      modifiedMods.put(attribute, lblModMod);


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
        baseMods.get(attribute),
        new Separator(),
        modifiedValues.get(attribute),
        modifiedMods.get(attribute));
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
      baseValues.get(attribute).getValueFactory().setValue(value);
      baseMods.get(attribute).setText(fmt.format(Attribute.getModifier(value)));
      modifiedValues.get(attribute).setText(String.valueOf(moddedValue));
      modifiedMods.get(attribute).setText(fmt.format(Attribute.getModifier(moddedValue)));
//      experianceEditors.get(attribute).getValueFactory().setValue(character.getExperiance(attribute));
    }
  }

  public Pane getPanel() {
    return pane;
  }
}
