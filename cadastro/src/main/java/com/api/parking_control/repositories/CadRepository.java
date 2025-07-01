package com.api.parking_control.repositories;

import com.api.parking_control.models.CadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadRepository extends JpaRepository<CadModel, Integer> {
    Optional<CadModel> findByEmailUsuario(String emailUsuario);
}