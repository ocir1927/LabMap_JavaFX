package lab.repository;

import lab.domain.Sarcina;
import lab.domain.ValidatorSarcina;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by camelia on 12/7/2016.
 */
public class SarcinaXMLStAX2Repository extends Repository<Sarcina, Integer> {

    String fileName;
    public SarcinaXMLStAX2Repository(String fileName){
        super();
        this.fileName=fileName;
        loadData();
    }

//    public SarcinaXMLStAX2Repository(ValidatorSarcina vali, String fName){
//        super(vali,fName);
//    }

    public void loadData() {
        try(FileInputStream input = new FileInputStream(fileName)){
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
            readFromXMLFile(reader);
        }
        catch (IOException ex){

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    /*
        read the students from XML File
     */
    private void readFromXMLFile(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elemName = reader.getLocalName();
                    if (elemName.equals("sarcina")) {
//                        try {
                        super.add(readSarcina(reader));
                     /*   } catch (ValidatorException e) {
                            e.printStackTrace();
                        }*/
//                    }
                    }
            }
        }
    }

    private Sarcina readSarcina(XMLStreamReader reader) throws XMLStreamException {
        Sarcina s=new Sarcina(Integer.parseInt(reader.getAttributeValue(null,"id")),"");
        StringBuilder currentProperty=new StringBuilder();
        StringBuilder currentPropertyValue=new StringBuilder();
        while (reader.hasNext())
        {
            int eventType=reader.next();
            switch (eventType){
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals("property"))
                    currentProperty.append(reader.getAttributeValue(null,"name"));
                break;

                case XMLStreamReader.CHARACTERS:
                    String text=reader.getText().trim();
                    if (! text.equals(""))
                        currentPropertyValue.append(text);
                    break;

                case XMLStreamReader.END_ELEMENT:
                    String endName=reader.getLocalName();
                    if (endName.equals("property")) {
                        if (currentProperty.toString().equals("descriere"))
                            s.setDescriere(currentPropertyValue.toString());
                        /*if (currentProperty.toString().equals("lastName"))
                            s.setLastName(currentPropertyValue.toString());
                        if (currentProperty.toString().equals("email"))
                            s.setEmail(currentPropertyValue.toString());*/
                        currentPropertyValue.setLength(0);
                        currentProperty.setLength(0);
                    }
                    else if (endName.equals("sarcina"))
                    {
                        return s;
                    }
                    break;
            }
        }
        throw new XMLStreamException("NU s-a citit sarcina!");
    }

    public void writeToFile() {
        try(FileOutputStream output = new FileOutputStream(fileName)){
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(output);
            writeToXMLFile(writer);
        }
        catch (IOException ex){

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void writeToXMLFile(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("sarcini");
        super.getAll().forEach(x -> {
            try {
                writeSarcina(x, writer);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
        writer.writeEndElement();
    }

    private void writeSarcina(Sarcina x, XMLStreamWriter writer) throws XMLStreamException{
        writer.writeStartElement("sarcina");
        writer.writeAttribute("id", x.getId().toString());

        writer.writeStartElement("property");
        writer.writeAttribute("name","descriere");
        writer.writeCharacters(x.getDescriere());
        writer.writeEndElement();
/*
        writer.writeStartElement("property");
        writer.writeAttribute("name","lastName");
        writer.writeCharacters(x.getLastName());
        writer.writeEndElement();

        writer.writeStartElement("property");
        writer.writeAttribute("name","email");
        writer.writeCharacters(x.getEmail());
        writer.writeEndElement();*/

        writer.writeEndElement();
    }

    @Override
    public void add(Sarcina p) {
        super.add(p);
        writeToFile();
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        writeToFile();
    }

    @Override
    public void update(Sarcina p) {
        super.update(p);
        writeToFile();
    }
}
