package budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {
    String yearMonth;
    int amount;
    public Budget (String yearMonth, int amount){
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    double overlappingAmount(Period period) {
        return dailyAmount() * period.getOverlappingDays(createPeriod());
    }

    Period createPeriod() {
        return new Period(firstDay(), lastDay());
    }

    LocalDate firstDay() {
        return getYearMonthInstance().atDay(1);
    }

    LocalDate lastDay() {
        return getYearMonthInstance().atEndOfMonth();
    }

    double dailyAmount() {
        return (double)this.amount / getYearMonthInstance().lengthOfMonth();
    }

    YearMonth getYearMonthInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return YearMonth.parse(this.yearMonth, formatter);
    }
}
