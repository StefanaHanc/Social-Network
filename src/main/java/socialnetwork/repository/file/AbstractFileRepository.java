package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractFileRepository<Id, E extends Entity<Id>> extends InMemoryRepository<Id,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<Id, E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();

    }

    private void emptyFile(){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,false))) {
            bW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                List<String> attr=Arrays.asList(linie.split(","));
                E e=extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity){
        E e=super.save(entity);
        if (e==null)
        {
            writeToFile(entity);
        }
        return e;

    }

    @Override
    public E delete(Id id) {
        E e=super.delete(id);
        if (e!=null)
        {
            emptyFile();
            findAll().forEach(entity->{
                if(!entity.getId().equals(e.getId())){
                    writeToFile(entity);
                }
            });
        }
        return e;
    }

    @Override
    public E update(E entity) {
        E e=super.update(entity);
        if (e!=null)
        {
            emptyFile();
            findAll().forEach(this::writeToFile);
        }
        return e;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

