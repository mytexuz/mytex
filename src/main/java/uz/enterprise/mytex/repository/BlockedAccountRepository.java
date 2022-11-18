package uz.enterprise.mytex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.enterprise.mytex.entity.BlockedAccount;

public interface BlockedAccountRepository extends JpaRepository<BlockedAccount, Long> {
    @Query(nativeQuery = true,
            value = "select ba.* from blocked_accounts ba where ba.user_id=:userId")
    Optional<BlockedAccount> findByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true,
            value = "select case when count(ba) > 0 then true else false end from blocked_accounts ba where ba.user_id=:userId")
    Boolean existsBlockedAccountByUserId(@Param("userId") Long userId);
}
