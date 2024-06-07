package com.example.dajs.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FinanceDTO {
    private List<CategoryDTO> categoryDTOList;
}
