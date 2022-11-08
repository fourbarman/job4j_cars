package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * PhotoRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 08.11.2022.
 */
@AllArgsConstructor
public class PhotoRepository {
    private final CrudRepository crudRepository;

    /**
     * Добавить photo.
     *
     * @param photo Photo.
     */
    public Photo createPhoto(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    /**
     * Обновить photo.
     *
     * @param photo Photo
     */
    public void updatePhoto(Photo photo) {
        crudRepository.run(session -> session.merge(photo));
    }

    /**
     * Удалить photo по id.
     *
     * @param photoId ID
     */
    public void delete(int photoId) {
        crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", photoId)
        );
    }

    /**
     * Список photo отсортированных по id.
     *
     * @return Photo list.
     */
    public List<Photo> findAllOrderById() {
        return crudRepository.query("from Photo", Photo.class);
    }

    /**
     * Найти photo по ID
     *
     * @return Optional or photo.
     */
    public Optional<Photo> findById(int photoId) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", photoId)
        );
    }
}
