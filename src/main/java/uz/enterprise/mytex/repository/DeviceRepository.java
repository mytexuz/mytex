package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query(nativeQuery = true,
            value = "select case when count(d) > 0 then true else false end from devices d where d.user_id=:userId")
    Boolean existsDeviceByUserId(@Param("userId") Long userId);

    Optional<Device> findByUserId(Long userId);

    Optional<Device> findByDeviceId(String deviceId);
}
