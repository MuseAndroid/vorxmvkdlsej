package kr.co.genexon.factFinder;

/**
 * Created by topgu on 2017-10-27.
 */

public class SelectServerURL {

//    String RealServerUrl = "http://m.factfinder.me"; // 상용서버
    String RealServerUrl = "https://m.factfinder.me"; // 상용서버(SSL 적용)
//    String DevServerUrl = "http://192.168.0.32:9449"; // 개발서버 (홍준호대리)
    String DevServerUrl = "http://192.168.0.49:8036"; // 개발서버 (정종태주임)
//    String DevServerUrl = "http://192.168.0.26:8081"; // 개발서버 (최윤식과장)
//    String DevServerUrl = "http://192.168.0.7:8080"; // 개발서버 (고기홍팀장)

    boolean devYN  = true;  // 개발
//    boolean devYN  = false; // 상용

    public SelectServerURL() {

    }

    public String getServerURL() {
        if (devYN == false) {
            return RealServerUrl;
        } else {
            return DevServerUrl;
        }
    }
}
