/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

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
public class JavaApplication5 {

 
    public static void main(String[] args) throws InterruptedException {
        WaitingRoom waitingRoom = new WaitingRoom(10);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.submit(new Barber(waitingRoom));
        executorService.submit(new Barber(waitingRoom));
        executorService.submit(new Barber(waitingRoom));

        List<Customer> customers = Stream.generate(() -> new Customer(waitingRoom))
                                         .limit(100)
                                         .peek(executorService::submit)
                                         .collect(toList());

        while (!customers.stream().allMatch(Customer::isShaved)) {
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("all customers have been shaved");
        executorService.shutdownNow();
        executorService.awaitTermination(1, MINUTES);
    }

}

    
    

