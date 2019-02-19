package data;


import javax.xml.bind.annotation.XmlElement;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


public class DicePool {
  @XmlElement
  private final Map<Dice, Integer> dice;

  public DicePool() {
    this(0, 1, 0, 0, 0, 0);
  }

  public DicePool (int d4, int d6, int d8, int d10, int d12, int d20) {
    dice = new TreeMap<>();
    dice.put(Dice.d4, d4);
    dice.put(Dice.d6, d6);
    dice.put(Dice.d8, d8);
    dice.put(Dice.d10, d10);
    dice.put(Dice.d12, d12);
    dice.put(Dice.d20, d20);
  }

  public DicePool (Map<Dice, Integer> pool) {
    dice = new TreeMap<>();
    for (Dice d: Dice.values()) {
      Integer numberOfDice = pool.get(d);
      dice.put(d, Objects.requireNonNullElse(numberOfDice, 0));
    }
  }

  public int getNumberOfPips() {
    int pips = 0;
    for (Dice d : Dice.values()) {
      pips += dice.get(d) * d.pips();
    }
    return pips;
  }

  public int rollPool(SkillLevel level){
    return 0;
  }

  public int getNumberOfDice(Dice die) {
    return dice.get(die);
  }

  public void setNumberOfDice(Dice die, int number) {
    dice.put(die, number);
  }
}
