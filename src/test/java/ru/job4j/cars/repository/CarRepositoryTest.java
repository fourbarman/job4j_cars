package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
/**
 * CarRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 25.11.2022.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);
    private final UserRepository userRepository = new UserRepository(crudRepository);

    Brand brand;
    Engine engine;
    Car car1;
    Car car2;
    Car car3;
    User user1;
    User user2;
    User user3;
    Driver driver1;
    Driver driver2;
    Driver driver3;

    @BeforeAll
    public void setVars() {
        brand = brandRepository.createBrand(new Brand(0, "toyota"));
        engine = engineRepository.createEngine(new Engine(0, "petrol"));
        user1 = userRepository.create(new User(0, "user-driver1", "pass"));
        user2 = userRepository.create(new User(0, "user-driver2", "pass"));
        user3 = userRepository.create(new User(0, "user-driver3", "pass"));
        driver1 = driverRepository.createDriver(new Driver(0, "driver for car1", user1));
        driver2 = driverRepository.createDriver(new Driver(0, "driver for car2", user2));
        driver3 = driverRepository.createDriver(new Driver(0, "driver for car3", user3));
    }

    @Test
    void createCar() {
        car1 = new Car(0, "car 1 name", brand, engine, driver1, List.of(driver1));
        Car storedCar = carRepository.createCar(car1);
        assertThat(storedCar.getName()).isEqualTo(car1.getName());
    }

    @Test
    void updateCar() {
        car2 = carRepository.createCar(new Car(0, "car 2 name", brand, engine, driver2, List.of(driver2)));
        String name = "new name";
        car2.setName(name);
        carRepository.updateCar(car2);
        Optional<Car> optionalCar = carRepository.findById(car2.getId());
        assertThat(optionalCar).isPresent();
        assertThat(optionalCar.get().getName()).isEqualTo(name);
    }

    @Test
    void delete() {
        car3 = carRepository.createCar(new Car(0, "car 3 name", brand, engine, driver3, List.of(driver3)));
        carRepository.delete(car3.getId());
        assertThat(carRepository.findAllOrderById()).doesNotContain(car3);
    }

    @Test
    void findAllOrderById() {
        assertThat(carRepository.findAllOrderById()).isNotEmpty();
    }

    @Test
    void findById() {
        Optional<Car> optionalCar = carRepository.findById(car2.getId());
        assertThat(optionalCar).isPresent();
        assertThat(optionalCar.get().getName()).isEqualTo(car2.getName());
    }

    @Test
    void findByLikeName() {
        String key = "new";
        assertThat(carRepository.findByLikeName(key)).isNotEmpty();
        assertThat(carRepository.findByLikeName(key).get(0).getName()).isEqualTo(car2.getName());
    }

    @Test
    void findByName() {
        Optional<Car> optionalCar = carRepository.findByName(car2.getName());
        assertThat(optionalCar).isPresent();
        assertThat(optionalCar.get().getName()).isEqualTo(car2.getName());
    }
}