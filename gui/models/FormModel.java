package gui.models;

import data.Form;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FormModel {
  private Form form;
  private SimpleIntegerProperty formClass, mv;
  private SimpleStringProperty name, desc ;

  public FormModel(Form form) {
    setForm(form);
  }

  private void init(Form form, SimpleIntegerProperty formClass, SimpleStringProperty name, SimpleStringProperty desc, SimpleIntegerProperty mv) {
    this.form = form;
    this.formClass = formClass;
    this.name = name;
    this.desc = desc;
    this.mv = mv;
  }

  public Form getForm() {
    return form;
  }

  public void setForm(Form form) {
    init(form, new SimpleIntegerProperty(form.getFormClass()),
      new SimpleStringProperty(form.getName()),
      new SimpleStringProperty(form.getDesc()),
      new SimpleIntegerProperty(form.getMv()));
  }

  public int getFormClass() {
    return formClass.get();
  }

  public SimpleIntegerProperty formClassProperty() {
    return formClass;
  }

  public void setFormClass(int formClass) {
    this.formClass.set(formClass);
    this.form.setFormClass(formClass);
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
    this.form.setName(name);
  }

  public String getDesc() {
    return desc.get();
  }

  public SimpleStringProperty descProperty() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc.set(desc);
    this.form.setDesc(desc);
  }

  public int getMv() {
    return mv.get();
  }

  public SimpleIntegerProperty mvProperty() {
    return mv;
  }

  public void setMv(int mv) {
    this.mv.set(mv);
    this.form.setMv(mv);
  }
}
