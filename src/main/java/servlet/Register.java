package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;


@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
				
		if (name == null || name.isEmpty() || pass == null || pass.isEmpty()) {
            request.setAttribute("error", "ユーザー名とパスワードは必須です。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
            return;
		}
		
		if (pass.length() < 4) {
	        request.setAttribute("error", "パスワードは4文字以上で入力してください。");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
		
		UserDAO dao = new UserDAO();
        if (dao.duplication(name)) {
            request.setAttribute("error", "すでに同じ名前のアカウントがあります。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
		
		User user = new User(name, pass);
        boolean success = dao.insert(user);
        
        if (success) {
        	HttpSession session = request.getSession();
            session.setAttribute("userName", name);
            response.sendRedirect("Main");
        } else {
            request.setAttribute("error", "登録に失敗しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
        }
}
}