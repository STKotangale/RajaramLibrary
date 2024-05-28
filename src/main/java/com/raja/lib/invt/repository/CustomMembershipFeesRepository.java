package com.raja.lib.invt.repository;

public interface CustomMembershipFeesRepository {
    boolean existsByMemberAndDate(int memberId, String date);
}