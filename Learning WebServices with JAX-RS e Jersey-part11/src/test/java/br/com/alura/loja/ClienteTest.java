package br.com.alura.loja;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

//Esse programa eh um teste para ver se consigo pegar o XMl do link "http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d"


public class ClienteTest {
	
	
	private HttpServer server;
	private WebTarget  target;
	private Client     client;


	@Before    // Isso faz com que esse metodo sempre seja executado antes de qualquer outro
	public void startaServidor(){

		
		//inicia o servidor
		server = Servidor.inicializaServidor();
		
		
		//Criando uma configuracao para o Client. Esto usando isso so para usar o LOG na linha de comando a seguir
		ClientConfig config = new ClientConfig();
		
		
		//Adicioando uma API de logo. Ou seja, tudo entre client and Server vai ser imprimido no LOG.
		config.register(new LoggingFilter());
		
		
		//Criando um obj Client e passando a configuracao para ele
		this.client = ClientBuilder.newClient( config );
		
		
		//Informo o target, ou seja de onde vou pegar os dados
		this.target = client.target("http://localhost:8080");
		
	}//@Before
	
	

	
	
	@Test
	public void testaSeAConexaoComOServidorFunciona(){
		
		
		//----Cliente fazendo uma requisicao----//
				
		
		//Faco a requisicao GET e pego o conteudo retornado
		String conteudoRetornado = target.path("/carrinhos/1").request().get( String.class );
		
		System.out.println( "**Primeiro get***\n" );
		System.out.println( conteudoRetornado );
		
		
		
		//Pegando o conteudo retornado do servidor em XML e transformando em um obj "Carrinho"
		Carrinho carrinho = (Carrinho) new XStream().fromXML( conteudoRetornado );
		
		
		
		//Verificando se o conteudo retornado eh o mesmo que eu espero. Eh so um teste de checagem de conteudo
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua() ) ;
		
		

	}//class
	
	
	
	

	@Test //Metodo que inseri um objeto no BD via "POST"
	public void testaQueSuportaNovosCarrinhos(){
		
		//Cria um obj do tipo "Carrinho" no qual vai ser enviado para o BD via POST
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona ( new Produto(314, "Microfone", 37, 1) );
		carrinho.setRua   ("Street example");
		carrinho.setCidade("Vancouver"     );
		
		//Transformo o obj em XML
		String xml = carrinho.toXML();
			
		
		//Prepraro um "entity" que eh  XML + Descricao dizendo que vou enviar dados no formato XML
		Entity<String> entity = Entity.entity( xml,  MediaType.APPLICATION_XML );
		

		

		//Fazendo a requisicao e recebendo a resposta do servidor, ou seja o status code
		Response response =  target.path("/carrinhos").request().post( entity );
		
		
		//Verifica se o status code foi 201 (signfica que deu tudo certo)
		Assert.assertEquals( 201, response.getStatus() );
		
		System.out.println( "**\n\nO POST do conteudo abaixo foi feito \n\n***\n" );
		System.out.println( xml );
		
		
		
		
		//***Agora vou fazer um GET para ver o que inseri no BD com o POST anterior***
		
		
		//Pegando a uri do objeto que adicionei no BD pelo POST
		String location = response.getHeaderString("Location");
		
		
		//Fazendo a requisicao e recebendo o XML 
		String conteudoRetornado = client.target( location ).request().get( String.class );
		
		
		//Verificando se  conteudo tem a palavra 
		Assert.assertTrue( conteudoRetornado.contains("Microfone") );
		
		
		System.out.println( "\n\n**Fazendo um GET para pegar o conteudo que inseri com no POST anterior***\n" );
		System.out.println( conteudoRetornado );
		
		
	}//testaQueSuportaNovosCarrinhos
	
	
	
	
	
	
	
	@After // Isso faz com que esse metodo sempre seja executado apos os outros metodos
	public void finalizaServidor(){
		
		
		//----Para o sevidor----
		server.stop();
		
		
		System.out.println("\nServidor finalizado\n");
		
	}//finalizaServidor
	
	
	

}//class
