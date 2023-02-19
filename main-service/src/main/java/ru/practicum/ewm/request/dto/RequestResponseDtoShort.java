package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class RequestResponseDtoShort {

    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;

}
