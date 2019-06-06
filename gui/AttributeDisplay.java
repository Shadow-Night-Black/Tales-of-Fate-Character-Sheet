package gui;

import data.Attribute;
import data.ToFCharacter;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.Objects;


public class AttributeDisplay {

  public Label identifier;
  public Label baseValue;
  public Label modAttribute;
  public Label modifiedValue;
  public Label dicePool;

  private static DecimalFormat formatter;

  public void setAttribute(Attribute attribute, ToFCharacter character) {
    int baseAttribute = character.getBaseAttribute(attribute);
    int moddedAttribute = character.getModifiedAttribute(attribute);
    int mod = moddedAttribute - baseAttribute;

    identifier.setText(attribute.getAbbreviation() + ": ");
    baseValue.setText(String.valueOf(baseAttribute));
    modAttribute.setText(formatModifier(mod));
    modifiedValue.setText(String.valueOf(moddedAttribute));

    dicePool.setText(character.getDicePool(attribute).toString());


  }

  private static String formatModifier(int mod) {
    if (Objects.isNull(formatter)) {
      formatter = new DecimalFormat("+#0;-#");
    }
    return formatter.format(mod);
  }
}
