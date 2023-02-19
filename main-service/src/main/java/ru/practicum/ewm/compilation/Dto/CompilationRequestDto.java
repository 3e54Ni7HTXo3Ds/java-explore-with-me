package ru.practicum.ewm.compilation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CompilationRequestDto {

    private List<Long> events;
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;

}
