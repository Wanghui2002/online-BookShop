package com.demo.web.controller;

import com.demo.domain.Administrator;
import com.demo.domain.Book;
import com.demo.domain.Category;
import com.demo.domain.User;
import com.demo.service.ManagerService;
import com.demo.service.impl.ManagerServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/admin/ManagerServlet")
public class ManagerServlet extends HttpServlet {
    private ManagerService service = new ManagerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String op = req.getParameter("op");// 得到传过来的请求
        // 管理员登陆
        if (op.equals("login")) {
            login(req, resp);
        }
        // 修改管理员资料
        if (op.equals("managerInformation")) {
            managerInformation(req, resp);
        }
        // 修改管理员登录密码
        if (op.equals("managerPassword")) {
            managerPassword(req, resp);
        }
        // 注销
        if (op.equals("layout")) {
            layout(req, resp);
        }
        // 添加书籍前先获取所有分类
        if (op.equals("addBookUI")) {
            addBookUI(req, resp);
        }
        // 添加书籍
        if (op.equals("addBook")) {
            try {
                addBook(req, resp);
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // 添加书籍分类
        if (op.equals("addCategory")) {
            addCategory(req, resp);
        }
        // 文学艺术类书籍列表
        if (op.equals("category")) {
            getCategoryBook(req, resp);
        }
        // 编辑书籍信息前获取书籍的信息回显
        if (op.equals("editBookUI")) {
            editBookUI(req, resp);
        }
        // 编辑书籍
        if (op.equals("editBook")) {
            try {
                editBook(req, resp);
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // 删除书籍
        if (op.equals("delBook")) {
            delBook(req, resp);
        }
        // 获取书籍分类列表
        if (op.equals("categoryList")) {
            categoryList(req, resp);
        }
        // 获得分类信息
        if (op.equals("editCategoryUI")) {
            editCategoryUI(req, resp);
        }
        // 修改书籍分类信息
        if (op.equals("editCategory")) {
            editCategory(req, resp);
        }
        // 删除书籍分类
        if (op.equals("delCategory")) {
            delCategory(req, resp);
        }
        // 用户信息管理
        if (op.equals("findUsers")) {
            findUsers(req, resp);
        }
        // 添加工作人员
        if (op.equals("addAdmin")) {
            addAdmin(req, resp);
        }
        // 书籍销售情况
        if (op.equals("sales")) {
            sales(req, resp);
        }
        //搜索
        if (op.equals("search")) {
            search(req, resp);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("search");
        if (name == null) {
            name = "";
        }
        List<Book> findListByBookName = service.findListByBookName(name);
        List<Category> categoryList = service.findAllCategory();// 分类
        req.setAttribute("currentName", name);
        req.setAttribute("books", findListByBookName);
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/admin/booksList.jsp").forward(req, resp);
    }

    private void sales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> sales = service.sales();
        req.setAttribute("sales", sales);
        req.getRequestDispatcher("/admin/sales.jsp").forward(req, resp);
    }

    private void addAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String tel = req.getParameter("tel");
        Administrator admin = new Administrator(username, password, name, sex, tel);
        service.addAdmin(admin);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>添加成功！</div>");
    }

    private void findUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = service.findUsers();
        HttpSession session = req.getSession();
        session.setAttribute("users", list);
        req.getRequestDispatcher("/admin/managerUsers.jsp").forward(req, resp);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || username == "") {
            req.setAttribute("message", "请填写用户名");
            req.getRequestDispatcher("/admin/404.jsp").forward(req, resp);
            return;
        }
        if (password == null || password == "") {
            req.setAttribute("message", "请填写密码");
            req.getRequestDispatcher("/admin/404.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        Administrator admin = service.login(username, password);
        if (admin.getUsername() != null && admin.getUsername() != "") {
            req.setAttribute("message", "登陆成功");
            session.setAttribute("admin", admin);
            req.getRequestDispatcher("/admin/message.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "账号或密码错误!");
            req.getRequestDispatcher("/admin/404.jsp").forward(req, resp);
        }
    }

    private void managerInformation(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String tel = req.getParameter("tel");
        service.managerInformation(username, name, sex, tel);
        HttpSession session = req.getSession();
        Administrator admin = (Administrator) session.getAttribute("admin");
        admin.setName(name);
        admin.setSex(sex);
        admin.setTel(tel);
        session.setAttribute("admin", admin);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>修改成功！</div>");

    }

    //修改管理员密码
    private void managerPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        String oldPassword = req.getParameter("oldPassword");
        if (!password.equals(repassword)) {
            resp.getWriter().print("<script>alert('两次密码不一致')</script>");
            return;
        }
        Administrator admin = service.findAdminByUsername(username);
        if (!admin.getPassword().equals(oldPassword)) {
            resp.getWriter().print("<script>alert('旧密码填写错误!')</script>");
            return;
        }
        service.managerPassword(username, password);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>修改成功！</div>");
    }

    private void layout(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            session.removeAttribute("admin");
            resp.sendRedirect("../admin/managerLogin.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBookUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categorys = service.findAllCategory();
        req.setAttribute("cs", categorys);
        req.getRequestDispatcher("/admin/addBook.jsp").forward(req, resp);

    }

    private void addBook(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException, IOException {
        // 判断表单是不是multipart/form-data类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            throw new RuntimeException("表单上传类型有误！！");
        }
        // 创建工厂用来 解析请求内容
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 构建核心类对象
        ServletFileUpload sfu = new ServletFileUpload(factory);
        List<FileItem> items = new ArrayList<FileItem>();
        items = sfu.parseRequest(req);
        Book book = new Book();
        for (FileItem item : items) {
            if (item.isFormField()) {
                // 普通字段：把数据分装到book对象中
                processFormField(item, req, book);
            } else {
                // 上传字段：上传
                processUplodFiled(item, req, book);
            }
        }
        // 把书籍信息保存到数据库中
        service.addBook(book);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>添加成功！</div>");
    }

    // 处理文件上传
    private void processUplodFiled(FileItem item, HttpServletRequest req, Book book) {
        try {
            // 存放路径：不要放在web-inf中
            // 01.获取存放文件的真实目录
            //  String dirImages = getServletContext().getRealPath("/images");
            String dirImages = req.getServletContext().getRealPath("/") + "images/";
//            String dirImages = getServletContext().getRealPath("/img");
            // 02. 通过io存文件
            // 03. 生成文件名 （用户上传图片， 图片名可能重复）
            String FieldName = item.getFieldName();// 输入框的name值
            String name = item.getName();
            String fileType = name.substring(name.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID().toString();// 生成用不重复的文件名
            // 生成文件夹名
            Date time = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strTime = simpleDateFormat.format(time);
            // path属性filename
            String path = strTime + File.separator + "books";// 存放到book对象中的路径
//            String path = "books";// 存放到book对象中
            String filename = fileName + "." + fileType;
            // fileDir：图片最终存在于fileDir
            File fileDir = new File(dirImages, path + File.separator + filename);
            // InputStream inputStream = item.getInputStream(); 从请求 对象中 通过流的方式
            // 把 上传的文件加载到 内存中 构建输出流
            File parentDir = new File(dirImages, path);// 父目录
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            book.setFilename(filename);
            book.setPath(path);

            InputStream inputStream = item.getInputStream();
            FileOutputStream fos = new FileOutputStream(fileDir);
            int len = 0;
            while ((len = inputStream.read()) != -1) {
                fos.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 把Fielditem中的数据封装到book中
    private void processFormField(FileItem item, HttpServletRequest req, Book book) {
        try {
            // item每一个item对象代表一个输入框
            // 01. 获取input输入框的 name 的值 根据规范 输入框 的name的取值， 与 javabean 中的 属性名一致
            String inputName = item.getFieldName();
            String inputValue = item.getString("UTF-8");
            if (inputName.equals("categoryid")) {// 分类单独处理
                // 获取到选择的分类的id，根据这个id取到分类的信息
                String categoryid = item.getString();
                System.out.println("categoryid=" + categoryid);
                Category category = service.findCategoryById(categoryid);
                System.out.println(category);
                book.setCategory(category);
            } else {
                BeanUtils.setProperty(book, inputName, inputValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加书籍分类
    private void addCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Category category = new Category();
            BeanUtils.populate(category, req.getParameterMap());
            String categoryName = category.getCategory_name();
            if (categoryName == null || categoryName.trim().equals("")) {
                resp.getWriter().print("<script>alert('请填写分类名称')</script>");
                resp.getWriter().print("<script>location.href='../admin/addCategory.jsp'</script>");
                return;
            }
            service.addCategory(category);// 调用添加方法
            resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                    + "/img/duigou.png'/>添加成功！</div>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoryBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = service.getCategoryBook(req.getParameter("cid"));// 文学艺术类书籍
        List<Category> categoryList = service.findAllCategory();// 分类
        req.setAttribute("books", books);
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("currentName", "");
        req.getRequestDispatcher("/admin/booksList.jsp").forward(req, resp);
    }

    private void editBookUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String book_id = req.getParameter("book_id");
        Book book = findBookById(book_id);
        List<Category> category = service.findAllCategory();
        HashMap map = new HashMap();
        map.put("book", book);
        map.put("category", category);
        req.setAttribute("map", map);
        req.getRequestDispatcher("/admin/editBook.jsp").forward(req, resp);
    }

    private void editBook(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException, IOException {
        String book_id = req.getParameter("book_id");
        String book_name = req.getParameter("book_name");
        String book_author = req.getParameter("book_author");
        String book_press = req.getParameter("book_press");
        String book_desc = req.getParameter("book_desc");
        double book_price = Double.parseDouble(req.getParameter("book_price"));
        String book_kunumber = req.getParameter("book_kunumber");
        String categoryId = req.getParameter("categoryid");
        Category findCategoryById = service.findCategoryById(categoryId);
        service.editBook(book_id, book_name, book_author, book_press, book_desc, book_price, book_kunumber, findCategoryById);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>修改成功！</div>");
    }

    private void delBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String book_id = req.getParameter("book_id");
        service.delBook(book_id);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>删除成功！</div>");
    }

    private void categoryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = service.findAllCategory();
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/admin/categorysList.jsp").forward(req, resp);
    }

    private void editCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Category category = service.findCategoryById(req.getParameter("category_id"));
        req.setAttribute("category", category);
        req.getRequestDispatcher("/admin/editCategory.jsp").forward(req, resp);
    }

    private void editCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Category category = new Category(req.getParameter("category_id"), req.getParameter("category_name"),
                req.getParameter("category_desc"));
        String categoryName = category.getCategory_name();
        if (categoryName == null || categoryName.trim().equals("")) {
            resp.getWriter().print("<script>alert('请填写分类名称')</script>");
            resp.getWriter().print("<script>location.href='../admin/editCategory.jsp'</script>");
            return;
        }
        service.editCategory(category);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>修改成功！</div>");
    }

    private void delCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String category_id = req.getParameter("category_id");
        service.delCategory(category_id);
        resp.getWriter().write("<div style='text-align: center;margin-top: 260px'><img src='" + req.getContextPath()
                + "/img/duigou.png'/>删除成功！</div>");
    }

    // 通过书籍id找到书籍信息
    private Book findBookById(String book_id) {
        return service.findBookById(book_id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
