package data;

import java.util.List;

public class Skill {
  private SkillLevel level;
  private String name;
  private List<Attribute> attributes;

  public Skill() {
  }

  public Skill(String name, SkillLevel level, List<Attribute> attributes) {
    this.level = level;
    this.name = name;
    this.attributes = attributes;
  }

  public SkillLevel getLevel() {
    return level;
  }

  public void setLevel(SkillLevel level) {
    this.level = level;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  public String toString() {
    return this.name + " " + this.level + " " + this.attributes.toString();
  }
}
