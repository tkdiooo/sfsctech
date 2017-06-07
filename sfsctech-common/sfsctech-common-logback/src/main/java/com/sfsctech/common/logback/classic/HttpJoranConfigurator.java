package com.sfsctech.common.logback.classic;

import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class HttpJoranConfigurator
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
public class HttpJoranConfigurator extends JoranConfigurator {

    public void doHttpConfigure(URL url) throws JoranException {
        InputStream in = null;
        try {
            informContextOfURLUsedForConfiguration(getContext(), url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // per http://jira.qos.ch/browse/LBCORE-105
            // per http://jira.qos.ch/browse/LBCORE-127
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            in = urlConnection.getInputStream();
            doConfigure(in);
        } catch (IOException ioe) {
            String errMsg = "Could not open URL [" + url + "].";
            addError(errMsg, ioe);
            throw new JoranException(errMsg, ioe);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    String errMsg = "Could not close input stream";
                    addError(errMsg, ioe);
                    throw new JoranException(errMsg, ioe);
                }
            }
        }
    }
}
