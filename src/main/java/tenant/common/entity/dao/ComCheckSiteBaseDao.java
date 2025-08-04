package tenant.common.entity.dao;

import entity.table.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Base DAO for common site checks and tenant operations.
 */
@Repository
public interface ComCheckSiteBaseDao extends JpaRepository<Company, Long> {

    /**
     * Find all active companies.
     */
    @Query("SELECT c FROM Company c WHERE c.createdAt IS NOT NULL ORDER BY c.companyName")
    List<Company> findAllActiveCompanies();

    /**
     * Find company by code.
     */
    Optional<Company> findByCompanyCode(String companyCode);

    /**
     * Check if company exists by code.
     */
    boolean existsByCompanyCode(String companyCode);

    /**
     * Get total count of companies.
     */
    @Query("SELECT COUNT(c) FROM Company c")
    long getTotalCompanyCount();
}