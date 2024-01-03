package backend.adapters.driven.transformer;

public interface Transformer<D, E> {
     E domainToEntity(D domain);
     D entityToDomain(E entity);
}
