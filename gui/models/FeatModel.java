package gui.models;

import data.Advantage;
import data.Attribute;
import data.Feat;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FeatModel {
  private Feat feat;
  private SimpleIntegerProperty bonus;
  private SimpleStringProperty name, desc, attribute;
  private SimpleBooleanProperty active, advantageMode;

  public FeatModel(Feat feat) {
    setFeat(feat);
  }

  public FeatModel(SimpleIntegerProperty bonus, SimpleStringProperty name, SimpleStringProperty desc, SimpleStringProperty attribute, SimpleBooleanProperty active, SimpleBooleanProperty advantageMode) {
    Feat feat;
    if (advantageMode.get()) {
      feat = new Feat(name.getValue(), desc.getValue(), Attribute.valueOf(attribute.getValue()), Advantage.fromInt(bonus.get()), active.getValue());
    }else {
     feat = new Feat(name.getValue(), desc.getValue(), Attribute.valueOf(attribute.getValue()), bonus.getValue(), active.getValue());
    }
    init(feat, bonus, name, desc, attribute, active, advantageMode);
  }

  public void init(Feat form, SimpleIntegerProperty bonus, SimpleStringProperty name, SimpleStringProperty desc, SimpleStringProperty attribute, SimpleBooleanProperty active, SimpleBooleanProperty advantageMode) {
    this.feat = form;
    this.name = name;
    this.desc = desc;
    this.attribute = attribute;
    this.bonus = bonus;
    this.active = active;
    this.advantageMode = advantageMode;
  }

  public Feat getFeat() {
    return feat;
  }

  public void setFeat(Feat feat) {
    SimpleIntegerProperty bonus;
    if (feat.getAdvantageMode()) {
      bonus = new SimpleIntegerProperty(feat.getAdvantages().toInt());
    }else {
      bonus = new SimpleIntegerProperty(feat.getBonus());
    }
    init(feat, bonus,
      new SimpleStringProperty(feat.getName()),
      new SimpleStringProperty(feat.getDesc()),
      new SimpleStringProperty(feat.getAttribute().name()),
      new SimpleBooleanProperty(feat.isActive()),
      new SimpleBooleanProperty(feat.getAdvantageMode()));

  }

  public int getBonus() {
    return bonus.get();
  }

  public SimpleIntegerProperty bonusProperty() {
    return bonus;
  }

  public void setBonus(int bonus) {
    this.bonus.set(bonus);
    if (advantageMode.get()) {
      this.feat.setAdvantages(Advantage.fromInt(bonus));
    }else {
      this.feat.setBonus(bonus);
    }
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
    this.feat.setName(name);
  }

  public String getDesc() {
    return desc.get();
  }

  public SimpleStringProperty descProperty() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc.set(desc);
    this.feat.setDesc(desc);
  }

  public String getAttribute() {
    return attribute.get();
  }

  public SimpleStringProperty attributeProperty() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute.set(attribute);
    this.feat.setAttribute(Attribute.valueOf(attribute));
  }

  public boolean isActive() {
    return active.get();
  }

  public SimpleBooleanProperty activeProperty() {
    return active;
  }

  public void setActive(boolean active) {
    this.active.set(active);
    this.feat.setActive(active);
  }

  public boolean isAdvantageMode() {
    return advantageMode.get();
  }

  public SimpleBooleanProperty advantageModeProperty() {
    return advantageMode;
  }

  public void setAdvantageMode(boolean advantageMode) {
    this.advantageMode.set(advantageMode);
    this.feat.setAdvantageMode(advantageMode);
  }

  public SimpleStringProperty valueProperty() {
   return new SimpleStringProperty(feat.getValue());
   }
}

