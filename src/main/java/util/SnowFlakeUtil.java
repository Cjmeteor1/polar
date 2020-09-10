package util;
import java.util.HashMap;
import java.util.Map;

/**
 * SnowFlakeUtil
 * 用于分布式环境下生成唯一id
 * @author zhangwenzhi
 * @description
 * @date 2020/7/14 14:23
 */
public class SnowFlakeUtil {
    /** 开始时间截 (2015-01-01) */
    private static final long twepoch = 1420041600000L;

    /** 机器id所占的位数 */
    private static final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private static final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private static final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private static final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private static final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private static long workerId = -1;

    /** 数据中心ID(0~31) */
    private static long datacenterId = -1;

    /** 毫秒内序列(0~4095) */
    private static long sequence = 0L;

    /** 上次生成ID的时间截 */
    private static long lastTimestamp = -1L;

    /**
     * 构造函数
     *  workerId 工作ID (0~31)
     *  datacenterId 数据中心ID (0~31)
     */
    public SnowFlakeUtil() {}

    /**
     * workId 初始化
     * @author zhangwenzhi
     * @date 2020/7/15 16:51
     */
    private static void initWorkId(long nodeId){
        long workerIdIn=0,datacenterIdIn=0;
        Map<String,Long> map;
        try {
            map = parseId(nodeId);
            workerIdIn = map.get("workerId");
            datacenterIdIn = map.get("datacenterId");
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

        if (workerIdIn > maxWorkerId || workerIdIn < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterIdIn > maxDatacenterId || datacenterIdIn < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        workerId = workerIdIn;
        datacenterId = datacenterIdIn;
    }


    /**
     * 获取机器id
     * @author zhangwenzhi
     * @date 2020/7/14 17:16
     */
    public static Map<String, Long> parseId(long nodeId) throws Exception{
        long workerId,datacenterId;

        if(nodeId > 511 || nodeId < 0){
            throw new Exception("配置文件中【node.id】非法，应为0-511的整数。");
        }

        datacenterId = nodeId % 32;
        workerId = (nodeId - datacenterId)/32;

        Map<String, Long> result = new HashMap<>();
        result.put("workerId",workerId);
        result.put("datacenterId",datacenterId);
        return result;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public static synchronized String nextId(long nodeId) {
        if(workerId == -1 || datacenterId == -1){
            initWorkId(nodeId);
        }
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过
        if (timestamp < lastTimestamp) {
            // 如果时钟回拨在可接受范围内, 等待即可
            if (lastTimestamp - timestamp < 5){
                try {
                    Thread.sleep(lastTimestamp - timestamp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                //启用备用id
                return String.valueOf(((timestamp - twepoch) << timestampLeftShift)
                        | (datacenterId << datacenterIdShift)
                        | ((workerId+16) << workerIdShift)
                        | sequence);
            }
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        long result = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
        return String.valueOf(result);
    }



    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

}
