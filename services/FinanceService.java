package com.example.dajs.services;

import com.example.dajs.dtos.CategoryDTO;
import com.example.dajs.dtos.FinanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final UserService userService;
    private final CategoryService categoryService;

    public FinanceDTO initDepositDTO() {
        String username = userService.getLoginUsername();
        List<CategoryDTO> categoryDTOList = categoryService.getAvailableUserCategories(username);
        return FinanceDTO.builder().categoryDTOList(categoryDTOList).build();
    }
}
