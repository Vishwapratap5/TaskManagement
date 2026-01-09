package com.taskmanagement.taskmanagement.Security;

import com.taskmanagement.taskmanagement.Enums.Permission;
import com.taskmanagement.taskmanagement.Enums.Role;

import java.util.*;


public class PermissionConfig {

    public static Map<Role, Set<Permission>> getRolePermissions() {
        Map<Role, Set<Permission>> map = new HashMap<>();

        map.put(Role.MANAGER,new HashSet<Permission>(Arrays.asList(
                                                                    Permission.ISSUE_VIEW,
                                                                    Permission.ISSUE_CREATE,
                                                                    Permission.ISSUE_ASSIGN,
                                                                    Permission.ISSUE_EDIT,
                                                                    Permission.COMMENT_ADD)));

        map.put(Role.ADMIN,new HashSet<>(Arrays.asList(
                                                    Permission.ISSUE_CREATE,
                                                    Permission.ISSUE_VIEW,
                                                    Permission.ISSUE_ASSIGN,
                                                    Permission.ISSUE_EDIT,
                                                    Permission.ISSUE_DELETE,
                                                    Permission.COMMENT_ADD,
                                                    Permission.COMMENT_DELETE,
                                                    Permission.USER_MANAGE)));

        map.put(Role.Developer,new HashSet<>(new HashSet<>(Arrays.asList(
                                                    Permission.ISSUE_VIEW,
                                                    Permission.ISSUE_EDIT,
                                                    Permission.COMMENT_ADD))));

        map.put(Role.TESTER,new HashSet<>(new HashSet<>(Arrays.asList(
                                                    Permission.ISSUE_VIEW,
                                                    Permission.COMMENT_ADD))));
        return map;
    }
}
