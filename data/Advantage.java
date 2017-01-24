package data;

public enum Advantage {
  EDISADVANTAGE("Extreme Disadvantage", -3),
  SDISADVANTAGE("S Disadvantage", -2),
  DISADVANTAGE("Disadvantage", -1),
  NOADVANTAGE("No Advantage", 0),
  ADVANTAGE("Advantage", 1),
  SADVANTAGE("S Advantage", 2),
  EADVANTAGE("Extreme Advantage", 3);

  private final String toString;
  private final int intForm;

  Advantage(String toString, int intForm) {
    this.toString = toString;
    this.intForm = intForm;
  }


  public int toInt() {
    return intForm;
  }

  public String toString() {
    return toString;
  }

  public static Advantage fromInt(int n) {
    for (Advantage advantage: Advantage.values()) {
      if (advantage.intForm == n) {
        return  advantage;
      }
    }
    System.out.println("Cant convert from " + n);
    return NOADVANTAGE;
  }
}
