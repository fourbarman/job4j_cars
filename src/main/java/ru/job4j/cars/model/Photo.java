package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/**
 * Image.
 * Model.
 * Stores Post image.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 06.10.2022.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private byte[] photo;
}
