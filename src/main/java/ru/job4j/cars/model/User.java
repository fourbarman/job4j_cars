package ru.job4j.cars.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * User.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 01.09.2022.
 */
@Entity
@Table(name = "auto_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
}
