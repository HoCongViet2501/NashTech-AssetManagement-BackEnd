package com.nashtech.rookies.java05.AssetManagement.repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Asset, String> {
    @Query(value = "SELECT c.id, c.name, a.state, count(a.id) " +
            "FROM assets a " +
            "INNER JOIN categories c ON c.id = a.category_id " +
            "GROUP BY c.id, a.category_id, a.state, c.name " +
            "ORDER BY c.name ASC ", nativeQuery = true)
    List<ReportInterface> findAssetByCategoryAndState();
}
