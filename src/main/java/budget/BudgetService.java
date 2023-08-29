package budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetService {
    private final BudgetRepo budgetRepo;

    BudgetService() {
        budgetRepo = new BudgetRepo();
    }

    public double query(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            return 0;
        }

        Period period = new Period(start, end);

        return budgetRepo.getAll().stream()
                .mapToDouble(budget -> budget.overlappingAmount(period))
                .sum();
    }

    public BudgetRepo getBudgetRepo() {
        return this.budgetRepo;
    }
}