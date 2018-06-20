package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.CollectionDebit;
import com.migu.schedule.info.IpMap;
import com.migu.schedule.info.TaskInfo;
import com.migu.schedule.info.WeightRoundRobin;

import java.util.*;

/*
*类名和方法不能修改
 */
public class Schedule {

    //注册节点
    public final Set set = new HashSet(10);

    //节点上运行的任务
    public final Map<Integer,List> map = new HashMap<Integer,List>(10);

    //任务运行的节点信息
    public final Map<Integer,Integer> taskMap = new HashMap<Integer,Integer>(10);

    //挂起的队列
    public final List<Integer> upList = new ArrayList(10);

    //运行中的队列
    public final List<Integer> runList = new ArrayList(10);

    //任务资源消耗
    public final Map<Integer,Integer> consumptionMap = new HashMap(10);


    public int init()
    {
        set.clear();
        map.clear();
        taskMap.clear();
        upList.clear();
        runList.clear();
        consumptionMap.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId)
    {

            if(nodeId <= 0)
            {
                return ReturnCodeKeys.E004;
            }
            else if(!set.isEmpty() && set.contains(nodeId))
            {
                return ReturnCodeKeys.E005;
            }
            else
            {
                set.add(nodeId);
                return ReturnCodeKeys.E003;

            }
    }

    public int unregisterNode(int nodeId)
    {
            if(nodeId <= 0)
            {
                return ReturnCodeKeys.E004;
            }
            else if (!set.isEmpty() && !set.contains(nodeId))
            {
                return ReturnCodeKeys.E007;
            }
            else
            {
                if(!set.isEmpty() && set.contains(nodeId))
                {
                    if(null != map && null != map.get(nodeId))
                    {
                        upList.addAll(map.get(nodeId));
                    }
                }
                set.remove(nodeId);
            }
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption)
    {
        if(taskId <= 0)
        {
            return ReturnCodeKeys.E009;
        }
        else if(null != upList && upList.contains(taskId))
        {
            return ReturnCodeKeys.E010;
        }
        else
        {
            upList.add(taskId);
            consumptionMap.put(taskId,consumption);
        }
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId)
    {
        if(taskId < 0)
        {
            return ReturnCodeKeys.E009;
        }
        else if(null != upList && !upList.contains(taskId))
        {
            return ReturnCodeKeys.E012;
        }
        else
        {
            if(null != upList && upList.contains(taskId))
            {
                upList.remove(upList.indexOf(taskId));
            }
            if(null != runList && runList.contains(taskId))
            {
                runList.remove(runList.indexOf(taskId));
            }
        }
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold)
    {
        if(threshold <= 0)
        {
            return ReturnCodeKeys.E002;
        }

        Map<Integer,Integer> resourceMap = new HashMap();

        //如果挂起任务存在
        if(null != upList && upList.size() > 0)
        {
            List upremoveList= new ArrayList();
            Map<Integer, List<Integer>> nodeMap = CollectionDebit.divideDebitResult(upList,3);

            for(Integer num : nodeMap.keySet())
            {
                


            }

            for (int i = 0; i < upList.size() ; i++)
            {
                IpMap ipMap =  new IpMap(resourceMap,set);
                int nodeId = Integer.valueOf(WeightRoundRobin.getServer());
                List taskList = map.get(nodeId);
                if (taskList == null)
                {
                    taskList = new ArrayList();
                }
                taskList.add(upList.get(i));
                //TODO 调度任务，将任务从挂起转移到正运行
                runList.add(upList.get(i));
                map.put(nodeId, taskList);
                //TODO 将任务分配的节点记录到taskMap中
                taskMap.put(upList.get(i),nodeId);

                int countNum =0;

                if(null !=resourceMap.get(nodeId))
                {
                    countNum = resourceMap.get(nodeId);
                }

                resourceMap.put(nodeId,countNum+consumptionMap.get(upList.get(i)));
                upremoveList.add(upList.get(i));
            }
            upList.removeAll(upremoveList);


        }
        return ReturnCodeKeys.E013;
    }


    public int queryTaskStatus(List<TaskInfo> tasks)
    {
        tasks= new ArrayList<TaskInfo>(10);
        if(null != upList && upList.size() > 0)
        {
            for (int i = 0; i < upList.size() ; i++)
            {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setTaskId(upList.get(i));
                taskInfo.setNodeId(-1);
                tasks.add(taskInfo);
            }
        }

        if(null != runList && runList.size() > 0)
        {
            for (int i = 0; i < runList.size() ; i++)
            {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setTaskId(runList.get(i));
                taskInfo.setNodeId(taskMap.get(runList.get(i)));
                tasks.add(taskInfo);
            }

        }

        if(null == tasks)
        {
            return ReturnCodeKeys.E016;
        }

        Collections.sort(tasks, new Comparator<TaskInfo>() {
            public int compare(TaskInfo task0, TaskInfo task1) {
                int taskId0 = task0.getTaskId();
                int taskId1 = task1.getTaskId();
                if (taskId1 > taskId0) {
                    return 1;
                } else if (taskId1 == taskId0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return ReturnCodeKeys.E015;
    }

}
