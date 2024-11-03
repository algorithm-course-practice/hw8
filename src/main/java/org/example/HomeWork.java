package org.example;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        Treap<Integer> treap = new Treap<>();
        int profit = 0;
        for (String line : actionList){
            List<String> args = Arrays.stream(line.split(" ")).collect(Collectors.toList());
            if (args.get(0).equalsIgnoreCase("BID")){
                //добавим в дерамиду ставку, учет ведем в копейках чтобы использовать Integer
                treap.add((int) (Double.valueOf(args.get(1)) * 100));
            }
            else if (args.get(0).equalsIgnoreCase("SALE")){
                // splitright -  размер что больше и в прибыль размер или количество на продажу (что меньше)
                long size = getSize(treap, args);
                if (valueOf(args.get(2)) > size){
                    profit = (int) (profit +  size);
                }
                else {
                    profit = profit + valueOf(args.get(2));
                }
            }
            else  if (args.get(0).equalsIgnoreCase("DEL")){
                treap.remove(valueOf(args.get(1)) * 100);
            } else if (args.get(0).equalsIgnoreCase("QUIT")) {
                // обратно в рубли
                return  profit/100D;
            }
            else {
                throw new IllegalArgumentException("Некорректные входные данные");
            }
        }
        // обратно в рубли
        return  profit/100D;
    }

    private static long getSize(Treap<Integer> treap, List<String> args) {
        Treap.Node<Integer> splitRight = treap.split((int) (Double.valueOf(args.get(1)) * 100))[1];
        Treap<Integer> treapRight = new Treap<>();
        treapRight.setRoot(splitRight);
        long size  = treapRight.inorder().stream()
                .map(n->n.split(",")[0].substring(1))
                .map(Integer::parseInt)
                .count();
        return size;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        return null;
    }

}
