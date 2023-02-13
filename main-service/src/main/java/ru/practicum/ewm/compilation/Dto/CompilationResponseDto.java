package ru.practicum.ewm.compilation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.event.model.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CompilationResponseDto {

    private List<Event> events;
    private Long id;
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;

}
