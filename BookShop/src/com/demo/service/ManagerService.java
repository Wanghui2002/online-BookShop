package com.demo.service;

import java.util.List;

import com.demo.domain.Administrator;
import com.demo.domain.Book;
import com.demo.domain.Category;
import com.demo.domain.User;

public interface ManagerService {

	Administrator login(String username, String password);

	void managerInformation(String username, String name, String sex, String tel);

	void managerPassword(String username, String password);

	List<Category> findAllCategory();

	Category findCategoryById(String categoryid);

	void addBook(Book book);

	void addCategory(Category category);

	List<Book> getCategoryBook(String cid);

	Book findBookById(String book_id);

	void delBook(String book_id);

	void editBook(String book_id, String book_name, String book_author, String book_press, String book_desc,
			double book_price, String book_kunumber,Category category);

	void editCategory(Category category);

	void delCategory(String category_id);

	List<User> findUsers();

	void addAdmin(Administrator admin);

	List<Book> sales();
	//根据用户名查询管理员信息
	Administrator findAdminByUsername(String username);
	//根据图书名称模糊查询
		List<Book> findListByBookName(String name);
}
