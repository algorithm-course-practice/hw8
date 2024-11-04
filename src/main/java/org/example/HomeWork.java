package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        Treap<Float> floatTreap = new Treap<>();

        /*
                "BID 0.01\n" +
                "BID 10000\n" +
                "BID 5000\n" +
                "BID 5000\n" +
                "SALE 7000 3\n" + -1 tr -> 10000
                "DEL 5000\n" +
                "SALE 3000 3\n" + -> 2 tr 10000 5000
                "SALE 0.01 3"   3 tr ->
         */

        double birgaEarnPerTransaction = 0.01d;
        int transactions = 0;

        for (String action : actionList) {
            String[] split = action.split(" ");
            switch (split[0]) {
                case "BID":
                    floatTreap.add(Float.parseFloat(split[1]), 1);
                    break;
                case "SALE":
                    Treap.Statistic stats = floatTreap.getStats(Float.parseFloat(split[1]) - 0.01f, 100001.00f);
                    transactions += Math.min(stats == null ? 0 : stats.sumValue, Integer.parseInt(split[2]));
                    System.out.println(stats);
                    break;
                case "DEL" :
                    floatTreap.remove(Float.parseFloat(split[1]));
                    break;
            }
        }

        return birgaEarnPerTransaction * transactions;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        Treap<Integer> intTreap = new Treap<>();
        Stack<Integer> result = new Stack<>();

        for (String actions : actionList) {
            String[] split = actions.split(" ");
            switch (split[0]) {
                case "+" :
                    Integer key = Integer.parseInt(split[1]);
                    key = result.isEmpty() ? key : result.peek() + key;
                    if (!intTreap.search(key)) {
                        intTreap.add(key, 1);
                    }
                    break;
                case "?" :
                    Integer minNode = intTreap.getMinNode(Integer.parseInt(split[1]) - 1, Integer.MAX_VALUE);
                    result.push(minNode == null ? -1 : minNode);
                    break;
            }
        }

        return new ArrayList<>(result);
    }

}
