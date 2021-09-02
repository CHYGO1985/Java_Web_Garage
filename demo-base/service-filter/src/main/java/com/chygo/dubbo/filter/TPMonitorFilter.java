package com.chygo.dubbo.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Predicate;

/**
 *
 * @author jingjiejiang
 * @history Sep 1, 2021
 *
 */
@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter {

    // expire time in second
    private static final int EXPIRE_TIME = 60;

    // key: service name_method name , value: SpentTime
    private final Map<String, DelayQueue<SpentTime>> methodSpentTimeMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    {
        System.out.println("-----Begin to execute TPMonitor-----");
        // Launch Scheduled Task
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                for (Map.Entry<String, DelayQueue<SpentTime>> entry : methodSpentTimeMap.entrySet()) {

                    final DelayQueue<SpentTime> delayQueue = entry.getValue();
                    // remove expired data
                    delayQueue.removeIf(new Predicate<SpentTime>() {
                        @Override
                        public boolean test(SpentTime spentTime) {
                            return spentTime.getDelay(TimeUnit.SECONDS) <= 0;
                        }
                    });

                    // get all spendtime data
                    final SpentTime[] spentTimes = delayQueue.toArray(new SpentTime[]{});
                    final List<SpentTime> spentTimeList = Arrays.asList(spentTimes);
                    // sort
                    Collections.sort(spentTimeList, (t1, t2) -> (int)(t1.getRespTime() - t2.getRespTime()));
                    final int listSize = spentTimeList.size();
                    System.out.println(entry.getKey() + "Result: -- TP50: "
                            + spentTimeList.get((int) Math.ceil(0.5d * listSize)).getRespTime()
                            + "milliseconds -- TP90: "
                            + spentTimeList.get((int) Math.ceil(0.9d * listSize)).getRespTime()
                            + "milliseconds.");
                }
            }
        }, 30, 5, TimeUnit.SECONDS);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        long startTime = System.currentTimeMillis();
        Result result = null;
        try {
            result = invoker.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (invocation.getMethodName() == "getMetadataInfo") return result;
            final long respTime = System.currentTimeMillis() - startTime;
            final String serviceName = invocation.getServiceName();
            final String methodName = invocation.getMethodName();
            final String key = serviceName + "#" + methodName;
            DelayQueue<SpentTime> delayQueue = methodSpentTimeMap.get(key);
            if (delayQueue == null) {
                delayQueue = new DelayQueue<>();
            }

            delayQueue.put(new SpentTime(respTime, EXPIRE_TIME * 1000));
            methodSpentTimeMap.put(key, delayQueue);
        }

        return result;
    }

    /**
     * The expired time for calculate each method for TP50 and TP90 is 60 seconds.
     */
    static class SpentTime implements Delayed {

        // response time of a method in milli seconds
        private final long respTime;
        // expire time
        private final long expireTime;

        SpentTime(long respTime, long expireTime) {
            this.respTime = respTime;
            this.expireTime = System.currentTimeMillis() + expireTime;
        }

        public long getRespTime() {
            return respTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            final SpentTime spentTime = (SpentTime) o;
            return (int)(this.getRespTime() - spentTime.getRespTime());
        }
    }
}
