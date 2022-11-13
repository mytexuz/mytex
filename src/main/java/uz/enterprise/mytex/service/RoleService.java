package uz.enterprise.mytex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Role;
import uz.enterprise.mytex.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role getRoleByUser(Long userId) {
        return roleRepository.findByUserId(userId).orElse(new Role());
    }
}
