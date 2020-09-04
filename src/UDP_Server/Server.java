package UDP_Server;

import java.net.*;

public class Server {

	public static void main(String[] args) {

		DatagramSocket dsock = null;

		int port = 0;

		if (args.length != 1) {

			System.out.println("인자로 포트번호 값만 입력해 주세요.");

			System.exit(1);
		}

		try {
			port = Integer.parseInt(args[0]);
		}

		catch (Exception e) {

			System.out.println("포트 번호를 정수로 변환하지 못했습니다.");

			System.exit(1);
		}

		if (port < 0 && port > 65535) {

			System.out.println("포트 번호의 범위를 벗어났습니다.");

			System.exit(1);
		}

		try {

			dsock = new DatagramSocket(port);

		} catch (SocketException e) {

			e.printStackTrace();

		}

		ServerThread Run1 = new ServerThread(dsock);
		ServerThread Run2 = new ServerThread(dsock);

		System.out.println("서버 구동중....");

		try {
			Run1.start();
			Run2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}