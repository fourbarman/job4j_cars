package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;

import static org.assertj.core.api.Assertions.*;

class PhotoRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sessionFactory);
    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);

    @Test
    void whenCreatePhoto() {
        Photo photo = new Photo(0, new byte[1]);
        Photo storedPhoto = photoRepository.createPhoto(photo);
        assertThat(storedPhoto.getPhoto()).isEqualTo(photo.getPhoto());
    }

    @Test
    void whenUpdatePhoto() {
        Photo photo = photoRepository.createPhoto(new Photo(0, new byte[1]));
        byte[] newPhoto = new byte[2];
        photo.setPhoto(newPhoto);
        photoRepository.updatePhoto(photo);
        assertThat(photoRepository.findById(photo.getId())).isPresent();
        assertThat(photoRepository.findById(photo.getId()).get().getPhoto()).isEqualTo(newPhoto);
    }

    @Test
    void whenDelete() {
        Photo photo = photoRepository.createPhoto(new Photo(0, new byte[1]));
        photoRepository.delete(photo.getId());
        assertThat(photoRepository.findAllOrderById()).doesNotContain(photo);
    }

    @Test
    void whenFindAllOrderById() {
        Photo photo1 = photoRepository.createPhoto(new Photo(0, new byte[1]));
        Photo photo2 = photoRepository.createPhoto(new Photo(0, new byte[2]));
        assertThat(photoRepository.findAllOrderById()).contains(photo1, photo2);
    }

    @Test
    void whenFindById() {
        Photo photo = new Photo(0, new byte[1]);
        Photo storedPhoto = photoRepository.createPhoto(photo);
        assertThat(photoRepository.findById(storedPhoto.getId())).isPresent();
        assertThat(photoRepository.findById(storedPhoto.getId()).get().getPhoto()).isEqualTo(photo.getPhoto());
    }
}