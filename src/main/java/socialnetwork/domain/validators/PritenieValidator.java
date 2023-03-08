package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;

public class PritenieValidator implements Validator<Tuple<String>, Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String mail1 = entity.getId().getLeft();
        String mail2 = entity.getId().getRight();
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        String errors = "";

        if(!mail1.matches(regex)){
            errors += "Mail1 invalid\n";
        }

        if(!mail2.matches(regex)){
            errors += "Mail2 invalid\n";
        }


        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateId(Tuple<String> mails) throws ValidationException {

        String mail1 = mails.getLeft();
        String mail2 = mails.getRight();
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        String errors = "";

        if(!mail1.matches(regex)){
            errors += "Mail1 invalid\n";
        }

        if(!mail2.matches(regex)){
            errors += "Mail2 invalid\n";
        }


        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
