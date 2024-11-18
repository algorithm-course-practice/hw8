package org.example;


import org.example.task.first.OrderedTree;
import org.example.task.second.Treap;

import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {

        OrderedTree treap = new OrderedTree();
        double profit = 0;
        int sold = 0;

        for (String action : actionList) {

            if (action.equals("QUIT")) break;

            String[] parts = action.split(" ");
            String command = parts[0];
            double price = Double.parseDouble(parts[1]);
            switch (command) {
                case "BID":
                    //treap.insert(price);
                    treap.insert(price);
                    break;
                case "DEL":
                    //treap.delete(price);
                    treap.delete(price);
                    break;
                case "SALE":
                    int quantity = Integer.parseInt(parts[2]);
                    sold = treap.sell(price, quantity);
                    profit += sold * 0.01;
                    break;
            }
        }
        return profit;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        Treap treap = new Treap();
        int lastAnswer = 0;
        int MOD = 1_000_000_000;
        List<Integer> result = new ArrayList<>();

        for (String action : actionList) {

            String[] parts = action.split(" ");
            String operation = parts[0];
            int x = Integer.parseInt(parts[1]);

            if (operation.equals("+")) {
                int value = ((lastAnswer == 0) ? x : (x + lastAnswer) % MOD);
                treap.insert(value);
                lastAnswer = 0;
            } else if (operation.equals("?")) {
                lastAnswer = treap.next(x);
               result.add(lastAnswer);
            }

        }
        return result;
    }

}
