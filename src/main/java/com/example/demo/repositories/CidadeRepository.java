package com.example.demo.repositories;

import com.example.demo.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Cidade obj where obj.estado.id =:estadoID order by obj.nome")
    public List<Cidade> findCidade(@Param("estadoID") Integer estado_ID);
}
