package socialnetwork.domain.validators;

import socialnetwork.domain.Entity;

/**
 *
 * @param <Id>
 * @param <E>
 */
public interface Validator<Id, E extends Entity<Id>> {
    void validate(E Entity) throws ValidationException;
    void validateId(Id Id) throws ValidationException;
}