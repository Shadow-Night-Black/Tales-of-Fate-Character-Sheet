package gui;

import data.Attribute;
import data.ToFCharacter;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CharacterSheet implements Initializable {
  public Tab attributeTab;
  public VBox attributeDisplayArea;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }

  public void update(ToFCharacter character) {
    attributeDisplayArea.getChildren().clear();
    for (Attribute attribute : Attribute.values()) {
      try {
        URL url = getClass().getResource("FXML/AttributeDisplay.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Pane p = loader.load();
        AttributeDisplay attributeDisplay = loader.getController();
        attributeDisplay.setAttribute(attribute, character);
        attributeDisplayArea.getChildren().addAll(p.getChildren());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
