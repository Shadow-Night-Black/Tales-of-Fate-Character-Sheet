package data;

public enum Dice {
  d4(4, "D4"),
  d6(6, "D6"),
  d8(8, "D8"),
  d10(10, "D10"),
  d12(12, "D12"),
  d20(20, "D20");

  private String identifier;
  private int size;


  Dice(int size, String identifier) {
    this.size = size;
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public int roll() {
    int result = 0;
    int roll = 0;
    do {
      roll =(int)( Math.random() * size + 1);
      result += roll;
    } while (roll == size);
    return result;
  }

  public int pips () {
    return size;
  }
}
