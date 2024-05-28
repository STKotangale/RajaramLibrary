package com.raja.lib.invt.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class MembershipFeesRepositoryImpl implements CustomMembershipFeesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByMemberAndDate(int memberId, String date) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(*) " +
            "FROM acc_membership_fees m " +
            "WHERE m.memberIdF = :memberId " +
            "AND STR_TO_DATE(:date, '%d-%m-%Y') = STR_TO_DATE(m.mem_invoice_date, '%d-%m-%Y')");
        query.setParameter("memberId", memberId);
        query.setParameter("date", date);
        return ((Number) query.getSingleResult()).intValue() > 0;
    }
}