package data;

import javax.swing.text.NumberFormatter;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.NumberFormat;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Feat/Ability")
public class Feat {
  private String name, desc;
  private Attribute attribute;
  private int bonus;
  private boolean active;
  private Advantage advantages;
  private boolean advantageMode;

  public Feat() {
    this("Test", "AHHH", Attribute.FINESE, -10, false);
  }

  public Feat(String name, String desc, Attribute attribute, int bonus, boolean active) {
    this.name = name;
    this.desc = desc;
    this.attribute = attribute;
    this.bonus = bonus;
    this.advantages = Advantage.NOADVANTAGE;
    this.active = active;
    this.advantageMode = false;
  }

  public Feat(String name, String desc, Attribute attribute, Advantage advantages, boolean active) {
    this.name = name;
    this.desc = desc;
    this.attribute = attribute;
    this.active = active;
    this.bonus = 0;
    this.advantages = advantages;
    this.advantageMode = true;
  }

  public Attribute getAttribute() {
    return attribute;
  }

  public void setAttribute(Attribute attribute) {
    this.attribute = attribute;
  }

  public int getBonus() {
    if (advantageMode)
      return 0;
    return bonus;
  }

  public void setBonus(int bonus) {
    this.bonus = bonus;
    if (bonus != 0) {
      advantageMode = false;
    }
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

  public Advantage getAdvantages() {
    if (advantageMode)
      return advantages;
    return Advantage.NOADVANTAGE;
  }

  public void setAdvantages(Advantage advantages) {
    this.advantages = advantages;
    if (advantages != Advantage.NOADVANTAGE) {
      this.advantageMode = true;
    }
  }

  public String getValue() {
    if (advantageMode) {
      return  advantages.toString();
    }else {
      return NumberFormat.getIntegerInstance().format(bonus);
    }
  }

  public boolean getAdvantageMode() {
    return advantageMode;
  }

  public void setAdvantageMode(boolean advantageMode) {
    this.advantageMode = advantageMode;
  }
}
