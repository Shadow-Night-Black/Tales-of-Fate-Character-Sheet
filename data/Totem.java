package data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Totem")
public class Totem {
  private static final int STARTING_BLESSING_COUNT = 6;
  private List<Blessing> blessings;
  private Map<Attribute, Integer> totemBonus;


  public Totem() {
    blessings = new ArrayList<>();
    totemBonus = new TreeMap<>();
    for (Attribute attribute: Attribute.values()) {
      totemBonus.put(attribute, 0);
    }
  }

  public int getBaseFate() {
    return STARTING_BLESSING_COUNT + blessings.size();
  }

  public void addBlessing(Blessing blessing) {
    blessings.add(blessing);
  }

  public int getAttributeBonus(Attribute attribute) {
    return totemBonus.get(attribute);
  }

  public void setAttributeBonus(Attribute attribute, int value) {
    totemBonus.put(attribute, value);
  }

  public List<Blessing> getBlessings() {
    return blessings;
  }

  public void setBlessings(List<Blessing> blessings) {
    this.blessings = blessings;
  }

  public Map<Attribute, Integer> getTotemBonus() {
    return totemBonus;
  }

  public void setTotemBonus(Map<Attribute, Integer> totemBonus) {
    this.totemBonus = totemBonus;
  }

  public void deleteBlessing(Blessing blessing) {
    blessings.remove(blessing);
  }
}
