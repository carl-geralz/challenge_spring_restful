package com.enigma.challengespringrestful.repository;

import com.enigma.challengespringrestful.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutletRepository extends JpaRepository<Outlet, String> {
    List<Outlet> findAllByNameLike(String name);
}