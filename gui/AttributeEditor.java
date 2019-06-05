package gui;

import data.Attribute;
import data.ToFCharacter;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class AttributeEditor {
  private ToFCharacter toFCharacter;
  private Map<Attribute, Spinner<Integer>> attributes;
  private Map<Attribute, DicePoolEditor> dicePools;
  private MainFrame mainFrame;

  AttributeEditor(MainFrame mainFrame, ToFCharacter toFCharacter) {
    this.mainFrame = mainFrame;
    this.toFCharacter = toFCharacter;
    attributes = new TreeMap<>();
    dicePools = new TreeMap<>();
  }


  public void show() {
    Stage popUp = new Stage();
    popUp.initOwner(mainFrame.getStage());
    popUp.initModality(Modality.WINDOW_MODAL);

    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    Scene scene = new Scene(vBox);


    GridPane pane = new GridPane();

    for (Attribute attribute: Attribute.values()) {
      Label attributeName = new Label(attribute.getAbbreviation());
      Spinner<Integer> attributeValue = new Spinner<Integer>(0, 24, toFCharacter.getBaseAttribute(attribute));
      attributes.put(attribute, attributeValue);

      DicePoolEditor attributePool = new DicePoolEditor(toFCharacter.getDicePool(attribute));
      dicePools.put(attribute, attributePool);

      pane.addRow(attribute.index(), attributeName, attributeValue, attributePool.getFrame());

    }


    HBox acceptQuit = new HBox();
    acceptQuit.setAlignment(Pos.CENTER);

    Button btnAccept = new Button("Accept Changes");
    Button btnQuit = new Button("Cancel");

    btnAccept.setOnAction(actionEvent -> {
      Arrays.stream(Attribute.values())
        .forEach(attribute -> {
          toFCharacter.setAttribute(attribute, attributes.get(attribute).getValue());
          toFCharacter.setDicePool(attribute, dicePools.get(attribute).getDicePool());
        });
      popUp.close();
    });

    btnQuit.setOnAction(actionEvent -> popUp.close());

    acceptQuit.getChildren().addAll(btnAccept, btnQuit);

    vBox.getChildren().addAll(pane, acceptQuit);
    popUp.setScene(scene);
    popUp.showAndWait();

    mainFrame.update(toFCharacter);


  }
}
