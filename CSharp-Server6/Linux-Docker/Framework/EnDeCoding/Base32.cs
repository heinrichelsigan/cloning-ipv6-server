﻿/*
 * Derived from https://github.com/google/google-authenticator-android/blob/master/AuthenticatorApp/src/main/java/com/google/android/apps/authenticator/Base32String.java
 * 
 * Copyright (C) 2016 BravoTango86
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;

namespace CSharp_Server6.Framework.EnDeCoding
{
    /// <summary>
    /// Base32 encoding is a mapping for double hex from A-Z0-7 (32 chiffers per digit)
    /// <see href="https://gist.github.com/erdomke/9335c394c5cc65404c4cf9aceab04143"/>
    /// </summary>
    public static class Base32
    {
        private static readonly char[] _digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=".ToCharArray();
        private static List<char> ValidCharList = new List<char>(_digits);
        private const int _mask = 31;
        private const int _shift = 5;

        private static int CharToInt(char c)
        {
            int bigA = 'A', litA = 'a', iChar = c;

            if (char.IsUpper(c))
                return iChar - bigA;
            else if (char.IsLower(c))
                return iChar - litA;
            else if (char.IsDigit(c) || char.IsNumber(c))
                switch (c)
                {
                    case '2': return 26;
                    case '3': return 27;
                    case '4': return 28;
                    case '5': return 29;
                    case '6': return 30;
                    case '7': return 31;
                    default: break;
                }

            return -1;
        }

        /// <summary>
        /// FromBase32 converts a base32 string to a binary byte array
        /// </summary>
        /// <param name="encoded">base32 encoded string</param>
        /// <returns>byte array</returns>
        /// <exception cref="ArgumentNullException"></exception>
        /// <exception cref="FormatException"></exception>
        public static byte[] FromBase32(string encoded)
        {
            if (encoded == null)
                throw new ArgumentNullException(nameof(encoded));

            // Remove whitespace and padding. Note: the padding is used as hint 
            // to determine how many bits to decode from the last incomplete chunk
            // Also, canonicalize to all upper case
            encoded = encoded.Trim().TrimEnd('=').ToUpper();
            if (encoded.Length == 0)
                return new byte[0];

            var outLength = encoded.Length * _shift / 8;
            var result = new byte[outLength];
            var buffer = 0;
            var next = 0;
            var bitsLeft = 0;
            var charValue = 0;
            foreach (var c in encoded)
            {
                charValue = CharToInt(c);
                if (charValue < 0)
                    throw new FormatException("Illegal character: `" + c + "`");

                buffer <<= _shift;
                buffer |= charValue & _mask;
                bitsLeft += _shift;
                if (bitsLeft >= 8)
                {
                    result[next++] = (byte)(buffer >> bitsLeft - 8);
                    bitsLeft -= 8;
                }
            }

            return result;
        }

        /// <summary>
        /// ToBase32
        /// </summary>
        /// <param name="data">binary data in byte array to convert</param>
        /// <param name="padOutput">block padding with =</param>
        /// <returns>Base32 encoded string</returns>
        public static string ToBase32(byte[] data, bool padOutput = true)
        {
            return ToBase32(data, 0, data.Length, padOutput);
        }

        public static string ToBase32(byte[] data, int offset, int length, bool padOutput = true)
        {
            if (data == null)
                throw new ArgumentNullException(nameof(data));

            if (offset < 0)
                throw new ArgumentOutOfRangeException(nameof(offset));

            if (length < 0)
                throw new ArgumentOutOfRangeException(nameof(length));

            if (offset + length > data.Length)
                throw new ArgumentOutOfRangeException();

            if (length == 0)
                return "";

            // SHIFT is the number of bits per output character, so the length of the
            // output is the length of the input multiplied by 8/SHIFT, rounded up.
            // The computation below will fail, so don't do it.
            if (length >= 1 << 28)
                throw new ArgumentOutOfRangeException(nameof(data));

            var outputLength = (length * 8 + _shift - 1) / _shift;
            var result = new StringBuilder(outputLength);

            var last = offset + length;
            int buffer = data[offset++];
            var bitsLeft = 8;
            while (bitsLeft > 0 || offset < last)
            {
                if (bitsLeft < _shift)
                {
                    if (offset < last)
                    {
                        buffer <<= 8;
                        buffer |= data[offset++] & 0xff;
                        bitsLeft += 8;
                    }
                    else
                    {
                        int pad = _shift - bitsLeft;
                        buffer <<= pad;
                        bitsLeft += pad;
                    }
                }
                int index = _mask & buffer >> bitsLeft - _shift;
                bitsLeft -= _shift;
                result.Append(_digits[index]);
            }

            if (padOutput)
            {
                int padding = 8 - result.Length % 8;
                if (padding > 0) result.Append('=', padding == 8 ? 0 : padding);
            }

            return result.ToString();
        }

        public static bool IsValidBase32(string inString)
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