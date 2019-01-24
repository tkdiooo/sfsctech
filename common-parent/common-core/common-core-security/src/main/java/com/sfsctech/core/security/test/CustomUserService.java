package com.sfsctech.core.security.test;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Class CustomUserService
 *
 * @author 张麒 2019-1-24.
 * @version Description:
 */
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    //    @Autowired
//    UserDao userDao;
//    @Autowired
//    PermissionDao permissionDao;
//
    public UserDetails loadUserByUsername(String username) {
//        SysUser user = userDao.findByUserName(username);
//        if (user != null) {
//            List<Permission> permissions = permissionDao.findByAdminUserId(user.getId());
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            for (Permission permission : permissions) {
//                if (permission != null && permission.getName() != null) {
//
//                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
//                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
//                    grantedAuthorities.add(grantedAuthority);
//                }
//            }
//            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        return new User("", "", null);
//        } else {
//            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
//        }
    }

}
