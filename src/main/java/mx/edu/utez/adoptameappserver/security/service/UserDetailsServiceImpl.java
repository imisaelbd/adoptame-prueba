package mx.edu.utez.adoptameappserver.security.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.model.user.User;
import mx.edu.utez.adoptameappserver.security.entity.UserDetailsImpl;
import mx.edu.utez.adoptameappserver.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> foundUser = service.findByEmail(email);
        if (foundUser.isPresent())
            return UserDetailsImpl.build(foundUser.get());
        throw new UsernameNotFoundException("UserNotFound");
    }
}
