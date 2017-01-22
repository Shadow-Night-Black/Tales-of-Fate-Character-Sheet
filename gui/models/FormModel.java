package gui.models;

import data.Form;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FormModel {
  private Form form;
  private SimpleIntegerProperty formClass;
  private SimpleStringProperty name, desc;

  public FormModel(Form form) {
    setForm(form);
  }

  public FormModel(SimpleIntegerProperty formClass, SimpleStringProperty name, SimpleStringProperty desc) {
    Form form = new Form(name.getValue(), formClass.getValue(), desc.getValue());
    init(form, formClass, name, desc);
  }

  private void init(Form form, SimpleIntegerProperty formClass, SimpleStringProperty name, SimpleStringProperty desc) {
    this.form = form;
    this.formClass = formClass;
    this.name = name;
    this.desc = desc;
  }

  public Form getForm() {
    return form;
  }

  public void setForm(Form form) {
    init(form, new SimpleIntegerProperty(form.getFormClass()),
      new SimpleStringProperty(form.getName()),
      new SimpleStringProperty(form.getDesc()));
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
}
