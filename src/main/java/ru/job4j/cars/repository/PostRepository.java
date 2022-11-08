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

    private final String GET_ALL_DISTINCT = """
            distinct from Post 
            left join fetch User 
            join fetch PriceHistory 
            join fetch Car 
            join fetch Photo
            """;
    private final String GET_ALL_BY_CURRENT_DAY = """
            select * from auto_post ap 
            where ap.created :: date = now() :: date
            """;
    private final String GET_ALL_BY_POST_HAS_PHOTO = """
            select * from auto_post ap 
            where ap.post_photo_id != null
            """;
    private final String GET_ALL_FILTER_BY_BRAND = """
            from Post p 
            left join fetch Car c 
            where c.brand = :fBrand
            """;
    private final String DELETE_POST_BY_ID = """
            delete from Post 
            where id = :fId
            """;
    private final String FIND_POST_BY_NAME = """
            from Post 
            where name = :fName
            """;
    private final String FIND_POST_BY_LIKE_NAME = """
            from Post 
            where name like :fKey
            """;
    private final String FIND_POST_BY_ID = """
            from Post 
            where id = :fId
            """;
    /**
     * Добавить post.
     *
     * @param post Post.
     */
    public Post createPost(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    /**
     * Обновить post.
     *
     * @param post Post.
     */
    public void updatePost(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    /**
     * Удалить post по id.
     *
     * @param postId ID
     */
    public void delete(int postId) {
        crudRepository.run(DELETE_POST_BY_ID, Map.of("fId", postId));
    }

    /**
     * Список post.
     *
     * @return Post list.
     */
    public List<Post> findAll() {
        return crudRepository.query(GET_ALL_DISTINCT, Post.class);
    }

    /**
     * Найти post по ID
     *
     * @return Optional or post.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(FIND_POST_BY_ID, Post.class, Map.of("fId", postId));
    }

    /**
     * Список post по name LIKE %key%
     *
     * @param key key
     * @return Engine list.
     */
    public List<Post> findByLikeName(String key) {
        return crudRepository.query(FIND_POST_BY_LIKE_NAME, Post.class, Map.of("fKey", "%" + key + "%"));
    }

    /**
     * Найти post по name.
     *
     * @param name Name.
     * @return Optional or post.
     */
    public Optional<Post> findByName(String name) {
        return crudRepository.optional(FIND_POST_BY_NAME, Post.class, Map.of("fName", name));
    }

    /**
     * Найти все post за последний день.
     * @return Post list.
     */
    public List<Post> findAllByCurrentDay() {
        return crudRepository.query(GET_ALL_BY_CURRENT_DAY, Post.class);
    }

    /**
     * Найти все posts у которых есть фото.
     * @return Post list.
     */
    public List<Post> findAllByPostHasPhoto() {
        return crudRepository.query(GET_ALL_BY_POST_HAS_PHOTO, Post.class);
    }

    /**
     * Найти все posts определенной марки.
     * @return Post list.
     */
    public List<Post> findAllByBrand(String brand) {
        return crudRepository.query(GET_ALL_FILTER_BY_BRAND, Post.class, Map.of("fBrand", brand));
    }
}
