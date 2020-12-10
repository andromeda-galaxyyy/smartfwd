package apps.smartfwd.src.main.java.task;

import apps.smartfwd.src.main.java.constants.App;

import java.util.concurrent.ExecutorService;

public abstract class Task implements Runnable {
    ExecutorService service= App.getInstance().getPool();
    Thread worker;
    public void start(){
        if(null==service){
            worker=new Thread(this);
            worker.start();
        }else{
            service.submit(this);
        }
    }
}