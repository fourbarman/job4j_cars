package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class DriverRepository {
    private final CrudRepository crudRepository;

    /**
     * Добавить driver.
     *
     * @param driver Driver
     */
    public Driver createDriver(Driver driver) {
        crudRepository.run(session -> session.persist(driver));
        return driver;
    }

    /**
     * Обновить driver.
     *
     * @param driver Driver
     */
    public void updateDriver(Driver driver) {
        crudRepository.run(session -> session.merge(driver));
    }

    /**
     * Удалить driver по id.
     *
     * @param driverId ID.
     */
    public void delete(int driverId) {
        crudRepository.run(
                "delete from Driver where id = :fId",
                Map.of("fId", driverId)
        );
    }

    /**
     * Список driver отсортированных по id.
     *
     * @return Driver list.
     */
    public List<Driver> findAllOrderById() {
        return crudRepository.query("from Driver", Driver.class);
    }

    /**
     * Найти driver по ID
     *
     * @return Optional or driver.
     */
    public Optional<Driver> findById(int driverId) {
        return crudRepository.optional(
                "from Driver where id = :fId", Driver.class,
                Map.of("fId", driverId)
        );
    }

    /**
     * Список driver по name LIKE %key%
     *
     * @param key key
     * @return Driver list.
     */
    public List<Driver> findByLikeName(String key) {
        return crudRepository.query(
                "from Driver where name like :fKey", Driver.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти driver по name.
     *
     * @param name login.
     * @return Optional or driver.
     */
    public Optional<Driver> findByName(String name) {
        return crudRepository.optional(
                "from Driver where name = :fName", Driver.class,
                Map.of("fName", name)
        );
    }
}
