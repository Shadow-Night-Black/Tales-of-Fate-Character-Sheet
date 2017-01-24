package gui.models;

import data.Attribute;
import data.Blessing;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by shado on 22/01/2017.
 */
public class BlessingModel {
  private Blessing blessing;
  private SimpleStringProperty name, god, desc;
  private SimpleIntegerProperty cost;

  public BlessingModel(Blessing blessing) {
    this.init(new SimpleStringProperty(blessing.getName()),
      new SimpleStringProperty(String.valueOf(blessing.getAttribute().getGod())),
      new SimpleStringProperty(blessing.getDescription()),
      new SimpleIntegerProperty(blessing.getLevel()),
      blessing);
  }

  public BlessingModel(SimpleStringProperty name, SimpleStringProperty god, SimpleStringProperty desc, SimpleIntegerProperty cost) {
    int intCost = cost.getValue();
    Attribute attribute = Attribute.valueOf(god.getValue());
    Blessing blessing = new Blessing(name.getValue(), attribute, intCost, desc.getValue());
    this.init(name, god, desc, cost, blessing);
  }

  private void init(SimpleStringProperty name, SimpleStringProperty god, SimpleStringProperty desc, SimpleIntegerProperty cost, Blessing blessing) {
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
    this.blessing.setAttribute(Attribute.valueOf(god));
    this.god.set(this.blessing.getAttribute().getGod());
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

  public Integer getCost() {
    return cost.get();
  }

  public SimpleIntegerProperty costProperty() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost.set(cost);
    this.blessing.setLevel(cost);
  }
}
