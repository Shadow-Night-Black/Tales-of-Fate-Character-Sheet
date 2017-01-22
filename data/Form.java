package data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
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
    this("You", 3, "Your normal everyday body");
  }

  public Form(String name, int formClass, String desc, Feat... feats) {
    this.name = name;
    this.desc = desc;
    this.formClass = formClass;
    this.feats = new ArrayList<>();
    for (Feat feat: feats) {
      this.feats.add(feat);
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

  public void addFeat (Feat feat) {
    this.feats.add(feat);
  }

  public int getAttributeBonus(Attribute attribute) {
    int bonus = 0;
    for (Feat feat: feats) {
      if (feat.getAttribute() == attribute && feat.isActive()) {
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

  public void removeFeat(Feat feat) {
    feats.remove(feat);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Form))
      return false;
    Form form = (Form) obj;
    return form.getName().equals(name);
  }
}
