package budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetRepo {
    List<Budget> listBudget = new ArrayList<>();
    public BudgetRepo(){}

    public List<Budget> getAll(){
        return this.listBudget;
    }

    public void createMonthBudget(String yearMonth, int amount){
        listBudget.add(new Budget(yearMonth,amount));
    }


}
