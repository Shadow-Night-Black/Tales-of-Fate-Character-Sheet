package data;


import java.util.ArrayList;
import java.util.List;

public class Figment extends Memory implements Comparable<Figment>{
  private String name, desc;
  private List<Skill> skillBonuses;
  private List<Feat> featBonuses;
  private boolean equipped;

  public Figment() {
    this("Test", "Testing", 0, 10, true);
  }

  public Figment(String name, String desc, int mv, int cost, boolean equipped) {
    super(mv, cost);
    this.name = name;
    this.desc = desc;
    this.equipped = equipped;
    this.skillBonuses = new ArrayList<>();
    this.featBonuses = new ArrayList<>();
  }

  public void addSkillBonus(Skill skill) {
    skillBonuses.add(skill);
  }

  public void removeSkillBonus(Skill skill) {
    skillBonuses.remove(skill);
  }

  public void addFeatBonus(Feat feat) {
    featBonuses.add(feat);
  }

  public void removeFeatBonus(Feat feat) {
    featBonuses.remove(feat);
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

  public List<Skill> getSkillBonuses() {
    return skillBonuses;
  }

  public void setSkillBonuses(List<Skill> skillBonuses) {
    this.skillBonuses = skillBonuses;
  }

  public List<Feat> getFeatBonuses() {
    return featBonuses;
  }

  public void setFeatBonuses(List<Feat> featBonuses) {
    this.featBonuses = featBonuses;
  }

  public boolean isEquipped() {
    return equipped;
  }

  public void setEquipped(boolean equipped) {
    this.equipped = equipped;
  }

  @Override
  public int compareTo(Figment o) {
    int result = this.getName().compareTo(o.getName());
    if (result == 0) {
      return this.getDesc().compareTo(o.getDesc());
    }
    return result;
  }
}
