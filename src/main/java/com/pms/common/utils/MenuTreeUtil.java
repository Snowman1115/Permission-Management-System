package com.pms.common.utils;

import com.pms.entity.Permission;
import com.pms.entity.Router;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Generate Menu Class
 */
public class MenuTreeUtil {

    /**
     * Generate Router
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Router> genRouter(List<Permission> menuList, long pid) {
        // Create new array list
        List<Router> routers = new ArrayList<>();
        // Determine is menuList is empty create new Array
        Optional.ofNullable(menuList).orElse(new ArrayList<Permission>())
                // Select Menu is not empty or same id wif parent id's data
                .stream().filter(item -> item != null && item.getPid() == pid)
                .forEach(item -> {
                    // Create Router Object
                    Router router = new Router();
                    router.setName(item.getName()); // Router Name
                    router.setPath(item.getPath()); // Router Path
                    // Determine it's First Layer Menu
                    if (item.getPid() == 0L) {
                        router.setComponent("Layout"); //
                        router.setAlwaysShow(true); // Visible
                    } else {
                        router.setComponent(item.getUrl()); // Specific which component
                        router.setAlwaysShow(false);
                    }
                    // Set Meta Info
                    router.setMeta(router.new Meta(item.getLabel(), item.getIcon(), item.getCode().split(",")));
                    // Recursion Generate Router
                    List<Router> children = genRouter(menuList, item.getId()); // Children Menu
                    router.setChildren(children); // Set children into router
                    routers.add(router);
                });
        return routers;
    }

    /**
     * Generate Menu Tree
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Permission> genMenuTree(List<Permission> menuList, long pid) {
        // Create new array list
        List<Permission> permissions = new ArrayList<>();
        // Determine is menuList is empty create new Array
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && item.getPid() == pid)
                .forEach(item -> {
                    // Create Permission Menu Object
                    Permission permission = new Permission();
                    BeanUtils.copyProperties(item,permission);
                    List<Permission> children = genMenuTree(menuList, item.getId());
                    permission.setChildren(children);
                    permissions.add(permission);
                });
        return permissions;
    }

}
