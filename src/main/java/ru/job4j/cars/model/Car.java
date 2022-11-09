package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Car.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 02.10.2022.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "history_owner",
            joinColumns = {@JoinColumn(name = "driver_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "car_id", nullable = false, updatable = false)})
    private List<Driver> drivers = new ArrayList<>();
}
