import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {
    private final BudgetRepo budgetRepo;

    BudgetService() {
        budgetRepo = new BudgetRepo();
    }

    private static int dailyAmount(Budget budget) {
        return budget.amount / budget.getYearMonthInstance().lengthOfMonth();
    }

    public double query(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            return 0;
        }
        List<Budget> listBudgets = budgetRepo.getAll();
        List<YearMonth> yearMonthsBetween = new ArrayList<>();
        YearMonth startYearMonth = YearMonth.from(start);
        YearMonth endYearMonth = YearMonth.from(end);

        YearMonth currentYearMonth = startYearMonth;
        while (!currentYearMonth.isAfter(endYearMonth)) {
            yearMonthsBetween.add(currentYearMonth);
            currentYearMonth = currentYearMonth.plusMonths(1);
        }

        long daysBetween;
        double rtBudget = 0;
        for (int i = 0; i < yearMonthsBetween.size(); i++) {
            if (i == 0) {
                for (Budget budget : listBudgets) {
                    if (budget.getYearMonthInstance().equals(startYearMonth)) {

                        if (end.isAfter(yearMonthsBetween.get(0).atEndOfMonth())) {
                            daysBetween = ChronoUnit.DAYS.between(start, yearMonthsBetween.get(0).atEndOfMonth());
                        } else {
                            daysBetween = ChronoUnit.DAYS.between(start, end);
                        }

                        rtBudget += dailyAmount(budget) * (daysBetween + 1);
                    }
                }
            } else if (i == yearMonthsBetween.size() - 1) {
                for (Budget budget : listBudgets) {
                    YearMonth dateYearMonth = YearMonth.from(end);
                    if (budget.getYearMonthInstance().equals(dateYearMonth)) {
                        daysBetween = ChronoUnit.DAYS.between(yearMonthsBetween.get(i).atDay(1), end);
                        rtBudget += budget.amount / budget.getYearMonthInstance().lengthOfMonth() * (daysBetween + 1);
                    }
                }
            } else {
                for (Budget budget : listBudgets) {
                    if (yearMonthsBetween.get(i).equals(budget.getYearMonthInstance())) {
                        rtBudget += budget.amount;
                    }
                }
            }
        }

        return rtBudget; // this seems to be a placeholder, you may want to change it to return rtBudget.
    }

    public BudgetRepo getBudgetRepo() {
        return this.budgetRepo;
    }
}