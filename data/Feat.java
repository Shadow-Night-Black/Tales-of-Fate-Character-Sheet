package data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Feat/Ability")
public class Feat {
  private String name, desc;
  private Attribute attribute;
  private int bonus;
  private boolean active;

  public Feat() {
    this("Test", "AHHH", Attribute.FINESE, -10, true);
  }

  public Feat(String name, String desc, Attribute attribute, int bonus, boolean active) {
    this.name = name;
    this.desc = desc;
    this.attribute = attribute;
    this.bonus = bonus;
    this.active = active;
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

  public boolean isActive() {
    return active;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
