package br.com.alura.loja.modelo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;




public class Carrinho {

	private List<Produto> produtos = new ArrayList<Produto>();
	private String rua;
	private String cidade;
	private long   id;
	
	


	
	public long getId() {
		return id;
	}
	
	
	public Carrinho setId(long id) {
		this.id = id;
		
		return this;
	}

	
	public String getRua() {
		return rua;
	}
	

	public void setRua(String rua) {
		this.rua = rua;
	}
	
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	
	public Carrinho para( String rua, String cidade ) {
		this.rua = rua;
		this.cidade = cidade;
		
		return this;
	}
	
	

	public Carrinho adiciona( Produto produto ) {
		produtos.add(produto);
		return this;
	}

	
	
	
	public void remove(long id) {
		
		for ( Iterator iterator = produtos.iterator(); iterator.hasNext(); ) {
			
			Produto produto = (Produto) iterator.next();
			
			if(produto.getId() == id) {
				iterator.remove();
			}//if
			
		}//for
		
	}//remove
	
	
	
	public void troca(Produto produto) {
		remove(produto.getId());
		adiciona(produto);
	}//troca

	
	
	public void trocaQuantidade(Produto produto) {
		
		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto p = (Produto) iterator.next();
			if(p.getId() == produto.getId()) {
				p.setQuantidade(produto.getQuantidade());
				return;
			}
		}
	}//trocaQuantidade
	
	
	
	
	public List<Produto> getProdutos() {
		return produtos;
	}//List


	
	
	//Metodo que retorna o objeto inteiro dessa classe em formato XML
	public String toXML() {
		
		//Esse XStream eh uma biblioteca especifica, mas eu posso usar outras bibliotecas, ou retornar o que eu quiser
		return new XStream().toXML( this );
		

	}//toXML


	
	
	//Metodo que retorna o objeto inteiro dessa classe em formato JSON
	public String toJson() {
		
		
		//Usando a biblioteca Gson do java para converter meu objeto em Json e retornar		
		return new Gson().toJson( this );
	}//toJson

	
	
	
}//class
