package keystrokesmod.script.classes;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Request {
    public String method;
    public String url;
    public List<String[]> headers = new ArrayList<>();
    public String userAgent;
    public int connectionTimeout;
    public int readTimeout;
    public String content = "";

    public Request(String method, String URL) {
        if (!method.equals("POST") && !method.equals("GET")) {
            this.method = "GET";
        }
        else {
            this.method = method;
        }
        this.url = URL;
    }

    public void addHeader(String header, String value) {
        this.headers.add(new String[]{header, value});
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setConnectTimeout(int timeout) {
        this.connectionTimeout = timeout;
    }

    public void setReadTimeout(int timeout) {
        this.readTimeout = timeout;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Response fetch() {
        HttpURLConnection con = null;
        try {
            URL url = new URL(this.url);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(this.method);
            if (!userAgent.isEmpty()) {
                con.setRequestProperty("User-Agent", this.userAgent);
            }
            if (headers != null && !headers.isEmpty()) {
                for (String[] header : headers) {
                    con.setRequestProperty(header[0], header[1]);
                }
            }
            if (connectionTimeout > 0) {
                con.setConnectTimeout(connectionTimeout);
            }
            if (readTimeout > 0) {
                con.setReadTimeout(readTimeout);
            }
            if (!content.isEmpty() && method.equals("POST")) {
                con.setDoOutput(true);
                OutputStream stream = con.getOutputStream();
                stream.write(content.getBytes());
                stream.close();
                con.getInputStream().close();
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
        }
        if (con == null) {
            return null;
        }
        return new Response(con);
    }
}
