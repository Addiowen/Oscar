package com.compulynx.compas.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.compulynx.compas.models.MenuHeaderMaster;

@Repository
public interface MenuHeaderRepository extends CrudRepository<MenuHeaderMaster, Long>{
	Collection<MenuHeaderMaster> findAll();
	
	@Query("SELECT c FROM MenuHeaderMaster c inner join fetch c.menus o "
			+ "inner join fetch o.groups g inner join g.rights s  "
			+ "where g.id=?1 and s.rightId=o.id and s.AllowView=1 order by c.headerPos asc")
	public List<MenuHeaderMaster> getAll(@Param("rightId") Long groupId);

}
//select * from MenuHeaderMaster c inner join groups g inner join rights r where g=26 and  r. 