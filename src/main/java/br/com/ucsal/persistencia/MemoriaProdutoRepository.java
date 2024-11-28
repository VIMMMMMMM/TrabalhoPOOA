package br.com.ucsal.persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import br.com.ucsal.controller.annotation.Singleton;
import br.com.ucsal.controller.managers.SingletonManager;
import br.com.ucsal.model.Produto;
@Singleton
public class MemoriaProdutoRepository implements ProdutoRepository<Produto, Integer>{

    private Map<Integer, Produto> produtos = new HashMap<>();
    private AtomicInteger currentId = new AtomicInteger(1);


    
    private MemoriaProdutoRepository() {
    }
    
    
    public static synchronized MemoriaProdutoRepository getInstancia() {
        return SingletonManager.getInstance(MemoriaProdutoRepository.class);
	}
    
    
    @Override
    public void adicionar(Produto entidade) {
        int id = currentId.getAndIncrement();
        entidade.setId(id);
        produtos.put(entidade.getId(), entidade);
    }
    
    @Override
    public void atualizar(Produto entidade) {
        produtos.put(entidade.getId(), entidade);
    }


    @Override
    public void remover(Integer id) {
        produtos.remove(id);
    }

    @Override
    public List<Produto> listar() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Produto obterPorID(Integer id) {
        return produtos.get(id);
    }


}