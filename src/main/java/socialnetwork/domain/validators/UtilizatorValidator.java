package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;


public class UtilizatorValidator implements Validator<String,Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String mail = entity.getId();
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        String regexName = "^[A-Za-z0-9 -]*$";

        String errors = "";

        if(!mail.matches(regex)){
            errors += "Mail invalid\n";
        }

        if(!firstName.matches(regexName)){
            errors += "FirstName invalid\n";
        }
        if(!lastName.matches(regexName)){
            errors += "LastName invalid\n";
        }

        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateId(String mail) throws ValidationException {

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        String errors = "";

        if(!mail.matches(regex)){
            errors += "Mail invalid\n";
        }

        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
