package UDP_Client;

import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {

		if (args.length != 2) {

			System.out.println("���ڰ� 2��(ip, ��Ʈ��ȣ)�� �ƴմϴ�.");

			System.exit(1);

		}

		String ip = args[0];

		int port = 0;

		try {

			port = Integer.parseInt(args[1]);

		} catch (Exception e) {

			System.out.println("��Ʈ ��ȣ�� ������ ��ȯ���� ���߽��ϴ�.");

			System.exit(1);

		}

		if (port < 0 && port > 65535) {

			System.out.println("��Ʈ ��ȣ�� ������ ������ϴ�.");

			System.exit(1);
		}

		InetAddress inetaddr = null;

		try {
			inetaddr = InetAddress.getByName(ip);

		} catch (Exception e) {

			System.out.println("�߸��� �������̳� ip�Դϴ�.");

			System.exit(1);

		}

		DatagramSocket dsock = null;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			dsock = new DatagramSocket();

			String line = null;
			System.out.println("-------------------------------------\n");
			System.out.println("������ �Է� : insert userid name grade\n");
			System.out.println("������ ���� : delete userid\n");
			System.out.println("������ ���� : update columm1 value1 columm2 value2\n");
			System.out.println("DB ��� : select\n");
			System.out.println("Ŭ���̾�Ʈ ���� : quit\n");
			System.out.println("-------------------------------------\n");
			while ((line = br.readLine()) != null) {

				DatagramPacket sendPacket = new DatagramPacket(line.getBytes(), line.getBytes().length, inetaddr, port);

				dsock.send(sendPacket);

				if (line.equals("quit"))
					break;

				byte[] buffer = new byte[9999];

				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

				dsock.receive(receivePacket);

				String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);

				System.out.println(msg);

			}

			System.out.println("Client�� �����մϴ�.");

		} catch (Exception ex) {

			System.out.println(ex);

			System.exit(1);

		} finally {

			if (dsock != null)

				dsock.close();

		}

	}
}