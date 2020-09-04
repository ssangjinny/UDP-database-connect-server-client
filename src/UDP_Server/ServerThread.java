package UDP_Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerThread extends Thread {

	DatagramSocket dsock;
	DB dt = new DB();

	public ServerThread(DatagramSocket dsock) {
		this.dsock = dsock;
	}

	public void send(String msg, DatagramPacket Packet, DatagramSocket sock) throws IOException {
		DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, Packet.getAddress(),
				Packet.getPort());
		sock.send(sendPacket);
	}

	public void run() {
		DatagramPacket receivePacket = null;

		byte[] buffer = new byte[9999];

		int grade = 0;

		try {

			receivePacket = new DatagramPacket(buffer, buffer.length);

			while (true) {

				dsock.receive(receivePacket);

				String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());

				System.out.println("���� ���� ���ڿ� : " + msg);

				String[] str = msg.split(" ", 5);

				if (str[0].equals("insert")) {
					if (str.length == 4) {
						try {
							grade = Integer.parseInt(str[3]);
						} catch (Exception e) {
							msg = "grade ���� ���������� ��ȯ�� �� �����ϴ�.";
							send(msg, receivePacket, dsock);
							continue;
						}
						msg = dt.insert(str[1], str[2], grade);
						send(msg, receivePacket, dsock);
						continue;
					} else {
						msg = "insert ��ɾ �˸��� �ʽ��ϴ�. (insert userid name grade)";
						send(msg, receivePacket, dsock);
						continue;
					}
				}

				else if (str[0].equals("delete")) {
					if (str.length == 2) {
						msg = dt.delete(str[1]);
						send(msg, receivePacket, dsock);
						continue;
					} else {
						msg = "delete ��ɾ �˸��� �ʽ��ϴ�. (delete userid)";
						send(msg, receivePacket, dsock);
						continue;
					}
				} else if (msg.equals("select")) {
					msg = dt.select();
					send(msg, receivePacket, dsock);
					continue;
				} else if (str[0].equals("update")) {
					if (str.length == 5) {
						msg = dt.update(str[1], str[2], str[3], str[4]);
						send(msg, receivePacket, dsock);
						continue;
					} else {
						msg = "update ��ɾ �˸��� �ʽ��ϴ�.";
						send(msg, receivePacket, dsock);
						continue;
					}
				} else {
					msg = "�˸��� ���� ��ɾ� �Դϴ�.";
					send(msg, receivePacket, dsock);
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dsock.close();
		}
	}
}
