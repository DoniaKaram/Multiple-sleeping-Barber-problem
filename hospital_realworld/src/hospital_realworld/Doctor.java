package hospital_realworld;

public class Doctor implements Runnable {

    private final WaitingRoom waitingRoom;

    public Doctor(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Patient patient= waitingRoom.nextPatient();
                //String patient = null;

                System.out.println("doctor call patient and treat him " + patient);
                patient.callAndTreat();
            }

        } catch (InterruptedException e) {
            System.out.println("doctor has finished his job");
        }
    }
}
