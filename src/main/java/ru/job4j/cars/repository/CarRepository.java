package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CarRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 02.10.2022.
 */
@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    /**
     * Добавить car.
     *
     * @param car Car
     */
    public Car createCar(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    /**
     * Обновить car.
     *
     * @param car Car
     */
    public void updateCar(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить car по id.
     *
     * @param carId ID
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Список car отсортированных по id.
     *
     * @return Car list.
     */
    public List<Car> findAllOrderById() {
        return crudRepository.query("from Car", Car.class);
    }

    /**
     * Найти car по ID
     *
     * @return Optional or car.
     */
    public Optional<Car> findById(int carId) {
        return crudRepository.optional(
                "from Car where id = :fId", Car.class,
                Map.of("fId", carId)
        );
    }

    /**
     * Список car по name LIKE %key%
     *
     * @param key key
     * @return Car list.
     */
    public List<Car> findByLikeName(String key) {
        return crudRepository.query(
                "from Car where name like :fKey", Car.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти cer по name.
     *
     * @param name login.
     * @return Optional or car.
     */
    public Optional<Car> findByName(String name) {
        return crudRepository.optional(
                "from Car where name = :fName", Car.class,
                Map.of("fName", name)
        );
    }
}
