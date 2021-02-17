package name.aaponomarev.restwithevents.controller;

import lombok.RequiredArgsConstructor;
import name.aaponomarev.restwithevents.domain.*;
import name.aaponomarev.restwithevents.service.ContractService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public Contract createContract(@RequestBody Contract contract) {
        return contractService.create(contract);
    }

    @PostMapping("/{id}/details")
    public Contract createDetails(@PathVariable Long id, @RequestBody Details details) {
        return contractService.saveDetails(id, details);
    }

    @PatchMapping("/{id}")
    public Contract partiallyUpdateContract(@PathVariable Long id, @Validated @RequestBody ContractPart contract) {
        contract.setId(id);
        return contractService.update(contract);
    }

    @GetMapping("/dozer")
    public void dozerExample() {
        DozerSource dozerSource = new DozerSource();
        List<Foo> fooList = new ArrayList<>();
        fooList.add(new Foo());
        dozerSource.setFooCollection(fooList);
        Mapper mapper = new DozerBeanMapper();
        DozerSource dozerDist = mapper.map(dozerSource, DozerSource.class);
        System.out.println("ok");
    }


    @GetMapping
    public List<Contract> list() {
        return contractService.list();
    }
}
