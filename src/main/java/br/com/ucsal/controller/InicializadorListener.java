package br.com.ucsal.controller;

import br.com.ucsal.controller.annotation.Command;
import br.com.ucsal.controller.annotation.Inject;
import br.com.ucsal.controller.annotation.Rota;
import br.com.ucsal.controller.annotation.Singleton;
import br.com.ucsal.controller.managers.InjectionManager;
import br.com.ucsal.controller.managers.SingletonManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebListener
public class InicializadorListener implements ServletContextListener {

    private Map<String, Command> commands = new HashMap<>();


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources("");

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());

                if (directory.exists() && directory.isDirectory()) {
                    processDirectory(directory, context);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Inicializando recursos na inicialização da aplicação");
    }

    private void processDirectory(File directory, ServletContext context) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    processDirectory(file, context);
                } else if (file.getName().endsWith(".class")) {
                    try {
                        String className = file.getPath()
                                .replace(File.separator, ".")
                                .replaceFirst(".*?.classes.", "")
                                .replace(".class", "");
                         System.out.println(className);

                        Class<?> clazz = Class.forName(className, false, context.getClassLoader());
                        System.out.println(clazz.isAnnotationPresent(Inject.class));

                        // **Nova Verificação para Anotações**
                        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                            continue;
                        }

                        // Processamento existente...
                        if (clazz.isAnnotationPresent(Singleton.class)) {
                            SingletonManager.getInstance(clazz);
                            System.out.println("Classe anotada com @Singleton inicializada: " + className);

                        }

                        if (clazz.isAnnotationPresent(Rota.class)) {

                                Rota rota = clazz.getAnnotation(Rota.class);
                                Command servlet = (Command) clazz.getDeclaredConstructor().newInstance();
                            commands.put(rota.caminho(),servlet);
                            InjectionManager.injectDependencies(servlet);
                                System.out.println("Rota registrada: " + rota.caminho());

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Erro ao processar a classe: " + file.getName());
                    }

                }
            }


            context.setAttribute("command",commands);
        } else {
            System.err.println("Diretório inválido ou vazio: " + directory.getAbsolutePath());
        }
    }
}
