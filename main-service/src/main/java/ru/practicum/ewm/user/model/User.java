package ru.practicum.ewm.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор пользователя;
    @Column(name = "user_name")
    private String name;// — имя или логин пользователя;
    @Email
    private String email;/* — адрес электронной почты ( два пользователя не могут
            иметь одинаковый адрес электронной почты).*/

}
