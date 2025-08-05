package tenant.common.entity.dao;

import common.constants.SqlQueries;
import entity.table.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Base DAO for common site checks and tenant operations.
 * SQL queries are externalized to XML files in resources/sql directory.
 */
@Repository
public interface ComCheckSiteBaseDao extends JpaRepository<Company, Long> {

    /**
     * Find all active companies.
     * Query defined in: resources/sql/ComCheckSiteBaseDao.xml
     */
    @Query(SqlQueries.COM_CHECK_FIND_ALL_ACTIVE_COMPANIES)
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
     * Query defined in: resources/sql/ComCheckSiteBaseDao.xml
     */
    @Query(SqlQueries.COM_CHECK_GET_TOTAL_COMPANY_COUNT)
    long getTotalCompanyCount();
}