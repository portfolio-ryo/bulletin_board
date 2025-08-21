package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.LoginLogic;
import model.User;

/**
 * ログイン処理を担当するサーブレット。
 * 
 * <p>入力チェックと認証を行い、成功時はメインページへ遷移する。</p>
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ログインフォームのPOSTリクエストを処理する。
	 * 
	 * <p>ユーザー名とパスワードの入力を検証し、認証に成功した場合はセッションを開始してメインページへリダイレクトする。
	 * 失敗した場合はエラーメッセージと共にトップページへ戻る。</p>
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラーが発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String plainPass = request.getParameter("plainPass");
		
		LoginLogic loginService = new LoginLogic();

		if (loginService.isInvalidInput(name, plainPass)) {
			request.setAttribute("error", "名前とパスワードは必須です。");
			RequestDispatcher rd = request.getRequestDispatcher("top.jsp");
			rd.forward(request, response);
			return;
		}

		User user = new User(name, plainPass);

		if (loginService.authenticate(user)) {
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
