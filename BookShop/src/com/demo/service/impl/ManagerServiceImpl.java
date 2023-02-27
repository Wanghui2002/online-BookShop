package com.demo.service.impl;

import com.demo.dao.ManagerDao;
import com.demo.dao.impl.ManagerDaoImpl;
import com.demo.domain.Administrator;
import com.demo.domain.Book;
import com.demo.domain.Category;
import com.demo.domain.User;
import com.demo.service.ManagerService;

import java.util.List;
import java.util.UUID;

public class ManagerServiceImpl implements ManagerService {
	private ManagerDao dao = new ManagerDaoImpl();

	@Override
	public Administrator login(String username, String password) {
		return dao.login(username, password);
	}

	@Override
	public void managerInformation(String username, String name, String sex, String tel) {
		Administrator admin = new Administrator(username, null, name, sex, tel);
		dao.managerInformation(admin);
	}

	@Override
	public void managerPassword(String username, String password) {
		Administrator admin = new Administrator(username, password, null, null, null);
		dao.managerPassword(admin);
	}

	@Override
	public List<Category> findAllCategory() {
		return dao.findAllCategory();
	}

	@Override
	public Category findCategoryById(String categoryid) {
		return dao.findCategoryById(categoryid);
	}

	@Override
	public void addBook(Book book) {
		book.setBook_id(UUID.randomUUID().toString());
		dao.addBook(book);

	}

	@Override
	public void addCategory(Category category) {
		category.setCategory_id(UUID.randomUUID().toString());
		dao.addCategory(category);
	}

	@Override
	public List<Book> getCategoryBook(String cid) {
		return dao.getCategoryBook(cid);
	}



	@Override
	public Book findBookById(String book_id) {
		return dao.findBookById(book_id);
	}

	@Override
	public void delBook(String book_id) {
		dao.delBook(book_id);
	}

	@Override
	public void editBook(String book_id, String book_name, String book_author, String book_press, String book_desc,
			double book_price, String book_kunumber,Category category) {
		dao.editBook(book_id, book_name, book_author, book_press, book_desc, book_price, book_kunumber,category);
	}

	@Override
	public void editCategory(Category category) {
		dao.editCategory(category);
	}

	@Override
	public void delCategory(String category_id) {
		dao.delCategory(category_id);
	}

	@Override
	public List<User> findUsers() {
		return dao.findUsers();
	}

	@Override
	public void addAdmin(Administrator admin) {
		dao.addAdmin(admin);
	}

	@Override
	public List<Book> sales() {
		return dao.sales();
	}

	@Override
	public Administrator findAdminByUsername(String username) {
		// TODO Auto-generated method stub
		return dao.findAdminByUsername(username);
	}
	//根据图书名称模糊查询
	@Override
	public List<Book> findListByBookName(String name) {
		// TODO Auto-generated method stub
		return dao.findListByBookName(name);
	}

}
