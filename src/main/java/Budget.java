import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {
    String yearMonth;
    int amount;
    public Budget (String yearMonth, int amount){
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    YearMonth getYearMonthInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return YearMonth.parse(this.yearMonth, formatter);
    }
}
