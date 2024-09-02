package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.OutletDAO;
import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.entity.Outlet;
import com.enigma.challengespringrestful.repository.OutletRepository;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutletService implements OutletDAO {

    private final OutletRepository outletRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public Outlet create(OutletDTORequest outletDTORequest) {
        validateOutletDTORequest(outletDTORequest);

        Outlet outlet = Outlet.builder().id(outletDTORequest.getId()).name(outletDTORequest.getName()).build();
        return outletRepository.saveAndFlush(outlet);
    }

    @Override
    public Outlet findById(String id) {
        Optional<Outlet> optionalOutlet = outletRepository.findById(id);
        if (optionalOutlet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalOutlet.get();
    }

    @Override
    public List<Outlet> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return outletRepository.findAllByNameLike("%" + name + "%");
        }
        return outletRepository.findAll();
    }

    @Override
    public Outlet update(OutletDTORequest outletDTORequest) {
        validateOutletDTORequest(outletDTORequest);

        Outlet existingOutlet = findById(outletDTORequest.getId());
        existingOutlet.setName(outletDTORequest.getName());
        return outletRepository.saveAndFlush(existingOutlet);
    }

    @Override
    public void deleteById(String id) {
        Outlet currentlySelectedOutlet = findById(id);
        outletRepository.delete(currentlySelectedOutlet);
    }

    private void validateOutletDTORequest(OutletDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getId())) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (ValidationUtils.isNotEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

}
