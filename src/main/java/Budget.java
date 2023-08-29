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

    LocalDate firstDay() {
        return getYearMonthInstance().atDay(1);
    }

    LocalDate lastDay() {
        return getYearMonthInstance().atEndOfMonth();
    }

    int dailyAmount() {
        return this.amount / getYearMonthInstance().lengthOfMonth();
    }

    YearMonth getYearMonthInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return YearMonth.parse(this.yearMonth, formatter);
    }
}
