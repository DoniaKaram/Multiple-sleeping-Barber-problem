/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital_realworld;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author ADMIN
 */
public class hospital_realworld {

 
    public static void main(String[] args) throws InterruptedException {
        WaitingRoom waitingRoom = new WaitingRoom(10);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.submit(new Doctor(waitingRoom));
        executorService.submit(new Doctor(waitingRoom));
        executorService.submit(new Doctor(waitingRoom));

        List<Patient> patients = Stream.generate(() -> new Patient(waitingRoom))
                                         .limit(100)
                                         .peek(executorService::submit)
                                         .collect(toList());

        while (!patients.stream().allMatch(Patient::isTreated)) {
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("all patients have been  treated");
        executorService.shutdownNow();
        executorService.awaitTermination(1, MINUTES);
    }

}