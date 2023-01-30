package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import static org.assertj.core.api.Assertions.*;

/**
 * UserRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 25.11.2022.
 */
class UserRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final UserRepository userRepository = new UserRepository(crudRepository);

    @Test
    void create() {
        User user = new User(0, "login", "password");
        User storedUser = userRepository.create(user);
        assertThat(storedUser.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void update() {
        User user = userRepository.create(new User(0, "login2", "password"));
        String newLogin = "new login";
        user.setLogin(newLogin);
        userRepository.update(user);
        assertThat(userRepository.findById(user.getId())).isPresent();
        assertThat(userRepository.findById(user.getId()).get().getLogin()).isEqualTo(newLogin);
    }

    @Test
    void delete() {
        User user = userRepository.create(new User(0, "login3", "password"));
        userRepository.delete(user.getId());
        assertThat(userRepository.findAllOrderById()).doesNotContain(user);
    }

    @Test
    void findAllOrderById() {
        User user1 = userRepository.create(new User(0, "login4", "password"));
        User user2 = userRepository.create(new User(0, "login5", "password"));
        assertThat(userRepository.findAllOrderById()).contains(user1, user2);
    }

    @Test
    void findById() {
        User user = new User(0, "login6", "password");
        User storedUser = userRepository.create(user);
        assertThat(userRepository.findById(user.getId())).isPresent();
        assertThat(userRepository.findById(storedUser.getId()).get().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void findByLikeLogin() {
        User user = userRepository.create(new User(0, "some login", "password"));
        String key = "some";
        assertThat(userRepository.findByLikeLogin(key)).isNotEmpty();
        assertThat(userRepository.findByLikeLogin(key).get(0).getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void findByLogin() {
        User user = userRepository.create(new User(0, "login7", "password"));
        assertThat(userRepository.findByLogin(user.getLogin())).isPresent();
        assertThat(userRepository.findByLogin(user.getLogin()).get().getLogin()).isEqualTo(user.getLogin());
    }
}