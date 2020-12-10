package apps.smartfwd.src.main.java.org.chandler.task;


import apps.smartfwd.src.main.java.org.chandler.constants.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PeriodicalTask implements Runnable{
    private final Logger logger= LoggerFactory.getLogger(getClass().getName());
    public interface Worker{
        void doWork();
    }
    ScheduledExecutorService service= App.getInstance().getScheduledPool();
    AtomicBoolean isRunning=new AtomicBoolean(false);
    //seconds
    int interval=5;
    int delay=10;
    ScheduledFuture<?> handle;
    Worker worker;

    public int getInterval() {
        return interval;
    }

    public PeriodicalTask setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public PeriodicalTask setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public void run(){
        try{
            worker.doWork();
        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
//            logger.info(e.getMessage());
//            e.printStackTrace();
        }
    }
    public void start(){
        if(!isRunning.get()){
            handle=service.scheduleAtFixedRate(this,delay, this.interval,TimeUnit.SECONDS);
        }
    }
    public void stop(){
        if(isRunning.get()){
            handle.cancel(true);
        }
        isRunning.set(false);
    }
    public boolean isRunning(){
        return isRunning.get();
    }
}

