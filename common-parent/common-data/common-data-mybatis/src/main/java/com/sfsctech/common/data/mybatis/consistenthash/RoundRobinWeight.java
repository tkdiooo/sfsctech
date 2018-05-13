package com.sfsctech.common.data.mybatis.consistenthash;



import com.sfsctech.common.support.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Class RoundRobinWeight
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
public class RoundRobinWeight {

    private static final List<Node> lNodes = new ArrayList<>();
    private static int cw = 0;
    private static int number = -1;// 当前Node的序号,开始是-1
    private static int max;// 最大权重
    private static int gcd;// 最大公约数

    private static final Object lock = new Object();

    public RoundRobinWeight() {
    }


    private static void refreshNode() {
        max = getMaxWeight(lNodes);
        gcd = gcd(lNodes);
    }


    public static void addNode(final Node n) {
        synchronized (lock) {
            lNodes.add(n);
            refreshNode();
        }
    }

    public static boolean removeNode(final Node node) {
        synchronized (lock) {
            if (ListUtil.isEmpty(lNodes)) return false;
            boolean f = false;
            for (int i = 0; i < lNodes.size(); i++) {
                Node n = lNodes.get(i);
                if (n.equals(node)) f = lNodes.remove(n);
            }
            if (f) refreshNode();
            return f;
        }
    }


    /**
     * 求最大公约数
     *
     * @param ary List<Node>
     * @return int
     */
    private static int gcd(List<Node> ary) {
        if (ary == null || ary.size() == 0) return 0;

        int min = ary.get(0).weight;

        for (Node anAry : ary) {
            int weight = anAry.weight;
            if (weight < min) min = weight;
        }
        while (min >= 1) {
            boolean isCommon = true;
            for (Node anAry : ary) {
                if (anAry.weight % min != 0) {
                    isCommon = false;
                    break;
                }
            }
            if (isCommon) break;
            min--;
        }
        return min;
    }

    /**
     * 求最大值，权重
     *
     * @return int
     */

    private static int getMaxWeight(List<Node> ary) {
        int max = 0;
        for (Node anAry : ary) {
            int weight = anAry.weight;
            if (max < weight) max = weight;
        }
        return max;
    }

    /**
     * 获取请求的SERVER序号
     *
     * @return Integer
     */
    private static synchronized Integer nextIndex() {
        if (lNodes.size() == 0) return null;

        while (true) {
            number = (number + 1) % lNodes.size();
            if (number == 0) {
                cw = cw - gcd;// cw比较因子，从最大权重开始，以最大公约数为步长递减
                if (cw <= 0) {//
                    cw = max;
                    if (cw == 0) return null;
                }
            }
            if (lNodes.get(number).weight >= cw) return number;
        }

    }

    /**
     * 获取请求的Node
     *
     * @return Node
     */
    public static Node nextNode() {
        synchronized (lock) {
            Integer index = nextIndex();
            if (index == null) return null;
            return lNodes.get(index);
        }
    }
}
