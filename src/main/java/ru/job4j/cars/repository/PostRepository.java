package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.time.ZonedDateTime;
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
    private static final String GET_ALL_BY_POST_HAS_PHOTO = """
            from Post p 
            left join fetch p.photo 
            where p.photo is not null
            """;
    private static final String GET_ALL_FILTER_BY_BRAND = """
            from Post p 
            join fetch p.car c 
            join fetch c.brand b 
            where b.name = :fName
            """;
    private static final String DELETE_POST_BY_ID = """
            delete from Post 
            where id = :fId
            """;
    private static final String FIND_POST_BY_LIKE_TEXT = """
            from Post 
            where text like :fKey
            """;
    private static final String FIND_ALL_BY_DATE_TIME_PERIOD = """
            from Post p 
            where p.created between :fStartTimestamp and :fEndTimeStamp
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
            String deletePriceHistory = "delete from price_history where post_id = " + postId;
            crudRepository.run(session -> session.createNativeQuery(deletePriceHistory).executeUpdate());
            crudRepository.run(DELETE_POST_BY_ID, Map.of("fId", postId));
    }

    /**
     * Список post.
     *
     * @return Post list.
     */
    public List<Post> findAll() {
        return crudRepository.query("from Post", Post.class);
    }

    /**
     * Найти post по ID
     *
     * @return Optional or post.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional("from Post where id = :fId", Post.class, Map.of("fId", postId));
    }

    /**
     * Список post по name LIKE %key%
     *
     * @param key key
     * @return Engine list.
     */
    public List<Post> findByLikeName(String key) {
        return crudRepository.query(FIND_POST_BY_LIKE_TEXT, Post.class, Map.of("fKey", "%" + key + "%"));
    }

    /**
     * Найти post по text.
     *
     * @param text Text.
     * @return Optional or post.
     */
    public Optional<Post> findByName(String text) {
        return crudRepository.optional("from Post where text = :fText", Post.class, Map.of("fText", text));
    }

    /**
     * Find Posts by in the middle of two dateTimes: EndDateTime - StartDateTime.
     * StartDateTime should be < EndDateTime.
     * @param startDateTime Start dateTime.
     * @param endDateTime End dateTime
     * @return Post list.
     */
    public List<Post> findAllByCurrent(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        return crudRepository.query(
                FIND_ALL_BY_DATE_TIME_PERIOD,
                Post.class, Map.of("fStartTimestamp", startDateTime, "fEndTimeStamp", endDateTime));
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
    public List<Post> findAllByBrand(String brandName) {
        return crudRepository.query(GET_ALL_FILTER_BY_BRAND, Post.class, Map.of("fName", brandName));
    }
}
