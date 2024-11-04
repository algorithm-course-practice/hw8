package org.example;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        List<Double> bids = new ArrayList<>();
        long totalCount = 0;

        for (String operation : actionList) {
            String[] split = operation.split(" ");
            double amount = Double.parseDouble(split[1]);

            switch (split[0]) {
                case "BID":
                    bids.add(amount);
                    break;

                case "SALE":
                    long unitsToSell = Long.parseLong(split[2]);
                    long availableBids = bids.stream().filter(bid -> bid >= amount).count();
                    totalCount += Math.min(unitsToSell, availableBids);
                    break;

                case "DEL":
                    bids.remove(amount);
                    break;
            }
        }

        return totalCount * 0.01;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        Set<Integer> uniqueValues = new HashSet<>();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < actionList.size(); i++) {
            String[] split = actionList.get(i).split(" ");
            String operation = split[0];
            int value = Integer.parseInt(split[1]);

            if (operation.equals("+")) {
                addValueToSet(uniqueValues, actionList, i, value, result);
            } else if (operation.equals("?")) {
                result.add(findMinValueGreaterThan(uniqueValues, value));
            }
        }

        return result;
    }

    private void addValueToSet(Set<Integer> uniqueValues, List<String> actionList, int index, int value, List<Integer> result) {
        if (index > 0 && actionList.get(index - 1).split(" ")[0].equals("?")) {
            uniqueValues.add(value + result.get(result.size() - 1));
        } else {
            uniqueValues.add(value);
        }
    }

    private int findMinValueGreaterThan(Set<Integer> uniqueValues, int threshold) {
        return uniqueValues.stream()
                .filter(num -> num >= threshold)
                .min(Integer::compare)
                .orElse(-1);
    }

}
