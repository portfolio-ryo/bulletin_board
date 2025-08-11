package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.TweetList;
import logic.TweetLogic;
import model.Tweet;

/**
 * メインページの表示と投稿を処理するサーブレット。
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * 投稿一覧を取得して表示する。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TweetList tweets = new TweetList();
		List<Tweet>tweetList=tweets.getAllTweets();
		
		request.setAttribute("tweetList", tweetList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * 投稿し、一覧を再表示する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String userName=(String)session.getAttribute("userName");
	    String text = request.getParameter("text");
	    
	    TweetLogic logic=new TweetLogic();
	    
	    String error = logic.validateTweet(userName, text);
	    if (error != null) {
	        request.setAttribute("error", error);
	        request.setAttribute("tweetList", logic.getAllTweets());

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
	    
	    logic.postTweet(userName, text);
	        
	    request.setAttribute("tweetList", logic.getAllTweets());
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
	    dispatcher.forward(request, response);
	}
}