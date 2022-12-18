package newpackage;

public class Doctor implements Runnable {
	private static final int TREAT_TIME_MILLIS = 20;
	private final Hospital hos;

	public Doctor(Hospital hos) {
		this.hos = hos;
	}

	@Override
	public void run() {
		while(hos.isOpen()) {
			try {
				Object patient = hos.napUntilPatientArrives();
				treatHim(patient);
				hos.recordTreatHim();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	private void treatHim(Object patient) throws InterruptedException {
		Thread.sleep(TREAT_TIME_MILLIS);
	}

}
