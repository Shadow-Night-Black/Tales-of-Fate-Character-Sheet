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
  private List<Skill> skills;
  private List<Figment> figments;
  private Totem totem;
  private String name, bio;
  private int fateDamage, currentFate;
  private List<Integer> body, mind;
  private int baseInit, currentInit;

  public ToFCharacter(String name, String bio) {
    this.name = name;
    this.bio = bio;
    attributes = new TreeMap<>();
    for (Attribute attribute: Attribute.values()) {
      attributes.put(attribute, 6);
    }

    forms = new ArrayList<>();
    skills = new ArrayList<>();
    figments = new ArrayList<>();
    this.currentForm = new Form();
    forms.add(currentForm);
    this.totem = new Totem();

    this.body = new ArrayList<>(7);
    this.mind = new ArrayList<>(7);
    int bodySegment = getBaseBody() / getCurrentForm().getFormClass();
    int mindSegment = getBaseMind() / getCurrentForm().getFormClass();
    for (int i = 0; i < 7; i++) {
      body.add(bodySegment);
      mind.add(mindSegment);
    }

    this.fateDamage = 0;
    this.currentFate = this.getBaseFate();
    this.currentInit = 0;
  }

  public ToFCharacter() {
    this("New Character", "Write your characters backstory here!");
  }

  public int getBaseAttribute(Attribute attribute) {
    return attributes.get(attribute);
  }

  public void setAttribute(Attribute attribute, int value) {
    System.out.println("Changing " + attribute + " from " + this.attributes.get(attribute) + " to " + value);
    int difference = value - this.attributes.get(attribute);
    List<Integer> segments;
    if (attribute.isPhysical()) {
      segments = body;
    }else {
      segments = mind;
    }
    for(int i = 0; i <= 6; i++) {
      segments.set(i, Math.max(segments.get(i) + difference, 0));
    }
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
        ToFCharacter character = (ToFCharacter) u.unmarshal(f);
        character.fixCurrentForm();
        return character;
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Couldn't load character!");
  }

  private void fixCurrentForm() {
    for (Form form: forms) {
      if (form.equals(currentForm)) {
        this.currentForm = form;
        return;
      }
    }
   // throw new RuntimeException("CurrentForm Not FOUND!!!!");
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

  @Deprecated
  public void setForms(List<Form> forms) {
    throw new RuntimeException("Don't use SetForms()");
  }


  public List<Skill> getSkills() {
    return skills;
  }

  public void setSkills(List<Skill> skills) {
    this.skills = skills;
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

  public void takePhysicalDamage(int damage) {
    int currentSegment = currentForm.getFormClass();
    do {
      if (body.get(currentSegment) < damage) {
        damage -= body.get(currentSegment);
        body.set(currentSegment, 0);
        currentSegment--;
      }else {
        body.set(currentSegment, body.get(currentSegment) - damage);
        damage = 0;
      }

    }while (damage > 0 && currentSegment >= 0);
  }

  public void takeMentalDamage(int damage) {
    int currentSegment = currentForm.getFormClass();
    do {
      if (mind.get(currentSegment) < damage) {
        damage -= mind.get(currentSegment);
        mind.set(currentSegment, 0);
        currentSegment--;
      }else {
        mind.set(currentSegment, mind.get(currentSegment) - damage);
        damage = 0;
      }
    }while (damage > 0 && currentSegment >= 0);
  }

  public void healPhysicalDamage(int healing) {
    int currentSegment = 0;
    int segmentMax = getBaseBody() / currentForm.getFormClass();
    do {
      if (body.get(currentSegment) < segmentMax) {
        int amountToHeal  = segmentMax - body.get(currentSegment);
        if (amountToHeal < healing) {
          healing = healing - amountToHeal;
          body.set(currentSegment, segmentMax);
          currentSegment++;
        }else {
          body.set(currentSegment, healing + body.get(currentSegment));
          healing = 0;
        }
      } else{
        currentSegment++;
      }
    }while(healing > 0 && currentSegment <= 6);
  }

  public void healMentalDamage(int healing) {
    int currentSegment = 0;
    int segmentMax = getBaseMind() / currentForm.getFormClass();
    do {
      if (mind.get(currentSegment) < segmentMax) {
        int amountToHeal  = segmentMax - mind.get(currentSegment);
        if (amountToHeal < healing) {
          healing = healing - amountToHeal;
          mind.set(currentSegment, segmentMax);
          currentSegment++;
        }else {
          mind.set(currentSegment, healing + mind.get(currentSegment));
          healing = 0;
        }
      } else{
        currentSegment++;
      }
    }while(healing > 0 && currentSegment <= 6);
  }

  public int getBaseDC() {
    int dc = 0;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isPhysical()){
        dc += Attribute.getModifier(this.getBaseAttribute(attribute));
      }
    }
    return dc;
  }

  public int getBaseMC() {
    int mc = 0;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isMental()){
        mc += Attribute.getModifier(this.getBaseAttribute(attribute));
      }
    }
    return mc;
  }

  public int getCurrentDC() {
    int dc = 0;
    for (Attribute attribute: Attribute.values()) {
      if (attribute.isPhysical()){
        dc += Attribute.getModifier(this.getModifiedAttribute(attribute));
      }
    }
    return dc;
  }

  public int getCurrentMC() {
    int mc = 0;
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
    this.fateDamage = Math.max(fateDamage, 0);
  }

  public int getCurrentFate() {
    return currentFate;
  }

  public void setCurrentFate(int currentFate) {
    if (currentFate < this.getBaseFate())
      this.currentFate = currentFate;
    else
      this.currentFate = this.getBaseFate();
  }

  public Form getCurrentForm() {
    return currentForm;
  }

  public void setCurrentForm(Form currentForm) {
    this.currentForm = currentForm;
  }

  public void addForm(Form form) {
    this.forms.add(form);
  }

  public void removeForm(Form form) {
    this.forms.remove(form);
  }

  public List<Integer> getBody() {
    return body;
  }

  public void setBody(List<Integer> body) {
    this.body = body;
  }

  public List<Integer> getMind() {
    return mind;
  }

  public void setMind(List<Integer> mind) {
    this.mind = mind;
  }

  public int getBaseInit() {
    return baseInit;
  }

  public void setBaseInit(int baseInit) {
    this.baseInit = baseInit;
    this.currentInit = baseInit;
  }

  public int getCurrentInit() {
    return currentInit;
  }

  public void setCurrentInit(int currentInit) {
    if (currentInit < baseInit) {
      this.currentInit = currentInit;
    }else {
      this.currentInit = baseInit;
    }
  }

  public void addAbility(Skill skill) {
    skills.add(skill);
  }

  public void removeAbility(Skill skill) {
    skills.remove(skill);
  }

  public void addFigment(Figment figment) {
    figments.add(figment);
  }

  public void removeFigment(Figment figment) {
    figments.remove(figment);
  }
}
