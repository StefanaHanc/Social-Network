package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;
import socialnetwork.utils.Constants;

import java.time.LocalDate;
import java.util.List;

public class PrietenieFile extends AbstractFileRepository<Tuple<String>, Prietenie> {

    public PrietenieFile(String fileName, Validator<Tuple<String>, Prietenie> validator) {
        super(fileName, validator);
    }

    /**
     *
     * @param attributes
     * @return
     */
    @Override
    public Prietenie extractEntity(List<String> attributes) {
        return new Prietenie(new Tuple<>(attributes.get(0), attributes.get(1)), LocalDate.parse(attributes.get(2),Constants.DATE_TIME_FORMATTER));
    }

    /**
     *
     * @param entity
     * @return
     */
    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft() + ","
                + entity.getId().getRight() + ","
                + entity.getDate().format(Constants.DATE_TIME_FORMATTER);
    }
}
