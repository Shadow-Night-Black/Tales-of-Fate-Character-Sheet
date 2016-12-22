package gui;

import data.ToFCharacter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Optional;

/**
 * Created by shado on 10/12/2016.
 */
public class StatsPanel {

  private final MainFrame mainFrame;
  private Label lblBaseBody;
  private Label lblCurrentBody;
  private Label lblBaseMind;
  private Label lblCurrentMind;
  private Label lblBaseDC;
  private Label lblCurrentDC;
  private Label lblBaseMC;
  private Label lblCurrentMC;
  private Label lblBaseFate;
  private Label lblCurrentFate;
  private GridPane grid;
  private ToFCharacter toFCharacter;

  public StatsPanel(MainFrame mainFrame, ToFCharacter character) {
    this.toFCharacter = character;
    this.mainFrame = mainFrame;
    grid = MainFrame.getGridPane();

    Label lblBase = new Label("Base:");
    Label lblCurrent = new Label("Current:");

    grid.addColumn(0,new Label(),  lblBase, lblCurrent);
    Label lblBody = new Label("Body");
    lblBaseBody = new Label(String.valueOf(character.getBaseBody()));
    lblCurrentBody = new Label(String.valueOf(character.getCurrentBody()));
    grid.addColumn(1, lblBody, lblBaseBody, lblCurrentBody);

    Label lblMind = new Label("Mind");
    lblBaseMind = new Label(String.valueOf(character.getBaseMind()));
    lblCurrentMind = new Label(String.valueOf(character.getCurrentMind()));
    grid.addColumn(2, lblMind, lblBaseMind, lblCurrentMind);

    Label lblDC = new Label("DC");
    lblBaseDC = new Label(String.valueOf(character.getBaseDC()));
    lblCurrentDC = new Label(String.valueOf(character.getCurrentDC()));
    grid.addColumn(3, lblDC, lblBaseDC, lblCurrentDC);

    Label lblMC = new Label("MC");
    lblBaseMC = new Label(String.valueOf(character.getBaseMC()));
    lblCurrentMC = new Label(String.valueOf(character.getCurrentMC()));
    grid.addColumn(4, lblMC, lblBaseMC, lblCurrentMC);


    Label lblFate = new Label("Fate");
    lblBaseFate = new Label(String.valueOf(character.getBaseFate()));
    lblCurrentFate = new Label(String.valueOf(character.getCurrentFate()));
    grid.addColumn(5, lblFate, lblBaseFate, lblCurrentFate);


    Button btnDamge = new Button("Take Damage");
    Button btnHeal = new Button(("Heal Damage"));
    btnDamge.setOnAction(actionEvent -> createPrompt(true));
    btnHeal.setOnAction(actionEvent -> createPrompt(false));
    grid.add(btnDamge, 6, 1);
    grid.add(btnHeal, 6, 2);

  }


  private void createPrompt(boolean damage) {
    Dialog<Pair<Integer, Integer>> healPrompt = new Dialog<>();
    if (damage) {
      healPrompt.setTitle("Damage");
      healPrompt.setHeaderText("How much Damage did " + toFCharacter.getName() + " take?");
    }else {
      healPrompt.setTitle("Healing");
      healPrompt.setHeaderText("How do you want to Heal " + toFCharacter.getName() + "?");
    }

    String btnText = damage?"Damage":"Heal";
    ButtonType btnHeal = new ButtonType(btnText, ButtonBar.ButtonData.OK_DONE);
    healPrompt.getDialogPane().getButtonTypes().addAll(btnHeal, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField txtHealingBody = new TextField();
    txtHealingBody.setPromptText("Enter your change in Body");
    TextField txtHealingMind = new TextField();
    txtHealingMind.setPromptText("Enter your change in Mind");

    grid.add(new Label("Body:"), 0, 0);
    grid.add(txtHealingBody, 1, 0);
    grid.add(new Label("Mind:"), 0, 1);
    grid.add(txtHealingMind, 1, 1);

    txtHealingBody.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        Platform.runLater(() -> {
          txtHealingBody.setText(newValue.replaceAll("[^\\d*]", ""));
          txtHealingBody.positionCaret(txtHealingBody.getLength());
        });}});

    txtHealingMind.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        Platform.runLater(() -> {
          txtHealingMind.setText(newValue.replaceAll("[^\\d*]", ""));
          txtHealingMind.positionCaret(txtHealingMind.getLength());
        });}});

    healPrompt.getDialogPane().setContent(grid);
    // Request focus on the username field by default.
    Platform.runLater(txtHealingBody::requestFocus);



// Convert the result to a username-password-pair when the login button is clicked.
    healPrompt.setResultConverter(dialogButton -> {
      if (dialogButton == btnHeal) {
        int healedBody, healedMind;
        try {
          healedBody = Integer.parseInt(txtHealingBody.getText());
        }catch (NumberFormatException e) {
          healedBody = 0;
        }

        try {
          healedMind = Integer.parseInt(txtHealingMind.getText());
        } catch (NumberFormatException e) {
          healedMind = 0;
        }
        return new Pair<>(healedBody, healedMind);
      }
      return null;
    });

    Optional<Pair<Integer, Integer>> result = healPrompt.showAndWait();

    result.ifPresent(healing -> {
      if (damage) {
        toFCharacter.setCurrentBody(toFCharacter.getCurrentBody() - healing.getKey());
        toFCharacter.setCurrentMind(toFCharacter.getCurrentMind() - healing.getValue());
      }else {
        toFCharacter.setCurrentBody(toFCharacter.getCurrentBody() + healing.getKey());
        toFCharacter.setCurrentMind(toFCharacter.getCurrentMind() + healing.getValue());
      }
      mainFrame.update(toFCharacter);
    });
  }


  public Pane getPanel() {
    return grid;
  }

  public void update(ToFCharacter character) {
    this.toFCharacter = character;

    lblBaseBody.setText(String.valueOf(character.getBaseBody()));
    lblCurrentBody.setText(String.valueOf(character.getCurrentBody()));

    lblBaseMind.setText(String.valueOf(character.getBaseMind()));
    lblCurrentMind.setText(String.valueOf(character.getCurrentMind()));

    lblBaseDC.setText(String.valueOf(character.getBaseDC()));
    lblCurrentDC.setText(String.valueOf(character.getCurrentDC()));

    lblBaseMC.setText(String.valueOf(character.getBaseMC()));
    lblCurrentMC.setText(String.valueOf(character.getCurrentMC()));


    lblBaseFate.setText(String.valueOf(character.getBaseFate()));
    lblCurrentFate.setText(String.valueOf(character.getCurrentFate()));

  }

}
