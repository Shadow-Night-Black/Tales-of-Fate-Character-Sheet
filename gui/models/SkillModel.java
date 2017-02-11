package gui.models;

import data.Attribute;
import data.Skill;
import data.SkillLevel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by shado on 11/02/2017.
 */
public class SkillModel {
  private SimpleStringProperty name, level, attributes;
  private Skill skill;

  public SkillModel(Skill skill) {
    name = new SimpleStringProperty();
    level = new SimpleStringProperty();
    attributes = new SimpleStringProperty();
    setSkill(skill);
  }


  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
    skill.setName(name);
  }

  public String getLevel() {
    return level.get();
  }

  public SimpleStringProperty levelProperty() {
    return level;
  }

  public void setLevel(String level) {
    this.level.set(level);
    skill.setLevel(SkillLevel.valueOf(level));
  }

  public String getAttributes() {
    return attributes.get();
  }

  public SimpleStringProperty attributesProperty() {
    return attributes;
  }

  public void setAttributes(String attributes) {
    this.attributes.set(attributes);
    String[] result = attributes.split(Pattern.quote(","));
    List<Attribute> attributeList = new ArrayList<>();
    for (String s: result) {
      attributeList.add(Attribute.valueOf(s));
    }
    skill.setAttributes(attributeList);
  }

  public Skill getSkill() {
    return skill;
  }

  public void setSkill(Skill skill) {
    this.skill = skill;
    this.name.set(skill.getName());
    this.level.set(skill.getLevel().toString());
    setAttribute(skill.getAttributes());


  }

  public void setAttribute(List<Attribute> attributes) {
    if (attributes.size() > 0) {
      StringBuilder s = new StringBuilder();
      for (Attribute attribute : skill.getAttributes()) {
        s.append(attribute.toString() + ", ");
      }
      s.deleteCharAt(s.length() - 1);
      s.deleteCharAt(s.length() - 1);
      this.attributes.set(s.toString());
      skill.setAttributes(attributes);
    }else {
      this.attributes.set("");
      skill.setAttributes(new ArrayList<>());
    }
  }
}
