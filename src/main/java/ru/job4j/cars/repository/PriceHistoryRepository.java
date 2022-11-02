package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PriceHistoryRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 02.10.2022.
 */
@AllArgsConstructor
public class PriceHistoryRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param priceHistory PriceHistory.
     * @return priceHistory с id.
     */
    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.persist(priceHistory));
        return priceHistory;
    }

    /**
     * Обновить в базе PriceHistory.
     *
     * @param priceHistory PriceHistory.
     */
    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
    }

    /**
     * Удалить PriceHistory по id.
     *
     * @param priceHistoryId ID
     */
    public void delete(int priceHistoryId) {
        crudRepository.run(
                "delete from PriceHistory where id = :fId",
                Map.of("fId", priceHistoryId)
        );
    }

    /**
     * Список PriceHistory отсортированных по id.
     *
     * @return PriceHistory list.
     */
    public List<PriceHistory> findAllOrderById() {
        return crudRepository.query("from PriceHistory", PriceHistory.class);
    }

    /**
     * Найти PriceHistory по ID
     *
     * @return Optional or PriceHistory.
     */
    public Optional<PriceHistory> findById(int priceHistoryId) {
        return crudRepository.optional(
                "from PriceHistory where id = :fId", PriceHistory.class,
                Map.of("fId", priceHistoryId)
        );
    }
}
