package gui;

import data.ToFCharacter;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


/**
 * Created by shado on 10/12/2016.
 */
public class NotesPanel {


  private final GridPane grid;
  private final TextField txtName;
  private final TextArea txtBio;

  public NotesPanel(MainFrame mainFrame, ToFCharacter character) {
    grid = MainFrame.getGridPane();

    txtName = new TextField(character.getName());
    txtName.textProperty().addListener((observable, oldValue, newValue) -> {
      character.setName(newValue);
      mainFrame.update(character);
    });

    txtBio = new TextArea(character.getBio());
    txtBio.textProperty().addListener((observable, oldValue, newValue) -> {
      character.setBio(newValue);
      mainFrame.update(character);
    });
    grid.addColumn(0, new Label("Character's Name:"), new Label("Character's Bio:"));
    grid.addColumn(1, txtName, txtBio);
  }

  public void update(ToFCharacter character) {
    txtName.setText(character.getName());
    txtBio.setText(character.getBio());
  }

  public Pane getPanel() {
    return grid;
  }
}
