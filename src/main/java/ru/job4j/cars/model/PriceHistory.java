package ru.job4j.cars.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;
import java.time.ZonedDateTime;

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
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private int before;
    private int after;
    private ZonedDateTime created;
}
