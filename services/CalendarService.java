package com.example.dajs.services;

import com.example.dajs.dtos.CalendarDTO;
import com.example.dajs.dtos.DepositDTO;
import com.example.dajs.repositories.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final DepositService depositRepository;
    private final WithdrawRepository withdrawRepository;
    private final UserService userService;
    private final DepositService depositService;

    public CalendarDTO getCalendarInfo(CalendarDTO calendarDTO) {
        String username = userService.getLoginUsername();
        Date date = calendarDTO.getDate();

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();

        Double depositAmount = depositRepository.getDepositsInMonth(username, year, month);
        Double withdrawAmount = withdrawRepository.getWithdrawsInMonth(username, year, month);
        List<DepositDTO> currentDateTransactions = depositService.getTransactionsOnDay(username, date);
        Double currentDateBalance = currentDateTransactions.stream().mapToDouble(DepositDTO::getAmount).sum();

        calendarDTO.setDepositAmount(depositAmount == null ? 0 : depositAmount);
        calendarDTO.setWithdrawAmount(withdrawAmount == null ? 0 : withdrawAmount);

        Double sumAmount = calendarDTO.getDepositAmount() - calendarDTO.getWithdrawAmount();
        calendarDTO.setSumAmount(sumAmount);
        calendarDTO.setCurrentDateTransactions(currentDateTransactions);
        calendarDTO.setCurrentDateBalance(currentDateBalance);

        return calendarDTO;
    }
}
