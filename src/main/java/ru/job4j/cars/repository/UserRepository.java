package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserRepository.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 01.09.2022.
 */
@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run(
                "delete from User where id = :fId",
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query("from User", User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :fKey", User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }
//    private final SessionFactory sf;
//
//    /**
//     * Сохранить в базе.
//     *
//     * @param user пользователь.
//     * @return пользователь с id.
//     */
//    public User create(User user) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.persist(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        return user;
//    }
//
//    /**
//     * Обновить в базе пользователя.
//     *
//     * @param user пользователь.
//     */
//    public void update(User user) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createMutationQuery("UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
//                    .setParameter("fLogin", user.getLogin())
//                    .setParameter("fPassword", user.getPassword())
//                    .setParameter("fId", user.getId())
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//    }
//
//    /**
//     * Удалить пользователя по id.
//     *
//     * @param userId ID
//     */
//    public void delete(int userId) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createMutationQuery("DELETE User WHERE id = :fId")
//                    .setParameter("fId", userId)
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//    }
//
//    /**
//     * Список пользователь отсортированных по id.
//     *
//     * @return список пользователей.
//     */
//    public List<User> findAllOrderById() {
//        Session session = sf.openSession();
//        Query<User> query = session.createQuery("from User ORDER BY id", User.class);
//        return query.list();
//    }
//
//    /**
//     * Найти пользователя по ID
//     *
//     * @return пользователь.
//     */
//    public Optional<User> findById(int userId) {
//        Session session = sf.openSession();
//        Query<User> query = session.createQuery("from User where id = :fId", User.class);
//        query.setParameter("fId", userId);
//        return Optional.ofNullable(query.uniqueResult());
//    }
//
//    /**
//     * Список пользователей по login LIKE %key%
//     *
//     * @param key key
//     * @return список пользователей.
//     */
//    public List<User> findByLikeLogin(String key) {
//        Session session = sf.openSession();
//        Query<User> query = session.createQuery("from User where login like :fKey", User.class);
//        query.setParameter("fKey", "%" + key + "%");
//        return query.list();
//    }
//
//    /**
//     * Найти пользователя по login.
//     *
//     * @param login login.
//     * @return Optional or user.
//     */
//    public Optional<User> findByLogin(String login) {
//        Session session = sf.openSession();
//        Query<User> query = session.createQuery("from User where login = :fLogin", User.class);
//        query.setParameter("fLogin", login);
//        return Optional.ofNullable(query.uniqueResult());
//    }
}
