package data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Form")
public class Form {
  private String name, desc;
  private int formClass;
  private List<Feat> feats;

  public Form() {
    this("You", "Your normal everyday body", 3);
  }

  public Form(String name, String desc, int formClass, Feat... feats) {
    this.name = name;
    this.desc = desc;
    this.formClass = formClass;
    this.feats = new ArrayList<Feat>();
    for (Feat f : feats) {
      this.feats.add(f);
    }
  }

  public int getFormClass() {
    return formClass;
  }

  public void setFormClass(int formClass) {
    this.formClass = formClass;
  }

  public List<Feat> getFeats() {
    return feats;
  }

  public void setFeats(List<Feat> feats) {
    this.feats = feats;
  }

  public int getAttributeBonus(Attribute attribute) {
    int bonus = 0;
    for (Feat feat: feats) {
      if (feat.getAttribute() == attribute) {
        bonus += feat.getBonus();
      }
    }
    return bonus;
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
}
