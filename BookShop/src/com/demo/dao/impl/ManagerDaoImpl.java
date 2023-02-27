package com.demo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.dao.ManagerDao;
import com.demo.domain.Administrator;
import com.demo.domain.Book;
import com.demo.domain.Category;
import com.demo.domain.User;
import com.demo.util.Util;

public class ManagerDaoImpl implements ManagerDao {

	// 管理员登录操作
	@Override
	public Administrator login(String username, String password) {
		Administrator admin = new Administrator();
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("select * from administrator where admin_username=? and admin_password=?");
			prepareStatement.setString(1, username);
			prepareStatement.setString(2, password);
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) {
				admin.setUsername(rs.getString("admin_username"));
				admin.setPassword(rs.getString("admin_password"));
				admin.setName(rs.getString("admin_name"));
				admin.setSex(rs.getString("admin_sex"));
				admin.setTel(rs.getString("admin_tel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

	// 修改个人信息
	@Override
	public void managerInformation(Administrator admin) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(
					"update administrator set admin_name=?, admin_sex=?, admin_tel=? where admin_username=? ");
			prepareStatement.setString(1, admin.getName());
			prepareStatement.setString(2, admin.getSex());
			prepareStatement.setString(3, admin.getTel());
			prepareStatement.setString(4, admin.getUsername());
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改密码操作
	@Override
	public void managerPassword(Administrator admin) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("update administrator set admin_password=? where admin_username=?");
			prepareStatement.setString(1, admin.getPassword());
			prepareStatement.setString(2, admin.getUsername());
			prepareStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查找所有的分类
	@Override
	public List<Category> findAllCategory() {
		List<Category> list = new ArrayList<Category>();
		try {
			Connection connection = Util.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select * from category");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Category category = new Category();
				category.setCategory_id(rs.getString("category_id"));
				category.setCategory_name(rs.getString("category_name"));
				category.setCategory_desc(rs.getString("category_desc"));
				list.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ͨ跟据分类id查询分类
	@Override
	public Category findCategoryById(String categoryid) {
		Category category = new Category();
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("select * from category where category_id = ?");
			prepareStatement.setString(1, categoryid);
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) {

				category.setCategory_id(rs.getString("category_id"));
				category.setCategory_name(rs.getString("category_name"));
				category.setCategory_desc(rs.getString("category_desc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	// 添加图书
	@Override
	public void addBook(Book book) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("insert into bookdb values(?,?,?,?,?,?,?,?,?,?,?)");
			prepareStatement.setString(1, book.getBook_id());
			prepareStatement.setString(2, book.getBook_name());
			prepareStatement.setString(3, book.getBook_author());
			prepareStatement.setString(4, book.getBook_press());
			prepareStatement.setString(5, book.getCategory().getCategory_id());
			prepareStatement.setString(6, book.getFilename());
			prepareStatement.setString(7, book.getPath());
			prepareStatement.setString(8, book.getBook_desc());
			prepareStatement.setDouble(9, book.getBook_price());
			prepareStatement.setInt(10, book.getBook_kunumber());
			prepareStatement.setInt(11, 0);
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加分类
	@Override
	public void addCategory(Category category) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement("insert into category values(?,?,?)");
			prepareStatement.setString(1, category.getCategory_id());
			prepareStatement.setString(2, category.getCategory_name());
			prepareStatement.setString(3, category.getCategory_desc());
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//根据分类id获取图书
	@Override
	public List<Book> getCategoryBook(String cid) {
		ClientDaoImpl clientDaoImpl = new ClientDaoImpl();
		return clientDaoImpl.getCategoryBook(cid);
	}


	//根据图示Id获取图书
	@Override
	public Book findBookById(String book_id) {
		Book book = new Book();
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement("select * from bookdb where book_id = ?");
			prepareStatement.setString(1, book_id);
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) {
				book.setBook_id(book_id);
				book.setBook_name(rs.getString("book_name"));
				book.setBook_author(rs.getString("book_author"));
				book.setBook_press(rs.getString("book_press"));
				Category category = findCategoryById(rs.getString("book_category"));
				book.setCategory(category);
				book.setFilename(rs.getString("filename"));
				book.setPath(rs.getString("path"));
				book.setBook_desc(rs.getString("book_desc"));
				book.setBook_price(rs.getDouble("book_price"));
				book.setBook_kunumber(rs.getInt("book_kunumber"));
				book.setBook_xiaonumber(rs.getInt("book_xiaonumber"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	// 删除图书
	@Override
	public void delBook(String book_id) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(" delete from bookdb where book_id=?");
			prepareStatement.setString(1, book_id);
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//修改图书
	@Override
	public void editBook(String book_id, String book_name, String book_author, String book_press, String book_desc,
			double book_price, String book_kunumber,Category category) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(
					"update bookdb set book_name=?,book_author=?,book_press=?,book_desc=?,book_price=?,book_kunumber=?,book_category=? where book_id=?");
			prepareStatement.setString(1, book_name);
			prepareStatement.setString(2, book_author);
			prepareStatement.setString(3, book_press);
			prepareStatement.setString(4, book_desc);
			prepareStatement.setDouble(5, book_price);
			prepareStatement.setString(6, book_kunumber);
			prepareStatement.setString(7, category.getCategory_id());
			prepareStatement.setString(8, book_id);
			prepareStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//修改图书分类
	@Override
	public void editCategory(Category category) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("update category set category_name=?,category_desc=? where category_id=?");
			prepareStatement.setString(1, category.getCategory_name());
			prepareStatement.setString(2, category.getCategory_desc());
			prepareStatement.setString(3, category.getCategory_id());
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//删除图书分类
	@Override
	public void delCategory(String category_id) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("delete from category where category_id=?");
			prepareStatement.setString(1, category_id);
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//查询所有的前台用户
	@Override
	public List<User> findUsers() {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement("select * from user");
			ResultSet rs = prepareStatement.executeQuery();
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setAddress(rs.getString("user_address"));
				user.setId(rs.getString("user_id"));
				user.setName(rs.getString("user_name"));
				user.setPassword(rs.getString("user_password"));
				user.setSex(rs.getString("user_sex"));
				user.setTel(rs.getString("user_tel"));
				user.setUsername(rs.getString("user_username"));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

	// 添加管理员
	@Override
	public void addAdmin(Administrator admin) {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("insert into administrator values(?,?,?,?,?)");
			prepareStatement.setString(1, admin.getUsername());
			prepareStatement.setString(2, admin.getPassword());
			prepareStatement.setString(3, admin.getName());
			prepareStatement.setString(4, admin.getSex());
			prepareStatement.setString(5, admin.getTel());

			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//查找销售记录
	@Override
	public List<Book> sales() {
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("select * from bookdb where book_xiaonumber>0 order by book_xiaonumber desc");
			ResultSet rs = prepareStatement.executeQuery();
			List<Book> books = new ArrayList<Book>();
			while (rs.next()) {
				Book book = new Book();
				book.setBook_id(rs.getString("book_id"));
				book.setBook_name(rs.getString("book_name"));
				book.setBook_author(rs.getString("book_author"));
				book.setBook_press(rs.getString("book_press"));
				Category category = findCategoryById(rs.getString("book_category"));
				book.setCategory(category);
				book.setFilename(rs.getString("filename"));
				book.setPath(rs.getString("path"));
				book.setBook_desc(rs.getString("book_desc"));
				book.setBook_price(rs.getDouble("book_price"));
				book.setBook_kunumber(rs.getInt("book_kunumber"));
				book.setBook_xiaonumber(rs.getInt("book_xiaonumber"));
				books.add(book);
			}
			return books;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

	//根据用户名查询管理员
	@Override
	public Administrator findAdminByUsername(String username) {
		// TODO Auto-generated method stub
		Administrator admin = new Administrator();
		try {
			Connection connection = Util.getConnection();
			PreparedStatement prepareStatement = connection
					.prepareStatement("select * from administrator where admin_username=?");
			prepareStatement.setString(1, username);
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) {
				admin.setUsername(rs.getString("admin_username"));
				admin.setPassword(rs.getString("admin_password"));
				admin.setName(rs.getString("admin_name"));
				admin.setSex(rs.getString("admin_sex"));
				admin.setTel(rs.getString("admin_tel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	//根据图书名称模糊查询
	@Override
	public List<Book> findListByBookName(String name) {
		Connection connection = Util.getConnection();
		List<Book> list = new ArrayList<Book>();
		try {
			PreparedStatement preparedStatement =connection.prepareStatement("select * from bookdb where book_name like ?");
			preparedStatement.setString(1, "%"+name+"%");
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				Book books = new Book();
				books.setBook_id(rs.getString("book_id"));
				books.setBook_name(rs.getString("book_name"));
				books.setBook_author(rs.getString("book_author"));
				books.setBook_press(rs.getString("book_press"));
				Category category = findCategoryById(rs.getString("book_category"));
				books.setCategory(category);
				books.setFilename(rs.getString("filename"));
				books.setPath(rs.getString("path"));
				books.setBook_desc(rs.getString("book_desc"));
				books.setBook_kunumber(rs.getInt("book_kunumber"));
				books.setBook_xiaonumber(rs.getInt("book_xiaonumber"));
				books.setBook_price(rs.getDouble("book_price"));
				list.add(books);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return list;
	}
}
