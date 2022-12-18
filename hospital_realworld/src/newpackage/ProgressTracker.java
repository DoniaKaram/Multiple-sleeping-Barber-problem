package newpackage;

public class ProgressTracker implements Runnable {

	private final Hospital hos;

	public ProgressTracker(Hospital hos) {
		this.hos = hos;
	}

	@Override
	public void run() {
		while (hos.isOpen()) {
			try {
				Thread.sleep(100);
				printProgress();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		printProgress();
		System.out.println();
	}

	private void printProgress() {
		System.out.printf("The shop served %s customers but turned away %s.\r", hos.treatHim(), hos.lostPatient());
	}
}
