package gui;

import data.ToFCharacter;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sun.applet.Main;

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

  public StatsPanel(MainFrame mainFrame, ToFCharacter character) {
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
  }



  public Pane getPanel() {
    return grid;
  }
  
  public void update(ToFCharacter character) {
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
