package UDP_Client;

import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {

		if (args.length != 2) {

			System.out.println("인자가 2개(ip, 포트번호)가 아닙니다.");

			System.exit(1);

		}

		String ip = args[0];

		int port = 0;

		try {

			port = Integer.parseInt(args[1]);

		} catch (Exception e) {

			System.out.println("포트 번호를 정수로 변환하지 못했습니다.");

			System.exit(1);

		}

		if (port < 0 && port > 65535) {

			System.out.println("포트 번호의 범위를 벗어났습니다.");

			System.exit(1);
		}

		InetAddress inetaddr = null;

		try {
			inetaddr = InetAddress.getByName(ip);

		} catch (Exception e) {

			System.out.println("잘못된 도메인이나 ip입니다.");

			System.exit(1);

		}

		DatagramSocket dsock = null;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			dsock = new DatagramSocket();

			String line = null;
			System.out.println("-------------------------------------\n");
			System.out.println("데이터 입력 : insert userid name grade\n");
			System.out.println("데이터 삭제 : delete userid\n");
			System.out.println("데이터 수정 : update columm1 value1 columm2 value2\n");
			System.out.println("DB 출력 : select\n");
			System.out.println("클라이언트 종료 : quit\n");
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

			System.out.println("Client를 종료합니다.");

		} catch (Exception ex) {

			System.out.println(ex);

			System.exit(1);

		} finally {

			if (dsock != null)

				dsock.close();

		}

	}
}