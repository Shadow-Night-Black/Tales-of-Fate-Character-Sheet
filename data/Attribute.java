package data;

import javafx.scene.paint.Color;

public enum Attribute {


  POWER("Pow", Color.RED, "Warroch", 1, true),
  FINESE("Fin", Color.BLACK, "Virimis", 2, true),
  RESISENCE("Res", Color.BROWN, "Corservus", 3, true),
  INTELIGENCE("Int", Color.PURPLE, "Genevie", 4, false),
  WITS("Wit", Color.WHITE, "Pherynne", 5, false),
  PRESENCE("Pres", Color.GOLD, "Belia", 6, false);

  private final String abbrevation;

  private final String god;
  private final Color color;
  private final int index;
  private boolean physical;

  Attribute(String abbrevation, Color color,String god,  int index, boolean physical) {
    this.abbrevation = abbrevation;
    this.color = color;
    this.god = god;
    this.index = index;
    this.physical = physical;
  }

  public String getAbbrevation(){
    return this.abbrevation;
  }

  public Color getColor(){
    return this.color;
  }

  public int index() {
    return this.index;
  }

  public static int getModifier(int value) {
    return value/2 -3;
  }

  public boolean isPhysical() {
    return physical;
  }

  public boolean isMental() {
    return !physical;
  }

  public String getGod() {
    return god;
  }

}
