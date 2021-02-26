package ru.ponomarev.jsonb.contract;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ponomarev.jsonb.GeneralContract;
import ru.ponomarev.jsonb.contract2.Contract2Service;

import java.util.concurrent.*;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Disabled
public class ContractServiceTest {

   // String id = "c515ee2e-6b68-4371-b6dd-e2585d332113";
    String id = "e18b5b83-e0dc-4bac-a75d-32d28fff5e49";

    @Autowired
//    private ContractService contractService;
    private Contract2Service contractService;

    @Test
    public void createContract() {
        contractService.createContractWithStringParam("sss");
        assertEquals(true, true);
    }

    @Test
    public void updateContractParam() {
        updateStringParam("0034840d-fee6-4fa1-b112-674c0dab59eb");
        assertEquals(true, true);
    }


    @Test
    public void concurrentlyUpdateStringParam() throws Exception {
        final int paramActionsNum = 1;
        final int valueActionsNum = 1;
        final ExecutorService executorService = Executors.newFixedThreadPool(paramActionsNum + valueActionsNum);
        final CompletionService<GeneralContract> completionService1 = new ExecutorCompletionService<>(executorService);
        //final CompletionService<GeneralContract> completionService2 = new ExecutorCompletionService<>(executorService);
        final CountDownLatch startCount = new CountDownLatch(1);
        final CountDownLatch finishCount = new CountDownLatch(paramActionsNum + valueActionsNum);

        long start = System.currentTimeMillis();

        rangeClosed(1, paramActionsNum).forEach(v -> {
            completionService1.submit(() -> {
                try {
                    startCount.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                Contract contractResponse = null;
                try {
                    //updateStringParam();
                    updateContractValue();
                } catch (Exception e) {
                    fail(e.toString());
                }
                finishCount.countDown();
                System.out.println("finishCount.countDown()");
                return contractResponse;
            });
        });

        startCount.countDown();

        rangeClosed(1, valueActionsNum).forEach(v -> {
            completionService1.submit(() -> {
                try {
                    startCount.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                Contract contractResponse = null;
                try {
                    updateStringParam();
                    //updateContractValue();
                } catch (Exception e) {
                    fail(e.toString());
                }
                finishCount.countDown();
                System.out.println("finishCount.countDown()");
                return contractResponse;
            });
        });

        finishCount.await();

        long finish = System.currentTimeMillis();

        System.out.println("Time elapsed is " + (finish - start) + " ms");
    }

    private void updateStringParam() {
        String newValue = RandomStringUtils.random(10);
        contractService.updateStringParam(id, newValue);
    }

    private void updateStringParam(String id) {
        String newValue = RandomStringUtils.random(10);
        contractService.updateStringParam(id, newValue);
    }

    private void updateContractValue() {
        String newValue = RandomStringUtils.random(10);
        contractService.longUpdateContractValue(id, newValue);
    }

}