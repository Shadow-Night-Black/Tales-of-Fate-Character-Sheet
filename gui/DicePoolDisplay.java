package gui;

import data.Dice;
import data.DicePool;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.TreeMap;

public class DicePoolDisplay {

  private final Pane panel;
  private final Label text;


  DicePoolDisplay() {
    this(new DicePool());
  }

  DicePoolDisplay(DicePool pool) {

    panel = new HBox(2);
    text = new Label( pool.toString());

    panel.getChildren().add(text);
  }


  public void update(DicePool dicePool) {
    text.setText(dicePool.toString());
  }

  public Node getFrame() {
    return panel;
  }
}
