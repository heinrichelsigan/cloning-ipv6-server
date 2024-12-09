using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using CSharp_Server6.Framework.Net;
using System.Net.Sockets;
using System.Net.WebSockets;
using CSharp_Server6.Framework;


namespace CSharp_Server6
{
    public class Listener6
    {
        private static Socket? serverSocket;
        private static IPAddress? serverAddress;
        private Socket clientSocket;
        private IPEndPoint? clientIEP;
        private byte[] data = new byte[8192];
        Thread t;

        public Listener6(IPAddress address)
        {
            serverAddress = address;
            serverSocket = GetTcpServer(address);            
        }

        public void RunServer()
        {
            if (serverSocket != null)
            {
                while (true)
                { 
                    clientSocket = serverSocket.Accept();
                    t = new Thread(new ThreadStart(HandleClient));
                    t.Start();
                    Thread.Sleep(500);
                }
            }
        }


        public void HandleClient()
        {

            if (clientSocket != null)
            {
                clientIEP = (IPEndPoint?)clientSocket.RemoteEndPoint;
                byte[] receiveData = new byte[8192];
                int rsize = clientSocket.Receive(receiveData, 0, 8192, 0);
                Array.Copy(receiveData, data, rsize);
                string rstring = Encoding.Default.GetString(data, 0, rsize);
                Console.WriteLine(rstring);
                string sstring = serverAddress?.ToString() + " => " + clientIEP?.Address.ToString() + " : " + rstring;
                byte[] sendData = new byte[8192];
                sendData = Encoding.Default.GetBytes(sstring);
                clientSocket.Send(sendData);
                clientSocket.Close();
            }
        }


        

        public static Socket GetTcpServer(IPAddress address)
        {
            IPEndPoint ipEo = new IPEndPoint(address, Constants.CHAT_PORT);
            Socket socket = new Socket(address.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
                        
            socket.Bind(ipEo);
            socket.Listen(Constants.BACKLOG);

            return socket;
                
        }


    }
}
