package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import static org.assertj.core.api.Assertions.*;
/**
 * EngineRepositoryTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 25.11.2022.
 */
public class EngineRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @Test
    public void whenCreateEngine() {
        Engine engine = new Engine(0, "electric");
        Engine storedEngine = engineRepository.createEngine(engine);
        assertThat(storedEngine.getName()).isEqualTo(engine.getName());
    }

    @Test
    public void whenUpdate() {
        Engine storedEngine = engineRepository.createEngine(new Engine(0, "petrol"));
        String newName = "hybrid";
        storedEngine.setName(newName);
        engineRepository.updateEngine(storedEngine);
        assertThat(engineRepository.findById(storedEngine.getId())).isPresent();
        assertThat(engineRepository.findById(storedEngine.getId()).get().getName()).isEqualTo(newName);
    }

    @Test
    public void whenDelete() {
        Engine engine = engineRepository.createEngine(new Engine(0, "diesel"));
        engineRepository.delete(engine.getId());
        assertThat(engineRepository.findAllOrderById()).doesNotContain(engine);
    }

    @Test
    public void whenFindAllOrderById() {
        Engine engine1 = engineRepository.createEngine(new Engine(0, "engine1"));
        Engine engine2 = engineRepository.createEngine(new Engine(0, "engine2"));
        assertThat(engineRepository.findAllOrderById()).contains(engine1, engine2);

    }

    @Test
    public void whenFindById() {
        Engine engine = engineRepository.createEngine(new Engine(0, "found engine"));
        assertThat(engineRepository.findById(engine.getId())).isPresent();
        assertThat(engineRepository.findById(engine.getId()).get().getName()).isEqualTo(engine.getName());
    }

    @Test
    public void whenFinByLikeName() {
        Engine engine = engineRepository.createEngine(new Engine(0, "likeName"));
        String key = "like";
        assertThat(engineRepository.findByLikeName(key)).isNotEmpty();
        assertThat(engineRepository.findByLikeName(key).get(0).getName()).isEqualTo(engine.getName());
    }

    @Test
    public void whenFindByName() {
        Engine engine = engineRepository.createEngine(new Engine(0, "byName"));
        assertThat(engineRepository.findByName(engine.getName())).isPresent();
        assertThat(engineRepository.findByName(engine.getName()).get().getName()).isEqualTo(engine.getName());
    }
}
