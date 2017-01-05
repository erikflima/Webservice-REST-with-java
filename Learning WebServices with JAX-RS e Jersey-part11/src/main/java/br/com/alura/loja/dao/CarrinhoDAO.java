package br.com.alura.loja.dao;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;



public class CarrinhoDAO {
	
	
	//Criando um banco de dados na memoria
	private static Map<Long, Carrinho> banco    = new HashMap<Long, Carrinho>();
	private static AtomicLong          contador = new AtomicLong(1);
	
	
	//Crio um ojbeto carrinho, preencho esse objeto e coloco ele dentro do objeto "banco" que eh um lista de "Carrinho"
	static {
		
		
		//Inserindo um carrinho
		Produto videogame = new Produto(6237, "Videogame 4"    , 4000, 1);
		Produto esporte   = new Produto(3467, "Jogo de esporte", 60,   2);
		
		Carrinho carrinho = new Carrinho()
								.adiciona(videogame)
								.adiciona(esporte)
								.para("Rua Vergueiro 3185, 8 andar", "Sao Paulo")
								.setId(1l);
		banco.put(1l, carrinho);
		
		
		
		//Inserindo um carrinho
		Produto espadaSamurai = new Produto(6237, "Espada samurai"    , 900,  1);
		Produto dvd           = new Produto(50,   "Box - DVD de Anime", 60,   2);
		
		Carrinho carrinho2    = new Carrinho()
								.adiciona(espadaSamurai)
								.adiciona(dvd)
								.para("23 Main Street", "Philadelphia")
								.setId(2l);
		banco.put(2l, carrinho2);
		
		
		
		
	}
	
	
	public void adiciona( Carrinho carrinho ) {
		
		long id = contador.incrementAndGet();
		
		carrinho.setId( id );
		
		banco.put(id, carrinho);
	}
	
	

	//O retorno desse metodo eh um objeto do tipo "Carrinho"
	public Carrinho busca( Long id ) {
		return banco.get( id );
	}
	
	
	
	public Carrinho remove(long id) {
		return banco.remove(id);
	}

}//class
