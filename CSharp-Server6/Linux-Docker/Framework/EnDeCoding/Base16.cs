﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSharp_Server6.Framework.EnDeCoding
{
    /// <summary>
    /// Base16 hexadecimal byte encoding / decoding
    /// </summary>
    public static class Base16
    {

        private static readonly char[] _digits = "0123456789ABCDEF".ToCharArray();
        private static List<char> ValidCharList = new List<char>(_digits);

        /// <summary>
        /// ToBase16 converts a binary byte array to hex string
        /// </summary>
        /// <param name="inBytes">byte array</param>
        /// <returns>hex string</returns>
        /// <exception cref="ArgumentNullException"></exception>
        public static string ToBase16(byte[] inBytes)
        {
            if (inBytes == null || inBytes.Length < 1)
                throw new ArgumentNullException("inBytes", "public static string ToHex(byte[] inBytes == NULL)");

            string hexString = string.Empty;
            for (int wc = 0; wc < inBytes.Length; wc++)
            {
                hexString += string.Format("{0:x2}", inBytes[wc]);
            }

            string strUtf8 = hexString.ToUpper();

            return strUtf8;
        }


        /// <summary>
        /// FromBase16 transforms a hex string to binary byte array
        /// </summary>
        /// <param name="hexStr">a hex string</param>
        /// <returns>binary byte array</returns>
        /// <exception cref="ArgumentNullException"></exception>
        public static byte[] FromBase16(string hexStr)
        {
            if (string.IsNullOrEmpty(hexStr))
                throw new ArgumentNullException("hexStr", "public static byte[] FromHex(string hexStr), hexStr == NULL || hexStr == \"\"");

            List<byte> bytes = new List<byte>();

            if (hexStr.Length % 2 == 1)
                hexStr += "0";
            for (int wb = 0; wb < hexStr.Length; wb += 2)
            {
                char msb = hexStr[wb];
                char lsb = hexStr[wb + 1];
                string sb = msb.ToString() + lsb.ToString();
                byte b = Convert.ToByte(sb, 16);
                bytes.Add(b);
            }

            byte[] bytesUtf8 = EnDeCoder.GetBytes8(hexStr);
            // return bytesUtf8;
            return bytes.ToArray();

        }

        public static bool IsValidBase16(string inString)
        {
            foreach (char ch in inString)
            {
                if (!ValidCharList.Contains(ch))
                    return false;
            }
            return true;
        }

    }

}
