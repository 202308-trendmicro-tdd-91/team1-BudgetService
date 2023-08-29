import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetService {
    private final BudgetRepo budgetRepo;

    BudgetService() {
        budgetRepo = new BudgetRepo();
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
        for (YearMonth yearMonth : yearMonthsBetween) {
            Optional<Budget> findBudget = listBudgets.stream().filter(b -> b.getYearMonthInstance().equals(yearMonth)).findFirst();
            if (findBudget.isEmpty()) {
                continue;
            }
            Budget budget = findBudget.get();

//        }
//        for (int i = 0; i < yearMonthsBetween.size(); i++) {
//            if (i == 0) {
//                for (Budget budget : listBudgets) {
            if (budget.getYearMonthInstance().equals(startYearMonth)) {

                if (end.isAfter(yearMonthsBetween.get(0).atEndOfMonth())) {
                    daysBetween = DAYS.between(start, yearMonthsBetween.get(0).atEndOfMonth());
                } else {
                    daysBetween = DAYS.between(start, end);
                }

                rtBudget += budget.dailyAmount() * (daysBetween + 1);
            }
//                }
//            } else if (i == yearMonthsBetween.size() - 1) {
//                for (Budget budget : listBudgets) {
            else if (budget.getYearMonthInstance().equals(endYearMonth)) {
                daysBetween = DAYS.between(yearMonth.atDay(1), end);
                rtBudget += budget.dailyAmount() * (daysBetween + 1);
            }
//                }
//            } else {
//                for (Budget budget : listBudgets) {
//                    if (yearMonthsBetween.get(i).equals(budget.getYearMonthInstance())) {
            else if (yearMonth.equals(budget.getYearMonthInstance())) {
                daysBetween = DAYS.between(budget.getYearMonthInstance().atDay(1), budget.getYearMonthInstance().atEndOfMonth());
                rtBudget += budget.dailyAmount() * (daysBetween + 1);
            }
//                }
        }

        return rtBudget; // this seems to be a placeholder, you may want to change it to return rtBudget.
    }

    public BudgetRepo getBudgetRepo() {
        return this.budgetRepo;
    }
}