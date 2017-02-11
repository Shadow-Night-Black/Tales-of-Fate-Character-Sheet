package data;

/**
 * Created by shado on 11/02/2017.
 */
public class Memory {
  private int mv;
  private int cost;

  public Memory() {
    this(30, 6);
  }

  public Memory(int mv, int cost) {
    this.mv = mv;
    this.cost = cost;

  }

  public int getMv() {
    return mv;
  }

  public void setMv(int mv) {
    this.mv = mv;
  }

  public int getCost() {
    if (mv > 0)
      return cost;
    else
      return 0;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }
}
