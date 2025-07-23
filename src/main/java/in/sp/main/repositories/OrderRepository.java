package in.sp.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.sp.main.entities.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>
{
//		SQL join query																																	Note: ':email' for get dynamic 'email' from database	  
	String SELECT_QUERY = "SELECT o.date_of_purchase,c.description, c.image_url, c.name, c.updated_on FROM orders o JOIN course c ON o.course_name=c.name WHERE o.user_email= :email";

	
	@Query(value = SELECT_QUERY, nativeQuery= true)  // native sql query
 	
	//method return values in object array
	List<Object[]> findPurchasedCoursesByEmail(@Param("email") String email);
	
	// store course details into object array, then object array will store inside the list
}
