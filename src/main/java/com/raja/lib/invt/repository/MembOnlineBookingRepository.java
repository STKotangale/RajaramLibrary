package com.raja.lib.invt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.raja.lib.invt.model.MembOnlineBooking;
import com.raja.lib.invt.objects.OnlineBookingDetails;

public interface MembOnlineBookingRepository extends JpaRepository<MembOnlineBooking, Integer> {

    @Query(value = "SELECT mob.*, au.username, ib.bookName " +
                   "FROM memb_online_booking mob " +
                   "JOIN auth_general_members agm ON agm.memberId = mob.memberIdF " +
                   "JOIN auth_users au ON au.memberIdF = agm.memberId " +
                   "JOIN invt_book ib ON ib.bookId = mob.book_idF " +
                   "WHERE mob.isBlock = 'Y' AND au.userId = :userId", nativeQuery = true)
    List<OnlineBookingDetails> findOnlineBookingsByUserId(@Param("userId") int userId);
}
