package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Skill implements Comparable<Skill>{
  private SkillLevel level;
  private String name;
  private List<Attribute> attributes;


  public Skill(String name, SkillLevel level, List<Attribute> attributes) {
    this.level = level;
    this.name = name;
    this.attributes = Objects.requireNonNullElse( attributes, new ArrayList<>());
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

  @Override
  public int compareTo(Skill o) {
    return this.getName().compareTo(o.getName());
  }
}
