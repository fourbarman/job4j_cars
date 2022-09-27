package ru.job4j.cars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * PriceHistory.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 27.09.2022.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "history_table")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private int before;
    private int after;
    private LocalDateTime created;
}
