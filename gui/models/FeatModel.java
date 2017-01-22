package gui.models;

import data.Attribute;
import data.Feat;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by shado on 22/01/2017.
 */
public class FeatModel {
  private Feat feat;
  private SimpleIntegerProperty bonus;
  private SimpleStringProperty name, desc, attribute;
  private SimpleBooleanProperty active;

  public FeatModel(Feat feat) {
    setFeat(feat);
  }

  public FeatModel(SimpleIntegerProperty bonus, SimpleStringProperty name, SimpleStringProperty desc, SimpleStringProperty attribute, SimpleBooleanProperty active) {
    Feat feat = new Feat(name.getValue(), desc.getValue(), Attribute.valueOf(attribute.getValue()), bonus.getValue(), active.getValue());
    init(feat, bonus, name, desc, attribute, active);
  }

  public void init(Feat form, SimpleIntegerProperty formClass, SimpleStringProperty name, SimpleStringProperty desc, SimpleStringProperty attribute, SimpleBooleanProperty active) {
    this.feat = form;
    this.name = name;
    this.desc = desc;
    this.attribute = attribute;
    this.bonus = formClass;
    this.active = active;
  }

  public Feat getFeat() {
    return feat;
  }

  public void setFeat(Feat feat) {
    init(feat, new SimpleIntegerProperty(feat.getBonus()),
      new SimpleStringProperty(feat.getName()),
      new SimpleStringProperty(feat.getDesc()),
      new SimpleStringProperty(feat.getAttribute().name()),
      new SimpleBooleanProperty(feat.isActive()));

  }

  public int getBonus() {
    return bonus.get();
  }

  public SimpleIntegerProperty bonusProperty() {
    return bonus;
  }

  public void setBonus(int bonus) {
    this.bonus.set(bonus);
    this.feat.setBonus(bonus);
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
}

