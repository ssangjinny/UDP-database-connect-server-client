package UDP_Server;

import java.net.*;

public class Server {

	public static void main(String[] args) {

		DatagramSocket dsock = null;

		int port = 0;

		if (args.length != 1) {

			System.out.println("���ڷ� ��Ʈ��ȣ ���� �Է��� �ּ���.");

			System.exit(1);
		}

		try {
			port = Integer.parseInt(args[0]);
		}

		catch (Exception e) {

			System.out.println("��Ʈ ��ȣ�� ������ ��ȯ���� ���߽��ϴ�.");

			System.exit(1);
		}

		if (port < 0 && port > 65535) {

			System.out.println("��Ʈ ��ȣ�� ������ ������ϴ�.");

			System.exit(1);
		}

		try {

			dsock = new DatagramSocket(port);

		} catch (SocketException e) {

			e.printStackTrace();

		}

		ServerThread Run1 = new ServerThread(dsock);
		ServerThread Run2 = new ServerThread(dsock);

		System.out.println("���� ������....");

		try {
			Run1.start();
			Run2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}