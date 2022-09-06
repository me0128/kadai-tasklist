package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasklist;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId()))
            ;

        EntityManager em = DBUtil.createEntityManager();

        //セッションスコープからメモのIDを取得
        //該当のIDのメモ１件のみをデータベースから取得
        Tasklist t = em.find(Tasklist.class, (Integer) (request.getSession().getAttribute("tasks_id")));

        //フォーム内容を各フィールドに上書き
        String content = request.getParameter("content");
        t.setContent(content);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        t.setUpdated_at(currentTime);

        //データベースを更新
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        //セッションスコープ上の不要データ削除
        request.getSession().removeAttribute("tasks_id");
        response.sendRedirect(request.getContextPath() + "/index");
    }
}
