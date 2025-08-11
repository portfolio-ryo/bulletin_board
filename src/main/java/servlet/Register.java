package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.RegisterLogic;

/**
 * 新規ユーザー登録を処理するサーブレット。
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザー登録フォームのPOSTリクエストを処理する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String plainPass = request.getParameter("plainPass");

		RegisterLogic service = new RegisterLogic();

		//入力値のバリデーションを実行
		String error = service.validate(name, plainPass);
		if (error != null) {
			request.setAttribute("error", error);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//重複ユーザーのチェック
		if (service.isDuplicateUser(name)) {
			request.setAttribute("error", "すでに同じ名前のアカウントがあります。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//ユーザーの登録処理を実行
		boolean success = service.registerUser(name, plainPass);
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
