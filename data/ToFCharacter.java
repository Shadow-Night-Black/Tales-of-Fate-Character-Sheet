package data;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@XmlRootElement(name = "Character")
public class ToFCharacter {
  private Map<Attribute, Integer> attributes;
  private List<Form> forms;
  private Form currentForm;
  private List<Feat> abilities;
  private List<Figment> figments;
  private Totem totem;
  private String name, bio;
  private int currentBody, currentMind, fateDamage, currentFate;

  public ToFCharacter(String name, String bio) {
    this.name = name;
    this.bio = bio;
    attributes = new TreeMap<>();
    for (Attribute attribute: Attribute.values()) {
      attributes.put(attribute, 6);
    }

    forms = new ArrayList<>();
    abilities = new ArrayList<>();
    figments = new ArrayList<>();
    this.currentForm = new Form();
    this.totem = new Totem();

    this.currentBody = getBaseBody();
    this.currentMind = getBaseMind();
    this.fateDamage = 0;
    this.currentFate = this.getBaseFate();
  }

  public ToFCharacter() {
    this("New Character", "Write your characters backstory here!");
  }

  public int getBaseAttribute(Attribute attribute) {
    return attributes.get(attribute);
  }

  public void setAttribute(Attribute attribute, int value) {
    this.attributes.put(attribute, value);
  }

  public Totem getTotem() {
    return totem;
  }

  public String getName() {
    return name;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public int getModifiedAttribute(Attribute attribute) {
    return getBaseAttribute(attribute) + currentForm.getAttributeBonus(attribute) + totem.getAttributeBonus(attribute);
  }

  public void save() {
    this.save(new File(this.name + ".xml"));
  }

  public void save(File f) {
    try {
      JAXBContext jc  = JAXBContext.newInstance(ToFCharacter.class);
      Marshaller m = jc.createMarshaller();
      m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
      if (f.exists() && (f.isDirectory() || !f.canWrite())) {
          throw new RuntimeException("File name Invalid!");
      }
      m.marshal(this, f);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  public static ToFCharacter load(File f){
    try {
      JAXBContext jc  = JAXBContext.newInstance(ToFCharacter.class);
      Unmarshaller u = jc.createUnmarshaller();
      if (f.exists() && f.isFile() && f.canRead()) {
        return(ToFCharacter) u.unmarshal(f);
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Couldn't load character!");
  }


  public Map<Attribute, Integer> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<Attribute, Integer> attributes) {
    this.attributes = attributes;
  }

  public List<Form> getForms() {
    return forms;
  }

  public void setForms(List<Form> forms) {
    this.forms = forms;
  }

  public List<Feat> getAbilities() {
    return abilities;
  }

  public void setAbilities(List<Feat> abilities) {
    this.abilities = abilities;
  }

  public List<Figment> getFigments() {
    return figments;
  }

  public void setFigments(List<Figment> figments) {
    this.figments = figments;
  }

  public void setTotem(Totem totem) {
    this.totem = totem;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBaseBody() {
    int totalBody = 0;
    for (Attribute attribute: Attribute.values()){
      if (attribute.isPhysical()) {
        totalBody += currentForm.getFormClass() * attributes.get(attribute);
      }
    }
    return totalBody;
  }

  public int getBaseMind() {
    int totalMind = 0;
    for (Attribute attribute: Attribute.values()){
      if (attribute.isMental()) {
        totalMind += currentForm.getFormClass() * attributes.get(attribute);
      }
    }
    return totalMind;
  }

  public int getCurrentBody() {
    return currentBody;
  }

  public void setCurrentBody(int currentBody) {
    if (currentBody < this.getBaseBody())
      this.currentBody = currentBody;
    else
      this.currentBody = this.getBaseBody();
  }

  public int getCurrentMind() {
    return currentMind;
  }

  public void setCurrentMind(int currentMind) {
    if (currentMind < this.getBaseMind())
      this.currentMind = currentMind;
    else
      this.currentMind = this.getBaseMind();
  }

  public int getBaseDC() {
    int dc = 1;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isPhysical()){
       dc += Attribute.getModifier(this.getBaseAttribute(attribute));
      }
    }
    return dc;
  }

  public int getBaseMC() {
    int mc = 1;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isMental()){
        mc += Attribute.getModifier(this.getBaseAttribute(attribute));
      }
    }
    return mc;
  }

  public int getCurrentDC() {
    int dc = 1;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isPhysical()){
        dc += Attribute.getModifier(this.getModifiedAttribute(attribute));
      }
    }
    return dc;
  }

  public int getCurrentMC() {
    int mc = 1;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isMental()){
        mc += Attribute.getModifier(this.getModifiedAttribute(attribute));
      }
    }
    return mc;
  }

  public int getBaseFate() {
    return totem.getBaseFate() - this.fateDamage;
  }

  public int getFateDamage() {
    return fateDamage;
  }

  public void setFateDamage(int fateDamage) {
    this.fateDamage = fateDamage;
  }

  public int getCurrentFate() {
    return currentFate;
  }

  public void setCurrentFate(int currentFate) {
    this.currentFate = currentFate;
  }

  public Form getCurrentForm() {
    return currentForm;
  }

  public void setCurrentForm(Form currentForm) {
    this.currentForm = currentForm;
  }
}
