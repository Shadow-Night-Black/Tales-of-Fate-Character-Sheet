package gui;

import data.Dice;
import data.DicePool;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.TreeMap;


public class DicePoolUI {


  private final Pane panel;
  private final Map<Dice, Spinner<Integer>> dice;


  DicePoolUI() {
    this(new DicePool());
  }

  DicePoolUI(DicePool pool) {

    panel = new HBox(2);

    dice = new TreeMap<>();

    for (Dice die : Dice.values()) {
        Label dieSize = new Label(die.getIdentifier());
        SpinnerAutoCommit<Integer> numberOfDie = new SpinnerAutoCommit<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8) {
        });
        numberOfDie.setEditable(true);
        numberOfDie.getEditor().setPrefColumnCount(3);
        numberOfDie.getValueFactory().setValue(pool.getNumberOfDice(die));

        numberOfDie.valueProperty().addListener((observable, oldValue, newValue) -> {
          pool.setNumberOfDice(die, newValue);
          this.update(pool);
        });

        dice.put(die, numberOfDie);


        panel.getChildren().addAll(dieSize, numberOfDie);
      if (die.pips() <= pool.getNumberOfPips()) {
        //TODO, Hide unecessary Dice
      }
    }
  }


  public void update(DicePool dicePool) {

    for (Dice die : Dice.values()) {
      dice.get(die).getValueFactory().setValue(dicePool.getNumberOfDice(die));
    }
  }

  public Node getFrame() {
    return panel;
  }
}
