package com.example.dajs.services;

import com.example.dajs.dtos.WithdrawDTO;
import com.example.dajs.entities.Category;
import com.example.dajs.entities.User;
import com.example.dajs.entities.Withdraw;
import com.example.dajs.repositories.WithdrawRepository;
import com.example.dajs.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final WithdrawRepository withdrawRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public WithdrawDTO save(WithdrawDTO withdrawDTO) {
        Withdraw withdraw = new Withdraw();
        withdraw.setDate(DateTimeUtil.convertDateToTimeStamp(withdrawDTO.getDate()));
        withdraw.setAmount(withdrawDTO.getAmount());
        withdraw.setDescription(withdrawDTO.getDescription());
        withdraw.setUser(User.builder().id(userService.getLoginUserId()).build());
        withdraw.setCategory(Category.builder().id(withdrawDTO.getCategoryId()).build());
        withdrawRepository.save(withdraw);
        return withdrawDTO;
    }

    public Double getSumAllWithdrawOfCurrentUser() {
        String username = userService.getLoginUsername();
        return withdrawRepository.getSumAllWithdrawOfCurrentUser(username);
    }

    public Double getCurrentMonthWithdrawAmount() {
        String username = userService.getLoginUsername();
        return withdrawRepository.getCurrentMonthWithdrawAmount(username);
    }

    public List<WithdrawDTO> convertToWithdrawDTOs(List<Withdraw> withdrawList) {
        return withdrawList.stream().map(this::convertToWithdrawDTO).collect(Collectors.toList());
    }

    public WithdrawDTO convertToWithdrawDTO(Withdraw withdraw) {
        return WithdrawDTO.builder()
                .amount(withdraw.getAmount())
                .date(DateTimeUtil.convertTimestampToDate(withdraw.getDate()))
                .description(withdraw.getDescription())
                .categoryDTO(categoryService.convertToCategoryDTO(withdraw.getCategory()))
                .build();
    }
}