package ru.ponomarev.jsonb.contract2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(Contract2Repository.class)
public class Contract2RepositoryTest {

    @Autowired
    Contract2Repository repository;

    String id;

    @BeforeEach
    public void initContract() {
        Contract2 contract2 = new Contract2();
        contract2 = repository.save(contract2);
        id = contract2.getId();
    }

    @Test
    public void longContractParamTest() {
        Contract2 contract2 = repository.get(id);
        contract2.setLongNamedParam(12L);
        repository.saveAndFlush(contract2);
        contract2 = repository.get(id);
        assertEquals(12L, contract2.getLongNamedParam());
    }

    @Test
    public void booleanContractParamTest() {
        Contract2 contract2 = repository.get(id);
        contract2.setBooleanNamedParam(true);
        repository.saveAndFlush(contract2);
        contract2 = repository.get(id);
        assertTrue(contract2.getBooleanNamedParam());
    }

    @Test
    public void doubleContractParamTest() {
        Contract2 contract2 = repository.get(id);
        contract2.setDoubleNamedParam(1.0);
        repository.saveAndFlush(contract2);
        contract2 = repository.get(id);
        assertEquals(1.0, contract2.getDoubleNamedParam());
    }

    @Test
    public void dateContractParamTest() {
        Contract2 contract2 = repository.get(id);
        LocalDate date = LocalDate.now();
        contract2.setLocalDateNamedParam(date);
        repository.saveAndFlush(contract2);
        contract2 = repository.get(id);
        assertEquals(date, contract2.getLocalDateNamedParam());
    }

    @Test
    public void stringContractParamTest() {
        Contract2 contract2 = repository.get(id);
        String s = "123";
        contract2.setNamedStringParam(s);
        repository.saveAndFlush(contract2);
        contract2 = repository.get(id);
        assertEquals(s, contract2.getNamedStringParam());
    }

    @Test
    public void enumContractParamTest() {
        Contract2 contract2 = repository.get(id);
        ContractParamType type = ContractParamType.LONG;
        contract2.setEnumParam(type);
        repository.saveAndFlush(contract2);
        contract2 = repository.get(id);
        assertEquals(type, contract2.getEnumParam());
    }

    @Test
    public void collectionLongContractParamTest() {
        Contract2 contract2 = repository.get(id);
        List<Long> longList = List.of(11L, 12L, 13L);
        contract2.setCollectionLongNamedParam(longList);
        repository.saveAndFlush(contract2);

        contract2 = repository.get(id);
        assertIterableEquals(longList, contract2.getCollectionLongNamedParam());
    }

    @Test
    public void collectionDoubleContractParamTest() {
        Contract2 contract2 = repository.get(id);
        List<Double> doubleList = List.of(11.0, 12.0, 13.0);
        contract2.setCollectionDoubleNamedParam(doubleList);
        repository.saveAndFlush(contract2);

        contract2 = repository.get(id);
        assertIterableEquals(doubleList, contract2.getCollectionDoubleNamedParam());
    }

    @Test
    public void collectionBooleanContractParamTest() {
        Contract2 contract2 = repository.get(id);
        List<Boolean> booleanList = List.of(true, false, true);
        contract2.setCollectionBooleanNamedParam(booleanList);
        repository.saveAndFlush(contract2);

        contract2 = repository.get(id);
        assertIterableEquals(booleanList, contract2.getCollectionBooleanNamedParam());
    }

    @Test
    public void collectionDateContractParamTest() {
        Contract2 contract2 = repository.get(id);
        List<LocalDate> dateList = List.of(LocalDate.now(), LocalDate.now(), LocalDate.now());
        contract2.setCollectionDateNamedParam(dateList);
        repository.saveAndFlush(contract2);

        contract2 = repository.get(id);
        assertIterableEquals(dateList, contract2.getCollectionDateNamedParam());
    }

    @Test
    public void collectionStringContractParamTest() {
        Contract2 contract2 = repository.get(id);
        List<String> stringList = List.of("11", "12", "13");
        contract2.setCollectionStringNamedParam(stringList);
        repository.saveAndFlush(contract2);

        contract2 = repository.get(id);
        assertIterableEquals(stringList, contract2.getCollectionStringNamedParam());
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

        Contract2 contract2 = repository.get(id);
        contract2.setContractParamObjectExample(example);
        repository.saveAndFlush(contract2);

        contract2 = repository.get(id);
        assertEquals(example, contract2.getContractParamObjectExample());
    }




}