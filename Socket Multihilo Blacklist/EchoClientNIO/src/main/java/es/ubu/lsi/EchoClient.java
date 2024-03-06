package es.ubu.lsi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Echo client with NIO.
 * 
 * @author Raúl Marticorena
 * @author Joaquín Seco
 *
 */
public class EchoClient implements Runnable {
	
	/** Text. */
	private String message;
	
	/** Selector. */
	private Selector selector;

	/** Flag. */
	private boolean flagContinue = true;

	/**
	 * Main method.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		String string1 = "Sending a test message"; // dummy message
		EchoClient test1 = new EchoClient(string1);
		Thread thread = new Thread(test1);
		thread.start();
	}

	/**
	 * Constructor.
	 * 
	 * @param message text message
	 */
	public EchoClient(String message) {
		this.message = message;
	}

	/***
	 * Run.
	 */
	@Override
	public void run() {
		SocketChannel channel;
		try {
			selector = Selector.open(); 
			channel = SocketChannel.open();
			channel.configureBlocking(false); // non-blocking

			channel.register(selector, SelectionKey.OP_CONNECT);
			channel.connect(new InetSocketAddress("127.0.0.1", 8000));

			while (flagContinue) {

				selector.select(1000);

				Iterator<SelectionKey> keys = selector.selectedKeys()
						.iterator();

				while (keys.hasNext()) {
					SelectionKey key = keys.next();
					keys.remove();

					if (key.isConnectable()) {
						System.out.println("I am connected to the server");
						connect(key);
					}
					if (key.isWritable()) {
						write(key);
					}
					if (key.isReadable()) {
						read(key);
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Close resources.
	 */
	private void close() {
		try {
			selector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads message from server.
	 * 
	 * @param key key 
	 * @throws IOException with problems with socket connection
	 */
	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(1000);
		readBuffer.clear();
		int length;
		try {
			length = channel.read(readBuffer);
		} catch (IOException e) {
			System.out.println("Reading problem, closing connection");
			key.cancel();
			channel.close();
			return;
		}
		if (length == -1) {
			System.out.println("Nothing was read from server");
			channel.close();
			key.cancel();
			return;
		}
		readBuffer.flip();
		byte[] buff = new byte[length];
		readBuffer.get(buff, 0, length);
		System.out.println("Server said: " + new String(buff));
		flagContinue = false;
		key.cancel();
	}

	/**
	 * Writes text to server.
	 * 
	 * @param key key
	 * @throws IOException with problems with socket connection
	 */
	private void write(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		channel.write(ByteBuffer.wrap(message.getBytes()));
		// lets get ready to read.
		key.interestOps(SelectionKey.OP_READ);
	}

	/**
	 * Connects with server.
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void connect(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		if (channel.isConnectionPending()) {
			channel.finishConnect();
		}
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_WRITE);
	}
}