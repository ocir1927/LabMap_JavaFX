package lab.repository;

import lab.domain.Post;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 26.10.2016
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public class PostFileRepository extends Repository<Post,Integer> {
    String fileName;

    public PostFileRepository(String fileName) {
        super();
        this.fileName = fileName;
        try {
            loadFromFile(fileName);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void loadFromFile(String fileName) throws Exception{

        BufferedReader br=null;

        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = br.readLine())!= null)
            {
                String[] fields = line.split("\\|");
                if(fields.length != 3){
                    throw new Exception("Fisier invalid!");
                }

                Post p = new Post(Integer.parseInt(fields[0]), fields[1],fields[2]);
                lista.add(p);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nu gasesc fisierul");
        } catch (IOException e) {
            System.out.println("Nu pot citi");
        }
        finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Cannot close fileReader");
                }

            }
        }
    }

    public void saveToFile(String fileName){
        BufferedWriter bw=null;

        try {
            bw = new BufferedWriter(new FileWriter(fileName));
            //bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fName)));

            for(Post p : lista){
                bw.write(p.getId()+"|"+p.getDenumire()+"|"+p.getTip() );
                if(lista.indexOf(p)<lista.size()-1)
                    bw.write("\n");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nu gasesc fisierul");
        } catch (IOException e) {
            System.out.println("Nu pot citi");
        }
        finally{
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println("I cannot close the bufferedWriter");
                }
            }
        }
    }

    @Override
    public void add(Post p) {
        super.add(p);
        saveToFile(fileName);
    }

    @Override
    public void update(Post p) {
        super.update(p);
        saveToFile(fileName);
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        saveToFile(fileName);
    }
}
