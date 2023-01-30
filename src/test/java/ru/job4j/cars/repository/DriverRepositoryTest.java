package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;


import static org.assertj.core.api.Assertions.*;

/**
 * DriverRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 25.11.2022.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DriverRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);
    private final UserRepository userRepository = new UserRepository(crudRepository);

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;

    @BeforeAll
    public void setVars() {
        user1 = userRepository.create(new User(0, "user1", "pass1"));
        user2 = userRepository.create(new User(0, "user2", "pass2"));
        user3 = userRepository.create(new User(0, "user3", "pass3"));
        user4 = userRepository.create(new User(0, "user4", "pass4"));
        user5 = userRepository.create(new User(0, "user5", "pass5"));
        user6 = userRepository.create(new User(0, "user6", "pass6"));
        user7 = userRepository.create(new User(0, "user7", "pass7"));
        user8 = userRepository.create(new User(0, "user8", "pass8"));
    }

    @Test
    void whenCreateDriver() {
        Driver driver = new Driver(0, "driver 1", user1);
        Driver storedDriver = driverRepository.createDriver(driver);
        assertThat(storedDriver.getName()).isEqualTo(driver.getName());
    }

    @Test
    void whenUpdateDriver() {
        Driver storedDriver = driverRepository.createDriver(new Driver(0, "driver 2", user2));
        String name = "new name";
        storedDriver.setName(name);
        driverRepository.updateDriver(storedDriver);
        assertThat(driverRepository.findById(storedDriver.getId())).isPresent();
        assertThat(driverRepository.findById(storedDriver.getId()).get().getName()).isEqualTo(storedDriver.getName());
    }

    @Test
    void whenDelete() {
        Driver storedDriver = driverRepository.createDriver(new Driver(0, "driver 3", user3));
        driverRepository.delete(storedDriver.getId());
        assertThat(driverRepository.findAllOrderById()).doesNotContain(storedDriver);
    }

    @Test
    void whenFindAllOrderById() {
        Driver storedDriver1 = driverRepository.createDriver(new Driver(0, "driver 4", user4));
        Driver storedDriver2 = driverRepository.createDriver(new Driver(0, "driver 5", user5));
        assertThat(driverRepository.findAllOrderById()).contains(storedDriver1, storedDriver2);
    }

    @Test
    void whenFindById() {
        Driver storedDriver = driverRepository.createDriver(new Driver(0, "driver 6", user6));
        assertThat(driverRepository.findById(storedDriver.getId())).isPresent();
        assertThat(driverRepository.findById(storedDriver.getId()).get().getName()).isEqualTo(storedDriver.getName());
    }

    @Test
    void whenFindByLikeName() {
        Driver storedDriver = driverRepository.createDriver(new Driver(0, "driver 7 by like", user7));
        String key = "like";
        assertThat(driverRepository.findByLikeName(key)).isNotEmpty();
        assertThat(driverRepository.findByLikeName(key)).contains(storedDriver);
    }

    @Test
    void whenFindByName() {
        Driver storedDriver = driverRepository.createDriver(new Driver(0, "driver 8 by name", user8));
        assertThat(driverRepository.findByName(storedDriver.getName())).isPresent();
        assertThat(driverRepository.findByName(storedDriver.getName()).get().getName()).isEqualTo(storedDriver.getName());
    }
}