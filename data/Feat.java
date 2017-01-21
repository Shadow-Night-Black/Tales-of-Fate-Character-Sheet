package data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Feat/Ability")
public class Feat {
  private Attribute attribute;
  private int bonus;

  public Feat() {
    attribute = Attribute.FINESE;
    bonus = 0;
  }

  public Feat(Attribute attribute, int bonus) {
    this.attribute = attribute;
    this.bonus = bonus;
  }

  public Attribute getAttribute() {
    return attribute;
  }

  public void setAttribute(Attribute attribute) {
    this.attribute = attribute;
  }

  public int getBonus() {
    return bonus;
  }

  public void setBonus(int bonus) {
    this.bonus = bonus;
  }
}
