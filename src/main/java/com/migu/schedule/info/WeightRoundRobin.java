package com.migu.schedule.info;


import java.util.*;

/**
 * 加权轮询法
 *
 */
public class WeightRoundRobin
{
    private static Integer pos = 0;

    public static void main(String[] args)
    {
        for (int i = 0; i < 50 ; i++) {

            System.out.println(getServer());
        }

    }

    public static Integer getServer(){
        //重建一个Map，避免服务器的上下线导致的并发问题
        Map<Integer,Integer> serverMap = new HashMap<Integer, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);

        //获取服务器List
        Set<Integer> keySet = serverMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();

        List<Integer> serverList = new ArrayList<Integer>();
        while( iterator.hasNext() ){
            Integer server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }
        Integer server ;
        synchronized (pos){
            if (pos >= serverList.size()) {
                pos = 0;
            }
            server = serverList.get(pos);
            pos ++ ;
        }
        return server;
    }

}