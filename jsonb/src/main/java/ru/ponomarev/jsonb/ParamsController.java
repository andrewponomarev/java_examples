package ru.ponomarev.jsonb;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ponomarev.jsonb.contract2.Contract;
import ru.ponomarev.jsonb.contract2.Contract2Repository;
import ru.ponomarev.jsonb.contract2.ContractParamObjectExample;
import ru.ponomarev.jsonb.contract2.ContractParamType;

import java.time.LocalDate;
import java.util.List;

@RestController()
@RequiredArgsConstructor
public class ParamsController {

    private final Contract2Repository repository;

    String id = "123";

    @PostMapping("/set_string")
    @Transactional
    public ResponseEntity<?> setString(@RequestParam String s) {
        Contract contract = repository.get(id);
        contract.setNamedStringParam(s);
        repository.saveAndFlush(contract);
        return ResponseEntity.ok("");
    }

    @PostMapping("/set_long_list")
    @Transactional
    public ResponseEntity<?> setLongList() {
        Contract contract = repository.get(id);
        List<Long> longList = List.of(11L, 12L, 13L);
        contract.setCollectionLongNamedParam(longList);
        repository.saveAndFlush(contract);
        return ResponseEntity.ok("");
    }

    @PostMapping("/set_complex_object")
    @Transactional
    public ResponseEntity<?> setComplex() {
        ContractParamObjectExample example = new ContractParamObjectExample();
        example.setB(true);
        example.setD(1.2);
        example.setL(12L);
        example.setLd(LocalDate.now());
        example.setS("123");
        example.setType(ContractParamType.LONG);

        ContractParamObjectExample simpleExample = new ContractParamObjectExample();
        simpleExample.setB(false);
//
        example.setCol(List.of(simpleExample));
        example.setCpoe(simpleExample);

        Contract contract = repository.get(id);
        contract.setContractParamObjectExample(example);
        repository.saveAndFlush(contract);

        return ResponseEntity.ok("");
    }

}
