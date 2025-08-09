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

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		

		if (name == null || name.isEmpty() || pass == null || pass.isEmpty()) {
			request.setAttribute("error", "名前とパスワードは必須です。");
			RequestDispatcher rd = request.getRequestDispatcher("top.jsp");
			rd.forward(request, response);
			return;
		}

		User user = new User(name, pass);
		UserDAO dao = new UserDAO();

		if (dao.login(user)) {
			HttpSession session = request.getSession();
			session.setAttribute("userName", name);
			response.sendRedirect("Main");
		} else {
			request.setAttribute("error", "ユーザー名またはパスワードが間違っています。");
			RequestDispatcher rd = request.getRequestDispatcher("top.jsp");
			rd.forward(request, response);
		}
	}

}
