import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetService {
    private final BudgetRepo budgetRepo;

    BudgetService() {
        budgetRepo = new BudgetRepo();
    }

    private static long getOverlappingDays(LocalDate start, LocalDate end, Budget budget) {
        LocalDate overlappingStart;
        LocalDate overlappingEnd;
        if (YearMonth.from(start).equals(YearMonth.from(end))) {
            overlappingStart = start;
            overlappingEnd = end;
        } else if (budget.getYearMonthInstance().equals(YearMonth.from(start))) {
            overlappingStart = start;
            overlappingEnd = budget.getYearMonthInstance().atEndOfMonth();
        } else if (budget.getYearMonthInstance().equals(YearMonth.from(end))) {
            overlappingStart = budget.getYearMonthInstance().atDay(1);
            overlappingEnd = end;
        } else {
            overlappingStart = budget.getYearMonthInstance().atDay(1);
            overlappingEnd = budget.getYearMonthInstance().atEndOfMonth();
        }
        long daysBetween = DAYS.between(overlappingStart, overlappingEnd);
        return daysBetween + 1;
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
            long overlappingDays = getOverlappingDays(start, end, budget);
            rtBudget += budget.dailyAmount() * overlappingDays;
        }

        return rtBudget; // this seems to be a placeholder, you may want to change it to return rtBudget.
    }

    public BudgetRepo getBudgetRepo() {
        return this.budgetRepo;
    }
}