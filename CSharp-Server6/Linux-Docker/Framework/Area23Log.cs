﻿using NLog;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSharp_Server6.Framework
{
    /// <summary>
    /// simple singelton logger via NLog
    /// </summary>
    public class Area23Log
    {
        private static readonly Lazy<Area23Log> instance = new Lazy<Area23Log>(() => new Area23Log());
        private static NLog.Logger logger = NLog.LogManager.GetCurrentClassLogger();

        /// <summary>
        /// LogFile
        /// </summary>
        public static string LogFile { get => LibPaths.LogPathFile; }


        /// <summary>
        /// Get the Logger
        /// </summary>
        public static Area23Log Logger { get => instance.Value; }


        /// <summary>
        /// LogStatic - static logger without Area23Log.Logger singelton
        /// </summary>
        /// <param name="msg">message to log</param>
        public static void LogStatic(string msg)
        {
            string logMsg = string.Empty;
            if (!File.Exists(LogFile))
            {
                try
                {
                    File.Create(LogFile);
                }
                catch (Exception ex)
                {
                    LogStatic(ex);
                }
            }
            try
            {
                logMsg = string.Format("{0} \t{1}\r\n",
                        Constants.DateArea23Seconds,
                        msg);
                File.AppendAllText(LogFile, logMsg);
            }
            catch (Exception)
            {
            }
        }

        /// <summary>
        /// LogStatic - static logger without Area23Log.Logger singelton
        /// </summary>
        /// <param name="exLog"><see cref="Exception"/> to log</param>
        public static void LogStatic(Exception exLog)
        {
            string excMsg = string.Format("Exception {0} ⇒ {1}\t{2}\t{3}",
                exLog.GetType(),
                exLog.Message,
                exLog.ToString().Replace("\r", "").Replace("\n", " "),
                exLog.StackTrace?.Replace("\r", "").Replace("\n", " "));

            if (!File.Exists(LogFile))
            {
                try
                {
                    File.Create(LogFile);
                }
                catch (Exception e)
                {
                    Console.WriteLine("Area23.At.Mono Exception: " + e.ToString());
                }
            }
            try
            {
                string logMsg = string.Format("{0} \t{1}\r\n",
                    Constants.DateArea23Seconds,
                    excMsg);
                File.AppendAllText(LogFile, logMsg);
            }
            catch (Exception e)
            {
                Console.WriteLine("Area23.At.Mono Exception: " + e.ToString());
            }
        }

        /// <summary>
        /// private Singelton constructor
        /// </summary>
        private Area23Log()
        {
            var config = new NLog.Config.LoggingConfiguration();
            // Targets where to log to: File and Console            
            var logfile = new NLog.Targets.FileTarget("logfile") { FileName = LogFile };
            var logconsole = new NLog.Targets.ConsoleTarget("logconsole");
            // Rules for mapping loggers to targets            
            config.AddRule(LogLevel.Trace, LogLevel.Trace, logconsole);
            config.AddRule(LogLevel.Debug, LogLevel.Debug, logfile);
            config.AddRule(LogLevel.Info, LogLevel.Info, logfile);
            config.AddRule(LogLevel.Warn, LogLevel.Warn, logfile);
            config.AddRule(LogLevel.Error, LogLevel.Error, logfile);
            config.AddRule(LogLevel.Fatal, LogLevel.Fatal, logfile);
            NLog.LogManager.Configuration = config; // Apply config
        }

        /// <summary>
        /// log - logs to NLog
        /// </summary>
        /// <param name="msg">debug msg to log</param>
        /// <param name="logLevel">log level: 0 for Trace, 1 for Debug, ..., 4 for Error, 5 for Fatal</param>
        public void Log(string msg, int logLevel = 3)
        {
            NLog.LogLevel nlogLvl = NLog.LogLevel.FromOrdinal(logLevel);
            logger.Log(nlogLvl, msg);
        }

        #region LogLevelLogger members

        public void LogDebug(string msg)
        {
            Log(msg, NLog.LogLevel.Debug.Ordinal);
        }

        public void LogInfo(string msg)
        {
            Log(msg, NLog.LogLevel.Info.Ordinal);
        }

        public void LogWarn(string msg)
        {
            Log(msg, NLog.LogLevel.Warn.Ordinal);
        }

        public void LogError(string msg)
        {
            Log(msg, NLog.LogLevel.Error.Ordinal);
        }

        #endregion LogLevelLogger members

        /// <summary>
        /// log Exception
        /// </summary>
        /// <param name="ex">Exception ex to log</param>
        /// <param name="level">log level: 0 for Trace, 1 for Debug, ..., 4 for Error, 5 for Fatal</param>
        public void Log(Exception ex, int level = 2)
        {
            Log(ex.Message, level);
            if (level < 4)
                Log(ex.ToString(), level);
            if (level < 2)
                Log(ex.StackTrace?.ToString(), level);
        }


        /// <summary>
        /// Log origin with message to NLog
        /// </summary>
        /// <param name="origin">origin of message</param>
        /// <param name="message">enabler message to log</param>
        /// <param name="level">log level: 0 for Trace, 1 for Debug, ..., 4 for Error, 5 for Fatal</param>
        public void LogOriginMsg(string origin, string message, int level = 2)
        {
            string logMsg = (string.IsNullOrEmpty(origin) ? "  \t" : origin + " \t") + message;
            Log(logMsg, level);
        }


        /// <summary>
        /// Log origin with message and thrown exception to NLog
        /// </summary>
        /// <param name="origin">origin of message</param>
        /// <param name="message">logging <see cref="string">string message</see></param>
        /// <param name="ex">logging <see cref="Exception">Exception ex</see></param>
        /// <param name="level"><see cref="int">int log level</see>: 0 for Trace, 1 for Debug, ..., 4 for Error, 5 for Fatal</param>
        public void LogOriginMsgEx(string origin, string message, Exception ex, int level = 2)
        {
            string logPrefix = string.IsNullOrEmpty(origin) ? "   " : origin;
            Log($"{logPrefix} \t{message} {ex.GetType()}: \t{ex.Message}", level);
            if (level < 4)
                Log($"{logPrefix} \tException {ex.GetType()}: \t{ex.ToString()}", level);
            if (level < 2)
                Log($"{logPrefix} \t{ex.GetType()} StackTrace: \t{ex.StackTrace}", level);
        }
    }


}
