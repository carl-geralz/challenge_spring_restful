package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.DiscountDAO;
import com.enigma.challengespringrestful.dto.request.DiscountDTORequest;
import com.enigma.challengespringrestful.entity.Discount;
import com.enigma.challengespringrestful.repository.DiscountRepository;
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
public class DiscountService implements DiscountDAO {

    private final DiscountRepository discountRepository;
    @Getter
    private final ValidationUtils validationUtils;

    @Override
    public Discount create(DiscountDTORequest discountDTORequest) {
        validateDiscountDTORequest(discountDTORequest);

        Discount discount = Discount.builder().id(discountDTORequest.getId()).name(discountDTORequest.getName()).startDate(discountDTORequest.getStartDate()).endDate(discountDTORequest.getEndDate()).isActive(discountDTORequest.getIsActive()).discountPercentage(discountDTORequest.getDiscountPercentage()).build();
        return discountRepository.saveAndFlush(discount);
    }

    @Override
    public Discount findById(String id) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND);
        }
        return optionalDiscount.get();
    }

    @Override
    public List<Discount> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return discountRepository.findAllByNameLike("%" + name + "%");
        }
        return discountRepository.findAll();
    }

    @Override
    public Discount update(DiscountDTORequest discountDTORequest) {
        validateDiscountDTORequest(discountDTORequest);

        Discount existingDiscount = findById(discountDTORequest.getId());
        existingDiscount.setName(discountDTORequest.getName());
        existingDiscount.setStartDate(discountDTORequest.getStartDate());
        existingDiscount.setEndDate(discountDTORequest.getEndDate());
        existingDiscount.setIsActive(discountDTORequest.getIsActive());
        existingDiscount.setDiscountPercentage(discountDTORequest.getDiscountPercentage());
        return discountRepository.saveAndFlush(existingDiscount);
    }

    @Override
    public void deleteById(String id) {
        Discount currentlySelectedDiscount = findById(id);
        discountRepository.delete(currentlySelectedDiscount);
    }

    private void validateDiscountDTORequest(DiscountDTORequest request) {
        if (ValidationUtils.isNotEmpty(request.getId())) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (ValidationUtils.isNotEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
