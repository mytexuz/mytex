package uz.enterprise.mytex.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.common.Generated;
import uz.enterprise.mytex.dto.BlockedAccountDto;
import uz.enterprise.mytex.dto.BlockedDeviceDto;
import uz.enterprise.mytex.dto.request.BlockedAccountCreateRequest;
import uz.enterprise.mytex.dto.request.BlockedDeviceCreateRequest;
import uz.enterprise.mytex.entity.BlockedAccount;
import uz.enterprise.mytex.entity.BlockedDevice;
import uz.enterprise.mytex.entity.Device;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.BlockingStatus;
import static uz.enterprise.mytex.enums.BlockingStatus.BLOCKED;
import static uz.enterprise.mytex.enums.BlockingStatus.UNBLOCKED;
import static uz.enterprise.mytex.enums.BlockingStatus.isBlocked;
import static uz.enterprise.mytex.enums.BlockingStatus.isUnBlocked;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.helper.SecurityHelper;
import uz.enterprise.mytex.repository.BlockedAccountRepository;
import uz.enterprise.mytex.repository.BlockedDeviceRepository;
import uz.enterprise.mytex.repository.DeviceRepository;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.security.CustomUserDetails;

@Service
public class FraudService {
    private final BlockedAccountRepository blockedAccountRepository;
    private final BlockedDeviceRepository blockedDeviceRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final ResponseHelper responseHelper;

    @Generated
    public FraudService(BlockedAccountRepository blockedAccountRepository,
                        BlockedDeviceRepository blockedDeviceRepository,
                        UserRepository userRepository,
                        DeviceRepository deviceRepository,
                        ResponseHelper responseHelper) {
        this.blockedAccountRepository = blockedAccountRepository;
        this.blockedDeviceRepository = blockedDeviceRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.responseHelper = responseHelper;
    }

    public ResponseEntity<?> blockDevice(BlockedDeviceCreateRequest blockDeviceDto) throws CustomException {
        Long deviceId = blockDeviceDto.getDeviceId();
        if (blockedDeviceRepository.existsBlockedDeviceByDeviceId(deviceId)) {
            Optional<BlockedDevice> blockedDeviceOptional = blockedDeviceRepository.findByDeviceId(deviceId);
            if (blockedDeviceOptional.isPresent()) {
                BlockedDevice blockedDevice = blockedDeviceOptional.get();
                User blockedBy = blockedDevice.getBlockedBy();
                BlockingStatus status = blockedDevice.getStatus();
                String fullName = blockedBy.getFirstName() + " " + blockedBy.getLastName();
                if (isBlocked(status)) {
                    return responseHelper.deviceAlreadyBlocked(fullName);
                }
                User user = getCurrentUser();
                if (Objects.isNull(user)) {
                    throw new CustomException(responseHelper.unauthorized());
                }
                blockedDevice.setBlockedBy(user);
                blockedDevice.setStatus(BLOCKED);
                blockedDevice.setDevice(getDevice(deviceId));
                if (!blockDeviceDto.getReason().isBlank()) {
                    blockedDevice.setReason(blockDeviceDto.getReason());
                }
                blockedDevice.setPeriod(blockDeviceDto.getPeriod());
                BlockedDevice save = blockedDeviceRepository.save(blockedDevice);
                BlockedDeviceDto blockedDeviceDto = new BlockedDeviceDto(save);
                return responseHelper.success(blockedDeviceDto);
            }
        }
        BlockedDevice blockedDevice = BlockedDevice.builder()
                .device(getDevice(deviceId))
                .blockedBy(getCurrentUser())
                .status(BLOCKED)
                .period(blockDeviceDto.getPeriod())
                .reason(blockDeviceDto.getReason())
                .build();
        BlockedDevice save = blockedDeviceRepository.save(blockedDevice);
        BlockedDeviceDto blockedDeviceDto = new BlockedDeviceDto(save);
        return responseHelper.success(blockedDeviceDto);
    }

    private User getCurrentUser() {
        CustomUserDetails currentUser = SecurityHelper.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            return userRepository.findById(currentUser.getId())
                    .orElse(new User());
        }
        return null;
    }

    private Device getDevice(Long id) {
        return deviceRepository.findById(id).orElse(new Device());
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public ResponseEntity<?> unblockDevice(Long deviceId) {
        if (!blockedDeviceRepository.existsBlockedDeviceByDeviceId(deviceId)) {
            return responseHelper.deviceNotBlocked();
        }
        Optional<BlockedDevice> deviceOptional = blockedDeviceRepository.findByDeviceId(deviceId);
        if (deviceOptional.isEmpty()) {
            return responseHelper.deviceNotBlocked();
        }
        BlockedDevice blockedDevice = deviceOptional.get();
        User blockedBy = blockedDevice.getBlockedBy();
        String fullName = blockedBy.getFirstName() + " " + blockedBy.getLastName();
        if (isUnBlocked(blockedDevice.getStatus())) {
            return responseHelper.deviceAlreadyUnBlocked(fullName);
        }
        blockedDevice.setStatus(UNBLOCKED);
        blockedDeviceRepository.save(blockedDevice);
        return responseHelper.success();
    }

    public boolean isDeviceBlockedByDeviceId(String deviceId) throws CustomException {
        Optional<Device> optionalDevice = deviceRepository.findByDeviceId(deviceId);
        if (optionalDevice.isEmpty()) {
            throw new CustomException(responseHelper.noDataFound());
        }
        Device device = optionalDevice.get();
        Optional<BlockedDevice> deviceOptional = blockedDeviceRepository.findByDeviceId(device.getId());
        if (deviceOptional.isEmpty()) {
            return false;
        }
        return isBlocked(deviceOptional.get().getStatus());
    }

    public ResponseEntity<?> blockAccount(BlockedAccountCreateRequest blockedAccountCreateRequest) {
        Long userId = blockedAccountCreateRequest.getAccountId();
        if (blockedAccountRepository.existsBlockedAccountByUserId(userId)) {
            Optional<BlockedAccount> blockedAccountOptional = blockedAccountRepository.findByUserId(userId);
            if (blockedAccountOptional.isPresent()) {
                BlockedAccount blockedAccount = blockedAccountOptional.get();
                User blockedBy = blockedAccount.getBlockedBy();
                BlockingStatus status = blockedAccount.getStatus();
                String fullName = blockedBy.getFirstName() + " " + blockedBy.getLastName();
                if (isBlocked(status)) {
                    return responseHelper.accountAlreadyBlocked(fullName);
                }
                User user = getCurrentUser();
                if (Objects.isNull(user)) {
                    throw new CustomException(responseHelper.unauthorized());
                }
                blockedAccount.setBlockedBy(user);
                blockedAccount.setStatus(BLOCKED);
                blockedAccount.setUser(getUser(userId));
                if (!blockedAccountCreateRequest.getReason().isBlank()) {
                    blockedAccount.setReason(blockedAccountCreateRequest.getReason());
                }
                blockedAccount.setPeriod(blockedAccountCreateRequest.getPeriod());
                BlockedAccount save = blockedAccountRepository.save(blockedAccount);
                BlockedAccountDto blockedAccountDto = new BlockedAccountDto(save);
                return responseHelper.success(blockedAccountDto);
            }
        }
        BlockedAccount blockedAccount = BlockedAccount.builder()
                .user(getUser(userId))
                .blockedBy(getCurrentUser())
                .status(BLOCKED)
                .period(blockedAccountCreateRequest.getPeriod())
                .reason(blockedAccountCreateRequest.getReason())
                .build();
        BlockedAccount save = blockedAccountRepository.save(blockedAccount);
        BlockedAccountDto blockedAccountDto = new BlockedAccountDto(save);
        return responseHelper.success(blockedAccountDto);
    }

    public ResponseEntity<?> unblockAccount(Long accountId) {
        if (!blockedAccountRepository.existsBlockedAccountByUserId(accountId)) {
            return responseHelper.accountNotBlocked();
        }
        Optional<BlockedAccount> accountOptional = blockedAccountRepository.findByUserId(accountId);
        if (accountOptional.isEmpty()) {
            return responseHelper.accountBlocked();
        }
        BlockedAccount blockedAccount = accountOptional.get();
        User blockedBy = blockedAccount.getBlockedBy();
        String fullName = blockedBy.getFirstName() + " " + blockedBy.getLastName();
        if (isUnBlocked(blockedAccount.getStatus())) {
            return responseHelper.accountAlreadyUnBlocked(fullName);
        }
        blockedAccount.setStatus(UNBLOCKED);
        blockedAccountRepository.save(blockedAccount);
        return responseHelper.success();
    }

    public boolean isAccountBlockedByUserId(Long userId) throws CustomException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new CustomException(responseHelper.noDataFound());
        }
        User user = optionalUser.get();
        Optional<BlockedAccount> accountOptional = blockedAccountRepository.findByUserId(user.getId());
        if (accountOptional.isEmpty()) {
            return false;
        }
        return isBlocked(accountOptional.get().getStatus());
    }
}
