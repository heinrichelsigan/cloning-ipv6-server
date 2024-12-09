﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSharp_Server6.Framework
{
    /// <summary>
    /// Extension methods for Area23.At.Mono
    /// </summary>
    public static class Extensions
    {

        #region primitive types extensions

        /// <summary>
        /// <see cref="double"/>.IsRoundNumber() extension methods: checks, if a double is a round number
        /// </summary>
        /// <param name="d">double to check</param>
        /// <returns>true, if it's integer number</returns>
        public static bool IsRoundNumber(this double d)
        {
            return Math.Truncate(d) == d || Math.Round(d) == d;
        }

        /// <summary>
        /// <see cref="double"/>.ToLong() extension methods: converts a double to a long <see cref="long"/>
        /// </summary>
        /// <param name="d"></param>
        /// <returns></returns>
        public static long ToLong(this double d)
        {
            return Convert.ToInt64(d);
        }

        /// <summary>
        /// <see cref="double"/>.IsNan() extension methods: checks, if a double is not a number
        /// </summary>
        /// <param name="d">double to check</param>
        /// <returns>true, if dounble is not a number, otherwise false</returns>
        public static bool IsNan(this double d)
        {
            return double.IsNaN(d);
        }

        #endregion primitive types extensions

        #region DateTime extensions

        /// <summary>
        /// <see cref="DateTime"/>.Area23Date() extension method: formats <see cref="DateTime"/>.ToString("yyyy-MM-dd")
        /// </summary>
        /// <param name="dateTime"><see cref="DateTime"/></param>
        /// <returns>formatted date <see cref="string"/></returns>
        public static string Area23Date(this DateTime dateTime)
        {
            return dateTime.ToString("yyyy-MM-dd");
        }

        /// <summary>
        /// <see cref="DateTime"/>.Area23DateTime() extension method: formats <see cref="DateTime"/>.ToString("yyyy-MM-dd HH:mm")
        /// </summary>
        /// <param name="dateTime"><see cref="DateTime"/></param>
        /// <returns>formatted date time <see cref="string"/> </returns>
        public static string Area23DateTime(this DateTime dateTime)
        {
            return dateTime.ToString("yyyy") + Constants.DATE_DELIM +
                DateTime.UtcNow.ToString("MM") + Constants.DATE_DELIM +
                DateTime.UtcNow.ToString("dd") + Constants.WHITE_SPACE +
                DateTime.UtcNow.ToString("HH") + Constants.ANNOUNCE +
                DateTime.UtcNow.ToString("mm") + Constants.ANNOUNCE + Constants.WHITE_SPACE;
        }

        /// <summary>
        /// <see cref="DateTime"/>.Area23DateTimeWithSeconds() extension method: formats <see cref="DateTime"/>.ToString("yyyy-MM-dd_HH:mm:ss")
        /// </summary>
        /// <param name="dateTime">d</param>
        /// <returns><see cref="string"/> formatted date time including seconds</returns>
        public static string Area23DateTimeWithSeconds(this DateTime dateTime)
        {
            return dateTime.ToString("yyyy-MM-dd_HH:mm:ss");
        }

        /// <summary>
        /// <see cref="DateTime"/>.Area23DateTimeWithMillis() extension method: formats <see cref="DateTime"/>.ToString("yyyyMMdd_HHmmss_milis")
        /// </summary>
        /// <param name="dateTime"><see cref="DateTime"/></param>
        /// <returns>formatted date time <see cref="string"/> </returns>
        public static string Area23DateTimeWithMillis(this DateTime dateTime)
        {
            string formatted = string.Format("{0:yyyyMMdd_HHmmss}_{1}", dateTime, dateTime.Millisecond);
            // return formatted;
            return dateTime.ToString("yyyyMMdd_HHmmss_") + dateTime.Millisecond;
        }

        #endregion DateTime extensions

        #region stream_byteArray_string_extensions

        #region stream_extensions

        /// <summary>
        /// <see cref="Stream"/>.ToByteArray() extension method: converts <see cref="Stream"/> to <see cref="byte[]"/> array
        /// </summary>
        /// <param name="stream"><see cref="Stream"/> which static methods are now extended</param>
        /// <returns>binary <see cref="byte[]">byte[] array</see></returns>
        public static byte[] ToByteArray(this Stream stream)
        {
            if (stream is MemoryStream)
                return ((MemoryStream)stream).ToArray();
            else
            {
                using (MemoryStream ms = new MemoryStream())
                {
                    stream.CopyTo(ms);
                    return ms.ToArray();
                }
            }
        }

        #endregion stream_extensions

        #region byteArray_extensions


        /// <summary>
        /// <see cref="byte[]"/>.ArrayIndexOf(byte value) extension method: gets the first index of specified byte value
        /// </summary>
        /// <param name="bytes">byte[] to search</param>
        /// <param name="value">byte to find</param>
        /// <returns>index in array if found, otherwise -1</returns>
        public static int ArrayIndexOf(this byte[] bytes, byte value)
        {
            for (int bCnt = bytes.Length - 1; bCnt >= 0; bCnt--)
            {
                if (bytes[bCnt] != value)
                {
                    return bCnt;
                }
            }
            return -1;
        }

        /// <summary>
        /// <see cref="byte[]"/>.ToHexString() extension method: converts byte[] to HexString
        /// </summary>
        /// <param name="bytes">Array of <see cref="byte"/></param>
        /// <returns>hexadecimal string</returns>
        public static string ToHexString(this byte[] bytes)
        {
            var sb = new StringBuilder();

            foreach (var t in bytes)
            {
                sb.Append(t.ToString("X2"));
            }

            return sb.ToString(); // returns: "48656C6C6F20776F726C64" for "Hello world"
        }


        /// <summary>
        /// <see cref="byte[]"/>.FindBytes extension method: searches hayStack for the first occurence of needle, 
        /// FindBytes uses static equivalent <see cref="BytesBytes(byte[], byte[], int)"/> 
        /// </summary>
        /// <param name="hayStack">byte[] of haystack to search through</param>
        /// <param name="needle">byte[] of needle to find</param>        
        /// <param name="matchBytes">match the only first matchBytes of needle, -1 for all bytes</param>
        /// <returns>index of first byte of matching needle in haystack</returns>
        public static int FindBytes(this byte[] hayStack, byte[] needle, int matchBytes = -1)
        {
            return BytesBytes(hayStack, needle, matchBytes);
        }

        /// <summary>
        /// BytesBytes static method: searches hayStack for the first occurence of needle, 
        /// BytesBytes was inspired by unix posix c function strstr 
        /// </summary>
        /// <param name="hayStack">byte[] of haystack to search through</param>
        /// <param name="needle">byte[] of needle to find</param>        
        /// <param name="matchBytes">match the only first matchBytes of needle, -1 for all bytes</param>
        /// <returns>index of first byte of matching needle in haystack</returns>
        public static int BytesBytes(byte[] hayStack, byte[] needle, int matchBytes = -1)
        {
            if (needle == null || needle.Length == 0 || hayStack == null || hayStack.Length == 0 || needle.Length > hayStack.Length)
                return -1;

            int needleIt = 0;
            for (int fFwdIt = 0; fFwdIt < hayStack.Length - needle.Length; fFwdIt++)
            {
                if (hayStack[fFwdIt] == needle[needleIt])
                {
                    if (needle.Length == 1)
                        return fFwdIt;

                    for (needleIt = 1; needleIt < needle.Length; needleIt++)
                    {
                        if (hayStack[fFwdIt + needleIt] != needle[needleIt])
                        {
                            needleIt = 0;
                            break;
                        }
                        if (matchBytes > 0 && needleIt == matchBytes)
                            return fFwdIt;

                        if (needleIt >= needle.Length - 1)
                            return fFwdIt;
                    }
                }
            }

            return -1;
        }

        /// <summary>
        /// <see cref="byte[]"/>.TarBytes extension method: tars 
        /// </summary>
        /// <param name="baseBytes">base byte array</param>
        /// <param name="bytesToAdd">more byte arrays</param>
        /// <returns>large tared byte array</returns>
        public static byte[] TarBytes(this byte[] baseBytes, params byte[][] bytesToAdd)
        {
            List<byte> largeBytesList = new List<byte>(baseBytes);

            foreach (byte[] bs in bytesToAdd)
            {
                largeBytesList.AddRange(bs);
            }

            return largeBytesList.ToArray();
        }

        /// <summary>
        /// TarBytes static method: tars all parameters of bytes array to one large byte array
        /// </summary>
        /// <param name="bytesToAdd">one up to many byte arrays</param>
        /// <returns>large tared byte array</returns>
        public static byte[] TarBytes(params byte[][] bytesToAdd)
        {
            List<byte> largeBytesList = new List<byte>();

            foreach (byte[] bs in bytesToAdd)
            {
                largeBytesList.AddRange(bs);
            }

            return largeBytesList.ToArray();
        }

        #endregion byte_array_extensions

        #region string_extensions

        /// <summary>
        /// <see cref="string"/>.FromHexString() extension method: converts hexadecimal string to byte[]
        /// </summary>
        /// <param name="hexString">hexadecimal string</param>
        /// <returns><see cref="byte[]">byte[]</see> Array of <see cref="byte"/></returns>
        public static byte[] FromHexString(this string hexString)
        {
            byte[] bytes = new byte[hexString.Length / 2];
            for (var i = 0; i < bytes.Length; i++)
            {
                bytes[i] = Convert.ToByte(hexString.Substring(i * 2, 2), 16);
            }

            return bytes; // returns: "Hello world" for "48656C6C6F20776F726C64"
        }

        /// <summary>
        /// <see cref="string"/>.FromHtmlToColor() extension methods: transforms hex #rrggbb string into <see cref="System.Drawing.Color"/>
        /// </summary>
        /// <param name="htmlRGBString"><see cref="string"/> to transform</param>
        /// <returns><see cref="System.Drawing.Color"/></returns>
        /// <exception cref="ArgumentException">invalid argument exception, in case of malformatted string</exception>
        public static System.Drawing.Color FromHtmlToColor(this string htmlRGBString)
        {
            if (string.IsNullOrWhiteSpace(htmlRGBString) || htmlRGBString.Length != 7 || !htmlRGBString.StartsWith("#"))
                throw new ArgumentException(
                    string.Format("System.Drawing.Color.FromHtml(string htmlRGBString = {0}), hex must be an rgb string in format \"#rrggbb\" like \"#3f230e\"!", htmlRGBString));

            System.Drawing.Color _color = System.Drawing.ColorTranslator.FromHtml(htmlRGBString);
            return _color;
        }

        #endregion string_extensions

        #endregion stream_byteArray_string_extensions

        #region System.Exception extensions

        /// <summary>
        /// <see cref="Exception"/>.ToLogMsg() extension method: formats an exception to a well formatted logging message
        /// </summary>
        /// <param name="exc">the <see cref="Exception">exception</see></param>
        /// <returns><see cref="string">logMsg</see></returns>
        public static string ToLogMsg(this Exception exc)
        {
            return string.Format("Exception {0} ⇒ {1}\t{2}\t{3}",
                    exc.GetType(),
                    exc.Message,
                    exc.ToString().Replace("\r", "").Replace("\n", " "),
                    exc.StackTrace?.Replace("\r", "").Replace("\n", " "));
        }

        #endregion System.Exception extensions

        #region System.Drawing.Color extensions

        /// <summary>
        /// <see cref="System.Drawing.Color"/>.FromHtml(string hex) extension method: gets color from hexadecimal rgb string html standard
        /// </summary>
        /// <param name="color">System.Drawing.Color.FromHtml(string hex) extension method</param>
        /// <param name="hex">hexadecimal rgb string with starting #</param>
        /// <returns>Color, that was defined by hexadecimal html standarized #rrggbb string</returns>
        public static System.Drawing.Color FromHtml(this System.Drawing.Color color, string hex)
        {
            if (string.IsNullOrWhiteSpace(hex) || hex.Length != 7 || !hex.StartsWith("#"))
                throw new ArgumentException(
                    string.Format("System.Drawing.Color.FromHtml(string hex = {0}), hex must be an rgb string in format \"#rrggbb\" like \"#3f230e\"!", hex));

            System.Drawing.Color _color = System.Drawing.ColorTranslator.FromHtml(hex);
            return _color;
        }

        /// <summary>
        /// <see cref="System.Drawing.Color"/>.FromXrgb(string hex) extension method: gets color from hexadecimal rgb string
        /// </summary>
        /// <param name="color">System.Drawing.Color.FromXrgb(string hex) extension method</param>
        /// <param name="hex">hexadecimal rgb string with starting #</param>
        /// <returns>Color, that was defined by hexadecimal rgb string</returns>
        public static System.Drawing.Color FromXrgb(this System.Drawing.Color color, string hex)
        {
            if (string.IsNullOrWhiteSpace(hex) || hex.Length < 6 || hex.Length > 9)
                throw new ArgumentException(
                    string.Format("System.Drawing.Color.FromXrgb(string hex = {0}), hex must be an rgb string in format \"#rrggbb\" or \"rrggbb\"", hex));

            string rgbWork = hex.TrimStart("#".ToCharArray());

            string colSeg = rgbWork.Substring(0, 2);
            colSeg = colSeg.Contains("00") ? "0" : colSeg.TrimStart("0".ToCharArray());
            int r = Convert.ToUInt16(colSeg, 16);
            colSeg = rgbWork.Substring(2, 2);
            colSeg = colSeg.Contains("00") ? "0" : colSeg.TrimStart("0".ToCharArray());
            int g = Convert.ToUInt16(colSeg, 16);
            colSeg = rgbWork.Substring(4, 2);
            colSeg = colSeg.Contains("00") ? "0" : colSeg.TrimStart("0".ToCharArray());
            int b = Convert.ToUInt16(colSeg, 16);

            return System.Drawing.Color.FromArgb(r, g, b);
        }

        /// <summary>
        /// <see cref="System.Drawing.Color"/>.FromRGB(byte r, byte g, byte b) extension method: gets color from R G B
        /// </summary>
        /// <param name="color">System.Drawing.Color.FromXrgb(string hex) extension method</param>
        /// <param name="r">red byte</param>
        /// <param name="g">green byte</param>
        /// <param name="b">blue byte</param>
        /// <returns>Color, that was defined by hexadecimal rgb string</returns>
        /// <exception cref="ArgumentException"></exception>
        public static System.Drawing.Color FromRGB(this System.Drawing.Color color, byte r, byte g, byte b)
        {
            return System.Drawing.Color.FromArgb(r, g, b);
        }

        /// <summary>
        /// <see cref="System.Drawing.Color"/>.ToXrgb() extension method: converts current color to hex string 
        /// </summary>
        /// <param name="color">current color</param>
        /// <returns>hexadecimal #rrGGbb string with leading # character</returns>
        public static string ToXrgb(this System.Drawing.Color color)
        {
            string rx = color.R.ToString("X");
            rx = rx.Length > 1 ? rx : "0" + rx;
            string gx = color.G.ToString("X");
            gx = gx.Length > 1 ? gx : "0" + gx;
            string bx = color.B.ToString("X");
            bx = bx.Length > 1 ? bx : "0" + bx;

            string hex = string.Format("#{0}{1}{2}", rx, gx, bx);
            return hex.ToLower();
        }

        /// <summary>
        /// <see cref="System.Drawing.Color"/>.IsInLevenSteinDistance(Color colorCompare) extension method: finds out, if colorSrc and colorCompare are inside Levenstein distance
        /// </summary>
        /// <param name="colorSrc">source <see cref="System.Drawing.Color"/></param>
        /// <param name="colorCompare"><see cref="System.Drawing.Color"/> to compare with</param>
        /// <param name="levenSteinDelta">the absolute distance between to colors to tolerate</param>
        /// <returns>true, if both colors are inside Levenstein distance</returns>
        public static bool IsInLevenSteinDistance(this System.Drawing.Color colorSrc, System.Drawing.Color colorCompare, int levenSteinDelta = 6)
        {
            byte sRed = colorSrc.R;
            byte sGreen = colorSrc.G;
            byte sBlue = colorSrc.B;

            byte cRed = colorCompare.R;
            byte cGreen = colorCompare.G;
            byte cBlue = colorCompare.B;

            int deltaRed = Math.Abs(cRed - sRed);
            int deltaGreen = Math.Abs(cGreen - sGreen);
            int deltaBlue = Math.Abs(cBlue - sBlue);

            int distanceRGB = deltaRed + deltaGreen + deltaBlue;

            return distanceRGB <= levenSteinDelta;
        }

        #endregion System.Drawing.Color extensions

        #region genericsT_extensions

        /// <summary>
        /// <see cref="Stack{T}"/>.ReverseToString<typeparamref name="T"/> extension method: reverses a objects in a stack to a string
        /// </summary>      
        /// <typeparam name="T">type parameter for generic <see cref="Stack{T}"/></typeparam>
        /// <param name="stack">a generic  <see cref="Stack{T}">Stack</see></param>  
        /// <returns>a string concatenation of reversed (fifoed) stack</returns>
        public static string ReverseToString<T>(this Stack<T> stack)
        {
            string reverse = string.Empty;
            foreach (object s in stack.Reverse().ToArray())
            {
                reverse += s.ToString();
            }
            return reverse;
        }


        #endregion genericsT_extensions

    }

}
