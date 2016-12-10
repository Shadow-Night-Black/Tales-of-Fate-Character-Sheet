package data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Form")
public class Form {
  private int formClass;
  private List<Feat> feats;

  public Form() {
    this(3);
  }

  public Form(int formClass, Feat... feats) {
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
}
