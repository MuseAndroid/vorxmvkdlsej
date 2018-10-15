package kr.co.genexon.factFinder;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;

/**
 * Created by topgu on 2018-04-13.
 */

public class FTPSendClient {
    private static final String TAG = "FactFinder";
    public FTPClient mFtpClient = null;

    public FTPSendClient() {
        mFtpClient = new FTPClient();
    }

    public boolean ftpConnect(String host, String userName, String password, int port) {
        boolean result = false;
        try {
            mFtpClient.connect(host, port);

            if (FTPReply.isPositiveCompletion(mFtpClient.getReplyCode())) {
                result = mFtpClient.login(userName, password);
                mFtpClient.enterLocalPassiveMode();
            }
        } catch (Exception e) {
            Log.d(TAG, "Couldn't connect to host");
        }
        return result;
    }

    public boolean ftpDisConnect() {
        boolean result = false;
        try {
            mFtpClient.logout();
            mFtpClient.disconnect();
            result = true;
        } catch (Exception e) {
            Log.d(TAG, "Failed to disconnect with server");
        }
        return result;
    }

    public boolean ftpChangeDirctory(String directory) {
        try{
            mFtpClient.changeWorkingDirectory(directory);
            return true;
        }catch (Exception e){
            Log.d(TAG, "Couldn't change the directory");
        }
        return false;
    }

    public boolean ftpCreateDirectory(String directory) {
        boolean result = false;
        try {
            result =  mFtpClient.makeDirectory(directory);
        } catch (Exception e){
            Log.d(TAG, "Couldn't make the directory");
        }
        return result;
    }

    public String[] ftpGetFileList(String directory) {
        String[] fileList = null;
        int i = 0;
        try {
            FTPFile[] ftpFiles = mFtpClient.listFiles(directory);
            fileList = new String[ftpFiles.length];
            for(FTPFile file : ftpFiles) {
                String fileName = file.getName();

                if (file.isFile()) {
                    fileList[i] = "(File) " + fileName;
                } else {
                    fileList[i] = "(Directory) " + fileName;
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public String ftpGetDirectory(){
        String directory = null;
        try{
            directory = mFtpClient.printWorkingDirectory();
        } catch (Exception e){
            Log.d(TAG, "Couldn't get current directory");
        }
        return directory;
    }

    public boolean ftpDeleteDirectory(String directory) {
        boolean result = false;
        try {
            result = mFtpClient.removeDirectory(directory);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't remove directory");
        }
        return result;
    }

    public boolean ftpDeleteFile(String file) {
        boolean result = false;
        try{
            result = mFtpClient.deleteFile(file);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't remove the file");
        }
        return result;
    }

    public boolean ftpRenameFile(String from, String to) {
        boolean result = false;
        try {
            result = mFtpClient.rename(from, to);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't rename file");
        }
        return result;
    }

    public boolean ftpUploadFile(String srcFilePath, String desFileName, String desDirectory) {
        boolean result = false;
        try {
            FileInputStream fis = new FileInputStream(srcFilePath);
            if (ftpChangeDirctory(desDirectory)) {
                result = mFtpClient.storeFile(desFileName, fis);
            }
            fis.close();
        } catch (Exception e) {
            Log.d(TAG, "Couldn't upload the file");
        }
        return result;
    }
}
