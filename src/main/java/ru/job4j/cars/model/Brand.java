package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Brand.
 * Model.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 06.10.2022.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
