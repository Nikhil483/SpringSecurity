package com.security.Auth.security;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

import static com.security.Auth.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
        STUDENT(Sets.newHashSet()),
        ADMIN(Sets.newHashSet(STUDENT_WRITE, STUDENT_READ, COURSE_READ, COURSE_WRITE));

        private final Set<ApplicationUserPermission> permissions;

        public Set<ApplicationUserPermission> getPermissions() {
                return permissions;
        }

        ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
                this.permissions = permissions;
        }
}
