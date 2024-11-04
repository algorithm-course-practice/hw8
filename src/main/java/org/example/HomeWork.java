package org.example;


import java.util.*;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        TreeMap<Double, Integer> bids = new TreeMap<>();
        double profit = 0.0;

        for (String line : actionList) {
            String[] parts = line.split(" ");
            String operation = parts[0];

            switch (operation) {
                case "BID":
                    double applicationPrice = Double.parseDouble(parts[1]);
                    bids.put(applicationPrice, bids.getOrDefault(applicationPrice, 0) + 1);
                    break;

                case "DEL":
                    double delPrice = Double.parseDouble(parts[1]);
                    if (bids.containsKey(delPrice)) {
                        int count = bids.get(delPrice);
                        if (count > 1) {
                            bids.put(delPrice, count - 1);
                        } else {
                            bids.remove(delPrice);
                        }
                    }
                    break;

                case "SALE":
                    double salePrice = Double.parseDouble(parts[1]);
                    int k = Integer.parseInt(parts[2]);
                    NavigableMap<Double, Integer> eligibleBids = bids.tailMap(salePrice, true);
                    int soldCount = 0;

                    for (Map.Entry<Double, Integer> entry : eligibleBids.entrySet()) {
                        int available = entry.getValue();
                        if (soldCount < k) {
                            int toSell = Math.min(available, k - soldCount);
                            soldCount += toSell;
                            profit += toSell * 0.01; // Each sale gives 0.01 profit
                        }
                    }
                    break;

                case "QUIT":
                    return profit;
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
        TreeSet<Long> set = new TreeSet<>();
        long lastNextResult = 0;
        List<Integer> results = new ArrayList<>();
        boolean lastWasQuery = false;

        for (String operation : actionList) {
            String[] parts = operation.split(" ");
            String command = parts[0];
            long value = Long.parseLong(parts[1]);

            if (command.equals("+")) {
                if (lastWasQuery) {
                    value = (value + lastNextResult) % 1000000000;
                }
                set.add(value);
            } else if (command.equals("?")) {
                Long nextValue = set.ceiling(value);
                if (nextValue != null) {
                    lastNextResult = nextValue;
                    results.add(nextValue.intValue());
                } else {
                    lastNextResult = -1;
                    results.add(-1);
                }
                lastWasQuery = true;
            }
        }

        return results;
    }

}
