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

    //private Map<String, Command> commands = new HashMap<>();


    /*@Override
    public void init() {
        mapAndInject("/editarProduto", new ProdutoEditarServlet());
        mapAndInject("/adicionarProduto", new ProdutoAdicionarServlet());
        mapAndInject("/excluirProduto", new ProdutoExcluirServlet());
        mapAndInject("/listarProdutos", new ProdutoListarServlet());
        mapAndInject("/", new ProdutoListarServlet()); // Routes the application root to list products
    }

    private void mapAndInject(String path, Command command) {
        InjectionManager.injectDependencies(command);
        commands.put(path, command);
    }*/
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        System.out.println(path);

        Map<String,Command>commands = (Map<String, Command>) request.getServletContext().getAttribute("command");
        Command command = commands.get(path);

        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada");
        }
    }

	


}
