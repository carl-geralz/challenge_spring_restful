package com.enigma.challengespringrestful.integrity;

import com.enigma.challengespringrestful.entity.Customer;
import com.enigma.challengespringrestful.entity.CustomerAcquisition;
import com.enigma.challengespringrestful.entity.CustomerMembership;
import com.enigma.challengespringrestful.entity.Transaction;
import com.enigma.challengespringrestful.repository.CustomerAcquisitionRepository;
import com.enigma.challengespringrestful.repository.CustomerMembershipRepository;
import com.enigma.challengespringrestful.repository.CustomerRepository;
import com.enigma.challengespringrestful.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReferentialIntegrityTestService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerAcquisitionRepository customerAcquisitionRepository;
    private final CustomerMembershipRepository customerMembershipRepository;

    public ReferentialIntegrityTestService(
            CustomerRepository customerRepository,
            TransactionRepository transactionRepository,
            CustomerAcquisitionRepository customerAcquisitionRepository,
            CustomerMembershipRepository customerMembershipRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.customerAcquisitionRepository = customerAcquisitionRepository;
        this.customerMembershipRepository = customerMembershipRepository;
    }

    public boolean checkReferentialIntegrity(String transactionId, String customerId) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isEmpty()) {
            return false;
        }

        Transaction transaction = transactionOptional.get();
        Customer customer = transaction.getCustomer();
        if (customer == null || !customer.getId().equals(customerId)) {
            return false;
        }

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            return false;
        }

        Customer retrievedCustomer = customerOptional.get();
        CustomerAcquisition customerAcquisition = retrievedCustomer.getCustomerAcquisition();
        if (customerAcquisition == null || !customerAcquisitionRepository.existsById(customerAcquisition.getId())) {
            return false;
        }

        CustomerMembership customerMembership = retrievedCustomer.getCustomerMembership();
        return customerMembership != null && customerMembershipRepository.existsById(customerMembership.getId());
    }
}
