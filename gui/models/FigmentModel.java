package gui.models;

import data.Feat;
import data.Figment;

public class FigmentModel extends FeatModel {
  private Figment figment;

  public FigmentModel(Feat feat, Figment figment) {
    super(feat);
    this.figment = figment;
  }

  public Figment getFigment() {
    return figment;
  }

  public void setFigment(Figment figment) {
    this.figment = figment;
  }
}

