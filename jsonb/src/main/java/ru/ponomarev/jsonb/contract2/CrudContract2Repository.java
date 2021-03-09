package ru.ponomarev.jsonb.contract2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface CrudContract2Repository extends JpaRepository<Contract, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from Contract where id = :id")
    Contract findOneForUpdate(@Param("id") String id);
}
