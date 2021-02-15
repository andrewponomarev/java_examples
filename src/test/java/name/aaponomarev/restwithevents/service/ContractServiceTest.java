package name.aaponomarev.restwithevents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.aaponomarev.restwithevents.domain.Contract;

import name.aaponomarev.restwithevents.domain.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ContractServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Contract> json;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContractService contractService;


    //@Test(timeout = 600000)
    @Test
    public void concurrencyOk() throws Exception {
        final int actionsNum = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(actionsNum);
        final ExecutorService executorService2 = Executors.newFixedThreadPool(actionsNum);
        final CompletionService<Contract> completionService = new ExecutorCompletionService<Contract>(executorService);
        final CountDownLatch startActions = new CountDownLatch(1);
        final CountDownLatch createCompleted = new CountDownLatch(actionsNum);
        final CountDownLatch createDetailsCompleted = new CountDownLatch(actionsNum);

        rangeClosed(1, actionsNum).forEach(value -> {
            Contract createRequest = new Contract();
            createRequest.setMain("Main");
            Future<Contract> futureCreateContractResponse =
                    submitActionFixture(createRequest, "/contracts", startActions, createCompleted, completionService);
        });

        startActions.countDown();

        rangeClosed(1, actionsNum).forEach(value -> {
            try {
                System.out.println("trying to take from Completion service");
                Future<Contract> result = completionService.take();
                System.out.println("result for a task availble in queue.Trying to get()");
                Contract createDetailsRequest = new Contract();
                createDetailsRequest.setDetails("Details");
                executorService2.submit(() -> {
                    try {
                        Thread.sleep((long)(Math.random() * 10000));
                        actionFixture(createDetailsRequest, "/contracts/" + result.get().getId() + "/details");
                    } catch (Exception e) {
                        fail(e.toString());
                    }
                    createDetailsCompleted.countDown();
                });
            } catch (InterruptedException e) {
                // Something went wrong with a task submitted
                System.out.println("Error Interrupted exception");
                e.printStackTrace();
            }
        });

        // Then
        createCompleted.await();
        createDetailsCompleted.await();

        int result = 0;
        while (!processIsCompleted()) {
            Thread.sleep(1000);
            result++;
        }
        Thread.sleep(20000);
        System.out.println("Num of double requests = " + numOfDoubleRequests());
        System.out.println("RESULT = " + (result -20));

//        List<Contract> contractList = contractService.list();
//        for (Contract contract : contractList) {
//            assertEquals(Status.FINAL, contract.getStatus());
//            assertEquals("VICTORY", contract.getMain());
//            assertEquals("Details", contract.getDetails());
//        }
        //20 - 30 ок
        //40 - 60 ок
    }

    private int numOfDoubleRequests() {
        List<Contract> contractList = contractService.list();
        int num = 0;
        for (Contract contract : contractList) {
            if (contract.getNum() > 1) {
                num++;
            }
        }
        return num;
    }

    private boolean processIsCompleted() {
        List<Contract> contractList = contractService.list();
        for (Contract contract : contractList) {
            if (!contractIsCompleted(contract)) {
                return false;
            }
        }
        return true;
    }

    private boolean contractIsCompleted(Contract contract) {
        return contract.getNum() > 0 &&
                contract.getDetails().equals("Details") &&
                contract.getStatus().equals(Status.FINAL);
    }

    private Future<Contract> submitActionFixture(Contract contract, String uri,
                                       CountDownLatch startActions, CountDownLatch actionsCompleted,
                                       CompletionService<Contract> completionService) {
        return completionService.submit(() -> {
            try {
                startActions.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Contract contractResponse = null;
            try {
                contractResponse = actionFixture(contract, uri);
            } catch (Exception e) {
                fail(e.toString());
            }
            actionsCompleted.countDown();
            System.out.println("actionsCompleted.countDown()");
            return contractResponse;
        });
    }

    private Contract actionFixture(Contract contract, String uri)
            throws Exception{
        ResultActions resultActions = mvc.perform(
                post(new URI(uri))
                        .content(json.write(contract).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString, Contract.class);
    }


}