package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

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
    public final List runList = new ArrayList(10);

    //任务资源消耗
    public final Map consumptionMap = new HashMap(10);


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
                runList.remove(upList.indexOf(taskId));
            }
        }
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现

        //TODO 调度任务，将任务从挂起转移到正运行
        //TODO 将任务分配的节点记录到taskMap中
        return ReturnCodeKeys.E000;
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
            for (int i = 0; i < upList.size() ; i++)
            {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setTaskId(upList.get(i));
                taskInfo.setNodeId(taskMap.get(upList.get(i)));
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
