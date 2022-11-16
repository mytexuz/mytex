package uz.enterprise.mytex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.enterprise.mytex.entity.BlockedAccount;

public interface BlockedAccountRepository extends JpaRepository<BlockedAccount, Long> {
}
