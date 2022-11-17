package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.job4j.cars.model.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final UserRepository userRepository = new UserRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);
    private final PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository(crudRepository);

    User user1;
    User user2;
    User user3;

    Brand brand;

    Engine engine;

    Car car1;
    Car car2;
    Car car3;

    Driver driver1;
    Driver driver2;
    Driver driver3;

    PriceHistory priceHistory1;
    PriceHistory priceHistory2;
    PriceHistory priceHistory3;
    PriceHistory priceHistory4;
    PriceHistory priceHistory5;
    PriceHistory priceHistory6;

    Photo photo1;
    Photo photo2;
    Photo photo3;

    Post post1;
    Post post2;
    Post post3;

    @BeforeAll
    public void setVars() {
        user1 = userRepository.create(new User(0, "post user 1", "post user 1 pass"));
        user2 = userRepository.create(new User(0, "post user 2", "post user 2 pass"));
        user3 = userRepository.create(new User(0, "post user 3", "post user 3 pass"));
        brand = brandRepository.createBrand(new Brand(0, "bmw"));
        engine = engineRepository.createEngine(new Engine(0, "1nz"));
        driver1 = driverRepository.createDriver(new Driver(0, "post driver 1 name", user1));
        driver2 = driverRepository.createDriver(new Driver(0, "post driver 2 name", user2));
        driver3 = driverRepository.createDriver(new Driver(0, "post driver 3 name", user3));
        car1 = carRepository.createCar(new Car(0, "post car 1", brand, engine, driver1, List.of(driver1)));
        car2 = carRepository.createCar(new Car(0, "post car 2", brand, engine, driver2, List.of(driver2)));
        car3 = carRepository.createCar(new Car(0, "post car 3", brand, engine, driver3, List.of(driver3)));
        photo1 = photoRepository.createPhoto(new Photo(0, new byte[1]));
        photo2 = photoRepository.createPhoto(new Photo(0, new byte[1]));
        photo3 = photoRepository.createPhoto(new Photo(0, new byte[1]));
        priceHistory1 = priceHistoryRepository.create(new PriceHistory(0, 1, 2, ZonedDateTime.now()));
        priceHistory2 = priceHistoryRepository.create(new PriceHistory(0, 2, 4, ZonedDateTime.now()));
        priceHistory3 = priceHistoryRepository.create(new PriceHistory(0, 5, 6, ZonedDateTime.now()));
        priceHistory4 = priceHistoryRepository.create(new PriceHistory(0, 7, 8, ZonedDateTime.now()));
        priceHistory5 = priceHistoryRepository.create(new PriceHistory(0, 9, 10, ZonedDateTime.now()));
        priceHistory6 = priceHistoryRepository.create(new PriceHistory(0, 11, 12, ZonedDateTime.now()));
    }

    @Test
    void createPost() {
        post1 = new Post(0, "text", ZonedDateTime.now(), user1, car1, List.of(priceHistory1, priceHistory2), List.of(user1), photo1);
        Post storedPost = postRepository.createPost(post1);
        assertThat(storedPost.getText()).isEqualTo(post1.getText());
    }

    @Test
    void updatePost() {
        post2 = postRepository.createPost(
                new Post(0, "text", ZonedDateTime.now(), user2, car2, List.of(priceHistory3, priceHistory4), List.of(user2), photo2));
        String text = "updated text";
        post2.setText(text);
        postRepository.updatePost(post2);
        Optional<Post> foundPost = postRepository.findById(post2.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getText()).isEqualTo(text);

    }

    void delete() {
        post3 = postRepository.createPost(
                new Post(0, "post to delete", ZonedDateTime.now(), user3, car3, List.of(priceHistory5, priceHistory6), List.of(user3), photo3));
        postRepository.delete(post3.getId());
        assertThat(postRepository.findAll()).doesNotContain(post3);
    }

    @Test
    void findAll() {
        assertThat(postRepository.findAll()).isNotEmpty();
    }

    @Test
    void findById() {
        Post post = postRepository.findAll().get(0);
        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getText()).isEqualTo(post.getText());
    }

    @Test
    void findByLikeName() {
        Post post = postRepository.findAll().get(0);
        String text = post.getText();
        String key = text.substring(0, text.length() / 2);
        assertThat(postRepository.findByLikeName(key)).isNotEmpty();
        assertThat(postRepository.findByLikeName(key).get(0).getText()).isEqualTo(post.getText());
    }

    @Test
    void findByName() {
        Post post = postRepository.findAll().get(0);
        Optional<Post> foundPost = postRepository.findByName(post.getText());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getText()).isEqualTo(post.getText());
    }

    @Test
    void findAllByCurrent() {
        ZonedDateTime current = ZonedDateTime.now();
        ZonedDateTime startDateTime = current.minusDays(1);
        ZonedDateTime endDateTime = current.plusDays(1);
        List<Post> foundPostList = postRepository.findAllByCurrent(startDateTime, endDateTime);
        assertThat(foundPostList).isNotEmpty();
        assertThat(foundPostList.get(0).getCreated()).isBetween(startDateTime, endDateTime);
    }

    @Test
    public void whenFindAllByPostHasPhoto() {
        assertThat(postRepository.findAllByPostHasPhoto()).isNotEmpty();
    }

    @Test
    public void whenFindAllByBrand() {
        List<Post> list = postRepository.findAllByBrand("bmw");
        for (Post post : list) {
            System.out.println(post.getText());
        }
        assertThat(list).isNotEmpty();
    }
}