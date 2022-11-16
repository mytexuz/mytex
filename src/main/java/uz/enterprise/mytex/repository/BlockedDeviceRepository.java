package uz.enterprise.mytex.repository;

import java.util.Optional;

import liquibase.pro.packaged.Q;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.BlockedDevice;

public interface BlockedDeviceRepository extends JpaRepository<BlockedDevice, Long> {
    @Query(nativeQuery = true,
            value = "select bd.* from blocked_devices bd where bd.device_id=:deviceId")
    Optional<BlockedDevice> findByDeviceId(@Param("deviceId") Long deviceId);

    @Query(nativeQuery = true,
            value = "select case when count(bd) > 0 then true else false end from blocked_devices bd where bd.device_id=:deviceId")
    Boolean existsBlockedDeviceByDeviceId(@Param("deviceId") Long deviceId);
}
