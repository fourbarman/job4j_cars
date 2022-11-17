package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;

import static org.assertj.core.api.Assertions.*;

public class BrandRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);

    @Test
    public void whenCreate() {
        Brand brand = new Brand(0, "new brand");
        Brand storedBrand = brandRepository.createBrand(brand);
        assertThat(storedBrand.getName()).isEqualTo(brand.getName());
    }

    @Test
    public void whenUpdate() {
        Brand brand = new Brand(0, "new brand");
        Brand storedBrand = brandRepository.createBrand(brand);
        storedBrand.setName("updated name");
        brandRepository.updateBrand(storedBrand);
        assertThat(brandRepository.findById(storedBrand.getId())).isPresent();
        assertThat(brandRepository.findById(storedBrand.getId()).get().getName()).isEqualTo(storedBrand.getName());
    }

    @Test
    public void whenDelete() {
        Brand brand = new Brand(0, "for delete");
        Brand storedBrand = brandRepository.createBrand(brand);
        brandRepository.delete(storedBrand.getId());
        assertThat(brandRepository.findAllOrderById()).doesNotContain(storedBrand);
    }

    @Test
    public void whenFindAllOrderById() {
        Brand brand1 = brandRepository.createBrand(new Brand(0, "first brand"));
        Brand brand2 = brandRepository.createBrand(new Brand(0, "second brand"));
        assertThat(brandRepository.findAllOrderById()).contains(brand1, brand2);
    }

    @Test
    public void whenFindById() {
        Brand storedBrand = brandRepository.createBrand(new Brand(0, "brand"));
        assertThat(brandRepository.findById(storedBrand.getId())).isPresent();
        assertThat(brandRepository.findById(storedBrand.getId()).get().getName()).isEqualTo(storedBrand.getName());
    }

    @Test
    public void whenFindByLikeName() {
        Brand storedBrand = brandRepository.createBrand(new Brand(0, "likeName"));
        String key = "like";
        assertThat(brandRepository.findByLikeName(key)).isNotEmpty();
        assertThat(brandRepository.findByLikeName(key).get(0).getName()).isEqualTo(storedBrand.getName());
    }

    @Test
    public void whenFindByName() {
        Brand storedBrand = brandRepository.createBrand(new Brand(0, "byName"));
        assertThat(brandRepository.findByName(storedBrand.getName())).isPresent();
        assertThat(brandRepository.findByName(storedBrand.getName()).get().getName()).isEqualTo(storedBrand.getName());
    }
}
