package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.entity.Outlet;

import java.util.List;

public interface OutletDAO {
    Outlet create(OutletDTORequest outlet);

    Outlet findById(String id);

    List<Outlet> findAll(String name);

    Outlet update(OutletDTORequest outlet);

    void deleteById(String id);
}
