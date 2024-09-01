package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.entity.Discount;
import com.enigma.challengespringrestful.entity.Discount;
import com.enigma.challengespringrestful.repository.DiscountRepository;
import com.enigma.challengespringrestful.dao.DiscountDAO;
import com.enigma.challengespringrestful.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService implements DiscountDAO {

    private final DiscountRepository discountRepository;
    private final ValidationUtils validationUtils;

    /**
     * @param discountDTORequest
     * @return
     */
    @Override
    public Discount create(DiscountDTORequest discountDTORequest) {
        validationUtils.validate(discountDTORequest);
        Discount discount = Discount.builder()
                .id(discountDTORequest.getId())
                .build();
        return discountRepository.saveAndFlush(discount);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Discount findById(String id) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalDiscount.get();
    }

    /**
     * @return
     */
    @Override
    public List<Discount> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return discountRepository.findAllByNameLike("%" + name + "%");
        }
        return discountRepository.findAll();
    }

    /**
     * @param discountDTORequest
     * @return
     */
    @Override
    public Discount update(DiscountDTORequest discountDTORequest) {
        validationUtils.validate(discountDTORequest);
        Discount existingDiscount = findById(discountDTORequest.getId());
        existingDiscount.setName(discountDTORequest.getName());
        return discountRepository.saveAndFlush(existingDiscount);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Discount currentlySelectedDiscountID = findById(id);
        discountRepository.delete(currentlySelectedDiscountID);
    }

}
