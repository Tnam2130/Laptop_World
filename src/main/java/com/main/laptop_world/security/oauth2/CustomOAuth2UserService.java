package com.main.laptop_world.security.oauth2;

import com.main.laptop_world.Entity.Role;
import com.main.laptop_world.Repository.RoleRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private RoleRepository roleRepository;
    public CustomOAuth2UserService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user=super.loadUser(userRequest);
        // Mở rộng yêu cầu để lấy thông tin vai trò từ OAuth2 provider
        List<Role> roles = getRolesFromOAuth2Provider(user);

        return new CustomOAuth2User(user, roles);
    }
    private List<Role> getRolesFromOAuth2Provider(OAuth2User oAuth2User) {
        // Trong ví dụ này, giả sử vai trò được lấy từ thuộc tính "roles" của OAuth2 provider.
        List<String> roleNames = oAuth2User.getAttribute("roles");

        if (roleNames != null) {
            // Chuyển đổi các tên vai trò thành đối tượng Role
            return roleNames.stream()
                    .map(roleName -> {
                        Role role = new Role();
                        role.setName(roleName);
                        return role;
                    })
                    .collect(Collectors.toList());
        } else {
            // Nếu không có thông tin vai trò, có thể trả về vai trò mặc định hoặc xử lý theo ý muốn của bạn.
            // Ví dụ: Trả về vai trò "USER" nếu không có thông tin vai trò.
            Role defaultRole = roleRepository.findByName("USER");
            return Collections.singletonList(defaultRole);
        }
    }
}
