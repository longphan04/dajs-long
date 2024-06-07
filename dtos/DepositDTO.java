package com.example.dajs.dtos;

import com.example.dajs.utils.DateTimeUtil;
import lombok.Builder;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.Date;

@Data
@Builder
public class DepositDTO {
    private static final DecimalFormat FORMATTER = new DecimalFormat("###,###,###");

    private Date date;
    private Double amount;
    private String description;
    private Long categoryId;
    private CategoryDTO categoryDTO;

    public String getDateStr() {
        return DateTimeUtil.getSimpleFormatDate(this.date);
    }

    public String getAmountStr() {
        return FORMATTER.format(this.amount);
    }
}
