package com.demo.service;

import java.util.List;

import com.demo.domain.Order;
import com.demo.domain.User;

public interface OrderService {
	/**
	 * 生成订单
	 * 
	 * @param o
	 */
	void genOrder(Order o);

	/**
	 * 根据订单号查询订单
	 * 
	 * @param ordernum
	 * @return
	 */
	Order findOrderByNum(String ordernum);

	/**
	 * 查询客户的订单
	 * 
	 * @param user
	 * @return
	 */
	List<Order> findUserOrders(User user);

	List<Order> findOrders();

	void faHuo(String ordernum);
	
	void deleteByNumber(String orderNumber);
}
