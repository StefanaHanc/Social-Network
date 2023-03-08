package socialnetwork.repository.memory;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<Id, E extends Entity<Id>> implements Repository<Id,E> {

    private Validator<Id, E> validator;
    Map<Id,E> entities;

    public InMemoryRepository(Validator<Id,E> validator) {
        this.validator = validator;
        entities=new HashMap<Id,E>();
    }

    @Override
    public E findOne(Id mail){
        if (mail==null)
            throw new IllegalArgumentException("Mail must be not null");
        return entities.get(mail);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return entity;
        }
        else entities.put(entity.getId(),entity);
        return null;
    }

    @Override
    public E delete(Id id) {

        if(id == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validateId(id);

        if(entities.get(id) != null) {
            E old = entities.remove(id);
            return old;
        }
        return null;
    }

    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        if(entities.get(entity.getId()) != null) {
            E old = entities.put(entity.getId(),entity);
            return old;
        }
        return null;

    }

}
