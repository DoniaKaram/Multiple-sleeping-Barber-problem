package hospital_realworld;

import java.util.concurrent.ArrayBlockingQueue;

public class WaitingRoom {

    private final ArrayBlockingQueue<Patient> waitingPatient;

    public WaitingRoom(int capacity) {
        waitingPatient = new ArrayBlockingQueue<>(capacity);
    }

    public void takeASeat(Patient patient) throws InterruptedException {
        waitingPatient.put(patient);
    }

    public Patient nextPatient() throws InterruptedException {
        return waitingPatient.take();
    }

}
