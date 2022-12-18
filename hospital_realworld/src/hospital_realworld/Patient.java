package hospital_realworld;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Patient implements Runnable {

    private static final AtomicInteger idGenerator = new AtomicInteger();

    private final int id;

    private final WaitingRoom waitingRoom;

    private final SynchronousQueue<Boolean> synchronousQueue;

    private volatile boolean treated;

    public Patient(WaitingRoom waitingRoom) {
        this.id = idGenerator.incrementAndGet();
        this.waitingRoom = waitingRoom;
        this.synchronousQueue = new SynchronousQueue<>();
    }

    @Override
    public void run() {
        try {
            waitingRoom.takeASeat(this);

            System.out.println("patient " + this + " wait to be called and treated");
            waitToBeCalledAndTreated();

            treated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callAndTreat() throws InterruptedException {
        synchronousQueue.put(true);
    }

    public void waitToBeCalledAndTreated() throws InterruptedException {
        synchronousQueue.take();
    }

    public boolean isTreated() {
        return treated;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
