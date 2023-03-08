package socialnetwork.repository.file;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.util.List;

public class UtilizatorFile extends AbstractFileRepository<String, Utilizator> {

    /**
     *
     * @param fileName
     * @param validator
     */
    public UtilizatorFile(String fileName, Validator<String, Utilizator> validator) {
        super(fileName, validator);
    }

    /**
     *
     * @param attributes
     * @return
     */
    @Override
    public Utilizator extractEntity(List<String> attributes) {

        Utilizator user = new Utilizator(attributes.get(0),attributes.get(1),attributes.get(2), attributes.get(3));

        return user;
    }

    /**
     *
     * @param entity
     * @return
     */
    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId()+","+entity.getFirstName()+","+entity.getLastName();
    }
}
