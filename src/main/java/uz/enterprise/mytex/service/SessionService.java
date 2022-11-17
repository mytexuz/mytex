package uz.enterprise.mytex.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Session;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.repository.SessionRepository;
import uz.enterprise.mytex.repository.UserRepository;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ResponseHelper responseHelper;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository,
                          ResponseHelper responseHelper) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
    }

    public Session getSessionByUserId(Long userId) throws CustomException {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException(responseHelper.userDoesNotExist());
        });
        return sessionRepository.findSessionByUserId(user.getId()).orElseThrow(() -> {
            throw new CustomException(responseHelper.noDataFound());
        });
    }
}
