package com.imooc.roy.stream.cases;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author roy f
 */
public class CaseThree {
    /**
     * 角色
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Role {
        /**
         * 权限列表
         */
        private List<Permission> permissions;
    }

    /**
     * 权限
     */
    @Data
    @AllArgsConstructor
    class Permission {
        /**
         * 权限名称
         */
        private String name;
    }

    /**
     * 用户角色列表
     */
    List<Role> roleList;

    @Before
    public void init() {
        roleList = new ArrayList();

        Role adminRole = new Role();
        List<Permission> adminPermissionList = Lists.newArrayList(
                new Permission("删除"),
                new Permission("查看"),
                new Permission("导出"));
        adminRole.setPermissions(adminPermissionList);

        Role userRole = new Role();
        List<Permission> userPermissionList = Lists.newArrayList(
                new Permission("新建"),
                new Permission("修改"),
                new Permission("删除"),
                new Permission("查看"));
        userRole.setPermissions(userPermissionList);

        roleList.add(adminRole);
        roleList.add(userRole);
    }

    /**
     * 找出这个人所有权限
     */
    @Test
    public void findPermission() {
        roleList.stream()
                // TODO 扁平化MAP 获取对象中的集合类属性，组成一个新的流
                // TODO 此处数据结构为：List<Role -> List<Permission -> String name>>
                /*
                roleList[
                    role{
                        permission[
                            name:""
                        ]
                    }
                ]
                 */
                // TODO 扁平化处理后得到的就是role.permission的集合流
                .flatMap(role -> role.getPermissions().stream())
                .peek(System.out::println)
                .distinct()
                .collect(Collectors.toList());
//                .forEach(permission -> System.out.println(permission));

    }
}
