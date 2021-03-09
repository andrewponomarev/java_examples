package ru.ponomarev.jsonb.contract2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(Contract2Repository.class)
public class ContractRepositoryTest {

    @Autowired
    Contract2Repository repository;

    String id;

    @BeforeEach
    public void initContract() {
        Contract contract = new Contract();
        contract = repository.save(contract);
        id = contract.getId();
    }

    @Test
    public void longContractParamTest() {
        Contract contract = repository.get(id);
        contract.setLongNamedParam(12L);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertEquals(12L, contract.getLongNamedParam());
    }

    @Test
    public void booleanContractParamTest() {
        Contract contract = repository.get(id);
        contract.setBooleanNamedParam(true);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertTrue(contract.getBooleanNamedParam());
    }

    @Test
    public void doubleContractParamTest() {
        Contract contract = repository.get(id);
        contract.setDoubleNamedParam(1.0);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertEquals(1.0, contract.getDoubleNamedParam());
    }

    @Test
    public void dateContractParamTest() {
        Contract contract = repository.get(id);
        LocalDate date = LocalDate.now();
        contract.setLocalDateNamedParam(date);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertEquals(date, contract.getLocalDateNamedParam());
    }

    @Test
    public void stringContractParamTest() {
        Contract contract = repository.get(id);
        String s = "123";
        contract.setNamedStringParam(s);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertEquals(s, contract.getNamedStringParam());
    }

    @Test
    public void enumContractParamTest() {
        Contract contract = repository.get(id);
        ContractParamType type = ContractParamType.LONG;
        contract.setEnumParam(type);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertEquals(type, contract.getEnumParam());
    }

    @Test
    public void collectionLongContractParamTest() {
        Contract contract = repository.get(id);
        List<Long> longList = List.of(11L, 12L, 13L);
        contract.setCollectionLongNamedParam(longList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertIterableEquals(longList, contract.getCollectionLongNamedParam());
    }

    @Test
    public void collectionDoubleContractParamTest() {
        Contract contract = repository.get(id);
        List<Double> doubleList = List.of(11.0, 12.0, 13.0);
        contract.setCollectionDoubleNamedParam(doubleList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertIterableEquals(doubleList, contract.getCollectionDoubleNamedParam());
    }

    @Test
    public void collectionBooleanContractParamTest() {
        Contract contract = repository.get(id);
        List<Boolean> booleanList = List.of(true, false, true);
        contract.setCollectionBooleanNamedParam(booleanList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertIterableEquals(booleanList, contract.getCollectionBooleanNamedParam());
    }

    @Test
    public void collectionDateContractParamTest() {
        Contract contract = repository.get(id);
        List<LocalDate> dateList = List.of(LocalDate.now(), LocalDate.now(), LocalDate.now());
        contract.setCollectionDateNamedParam(dateList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertIterableEquals(dateList, contract.getCollectionDateNamedParam());
    }

    @Test
    public void collectionStringContractParamTest() {
        Contract contract = repository.get(id);
        List<String> stringList = List.of("11", "12", "13");
        contract.setCollectionStringNamedParam(stringList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertIterableEquals(stringList, contract.getCollectionStringNamedParam());
    }

    @Test
    public void customObjectContractParamTest() {
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

        contract = repository.get(id);
        assertEquals(example, contract.getContractParamObjectExample());
    }


    @Test
    public void bigDecimalContract() {
        Contract contract = repository.get(id);
        BigDecimal expected = BigDecimal.valueOf(1.213312);
        contract.setBigDecimalParam(expected);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertEquals(expected, contract.getBigDecimalParam());
    }

    @Test
    public void longContractParamNullTest() {
        Contract contract = repository.get(id);
        contract.setLongNamedParam(null);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getLongNamedParam());
    }

    @Test
    public void booleanContractParamNullTest() {
        Contract contract = repository.get(id);
        contract.setBooleanNamedParam(null);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getBooleanNamedParam());
    }

    @Test
    public void doubleContractParamNullTest() {
        Contract contract = repository.get(id);
        contract.setDoubleNamedParam(null);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getDoubleNamedParam());
    }

    @Test
    public void dateContractParamNullTest() {
        Contract contract = repository.get(id);
        LocalDate date = null;
        contract.setLocalDateNamedParam(date);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getLocalDateNamedParam());
    }

    @Test
    public void stringContractParamNullTest() {
        Contract contract = repository.get(id);
        String s = null;
        contract.setNamedStringParam(s);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getNamedStringParam());
    }

    @Test
    public void enumContractParamNullTest() {
        Contract contract = repository.get(id);
        ContractParamType type = null;
        contract.setEnumParam(type);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getEnumParam());
    }

    @Test
    public void collectionLongContractParamNullTest() {
        Contract contract = repository.get(id);
        List<Long> longList = null;
        contract.setCollectionLongNamedParam(longList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertNull(contract.getCollectionLongNamedParam());
    }

    @Test
    public void collectionDoubleContractParamNullTest() {
        Contract contract = repository.get(id);
        List<Double> doubleList = null;
        contract.setCollectionDoubleNamedParam(doubleList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertNull(contract.getCollectionDoubleNamedParam());
    }

    @Test
    public void collectionBooleanContractParamNullTest() {
        Contract contract = repository.get(id);
        List<Boolean> booleanList = null;
        contract.setCollectionBooleanNamedParam(booleanList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertNull(contract.getCollectionBooleanNamedParam());
    }

    @Test
    public void collectionDateContractParamNullTest() {
        Contract contract = repository.get(id);
        List<LocalDate> dateList = null;
        contract.setCollectionDateNamedParam(dateList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertNull(contract.getCollectionDateNamedParam());
    }

    @Test
    public void collectionStringContractParamNullTest() {
        Contract contract = repository.get(id);
        List<String> stringList = null;
        contract.setCollectionStringNamedParam(stringList);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertNull(contract.getCollectionStringNamedParam());
    }

    @Test
    public void customObjectContractParamNullTest() {
        ContractParamObjectExample example = new ContractParamObjectExample();
        example.setB(true);
        example.setD(null);
        example.setL(12L);
        example.setLd(null);
        example.setS("123");
        example.setType(ContractParamType.LONG);

        ContractParamObjectExample simpleExample = new ContractParamObjectExample();
        simpleExample.setB(null);
//
        example.setCol(List.of(simpleExample));
        example.setCpoe(simpleExample);

        Contract contract = repository.get(id);
        contract.setContractParamObjectExample(example);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertEquals(example, contract.getContractParamObjectExample());
    }

    @Test
    public void customObjectContractParamNull2Test() {
        ContractParamObjectExample example = null;
        Contract contract = repository.get(id);
        contract.setContractParamObjectExample(example);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        assertNull(contract.getContractParamObjectExample());
    }


    @Test
    public void bigDecimalContractNullTest() {
        Contract contract = repository.get(id);
        BigDecimal expected = null;
        contract.setBigDecimalParam(expected);
        repository.saveAndFlush(contract);
        contract = repository.get(id);
        assertNull(contract.getBigDecimalParam());
    }


    @Test
    public void customObjectContractParamNull3Test() {
        ContractParamObjectExample example = new ContractParamObjectExample();
        example.setB(true);
        example.setL(12L);
        example.setS("123");
        example.setType(ContractParamType.LONG);

        ContractParamObjectExample simpleExample = new ContractParamObjectExample();
        simpleExample.setB(false);

        example.setCol(List.of(simpleExample));
        example.setCpoe(simpleExample);

        Contract contract = repository.get(id);
        contract.setContractParamObjectExample(example);
        repository.saveAndFlush(contract);

        contract = repository.get(id);
        ContractParamObjectExample actual = contract.getContractParamObjectExample();
        example = contract.getContractParamObjectExample();
        example.setCol(null);
        example.setS(null);

        contract.setContractParamObjectExample(example);
        repository.saveAndFlush(contract);

        actual = contract.getContractParamObjectExample();
        assertEquals(example, actual);
    }

}