package com.enigma.challengespringrestful.service;

import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.dao.UserRoleDAO;
import com.enigma.challengespringrestful.entity.UserRole;
import com.enigma.challengespringrestful.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleService implements UserRoleDAO {
    private final UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserRole getOrSave(ConstantUserRole role) {
        UserRoleRepository repository = userRoleRepository;
        return repository.findAllByRole(String.valueOf(role)).orElseGet(() -> repository.saveAndFlush(UserRole.builder().role(role).build()));
    }
}
