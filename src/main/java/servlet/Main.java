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

import dao.TweetListDAO;
import model.Tweet;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TweetListDAO dao=new TweetListDAO();
		List<Tweet>tweetList=dao.findAll();
		request.setAttribute("tweetList", tweetList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String userName=(String)session.getAttribute("userName");
	    String text = request.getParameter("text");
	    
	    if (userName == null || userName.isEmpty() || text == null || text.isEmpty()) {
	        request.setAttribute("error", "名前と投稿内容は必須です。");
	        
	        TweetListDAO dao = new TweetListDAO();
	        List<Tweet> tweetList = dao.findAll();
	        request.setAttribute("tweetList", tweetList);
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
	    
	    Tweet tweet = new Tweet(userName, text);
	    TweetListDAO dao=new TweetListDAO();
	    dao.insert(tweet);
	    
	    List<Tweet> tweetList = dao.findAll();
	    request.setAttribute("tweetList", tweetList);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
	    dispatcher.forward(request, response);
	}

}
