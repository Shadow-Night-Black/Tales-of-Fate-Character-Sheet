package data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by shado on 06/12/2016.
 */
@XmlRootElement(name = "Blessing")
public class Blessing {
  private String Name;
  private int level;
  private String Description;
  private Attribute attribute;

  public Blessing() {
    this("Test", Attribute.POWER, 1, "Debug");
  }
  public Blessing(String name, Attribute god, int level, String description) {
    Name = name;
    this.level = level;
    Description = description;
    this.attribute = god;
  }

  public Attribute getAttribute() {
    return attribute;
  }

  public void setAttribute(Attribute attribute) {
    this.attribute = attribute;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }
}
