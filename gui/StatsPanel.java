package gui;

import data.ToFCharacter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatsPanel {

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
  private GridPane gridHealth;
  private GridPane gridPoints;
  private List<Pair<Label, Label>> listSegments;
  private ToFCharacter toFCharacter;
//  private final Label lblBaseInit;
//  private final Label lblCurrentInit;

  public StatsPanel(MainFrame mainFrame, ToFCharacter character) {
    this.toFCharacter = character;
    gridPoints = MainFrame.getGridPane();
    gridHealth = MainFrame.getGridPane();

    Label lblClass = new Label("Class");
    Label lblBody = new Label("Body");
    Label lblMind = new Label("Mind");
    gridHealth.addColumn(0, lblClass, lblBody, lblMind);

    listSegments = new ArrayList<>(7);

    List<Integer> listBody = character.getBody();
    List<Integer> listMind = character.getMind();
    for (int i=0; i<=6; i++) {
      Label body = new Label(String.valueOf(listBody.get(i)));
      Label mind = new Label(String.valueOf(listMind.get(i)));
      gridHealth.addColumn(7-i, new Label(String.valueOf(i)), body, mind);
      listSegments.add(new Pair<>(body, mind));
    }


    Label lblBase = new Label("Base:");
    Label lblCurrent = new Label("Current:");

    gridPoints.addColumn(0,new Label(),  lblBase, lblCurrent);

    Label lblDC = new Label("DC");
    lblBaseDC = new Label(String.valueOf(character.getBaseDC()));
    lblCurrentDC = new Label(String.valueOf(character.getCurrentDC()));
    gridPoints.addColumn(1, lblDC, lblBaseDC, lblCurrentDC);

    Label lblMC = new Label("MC");
    lblBaseMC = new Label(String.valueOf(character.getBaseMC()));
    lblCurrentMC = new Label(String.valueOf(character.getCurrentMC()));
    gridPoints.addColumn(2, lblMC, lblBaseMC, lblCurrentMC);

//    Label lblInit = new Label("Initative");
//    lblBaseInit = new Label(String.valueOf(character.getBaseInit()));
//    lblCurrentInit = new Label(String.valueOf(character.getCurrentInit()));
//    gridPoints.addColumn(3, lblInit, lblBaseInit, lblCurrentInit);

    Label lblFate = new Label("Fate");
    lblBaseFate = new Label(String.valueOf(character.getBaseFate()));
    lblCurrentFate = new Label(String.valueOf(character.getCurrentFate()));
    gridPoints.addColumn(4, lblFate, lblBaseFate, lblCurrentFate);

    Button btnHeal = new Button("Heal Damage");
    Button btnDamge = new Button("Take Damage");
    btnHeal.setOnAction(actionEvent ->  {
      Optional<Pair<Integer, Integer>> result = createDoublePrompt("Healing", "How much do you wish to heal?", "Body", "Mind", "Heal");
      if (result.isPresent()) {
        Pair<Integer, Integer> values = result.get();
        toFCharacter.healPhysicalDamage(getBodyValue(values));
        toFCharacter.healMentalDamage(getMindValue(values));
        mainFrame.update(toFCharacter);

      }
    });
    btnDamge.setOnAction(actionEvent -> {
      Optional<Pair<Integer, Integer>> result = createDoublePrompt("Damage", "How much damage did you take?", "Body", "Mind", "Take Damage");
      if (result.isPresent()) {
        Pair<Integer, Integer> values = result.get();
        toFCharacter.takePhysicalDamage(getBodyValue(values));
        toFCharacter.takeMentalDamage(getMindValue(values));
        mainFrame.update(toFCharacter);
      }
    });
    gridPoints.add(btnHeal, 5, 1);
    gridPoints.add(btnDamge, 5, 2);

//    Button btnInitSet = new Button("Set Initative");
//    Button btnInitUse = new Button("Use Initative");
//    btnInitSet.setOnAction(event -> {
//      Optional<Integer> result = createSinglePrompt("Set Initative", "What is your new Initiative?", "");
//      if (result.isPresent()) {
//        toFCharacter.setBaseInit(result.get());
//        mainFrame.update(toFCharacter);
//      }
//    });

//    btnInitUse.setOnAction(event -> {
//     Optional<Integer> result = createSinglePrompt("Use Initative", "How much Initative do you wish to use?", "");
//      if (result.isPresent()) {
//        Integer value = result.get();
//        if (value > toFCharacter.getCurrentInit()) {
//          Alert alert = new Alert(Alert.AlertType.ERROR);
//          alert.setTitle("Too little Initative!");
//          alert.setContentText("You have too little Initative to use that much!");
//          alert.showAndWait();
//        }else {
//          toFCharacter.setCurrentInit(toFCharacter.getCurrentInit() - result.get());
//          mainFrame.update(toFCharacter);
//        }
//      }
//    });

//    gridPoints.add(btnInitSet, 6, 1);
//    gridPoints.add(btnInitUse, 6, 2);


    Button btnFateGain = new Button("Recover Fate");
    Button btnFateUse = new Button("Use Fate");
    btnFateGain.setOnAction(event -> {
      Optional<Integer> result = createSinglePrompt("Recover Current Fate", "How much Fate do you regain?", "");
      if (result.isPresent()) {
        toFCharacter.setCurrentFate(result.get() + toFCharacter.getCurrentFate());
        mainFrame.update(toFCharacter);
      }
    });

    btnFateUse.setOnAction(event -> {
     Optional<Integer> result = createSinglePrompt("Use Fate", "How much Fate do you wish to use?", "");
      if (result.isPresent()) {
        Integer value = result.get();
        if (value > toFCharacter.getCurrentFate()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Too little Fate!");
          alert.setContentText("You have too little Fate to use that much!");
          alert.showAndWait();
        }else {
          toFCharacter.setCurrentFate(toFCharacter.getCurrentFate() - result.get());
          mainFrame.update(toFCharacter);
        }
      }
    });
    gridPoints.add(btnFateGain, 7, 1);
    gridPoints.add(btnFateUse, 7,2);

//    Button btnMaxFateRecovery = new Button("Regain Max Fate");
//    Button btnMaxFateDamage = new Button("Lose Max Fate");
//    btnMaxFateRecovery.setOnAction(event -> {
//      Optional<Integer> result = createSinglePrompt("Recover Max Fate", "How much Max Fate do you regain?", "");
//      if (result.isPresent()) {
//        int amount = result.get();
//        toFCharacter.setCurrentFate(toFCharacter.getCurrentFate() + amount);
//        toFCharacter.setFateDamage(toFCharacter.getFateDamage() - amount);
//        mainFrame.update(toFCharacter);
//      }
//    });
//    btnMaxFateDamage.setOnAction(event -> {
//      toFCharacter.setCurrentFate(toFCharacter.getCurrentFate() - 1);
//      toFCharacter.setFateDamage(toFCharacter.getFateDamage()+1);
//      mainFrame.update(toFCharacter);
//    });
//    gridPoints.add(btnMaxFateRecovery, 8, 1);
//    gridPoints.add(btnMaxFateDamage, 8,2);


  }

  private Optional<Integer> createSinglePrompt(String title, String query, String label){
    TextInputDialog dialog = new TextInputDialog(label);
    dialog.setTitle(title);
    dialog.setHeaderText(query);
    dialog.setContentText(query);

    TextField field = dialog.getEditor();
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        Platform.runLater(() -> {
          field.setText(newValue.replaceAll("[^\\d*]", ""));
          field.positionCaret(field.getLength());
        });}});

// Traditional way to get the response value.
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()){
      return Optional.of(Integer.parseInt(result.get()));
    }else {
      return Optional.empty();
    }
  }

  private Optional<Pair<Integer, Integer>> createDoublePrompt(String title, String query, String label1, String label2, String buttonLabel) {
    Dialog<Pair<Integer, Integer>> prompt = new Dialog<>();
    prompt.setTitle(title);
    prompt.setHeaderText(query);

    ButtonType btnConfirm = new ButtonType(buttonLabel, ButtonBar.ButtonData.OK_DONE);
    prompt.getDialogPane().getButtonTypes().addAll(btnConfirm, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField txtHealingBody = new TextField();
    txtHealingBody.setPromptText(label1);
    TextField txtHealingMind = new TextField();
    txtHealingMind.setPromptText(label2);

    grid.add(new Label(label1), 0, 0);
    grid.add(txtHealingBody, 1, 0);
    grid.add(new Label(label2), 0, 1);
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

    prompt.getDialogPane().setContent(grid);
    // Request focus on the username field by default.
    Platform.runLater(txtHealingBody::requestFocus);



// Convert the result to a username-password-pair when the login button is clicked.
    prompt.setResultConverter(dialogButton -> {
      if (dialogButton == btnConfirm) {
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

    return prompt.showAndWait();
  }


  public Pane getPanel() {
    return new VBox(gridHealth, gridPoints);
  }

  public void update(ToFCharacter character) {
    this.toFCharacter = character;

    List<Integer> listBody = character.getBody();
    List<Integer> listMind = character.getMind();
    for (int i=6; i>=0; i--) {
      Pair<Label, Label> segment = listSegments.get(i);
      getBodyValue(segment).setText(String.valueOf(listBody.get(i)));
      getMindValue(segment).setText(String.valueOf(listMind.get(i)));
    }

    lblBaseDC.setText(String.valueOf(character.getBaseDC()));
    lblCurrentDC.setText(String.valueOf(character.getCurrentDC()));

    lblBaseMC.setText(String.valueOf(character.getBaseMC()));
    lblCurrentMC.setText(String.valueOf(character.getCurrentMC()));

//    lblBaseInit.setText(String.valueOf(character.getBaseInit()));
//    lblCurrentInit.setText(String.valueOf(character.getCurrentInit()));

    lblBaseFate.setText(String.valueOf(character.getBaseFate()));
    lblCurrentFate.setText(String.valueOf(character.getCurrentFate()));

  }

  private <T extends Pair<V, V>, V> V getBodyValue(T pair) {
    return pair.getKey();
  }

  private <T extends Pair<V, V>, V> V getMindValue(T pair) {
    return pair.getValue();
  }
}
