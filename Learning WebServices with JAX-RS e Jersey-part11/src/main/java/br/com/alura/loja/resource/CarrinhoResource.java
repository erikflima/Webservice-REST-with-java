package br.com.alura.loja.resource;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

//Essa classe pega informacao no DB e retorna para o cliente


@Path("carrinhos")               //Toda a classe deve ter esse @Path, que diz qual eh a URI do servidor no qual quero pegar os dados
public class CarrinhoResource {
	
	
	
	
	@Path("{id}")                                            //Dizendo que vou receber um parametro da requisicao que vai vir pela uri
	@GET                                                     //Digo que esse metodo deve ser acessado usando GET
	@Produces(MediaType.APPLICATION_XML)                     //Digo que qual eh  tipo do retorno,nesse caso XML
	public String busca( @PathParam ("id") long id ){       //Recebe o parametro que veio na requisicao pela uri, depois Vai no banco e retorna o conteudo
		
		
		//Busca o carrinho com o id que veio da requisicao pelo cliente
		Carrinho carrinho = new CarrinhoDAO().busca( id );
		
		
		//retorna um XML do objeto pego
		return carrinho.toXML();
		
	}//busca

	
	
	
	
	@POST                                                  //Digo que esse metodo deve ser acessado usando POST
	@Consumes(MediaType.APPLICATION_XML)                   //Digo que qual eh  tipo de dado espero receber do cliente, nesse caso XML
	public Response adiciona( String conteudoRecebido ){
		
		
		//Deserializando o dado recebido
		Carrinho carrinho = (Carrinho) new XStream().fromXML( conteudoRecebido );
		
		
		
		//Salvando o objeto recebido no DB
		new CarrinhoDAO().adiciona( carrinho );
		
		
		
		//Aqui eu crio o endereco do objeto que inseri no DB
		//Faco isso pegando o id do objeto que enseri no DB e concateno isso com o endereco do recurso
		URI uri = URI.create( "/carrinhos/" + carrinho.getId() );
		
		
		
		//Retorna para o client uma variavel do tipo "Response" que contem o status code da requisicao feita + a uri do objeto que fui inserido no db
		//O metodo.created() retorna o status code 201, que significa que o conteudo recebido foi inserido no banco de dados ou algo assim
		//Ai esse status vai aparecer na informacoes da requisicao
		return Response.created( uri ).build();
			
		
		
	}//adiciona
	
	
	
	
	
	
	@Path("{id}/produtos/{produtoId}")                                                                    //Dizendo os parametro a serem recebidos na requisicao que vai vir pela uri
	@DELETE                                                                                               //Digo que esse metodo deve ser acessado usando DELETE
	public Response removeProduto( @PathParam ("id") long id, @PathParam("produtoId") long produtoId ){   //Descrevo o tipo dos parametros recebidos
	
		//Busca o carrinho
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		//Delecao do carrinho
		carrinho.remove( produtoId );

		
		//Retorno o statusCode 200 ok
		return Response.ok().build();
		
		
	}//removeProduto
	
	
	
    //Altera a quantidade de produtos de um carrinho
	@Path("{id}/produtos/{produtoId}/quantidade")                                                                    //Dizendo os parametro a serem recebidos na requisicao que vai vir pela uri
	@PUT                                                                                                  //Digo que esse metodo deve ser acessado usando PUT (update)
	public Response alteraProduto( String conteudoRecebido, @PathParam ("id") long id, @PathParam("produtoId") long produtoId ){   //Descrevo o tipo dos parametros recebidos
	
		//Busca o carrinho
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		
		//Pegando o conteudo enviado na requisicao
		Produto  produto = (Produto) new XStream().fromXML( conteudoRecebido );
		
		
		//Update do produto do Carrinho
		carrinho.trocaQuantidade( produto );

		
		//Retorno o statusCode 200 ok
		return Response.ok().build();
		
		
	}//alteraProduto
	
	
	
	
	
	
	
	
	
	
	@Path("erik")
	@GET                                                     //Digo que esse metodo deve ser acessado usando GET
	@Produces(MediaType.APPLICATION_XML)                     //Digo que qual eh  tipo do retorno,nesse caso XML
	public String minhaFeatureTeste(){       
		
		

	
		//retorna um XML do objeto pego
		return "<status>It is working :) !</status>";
		
	}//minhaFeatureTeste
	
	

	
	
	
}//class
