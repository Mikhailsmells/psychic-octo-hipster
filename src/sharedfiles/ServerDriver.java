package sharedfiles;

import sharedfiles.Board;

import java.net.*;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.io.*;


public class ServerDriver
{
	public static void main(String[] args)
	{
		String host;
		String address;
		int port = 8080;
		Board b= new Board();
		StringBuffer instr= new StringBuffer();
		System.out.println("Socket Started");
		try
		{
			address = "localhost";
			Socket connection = new Socket(address, port);
			PrintStream ps = new PrintStream(connection.getOutputStream());
			String process= "Accessing server on " + address + "port" + port;
			b.printBoard();			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while(true)
			{
				String r[]= new String[8];
				int i=0;
				String poopy;
				while ((poopy=br.readLine())!=".") 
				{
					if(poopy==null)
					{
						
					}
					else
					{
					r[i]=poopy;
					i++;
					}

			}
				Board c = new Board();
				b.buildBoard(r);
				AI poopyFart= new AI();
				poopyFart.setColor('W');
				b = poopyFart.takeTurn(b);
				
			
		}
	
	}catch(Exception e) {
		e.printStackTrace();
	}
}}