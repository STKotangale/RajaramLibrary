package com.raja.lib.invt.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MemberMonthlyFeesRepositoryImpl implements CustomMemberMonthlyFeesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByMemberAndDate(int memberId, String date) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(*) " +
            "FROM acc_member_monthly_fees m " +
            "WHERE m.memberIdF = :memberId " +
            "AND STR_TO_DATE(:date, '%d-%m-%Y') BETWEEN STR_TO_DATE(m.fromDate, '%d-%m-%Y') AND STR_TO_DATE(m.toDate, '%d-%m-%Y')");
        query.setParameter("memberId", memberId);
        query.setParameter("date", date);
        return ((Number) query.getSingleResult()).intValue() > 0;
    }
}