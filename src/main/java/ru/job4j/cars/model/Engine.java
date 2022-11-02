package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Engine.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 02.10.2022.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
