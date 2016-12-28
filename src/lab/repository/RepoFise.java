package lab.repository;

import lab.domain.ElementFisa;
import lab.domain.Sarcina;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Costi on 05.12.2016.
 */
public class RepoFise extends Repository<ElementFisa,Integer> {
    String fileName;

    public RepoFise(String fileName) {
        super();
        this.fileName = fileName;
        try{
            unserializeFise(fileName);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void serializeFise(String fileName){
        ObjectOutputStream os=null;
        try {
            os=new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(lista);
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Fisier invalid");
            // e.printStackTrace();
        }
        finally {
            if (os!=null)
            {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("Bufferul nu poate fii inchis");
                    //e.printStackTrace();
                }
            }
        }
    }

    public  void unserializeFise(String fileName){
        ObjectInputStream os=null;
        try {
            os=new ObjectInputStream(new FileInputStream(fileName));
            try {
                lista=(ArrayList<ElementFisa>)os.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (os!=null)
            {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void add(ElementFisa p) {
        super.add(p);
        serializeFise(fileName);
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        serializeFise(fileName);
    }

    @Override
    public void update(ElementFisa p) {
        super.update(p);
        serializeFise(fileName);
    }
}
