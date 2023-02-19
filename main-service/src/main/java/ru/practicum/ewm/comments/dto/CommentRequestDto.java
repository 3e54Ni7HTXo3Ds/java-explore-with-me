package ru.practicum.ewm.comments.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequestDto {
    @NotNull
    @NotBlank
    private String text;
}
