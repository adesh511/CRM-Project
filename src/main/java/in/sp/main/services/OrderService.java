package in.sp.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Orders;
import in.sp.main.repositories.OrderRepository;

@Service
public class OrderService 
{
	@Autowired
	private OrderRepository orderRepository;
	
	public void storeUserOrders(Orders Orders)
	{
		orderRepository.save(Orders); // save orders in database
	}
	
}
