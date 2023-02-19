package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class RequestDtoShort {

    private List<Long> requestIds;
    private String status;

}
