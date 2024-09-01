package com.enigma.challengespringrestful.dao;

import com.enigma.challengespringrestful.constant.ConstantUserRole;
import com.enigma.challengespringrestful.entity.UserRole;

public interface UserRoleDAO {
    UserRole getOrSave(ConstantUserRole name);
}
