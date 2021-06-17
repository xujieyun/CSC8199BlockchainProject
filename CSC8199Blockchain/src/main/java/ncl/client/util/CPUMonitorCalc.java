package ncl.client.util;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

public class CPUMonitorCalc {

    private static CPUMonitorCalc instance = new CPUMonitorCalc();

    private OperatingSystemMXBean osMxBean;
    private ThreadMXBean threadBean;
    private long preTime = System.nanoTime();
    private long preUsedTime = 0;

    private CPUMonitorCalc() {
        osMxBean = ManagementFactory.getOperatingSystemMXBean();
        threadBean = ManagementFactory.getThreadMXBean();
    }

    public static CPUMonitorCalc getInstance() {
        return instance;
    }

    public double getProcessCpu() {
        long totalTime = 0;
        for (long id : threadBean.getAllThreadIds()) {
            totalTime += threadBean.getThreadCpuTime(id);
        }
        long curtime = System.nanoTime();
        long usedTime = totalTime - preUsedTime;
        long totalPassedTime = curtime - preTime;
        preTime = curtime;
        preUsedTime = totalTime;
        return (((double) usedTime) / totalPassedTime / osMxBean.getAvailableProcessors()) * 100;
    }
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    long bac = 1000000;
                    bac = bac >> 1;
                }
            }).start();;
        }
        while (true) {
            int memory=0;
            cpu c =new cpu();
            MonitorInfoBean monitorInfo = c.getMonitorInfoBean();
            Thread.sleep(2000);
            System.out.println(CPUMonitorCalc.getInstance().getProcessCpu());
            System.out.println("总的物理内存=" + monitorInfo.getTotalMemorySize() + "kb");
            System.out.println("剩余的物理内存=" + monitorInfo.getFreeMemory() + "kb");
            System.out.println("已使用的物理内存=" + monitorInfo.getUsedMemory() + "kb");
            memory= 100*(int)monitorInfo.getUsedMemory()/8271096;
            System.out.println(memory);
        }

    }

}
