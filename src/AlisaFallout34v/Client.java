package AlisaFallout34v;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket;//Сокет для общения
    private static BufferedReader reader;//reader для чтения с консоли текста который будет передваться
    private static BufferedReader in;//Поток чтения из сокета
    private static BufferedWriter out;//Поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                //Адрес - localhost с таким же портом как у сервера
                clientSocket = new Socket("localhost",4004);//Запрос доступа на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                //чтение и запись сообщений с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while (true) {
                    System.out.println("Enter your message");
                    //Если соединение прошло и потоки успешно созданы можно предложить что то ввести иначе исключение
                    String word = reader.readLine();//Ждём сообщения клиента
                    out.write(word + "\n");//Отправляем сообщение на сервер (\n в конце обязательно!)
                    out.flush();//Выталкиваем все из буфера
                    String serverWord = in.readLine();//Ждём сообщение от сервера
                    System.out.println(serverWord);//Выводим его на экран
                    if (word.equals("close")) break;
                }
            }
            finally {
                //Закрытие всех сокетов и потоков
                System.out.println("Client was closed");
                clientSocket.close();
                in.close();
                out.close();
            }
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
}
