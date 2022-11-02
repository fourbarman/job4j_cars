package ru.job4j.cars.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Post.
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
@Table(name = "auto_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "history_table_id")
    private List<PriceHistory> priceHistory;
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "auto_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "auto_user_id")}
    )
    private List<User> participates;
}
