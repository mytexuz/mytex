package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

}
