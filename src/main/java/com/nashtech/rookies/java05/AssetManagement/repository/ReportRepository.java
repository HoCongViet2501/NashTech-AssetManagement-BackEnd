package com.nashtech.rookies.java05.AssetManagement.repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Asset, String> {
    @Query(value = "select c.name, " +
            "count(1) as total, " +
            "count(1) filter (where a.state='Assigned') as assigned, " +
            "count(1) filter (where a.state='Available') as available, " +
            "count(1) filter (where a.state='Not available') as notAvailable, " +
            "count(1) filter (where a.state='Waiting for recycling') as waitingForRecycling, " +
            "count(1) filter (where a.state='Recycled') as recycled " +
            "from assets a inner join categories c on a.category_id  = c.id " +
            "group by c.name ", nativeQuery = true)
    List<ReportInterface> findAssetByCategoryAndState();
}
