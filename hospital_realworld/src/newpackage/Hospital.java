package newpackage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.concurrent.TimeUnit.*;

public class Hospital {
	public static final int NUM_WAITING_ROOM_BEDS = 3;
	public static final long HOS_RUNTIME_MILLIS = SECONDS.toMillis(10);
	private final static AtomicBoolean hospitalOpen = new AtomicBoolean();
	private final static AtomicInteger totalTreats = new AtomicInteger();
	private final static AtomicInteger lostPatient = new AtomicInteger();
	private final BlockingQueue<Object> waitingRoom = new LinkedBlockingQueue<>(NUM_WAITING_ROOM_BEDS);

	public static void main(String[] args) throws InterruptedException {
		Hospital hos = new Hospital();

		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		Runnable PatientGenerator = new PatientGenerator(hos);
		Runnable doctor = new Doctor(hos);
		Runnable progressTracker = new ProgressTracker(hos);
		
		hos.open();
		
		executor.execute(progressTracker);
		executor.execute(doctor);
		executor.execute(PatientGenerator);
		executor.shutdown();
		
		Thread.sleep(HOS_RUNTIME_MILLIS);
		
		hos.close();
	}

	private void close() {
		hospitalOpen.set(false);
	}

	private void open() {
		hospitalOpen.set(true);
	}

	public boolean isOpen() {
		return hospitalOpen.get();
	}

	public boolean seatPatientInWaitingRoom(Object patient) {
		boolean patientSeated = waitingRoom.offer(patient);
		if(!patientSeated) {
			lostPatient.incrementAndGet();
		}
		return patientSeated;
	}
	
	public Object napUntilPatientArrives() throws InterruptedException {
		return waitingRoom.take();
	}

	public void recordTreatHim() {
		totalTreats.incrementAndGet();
	}

	public Object lostPatient() {
		return lostPatient.get();
	}

	public Object treatHim() {
		return totalTreats.get();
	}

}
