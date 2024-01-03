package backend.adapters.driving.transformer;

public interface Transformer<I, O> {
    O fromDomain(I i);
    I toDomain(O o);
}
