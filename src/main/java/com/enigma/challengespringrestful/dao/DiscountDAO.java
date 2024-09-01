package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.entity.*;

import java.util.List;

public interface DiscountDAO {
    Discount create(DiscountDTORequest discount);
    Discount findById(String id);
    List<Discount> findAll(String name);
    Discount update(DiscountDTORequest discount);
    void deleteById(String id);
}
