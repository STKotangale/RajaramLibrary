package com.raja.lib.invt.repository;

public interface CustomMemberMonthlyFeesRepository {
    boolean existsByMemberAndDate(int memberId, String date);
}