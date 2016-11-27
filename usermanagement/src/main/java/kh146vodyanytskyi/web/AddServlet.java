package kh146vodyanytskyi.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh146vodyanytskyi.User;
import kh146vodyanytskyi.db.DaoFactory;
import kh146vodyanytskyi.db.DatabaseException;

public class AddServlet extends HttpServlet {

	/**
     * 
     */
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }
    /**
     * 
     */
    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }

}
