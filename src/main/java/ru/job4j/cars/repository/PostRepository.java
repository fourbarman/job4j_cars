package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PostRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 02.10.2022.
 */
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    /**
     * Добавить engine.
     *
     * @param post Post.
     */
    public Post createCar(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    /**
     * Обновить post.
     *
     * @param post Post.
     */
    public void updateCar(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    /**
     * Удалить post по id.
     *
     * @param postId ID
     */
    public void delete(int postId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    /**
     * Список post отсортированных по id.
     *
     * @return Post list.
     */
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post", Post.class);
    }

    /**
     * Найти post по ID
     *
     * @return Optional or post.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    /**
     * Список post по name LIKE %key%
     *
     * @param key key
     * @return Engine list.
     */
    public List<Post> findByLikeName(String key) {
        return crudRepository.query(
                "from Post where name like :fKey", Post.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти engine по name.
     *
     * @param name Name.
     * @return Optional or post.
     */
    public Optional<Post> findByName(String name) {
        return crudRepository.optional(
                "from Post where login = :fName", Post.class,
                Map.of("fName", name)
        );
    }
}
