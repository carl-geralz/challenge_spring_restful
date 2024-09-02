package com.enigma.challengespringrestful.integrity;

import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.entity.*;
import com.enigma.challengespringrestful.repository.*;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ReferentialIntegrityTest {

    private static final Logger logger = LoggerFactory.getLogger(ReferentialIntegrityTest.class);

    @Mock
    private CustomerRepository customerRepository;

    @Setter
    @Getter
    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerAcquisitionRepository customerAcquisitionRepository;

    @Mock
    private CustomerMembershipRepository customerMembershipRepository;

    @Mock
    private TransactionDetailRepository transactionDetailRepository;

    @Mock
    private OutletRepository outletRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Setter
    @Getter
    @InjectMocks
    private ReferentialIntegrityTestService referentialIntegrityTestService;

    private Customer customer;
    private CustomerAcquisition customerAcquisition;
    private CustomerMembership customerMembership;
    private Transaction transaction;
    private TransactionDetail transactionDetail;
    private Outlet outlet;
    private Product product;
    private Discount discount;
    @Setter
    private UserAccount userAccount;
    @Setter
    private UserRole userRole;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userRole = UserRole.builder().role(ConstantUserRole.ROLE_USER).build();

        userAccount = UserAccount.builder()
                .username("testuser")
                .password("password")
                .isEnable(true)
                .roles(new ArrayList<>(List.of(userRole))) // Initialize roles with a list containing userRole
                .build();

        customerAcquisition = CustomerAcquisition.builder().name("Acquisition Group A").build();

        customerMembership = CustomerMembership.builder()
                .name("Premium Membership")
                .duration(LocalDateTime.now().plusMonths(12))
                .isActive(true)
                .discountPercentage(BigDecimal.valueOf(10.00))
                .monthlyFee(BigDecimal.valueOf(50.00))
                .build();

        customer = Customer.builder()
                .name("John Doe")
                .phoneNumber("123456789")
                .status(true)
                .userAccount(userAccount)
                .customerAcquisition(customerAcquisition)
                .customerMembership(customerMembership)
                .build();

        transaction = Transaction.builder()
                .invoiceDate(LocalDateTime.now())
                .customer(customer)
                .build();

        outlet = Outlet.builder()
                .name("Main Outlet")
                .location("Downtown")
                .build();

        product = Product.builder()
                .name("Sample Product")
                .inventoryQty(BigDecimal.valueOf(100))
                .purchasePrice(BigDecimal.valueOf(20))
                .retailPrice(BigDecimal.valueOf(25))
                .description("A test product")
                .salesPrice(BigDecimal.valueOf(22))
                .build();

        discount = Discount.builder()
                .name("Seasonal Discount")
                .startDate(LocalDateTime.now().minusMonths(1))
                .endDate(LocalDateTime.now().plusMonths(1))
                .isActive(true)
                .discountPercentage(BigDecimal.valueOf(5))
                .products(new ArrayList<>())
                .build();

        transactionDetail = TransactionDetail.builder()
                .invoiceQty(BigDecimal.valueOf(10))
                .vatPercentage(BigDecimal.valueOf(5))
                .invoicePrice(BigDecimal.valueOf(100))
                .transaction(transaction)
                .customerMembership(customerMembership)
                .outlet(outlet)
                .product(product)
                .build();

        product.setDiscount(discount);
        discount.getProducts().add(product);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(customerAcquisitionRepository.findById(customerAcquisition.getId())).thenReturn(Optional.of(customerAcquisition));
        when(customerMembershipRepository.findById(customerMembership.getId())).thenReturn(Optional.of(customerMembership));
        when(transactionDetailRepository.findById(transactionDetail.getId())).thenReturn(Optional.of(transactionDetail));
        when(outletRepository.findById(outlet.getId())).thenReturn(Optional.of(outlet));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));
        when(userAccountRepository.findById(userAccount.getId())).thenReturn(Optional.of(userAccount));
        when(userRoleRepository.findById(userRole.getId())).thenReturn(Optional.of(userRole));
    }


    @Test
    public void testReferentialIntegrity() {
        Transaction retrievedTransaction = transactionRepository.findById(transaction.getId()).orElse(null);
        assertNotNull(retrievedTransaction, "Transaction should not be null");
        assertNotNull(retrievedTransaction.getCustomer(), "Transaction's Customer should not be null");
        assertEquals(retrievedTransaction.getCustomer().getId(), customer.getId(), "Transaction's Customer ID should match the saved Customer ID.");

        logger.debug("Retrieved Transaction: {}", retrievedTransaction);
        logger.debug("Associated Customer: {}", retrievedTransaction.getCustomer());

        Customer retrievedCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertNotNull(retrievedCustomer, "Customer should not be null");
        assertNotNull(retrievedCustomer.getCustomerAcquisition(), "Customer's CustomerAcquisition should not be null");
        assertEquals(retrievedCustomer.getCustomerAcquisition().getId(), customerAcquisition.getId(), "Customer's CustomerAcquisition ID should match the saved CustomerAcquisition ID.");

        logger.debug("Retrieved Customer: {}", retrievedCustomer);
        logger.debug("Associated CustomerAcquisition: {}", retrievedCustomer.getCustomerAcquisition());

        assertNotNull(retrievedCustomer.getCustomerMembership(), "Customer's CustomerMembership should not be null");
        assertEquals(retrievedCustomer.getCustomerMembership().getId(), customerMembership.getId(), "Customer's CustomerMembership ID should match the saved CustomerMembership ID.");

        logger.debug("Associated CustomerMembership: {}", retrievedCustomer.getCustomerMembership());
        TransactionDetail retrievedTransactionDetail = transactionDetailRepository.findById(transactionDetail.getId()).orElse(null);
        assertNotNull(retrievedTransactionDetail, "TransactionDetail should not be null");
        assertNotNull(retrievedTransactionDetail.getTransaction(), "TransactionDetail's Transaction should not be null");
        assertEquals(retrievedTransactionDetail.getTransaction().getId(), transaction.getId(), "TransactionDetail's Transaction ID should match the saved Transaction ID.");

        logger.debug("Retrieved TransactionDetail: {}", retrievedTransactionDetail);
        logger.debug("Associated Transaction: {}", retrievedTransactionDetail.getTransaction());

        assertNotNull(retrievedTransactionDetail.getCustomerMembership(), "TransactionDetail's CustomerMembership should not be null");
        assertEquals(retrievedTransactionDetail.getCustomerMembership().getId(), customerMembership.getId(), "TransactionDetail's CustomerMembership ID should match the saved CustomerMembership ID.");

        logger.debug("Associated CustomerMembership: {}", retrievedTransactionDetail.getCustomerMembership());

        assertNotNull(retrievedTransactionDetail.getProduct(), "TransactionDetail's Product should not be null");
        assertEquals(retrievedTransactionDetail.getProduct().getId(), product.getId(), "TransactionDetail's Product ID should match the saved Product ID.");

        logger.debug("Associated Product: {}", retrievedTransactionDetail.getProduct());

        assertNotNull(retrievedTransactionDetail.getOutlet(), "TransactionDetail's Outlet should not be null");
        assertEquals(retrievedTransactionDetail.getOutlet().getId(), outlet.getId(), "TransactionDetail's Outlet ID should match the saved Outlet ID.");

        logger.debug("Associated Outlet: {}", retrievedTransactionDetail.getOutlet());

        Product retrievedProduct = productRepository.findById(product.getId()).orElse(null);
        assertNotNull(retrievedProduct, "Product should not be null");
        assertNotNull(retrievedProduct.getDiscount(), "Product's Discount should not be null");
        assertEquals(retrievedProduct.getDiscount().getId(), discount.getId(), "Product's Discount ID should match the saved Discount ID.");

        logger.debug("Retrieved Product: {}", retrievedProduct);
        logger.debug("Associated Discount: {}", retrievedProduct.getDiscount());

        Discount retrievedDiscount = discountRepository.findById(discount.getId()).orElse(null);
        assertNotNull(retrievedDiscount, "Discount should not be null");
        assertNotNull(retrievedDiscount.getProducts(), "Discount's Products list should not be null");
        assertEquals(1, retrievedDiscount.getProducts().size(), "Discount's Products list should have one product.");
        assertEquals(retrievedProduct.getId(), retrievedDiscount.getProducts().get(0).getId(), "Discount's Product ID should match the saved Product ID.");

        logger.debug("Associated Products: {}", retrievedDiscount.getProducts());

        UserAccount retrievedUserAccount = userAccountRepository.findById(userAccount.getId()).orElse(null);
        assertNotNull(retrievedUserAccount, "UserAccount should not be null");
        assertNotNull(retrievedUserAccount.getRoles(), "UserAccount's Roles should not be null");
        assertEquals(1, retrievedUserAccount.getRoles().size(), "UserAccount's Roles list should have one role.");
        assertEquals(userRole.getId(), retrievedUserAccount.getRoles().get(0).getId(), "UserAccount's Role ID should match the saved UserRole ID.");

        logger.debug("Retrieved UserAccount: {}", retrievedUserAccount);
        logger.debug("Associated Roles: {}", retrievedUserAccount.getRoles());

        UserRole retrievedUserRole = userRoleRepository.findById(userRole.getId()).orElse(null);
        assertNotNull(retrievedUserRole, "UserRole should not be null");

        logger.debug("Retrieved UserRole: {}", retrievedUserRole);
    }

}
