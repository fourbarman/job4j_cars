package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.PriceHistory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;
/**
 * PriceHistoryRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 25.11.2022.
 */
class PriceHistoryRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository(crudRepository);

    @Test
    void create() {
        ZonedDateTime created = ZonedDateTime.now();
        PriceHistory priceHistory = priceHistoryRepository.create(new PriceHistory(0, 200, 300, created));
        assertThat(priceHistoryRepository.findById(priceHistory.getId())).isPresent();
        assertThat(priceHistoryRepository.findById(priceHistory.getId()).get()
                .getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .isEqualTo(created.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    @Test
    void update() {
        ZonedDateTime created = ZonedDateTime.now();
        PriceHistory priceHistory = priceHistoryRepository.create(new PriceHistory(0, 200, 300, created));
        priceHistory.setAfter(500);
        priceHistoryRepository.update(priceHistory);
        assertThat(priceHistoryRepository.findById(priceHistory.getId())).isPresent();
        assertThat(priceHistoryRepository.findById(priceHistory.getId()).get().getAfter()).isEqualTo(500);
    }

    @Test
    void delete() {
        ZonedDateTime created = ZonedDateTime.now();
        PriceHistory priceHistory = priceHistoryRepository.create(new PriceHistory(0, 200, 300, created));
        priceHistoryRepository.delete(priceHistory.getId());
        assertThat(priceHistoryRepository.findAllOrderById()).doesNotContain(priceHistory);
    }

    @Test
    void findAllOrderById() {
        PriceHistory priceHistory1 = priceHistoryRepository.create(new PriceHistory(0, 200, 300, ZonedDateTime.now()));
        PriceHistory priceHistory2 = priceHistoryRepository.create(new PriceHistory(0, 100, 200, ZonedDateTime.now()));
        assertThat(priceHistoryRepository.findAllOrderById()).isNotEmpty();
        assertThat(priceHistoryRepository.findAllOrderById()).contains(priceHistory1, priceHistory2);
    }

    @Test
    void findById() {
        ZonedDateTime created = ZonedDateTime.now();
        PriceHistory priceHistory = priceHistoryRepository.create(new PriceHistory(0, 200, 300, created));
        assertThat(priceHistoryRepository.findById(priceHistory.getId())).isPresent();
        assertThat(priceHistoryRepository.findById(priceHistory.getId()).get().getCreated()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .isEqualTo(created.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }
}