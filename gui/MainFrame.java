package gui;

import data.ToFCharacter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class MainFrame extends Application {
  private ToFCharacter character;
  private Stage stage;
  private AttributePanel attributePanel;
  private BlessingsPanel blessingsPanel;
  private NotesPanel notesPanel;
  private StatsPanel statsPanel;
  private static FileChooser fileChooser;
  private static File lastUsed;
  private FormsPanel formsPanel;

  public MainFrame() {
    this(new ToFCharacter());
  }

  private MainFrame(ToFCharacter character) {
    this.character = character;
  }

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {
    this.stage = primaryStage;
    primaryStage.setTitle(character.getName() + " - ToFCharacter Sheet");

    VBox root = new VBox();
    this.stage.setScene(new Scene(root));

    setupMenuBar(root);

    TabPane mainView = new TabPane();
    mainView.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    attributePanel = new AttributePanel(this, character);
    Tab attributes = new Tab("Attributes", attributePanel.getPanel());

    formsPanel = new FormsPanel(this, character);
    Tab forms = new Tab("Forms", formsPanel.getPanel());

    Tab feats = new Tab("Feats/Abilites", createFormsPane());

    Tab figments = new Tab("Figments", createFigmentPane());

    blessingsPanel = new BlessingsPanel(this, character);
    Tab blessings = new Tab("Blessings", blessingsPanel.getPanel());

    notesPanel = new NotesPanel(this, character);
    Tab notes = new Tab("Notes", notesPanel.getPanel());

    mainView.getTabs().addAll(attributes, forms, feats, figments, blessings, notes);

    statsPanel = new StatsPanel(this, character);
    root.getChildren().addAll(mainView, statsPanel.getPanel());
    this.stage.show();
  }

  private Pane createFigmentPane() {
    return null;
  }

  private Pane createFormsPane() {
    return null;
  }

  public void update(ToFCharacter character) {
    this.character = character;

    stage.setTitle(character.getName() + " - ToFCharacter Sheet");
    attributePanel.update(character);

    formsPanel.update(character);
    blessingsPanel.update(character);
    notesPanel.update(character);

    statsPanel.update(character);
  }

  private void setupMenuBar(VBox root) {
    MenuBar menuBar = new MenuBar();
    Menu menuFile = new Menu("File");
    Menu menuEdit = new Menu("Edit");


    MenuItem newCharacter = new MenuItem("New");
    newCharacter.setOnAction((event -> this.newWindow(new ToFCharacter())));


    MenuItem saveCharacter = new MenuItem("Save");
    saveCharacter.setOnAction((event) -> {
      FileChooser fileChooser = getFileChooser();
      File f = fileChooser.showSaveDialog(stage);
      if (f != null) {
        if (!f.getName().endsWith(".xml"))
          f = new File(f.getPath() + ".xml");
        lastUsed = f;
        this.character.save(f);
        System.out.println("Saved " + f.getPath());
      }
    });
    saveCharacter.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

    MenuItem loadCharacter = new MenuItem("Load");
    loadCharacter.setOnAction((event) -> {
      FileChooser fileChooser = getFileChooser();
      File f = fileChooser.showOpenDialog(stage);
      if (f != null) {
        lastUsed = f;
        this.newWindow(ToFCharacter.load(f));
        stage.close();
      }
    });
    loadCharacter.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));


    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction((event) -> System.exit(0));

    menuFile.getItems().addAll(newCharacter, loadCharacter, saveCharacter, exit);

    menuBar.getMenus().addAll(menuFile, menuEdit);

    root.getChildren().addAll(menuBar);
  }

  private void newWindow(ToFCharacter character) {
    new MainFrame(character).start(new Stage());
  }

  private static FileChooser getFileChooser() {
    if (fileChooser == null) {
      fileChooser = new FileChooser();
      fileChooser.setTitle("Character File Loader");
      fileChooser.setInitialDirectory(
              new File(System.getProperty("user.home"))
      );
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tales of Fate Characters", "*.xml"));
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
    }else if (lastUsed != null) {
      fileChooser.setInitialDirectory(lastUsed.getParentFile());
      fileChooser.setInitialFileName(lastUsed.getName());
    }

    return fileChooser;
  }

  protected static GridPane getGridPane() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    //grid.setGridLinesVisible(true);
    grid.setPadding(new Insets(25, 25, 25, 25));
    return grid;
  }
}
