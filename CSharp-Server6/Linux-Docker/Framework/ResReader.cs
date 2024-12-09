﻿using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System;
using System.Text;
using System.Web;
using System.Globalization;

namespace CSharp_Server6.Framework
{


    /// <summary>
    /// ResReader a simple resource file resx reader
    /// </summary>
    public static class ResReader
    {
        /// <summary>
        /// GetValue gets string resource form language specific resource file 
        /// </summary>
        /// <param name="key">unique key (culture independent) to address resource string</param>
        /// <param name="langCode">two letter long iso language code</param>
        /// <returns>string in local language fetched from resource file</returns>
        public static string GetValue(string key, string langCode = "")
        {
            string retVal = key;
            if (Properties.Resource.ResourceManager != null)
                retVal = Properties.Resource.ResourceManager?.GetString(key);

            return !string.IsNullOrEmpty(retVal) ? retVal : key;
        }


        /// <summary>
        /// GetRes gets string resource form language specific resource file 
        /// </summary>
        /// <param name="key">unique key (culture independent) to address resource string</param>
        /// <param name="ci">CultureInfo for currently used language</param>
        /// <returns>string in local language fetched from resource file</returns>
        public static string GetRes(string key, CultureInfo ci)
        {
            string lang2IsoToLower = ci != null ? ci.TwoLetterISOLanguageName.ToLower() : string.Empty;
            string? retVal = Properties.Resource.ResourceManager.GetString(key);

            return !string.IsNullOrEmpty(retVal) ? retVal : key.Replace("_", " ");
        }

        /// <summary>
        /// GetStringFormated gets a formated string from language specific resource file
        /// </summary>
        /// <param name="key">unique key (culture independent) to address resource string</param>
        /// <param name="ci">CultureInfo for currently used language</param>
        /// <param name="args">object[] arguments needed for <see cref="string.Format(string, object[])"/></param>
        /// <returns>string in local language fetched from resource file</returns>
        public static string GetStringFormated(string key, CultureInfo ci, params object[] args)
        {
            string lang2IsoToLower = ci != null ? ci.TwoLetterISOLanguageName.ToLower() : string.Empty;
            string? retVal = Properties.Resource.ResourceManager.GetString(key);
            string retValLang = retVal ?? string.Empty;

            if (!string.IsNullOrEmpty(retVal))
            {
                if (args != null && args.Length > 0 &&
                    retValLang.Contains("{") && retValLang.Contains("}") &&
                    (retValLang.Contains("{0}") || retValLang.Contains("{1}") || retValLang.Contains("{2}")))
                {
                    retVal = string.Format(retValLang, args);
                }
                return retVal;
            }

            return key.Replace("_", " ");
        }

        public static string GetAllFortunes()
        {
            string fortuneString = Encoding.UTF8.GetString(Properties.Resource.fortune_u8);
            return fortuneString;
        }

    }


}
