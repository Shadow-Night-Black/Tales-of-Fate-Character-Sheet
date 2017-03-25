package data;

public class Memory {
  private int mv;
  private int cost;

  public Memory() {
    this(30, 6);
  }

  public Memory(int mv, int cost) {
    setCost(cost);
    setMemorisationValue(mv);

  }

  public int getMv() {
    return mv;
  }

  public void setMv(int mv) {
    this.mv = Math.max(mv, 0);
  }

  public int getCost() {
      return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public void setMemorisationValue(int mv) {
    setMv(Math.max(36 - 6 * (int)Math.floor(mv/4) + cost, cost));
  }

  public int getNumberOfSlots() {
    return Math.max((37 - mv) / 6, 1);
  }
}
