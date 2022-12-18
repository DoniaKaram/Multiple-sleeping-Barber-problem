package newpackage;

import java.util.concurrent.ThreadLocalRandom;

public class PatientGenerator implements Runnable {
	public static final int ARRIVAL_INTERVAL_OFFSET_MILLIS = 10;
	public static final int ARRIVAL_INTERVAL_RANGE_MILLIS = 20;
	private final Hospital hos;

	public PatientGenerator(Hospital hos) {
		this.hos = hos;
	}

	@Override
	public void run() {
		while (hos.isOpen()) {
			try {
				Thread.sleep(nextRandomInterval());
				hos.seatPatientInWaitingRoom(new Object());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	public int nextRandomInterval() {
		return ThreadLocalRandom.current().nextInt(ARRIVAL_INTERVAL_RANGE_MILLIS) + ARRIVAL_INTERVAL_OFFSET_MILLIS;
	}

}
