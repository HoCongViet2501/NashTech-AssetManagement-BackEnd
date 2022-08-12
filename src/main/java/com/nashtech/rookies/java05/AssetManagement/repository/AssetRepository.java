package com.nashtech.rookies.java05.AssetManagement.repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.interfaces.AssetHistoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
	@Query(value = "SELECT * FROM assets a WHERE a.category_id LIKE %:category ORDER BY  a.id desc", nativeQuery = true)
	List<Asset> findByOrderByIdDesc(String category);

	@Query(value = "select a.* from information i inner join assets a on i.user_id = a.creator_id where i.location = :location", nativeQuery = true)
	public List<Asset> findAssetByLocation(String location);

	@Query(value = "select a.* from information i inner join assets a on i.user_id = a.creator_id where (lower(a.name) like lower(concat('%', :content,'%')) or lower(a.id) like lower(concat('%', :content,'%'))) and i.location = :location", nativeQuery = true)
	public List<Asset> searchAssetByNameOrCode(String content, String location);

	@Query(value = "select a.* from assets a where a.id = :id and a.state = :state", nativeQuery = true)
	Optional<Asset> findByIdAndState(@Param("id") String id,@Param("state") String state);
	
	@Query(value = "select a.* from information i inner join assets a on i.user_id = a.creator_id where i.location = :location and a.state = 'Available'", nativeQuery = true)
	public List<Asset> findAssetByLocationAndState(@Param("location")String location);
	
	@Query(value = "select a.* from information i "
			+ "inner join assets a on i.user_id = a.creator_id "
			+ "where (lower(a.name) like lower(concat('%', :content,'%')) or lower(a.id) like lower(concat('%', :content,'%'))) "
			+ "and i.location = :location and a.state = 'Available'", nativeQuery = true)
	public List<Asset> searchAssetByLocationAndState(@Param("location")String location,@Param("content") String content);

	@Query(value = "SELECT a.assigned_date as assignedDate, u.username as requestBy, u2.username as acceptedBy, r.returned_date as returnedDate " +
			"FROM returnings r, assignments a , users u , users u2 " +
			"WHERE a.asset_id = :assetId AND r.state = 'Completed' AND r.is_delete = false  AND a.status = false AND  r.assignment_id = a.id AND u.id = r.request_by AND  u2.id = r.accepted_by " +
			"GROUP BY a.assigned_date , u.username , u2.username , r.returned_date " +
			"ORDER BY r.returned_date ASC", nativeQuery = true)
	List<AssetHistoryInterface> getAssetHistory(@Param("assetId") String assetId);
}
