package com.migu.schedule.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionDebit
{
    static Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();

    public static Map<Integer, List<Integer>> divideDebitResult(List<Integer> debitList, Integer num)
    {
        Collections.sort(debitList, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return -1;
                } else if (o1 < o2) {
                    return 1;
                }
                return 0;
            }
        });

        //初始化map
        List<Integer> list = null;
        Integer count = num;
        for (; count > 0; count--) {
            list = new ArrayList<Integer>();
            map.put(count, list);
        }

        //分配资源
        divideDebit(debitList, num, true);

        return map;

    }

    public static void divideDebit(List<Integer> dList, Integer num, boolean direction)
    {
        if (dList.size() >= num)
        {
            for (int i = 0; i < num; i++)
            {
                Integer index;
                if (direction) {
                    index = i + 1;
                } else {
                    index = num - i;
                }
                List<Integer> list = map.get(index);
                list.add(dList.get(i));
                map.put(index, list);
            }
            //去除已经分配的资源
            List<Integer> newDebitList = new ArrayList<Integer>();
            for (int i = 0; i < dList.size(); i++) {
                if (i > num - 1) {
                    newDebitList.add(dList.get(i));
                }
            }

            if (newDebitList.size() > 0) {
                //下次分配资源单，按反方向分配
                divideDebit(newDebitList, num, !direction);
            }
        } else if (dList.size() < num) {//资源小于服务器
            for (int i = 0; i < dList.size(); i++) {
                List<Integer> list = map.get(i + 1);
                list.add(dList.get(i));
                map.put(i + 1, list);
            }
        }

    }
}


