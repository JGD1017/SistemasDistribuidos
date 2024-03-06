package es.ubu.lsi;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

/**
 * Echo server with NIO.
 * 
 * @author Raúl Marticorena
 * @author Joaquín Seco
 *
 */
public class EchoServer {

	/** Port. */
	private int port;

	/** Byte buffer. */
	private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

	/**
	 * Constructor.
	 * 
	 * @param port
	 *            port
	 * @throws IOException
	 *             with problems with socket connection
	 */
	public EchoServer(int port) throws IOException {
		this.port = port;
		configureSelector();
	}

	/**
	 * Configuer selector.
	 * 
	 * @throws IOException
	 *             with problems with socket connection
	 */
	private void configureSelector() throws IOException {
		// Create a new selector
		Selector selector = Selector.open();

		// Open a listener on each port, and register each one
		// with the selector

		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ServerSocket ss = ssc.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ss.bind(address);

		SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("Going to listen on " + port);

		while (true) {
			int num = selector.select();

			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();

			while (it.hasNext()) {
				key = it.next();

				if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					// Accept the new connection
					ssc = (ServerSocketChannel) key
							.channel();
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);

					// Add the new connection to the selector
					SelectionKey newKey = sc.register(selector,
							SelectionKey.OP_READ);
					it.remove();

					System.out.println("Got connection from " + sc);
				} else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					// Read the data
					SocketChannel sc = (SocketChannel) key.channel();
					if (sc.isConnected() && sc.isOpen()) {
						// Echo data
						int bytesEchoed = 0;
						while (true) {
							echoBuffer.clear();

							int number_of_bytes = sc.read(echoBuffer);

							if (number_of_bytes <= 0) {
								break;
							}

							echoBuffer.flip();

							sc.write(echoBuffer);
							bytesEchoed += number_of_bytes;
						}
						String text = new String(echoBuffer.array()).trim();
						System.out.println("Echoed " + text + " with "
								+ bytesEchoed + " bytes from " + sc);
					}
					it.remove();
					key.cancel(); // remove from selector
				}

			}
		}
	}

	/**
	 * Main.
	 * 
	 * @param args aguments
	 * @throws Exception with any problem
	 */
	static public void main(String args[]) throws Exception {
		if (args.length <= 0) {
			System.err
					.println("Usage: java MultiPortEcho port");
			System.exit(1);
		}

		int port = Integer.parseInt(args[0]);
		new EchoServer(port);
	}
}