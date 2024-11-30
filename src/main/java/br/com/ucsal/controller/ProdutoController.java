package br.com.ucsal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.ucsal.controller.annotation.Command;
import br.com.ucsal.controller.annotation.Rota;
import br.com.ucsal.controller.managers.InjectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view/*")
public class ProdutoController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        System.out.println(path);
        String view;
        Command command;

        Map<String,Command>commands = (Map<String, Command>) request.getServletContext().getAttribute("command");
        if (path.equals("/")){
            view="/listarProdutos";
             command = commands.get(view);
        }else {
            command = commands.get(path);
        }

        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada");
        }
    }

}
