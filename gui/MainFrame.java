package gui;

import data.ToFCharacter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class MainFrame extends Application {
  private ToFCharacter character;
  private Stage stage;
  private AttributePanel attributePanel;
  private BlessingsPanel blessingsPanel;
  private NotesPanel notesPanel;
  private StatsPanel statsPanel;
  private static FileChooser fileChooser;
  private static File lastUsed;
//  private FormsPanel formsPanel;
  private SkillsPanel skillsPanel;
  private FigmentsPanel figmentPanel;

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
    URL fxml = this.getClass().getResource("FXML/CharacterSheet.fxml");
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Parent root = fxmlLoader.load();
      CharacterSheet characterSheet =  fxmlLoader.getController();
      characterSheet.update(new ToFCharacter());

      Scene scene = new Scene(root);
      primaryStage.setScene(scene);

      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
    /*
    this.stage = primaryStage;
    primaryStage.setTitle(character.getName() + " - ToFCharacter Sheet");

    VBox root = new VBox();
    this.stage.setScene(new Scene(root));

    setupMenuBar(root);

    TabPane mainView = new TabPane();
    mainView.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    attributePanel = new AttributePanel(this, character);
    Tab attributes = new Tab("Attributes", attributePanel.getPanel());

//    formsPanel = new FormsPanel(this, character);
//    Tab forms = new Tab("Forms", formsPanel.getPanel());

    skillsPanel = new SkillsPanel(this, character);
    Tab skills = new Tab("Skills", skillsPanel.getPanel());

    figmentPanel = new FigmentsPanel(this, character);
    Tab figments = new Tab("Figments", figmentPanel.getPanel());

    blessingsPanel = new BlessingsPanel(this, character);
    Tab blessings = new Tab("Blessings", blessingsPanel.getPanel());

    notesPanel = new NotesPanel(this, character);
    Tab notes = new Tab("Notes", notesPanel.getPanel());

    mainView.getTabs().addAll(attributes, skills, figments, blessings, notes);

    statsPanel = new StatsPanel(this, character);
    root.getChildren().addAll(mainView, statsPanel.getPanel());
    this.stage.show();
    */
  }


  public void update(ToFCharacter character) {
    this.character = character;

    stage.setTitle(character.getName() + " - ToFCharacter Sheet");
    attributePanel.update(character);
//    formsPanel.update(character);
    skillsPanel.update(character);
    figmentPanel.update(character);
    blessingsPanel.update(character);
    notesPanel.update(character);
    statsPanel.update(character);
  }

  private void setupMenuBar(VBox root) {
    MenuBar menuBar = new MenuBar();
    Menu menuFile = new Menu("File");


    MenuItem newCharacter = new MenuItem("New");
    newCharacter.setOnAction((event -> this.newWindow(new ToFCharacter())));


    MenuItem saveAsCharacter = new MenuItem("Save As");
    saveAsCharacter.setOnAction((event) -> {
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

    MenuItem saveCharacter = new MenuItem("Save ");
    saveCharacter.setOnAction(event -> {
      if (lastUsed != null) {
        this.character.save(lastUsed);
      }else {
        saveAsCharacter.fire();
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

    menuFile.getItems().addAll(newCharacter, loadCharacter, saveCharacter, saveAsCharacter, exit);

    menuBar.getMenus().addAll(menuFile);

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

  public Stage getStage() {
    return stage;
  }
}
