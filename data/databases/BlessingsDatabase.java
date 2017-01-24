package data.databases;

import data.Attribute;
import data.Blessing;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@XmlRootElement(name = "Blessings Data")
public class BlessingsDatabase {
  private TreeMap<Attribute, List<Blessing>> blessings;
  private File file;

  public BlessingsDatabase() {
    blessings = new TreeMap<>();
    for (Attribute attribute: Attribute.values()) {
      blessings.put(attribute, new ArrayList<>());
    }
  }

  public void addBlessing(Blessing blessing) {
    blessings.get(blessing.getAttribute()).add(blessing);
    this.save(file);
  }

  public List<Blessing> getBlessings(Attribute attribute) {
    return blessings.get(attribute);
  }

  public void save(File f) {
    try {
      JAXBContext jc  = JAXBContext.newInstance(BlessingsDatabase.class);
      Marshaller m = jc.createMarshaller();
      m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
      if (f.exists() && (f.isDirectory() || !f.canWrite())) {
          throw new RuntimeException("File name Invalid!");
      }
      m.marshal(this, f);
      this.file = f;
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  public static BlessingsDatabase load(File f){
    try {
      JAXBContext jc  = JAXBContext.newInstance(BlessingsDatabase.class);
      Unmarshaller u = jc.createUnmarshaller();
      if (f.exists() && f.isFile() && f.canRead()) {
        BlessingsDatabase database = (BlessingsDatabase) u.unmarshal(f);
        database.file = f;
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Couldn't load blessings database!");
  }


  public TreeMap<Attribute, List<Blessing>> getBlessings() {
    return blessings;
  }

  public void setBlessings(TreeMap<Attribute, List<Blessing>> blessings) {
    this.blessings = blessings;
  }
}
