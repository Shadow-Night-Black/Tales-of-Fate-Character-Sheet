package gui.models;

import data.Feat;
import data.Figment;
import data.Skill;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class ItemModel {
  private List<FigmentModel> figmentModels;
  private SimpleStringProperty name, desc;
  private SimpleIntegerProperty cost, mv;
  private SimpleBooleanProperty equipped;
  private Figment figment;

  public ItemModel(Figment figment) {
    figmentModels = new ArrayList<>();
    this.name = new SimpleStringProperty();
    this.desc = new SimpleStringProperty();
    this.cost = new SimpleIntegerProperty();
    this.mv = new SimpleIntegerProperty();
    this.equipped = new SimpleBooleanProperty();
    setFigment(figment);
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
    this.figment.setName(name);
  }

  public String getDesc() {
    return desc.get();
  }

  public SimpleStringProperty descProperty() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc.set(desc);
    this.figment.setDesc(desc);
  }

  public int getCost() {
    return cost.get();
  }

  public SimpleIntegerProperty costProperty() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost.set(cost);
    this.figment.setCost(cost);
  }

  public int getMv() {
    return mv.get();
  }

  public SimpleIntegerProperty mvProperty() {
    return mv;
  }

  public void setMv(int mv) {
    this.mv.set(mv);
    this.figment.setMv(mv);
  }

  public boolean isEquipped() {
    return equipped.get();
  }

  public SimpleBooleanProperty equippedProperty() {
    return equipped;
  }

  public void setEquipped(boolean equipped) {
    this.equipped.set(equipped);
  }

  public List<FigmentModel> getFigmentModels() {
    return figmentModels;
  }

  public void addFigmentModel(FigmentModel model) {
    this.figmentModels.add(model);
  }

  public void removeFigmentModel(FigmentModel model) {
    this.figmentModels.remove(model);
  }

  public Figment getFigment() {
    return figment;
  }

  public void setFigment(Figment figment) {
    this.figment = figment;
    this.name.set(figment.getName());
    this.desc.set(figment.getDesc());
    this.cost.set(figment.getCost());
    this.mv.set(figment.getMv());
    this.equipped.set(figment.isEquipped());
    for (Skill skill: figment.getSkillBonuses()) {
    //  figmentModels.add(new FigmentModel(skill));
    }
    for (Feat feat: figment.getFeatBonuses()){
      figmentModels.add(new FigmentModel(feat, figment, this));
    }
  }
}
