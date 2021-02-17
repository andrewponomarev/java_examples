package ru.ponomarev.jsonb.contract2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ponomarev.jsonb.contract.Contract;

import javax.persistence.LockModeType;

public interface CrudContract2Repository extends JpaRepository<Contract2, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from Contract2 where id = :id")
    Contract2 findOneForUpdate(@Param("id") String id);
}
