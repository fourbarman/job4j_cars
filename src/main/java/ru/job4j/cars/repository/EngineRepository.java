package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * EngineRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 02.10.2022.
 */
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    /**
     * Добавить engine.
     *
     * @param engine Engine
     */
    public Engine createEngine(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    /**
     * Обновить engine.
     *
     * @param engine Engine
     */
    public void updateEngine(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    /**
     * Удалить engine по id.
     *
     * @param engineId ID
     */
    public void delete(int engineId) {
        crudRepository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", engineId)
        );
    }

    /**
     * Список engine отсортированных по id.
     *
     * @return Engine list.
     */
    public List<Engine> findAllOrderById() {
        return crudRepository.query("from Engine", Engine.class);
    }

    /**
     * Найти engine по ID
     *
     * @return Optional or engine.
     */
    public Optional<Engine> findById(int engineId) {
        return crudRepository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", engineId)
        );
    }

    /**
     * Список engine по name LIKE %key%
     *
     * @param key key
     * @return Engine list.
     */
    public List<Engine> findByLikeName(String key) {
        return crudRepository.query(
                "from Engine where name like :fKey", Engine.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти engine по name.
     *
     * @param name login.
     * @return Optional or engine.
     */
    public Optional<Engine> findByName(String name) {
        return crudRepository.optional(
                "from Engine where name = :fName", Engine.class,
                Map.of("fName", name)
        );
    }
}
