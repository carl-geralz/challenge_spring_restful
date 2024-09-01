package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.entity.Outlet;
import com.enigma.challengespringrestful.entity.Outlet;
import com.enigma.challengespringrestful.repository.OutletRepository;
import com.enigma.challengespringrestful.dao.OutletDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
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
    private final ValidationUtils validationUtils;

    /**
     * @param outletDTORequest
     * @return
     */
    @Override
    public Outlet create(OutletDTORequest outletDTORequest) {
        validationUtils.validate(outletDTORequest);
        Outlet outlet = Outlet.builder()
                .id(outletDTORequest.getId())
                .build();
        return outletRepository.saveAndFlush(outlet);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Outlet findById(String id) {
        Optional<Outlet> optionalOutlet = outletRepository.findById(id);
        if (optionalOutlet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalOutlet.get();
    }

    /**
     * @return
     */
    @Override
    public List<Outlet> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return outletRepository.findAllByNameLike("%" + name + "%");
        }
        return outletRepository.findAll();
    }

    /**
     * @param outletDTORequest
     * @return
     */
    @Override
    public Outlet update(OutletDTORequest outletDTORequest) {
        validationUtils.validate(outletDTORequest);
        Outlet existingOutlet = findById(outletDTORequest.getId());
        existingOutlet.setName(outletDTORequest.getName());
        return outletRepository.saveAndFlush(existingOutlet);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Outlet currentlySelectedOutletID = findById(id);
        outletRepository.delete(currentlySelectedOutletID);
    }

}
