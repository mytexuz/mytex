package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.Device;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 10:57 AM 10/24/22 on Monday in October
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
