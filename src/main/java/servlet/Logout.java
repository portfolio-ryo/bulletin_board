package servlet;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログアウト処理を行うサーブレット。
 * 
 * <p>セッションを無効化し、トップページにリダイレクトする。</p>
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * セッションを破棄し、トップページへリダイレクトする。
	 *
	 * @param request  クライアントからのリクエスト
	 * @param response サーバーからのレスポンス
	 * @throws ServletException サーブレットエラーが発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		session.invalidate();
		
		response.sendRedirect("top.jsp");
	}
}
