package ch.cern.todo.model.mapper;

public interface ModelMapper<E, A> {
    E toEntity(A api);

    A toApi(E entity);
}
