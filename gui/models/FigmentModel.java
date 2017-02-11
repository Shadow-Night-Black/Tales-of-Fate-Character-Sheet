package gui.models;

import data.Feat;
import data.Figment;
import javafx.beans.property.SimpleStringProperty;

public class FigmentModel extends FeatModel {
  private Figment  figment;
  private ItemModel model;

  public FigmentModel(Feat feat, Figment figment, ItemModel model) {
    super(feat);
    this.figment = figment;
    this.model = model;
  }

  public Figment getFigment() {
    return figment;
  }

  public void setFigment(Figment figment) {
    this.figment = figment;
  }

  public ItemModel getModel() {
    return model;
  }

  public void setModel(ItemModel model) {
    this.model = model;
  }
}
