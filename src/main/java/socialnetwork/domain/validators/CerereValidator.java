package socialnetwork.domain.validators;

import socialnetwork.domain.CererePrietenie;


public class CerereValidator {

    public void validate(CererePrietenie entity) throws ValidationException {
        String fromUser = entity.getFromUser();
        String toUser = entity.getToUser();
        String status = entity.getStatus();

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        String regexName = "^[A-Za-z0-9 -]*$";

        String errors = "";

        if (!fromUser.matches(regex)) {
            errors += "FromUser invalid\n";
        }
        if (!toUser.matches(regex)) {
            errors += "ToUser invalid\n";
        }

        if (!status.equals("approved") && !status.equals("rejected")) {
            errors += "Status invalid!!!\n";
        }

        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
}
