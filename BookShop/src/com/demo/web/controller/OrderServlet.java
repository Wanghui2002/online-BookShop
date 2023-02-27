package com.demo.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.domain.Book;
import com.demo.domain.Order;
import com.demo.domain.Orderitem;
import com.demo.domain.User;
import com.demo.service.OrderService;
import com.demo.service.impl.ClientServiceImpl;
import com.demo.service.impl.OrderServiceImpl;
import com.demo.web.formbean.Cart;
import com.demo.web.formbean.CartItem;

@WebServlet("/order/OrderServlet")
public class OrderServlet extends HttpServlet {
	private OrderService service = new OrderServiceImpl();
	private ClientServiceImpl clientService=new ClientServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String op = req.getParameter("op");// 得到传过来的请求
		// 生成订单
		if (op.equals("genOrder")) {
			genOrder(req, resp);
		}
		// 查看订单
		if (op.equals("findAllOrders")) {
			findAllOrders(req, resp);
		}
		// 管理员查看订单
		if (op.equals("findOrders")) {
			findOrders(req, resp);
		}
		// 发货
		if (op.equals("faHuo")) {
			faHuo(req, resp);
		}
		//删除订单
		if(op.equals("deletes")) {
			deletes(req,resp);
		}
	}

	private void deletes(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String orderNumber = req.getParameter("orderNum");
		Order findOrderByNum = service.findOrderByNum(orderNumber);
		if(findOrderByNum.getItems().size()!=0) {
			resp.getWriter().print("<script>alert('该订单下有订单子项不可删除,请先删除订单子项!')</script>");
			resp.getWriter().print("<script>location.href='../admin/managerOrder.jsp'</script>");
			return;
		}
		try {
			service.deleteByNumber(orderNumber);
			resp.getWriter().print("<script>alert('删除成功!')</script>");
			resp.sendRedirect("../admin/managerOrder.jsp");
		}catch (Exception e) {
			resp.getWriter().print("<script>alert('该订单下有订单子项不可删除,请先删除订单子项!')</script>");
			return;
		}
	}
	
	private void faHuo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ordernum = req.getParameter("ordernum");
		service.faHuo(ordernum);
		List<Order> orders = service.findOrders();
		HttpSession session = req.getSession();
		session.setAttribute("orders", orders);
		System.out.println(orders);
		resp.sendRedirect(req.getContextPath() + "/admin/managerOrder.jsp");
	}

	private void findOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Order> orders = service.findOrders();
		HttpSession session = req.getSession();
		session.setAttribute("orders", orders);
		req.getRequestDispatcher("/admin/managerOrder.jsp").forward(req, resp);
	}

	private void findAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		List<Order> orders = service.findUserOrders(user);
		req.setAttribute("orders", orders);
		req.getRequestDispatcher("/person/personOrder.jsp").forward(req, resp);
	}

	private void genOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 取出购物车信息
		// 取出购物项信息
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		User user = (User) session.getAttribute("user");
		if (cart == null) {
			session.setAttribute("message", "会话已经结束！");
			req.getRequestDispatcher("../message.jsp").forward(req, resp);
			return;
		}
		//先判断库存够不够！
		Book book = cart.getBook();
		String book_id = book.getBook_id();
		Book findBookById = clientService.findBookById(book_id);
		if(findBookById.getBook_kunumber()-cart.getTotalQuantity()<0) {
			session.setAttribute("message", "库存不足,无法购买！");
			session.removeAttribute("cart");
			req.getRequestDispatcher("../message.jsp").forward(req, resp);
			return;
		}
		Order order = new Order();
		order.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String ordernum = genOrdernum();
		order.setOrdernum(ordernum);
		order.setQuantity(cart.getTotalQuantity());
		order.setMoney(cart.getTotalMoney());
		order.setUser(user);
		// 订单项
		List<Orderitem> oItems = new ArrayList<Orderitem>();
		for (Map.Entry<String, CartItem> me : cart.getItmes().entrySet()) {
			CartItem cItem = me.getValue();
			Orderitem oItem = new Orderitem();
			oItem.setId(genOrdernum());
			oItem.setBook(cItem.getBook());
			oItem.setPrice(cItem.getMoney());
			oItem.setQuantity(cItem.getQuantity());
			oItem.setOrdernum(ordernum);
			oItems.add(oItem);
		}
		// 建立订单项和订单的关系
		order.setItems(oItems);
		service.genOrder(order);
		req.setAttribute("order", order);
		req.getSession().removeAttribute("cart");
		req.removeAttribute("cart");
		req.getRequestDispatcher("/order.jsp").forward(req, resp);
	}

	// 生成订单号
	private String genOrdernum() {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String s1 = df.format(now);
		return s1 + System.nanoTime();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
