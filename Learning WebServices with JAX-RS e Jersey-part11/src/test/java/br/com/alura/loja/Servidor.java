package br.com.alura.loja;
import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

//Essa classe eh o meu servidor. 
//Eu posso iniciar o servidor pelo metodo Main() ou pelo metodo inicializaServidor() acesso de um objeto dessa classe


public class Servidor {

	public static void main(String[] args) throws IOException {
		
		
		HttpServer server = inicializaServidor();
				
		
		//Aguardar o botao enter para finalizar o servidor
		System.in.read();
		
		
		//Para o sevidor
		server.stop();
		
		
		System.out.println("\nServidor finalizado\n");
		
	}//main

	
	
	
	public static HttpServer inicializaServidor() {
		

		
		
		//Indico onde esta a classe "CarrinhoResource.java" que vai retornar os dados, que nesse caso sera em XML
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja.resource");
		
		
		//Crio a URI do servidor que vou utilizar
		URI uri = URI.create( "http://localhost:8080/" );
		
		
		//Criando o servidor local
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer( uri, config );
		
		
		System.out.println("\nServidor rodando\n\n");
		
		return server;
		
		
		
	}//inicializaServidor
	
	
	
	
}//class
