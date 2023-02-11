package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
public class UserRequestDto {

    private Long id; // — уникальный идентификатор пользователя;
    @NotNull
    @NotBlank
    private String name;// — имя или логин пользователя;
    @Email
    private String email;/* — адрес электронной почты ( два пользователя не могут
            иметь одинаковый адрес электронной почты).*/
}
