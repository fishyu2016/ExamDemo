package com.migu.schedule.info;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 服务器地址池
 * Created by zhang on 17/1/17.
 */
public class IpMap {
    public static HashMap<Integer, Integer> serverWeightMap = new HashMap<Integer, Integer>();

    public final  Map map = new HashMap();

    static {
        serverWeightMap.put(7, 1);
        serverWeightMap.put(1, 2);
        serverWeightMap.put(6, 3);
    }

    public IpMap(Map<Integer, Integer> resoureMap,Set<Integer> set)
    {
        if(null!= set && set.size() > 0)
        {
            for(Integer nodeId: set)
            {
                map.put(nodeId,1);
            }

        }
        if(null == resoureMap || resoureMap.size() == 0)
        {
            serverWeightMap.putAll(map);
        }
        else
        {
            serverWeightMap.putAll(resoureMap);
            if(map.size() > 0)
            {
                for(Integer key: resoureMap.keySet())
                {
                    map.remove(key);

                }
                serverWeightMap.putAll(map);
            }
        }

    }
}
