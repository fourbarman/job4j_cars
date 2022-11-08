package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * BrandRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 08.11.2022.
 */
@AllArgsConstructor
public class BrandRepository {
    private final CrudRepository crudRepository;

    /**
     * Добавить brand.
     *
     * @param brand Brand.
     */
    public Brand createBrand(Brand brand) {
        crudRepository.run(session -> session.persist(brand));
        return brand;
    }

    /**
     * Обновить brand.
     *
     * @param brand Brand
     */
    public void updateBrand(Brand brand) {
        crudRepository.run(session -> session.merge(brand));
    }

    /**
     * Удалить brand по id.
     *
     * @param brandId ID
     */
    public void delete(int brandId) {
        crudRepository.run(
                "delete from Brand where id = :fId",
                Map.of("fId", brandId)
        );
    }

    /**
     * Список brand отсортированных по id.
     *
     * @return Brand list.
     */
    public List<Brand> findAllOrderById() {
        return crudRepository.query("from Brand", Brand.class);
    }

    /**
     * Найти brand по ID
     *
     * @return Optional or brand.
     */
    public Optional<Brand> findById(int brandId) {
        return crudRepository.optional(
                "from Brand where id = :fId", Brand.class,
                Map.of("fId", brandId)
        );
    }

    /**
     * Список brand по name LIKE %key%
     *
     * @param key key
     * @return Brand list.
     */
    public List<Brand> findByLikeName(String key) {
        return crudRepository.query(
                "from Brand where name like :fKey", Brand.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти brand по name.
     *
     * @param name name.
     * @return Optional or brand.
     */
    public Optional<Brand> findByName(String name) {
        return crudRepository.optional(
                "from Brand where login = :fName", Brand.class,
                Map.of("fName", name)
        );
    }
}
