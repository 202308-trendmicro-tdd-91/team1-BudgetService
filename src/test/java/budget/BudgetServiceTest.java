package budget;

import budget.BudgetRepo;
import budget.BudgetService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    public void queryOneMonthHasBudget() {
        BudgetService budgetService = new BudgetService();
        BudgetRepo budgetRepo = budgetService.getBudgetRepo();
        budgetRepo.createMonthBudget("202306", 300);
        LocalDate start = LocalDate.parse("20230601", formatter);
        LocalDate end = LocalDate.parse("20230630", formatter);
        assertEquals(300, budgetService.query(start, end));
    }

    @Test
    public void queryOneDayHasBudget() {
        BudgetService budgetService = new BudgetService();
        BudgetRepo budgetRepo = budgetService.getBudgetRepo();
        budgetRepo.createMonthBudget("202306", 300);
        LocalDate start = LocalDate.parse("20230629", formatter);
        LocalDate end = LocalDate.parse("20230629", formatter);
        assertEquals(10, budgetService.query(start, end));
    }

    @Test
    public void queryInvalidInput() {
        BudgetService budgetService = new BudgetService();
        BudgetRepo budgetRepo = budgetService.getBudgetRepo();
        budgetRepo.createMonthBudget("202306", 300);
        LocalDate start = LocalDate.parse("20230630", formatter);
        LocalDate end = LocalDate.parse("20230628", formatter);
        assertEquals(0, budgetService.query(start, end));
    }

    @Test
    public void queryNoData() {
        BudgetService budgetService = new BudgetService();
        BudgetRepo budgetRepo = budgetService.getBudgetRepo();
        budgetRepo.createMonthBudget("202306", 300);
        LocalDate start = LocalDate.parse("20230701", formatter);
        LocalDate end = LocalDate.parse("20230702", formatter);
        assertEquals(0, budgetService.query(start, end));
    }

    @Test
    public void queryCrossMonth() {
        BudgetService budgetService = new BudgetService();
        BudgetRepo budgetRepo = budgetService.getBudgetRepo();
        budgetRepo.createMonthBudget("202306", 300);
        budgetRepo.createMonthBudget("202307", 31);
        LocalDate start = LocalDate.parse("20230630", formatter);
        LocalDate end = LocalDate.parse("20230701", formatter);
        assertEquals(11, budgetService.query(start, end));
    }

    @Test
    public void queryCross3Month() {
        BudgetService budgetService = new BudgetService();
        BudgetRepo budgetRepo = budgetService.getBudgetRepo();
        budgetRepo.createMonthBudget("202305", 3100);
        budgetRepo.createMonthBudget("202306", 300);
        budgetRepo.createMonthBudget("202307", 31);

        LocalDate start = LocalDate.parse("20230531", formatter);
        LocalDate end = LocalDate.parse("20230701", formatter);
        assertEquals(401, budgetService.query(start, end));
    }
}
