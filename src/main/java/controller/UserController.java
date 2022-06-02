package controller;

import dao.UserDao;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class UserController extends HttpServlet {
    private UserDao userDao;

    public UserController(){
        this.userDao = new UserDao();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();

        switch (action){
            case "/new":
                try{
                    showNewForm(request, response);
                } catch (IOException ex){
                    ex.printStackTrace();
                }catch (ServletException e) {
                    e.printStackTrace();
                }
                break;
            case "/insert":
                try{
                    insertUser(request, response);
                } catch (IOException ex){
                    ex.printStackTrace();
                }catch (ServletException e) {
                    e.printStackTrace();
                }
                break;
            case "/delete":
                try{
                    deleteUser(request, response);
                } catch (IOException ex){
                    ex.printStackTrace();
                }catch (ServletException e) {
                    e.printStackTrace();
                }
                break;
            case "/edit":
                try{
                    showEditForm(request, response);
                } catch (IOException ex){
                    ex.printStackTrace();
                }catch (ServletException e) {
                    e.printStackTrace();
                }
                break;
            case "/update":
                try{
                    updateUser(request, response);
                } catch (IOException ex){
                    ex.printStackTrace();
                }catch (ServletException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try{
                    listUser(request, response);
                } catch (IOException ex){
                    ex.printStackTrace();
                }catch (ServletException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDao.selectAllUser();
        request.setAttribute("listUser", users);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User updatedUser = new User(id, name, email, country);
        userDao.updateUser(updatedUser);

        response.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        User selectedUser = userDao.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", selectedUser);
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(id);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User newUser = new User(name, email, country);
        userDao.insertUser(newUser);

        response.sendRedirect("list");
    }


}
