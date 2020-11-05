package Testat3;


import java.io.*;
//Read Write for a specified File, uses Monitors to synchronize reads and writes
//takes read/write command from workerThreads
public class rw {
    File file;
    rwMonitor monitor;
    
    public rw(File file){
        this.file = file;
        this.monitor = new rwMonitor(file.getName());
    }
    
    public String read(int line) {
        try {
            monitor.startRead();
            BufferedReader fileIn = new BufferedReader(new FileReader(file));
            for (int i = 1; i < line; i++) {
                fileIn.readLine();
            }
            String readLine = fileIn.readLine();
            fileIn.close();
            monitor.endRead();
            return readLine;
            
        } catch (FileNotFoundException e) {
            monitor.endRead();
            e.printStackTrace();
            return "This File doesn`t exist";
        } catch (IOException e) {
            monitor.endRead();
            e.printStackTrace();
            return "ERROR";
        }
    }
    
    public String write(int line, String data) {
        try {
            monitor.startWrite();
            new File(file + "Temp.txt").delete();
            PrintWriter writer = new PrintWriter(new FileWriter(file + "Temp.txt"));
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer;
            
            for (int i = 1; i < line; i++) {
                buffer = reader.readLine();
                if (buffer != null) {
                    writer.println(buffer);
                } else {
                    writer.close();
                    reader.close();
                    return "Line " + line + " does not exist";
                }
            }
            reader.readLine();
            writer.println(data);
            buffer = reader.readLine();
            while (buffer != null) {
                writer.println(buffer);
                buffer = reader.readLine();
            }
            writer.flush();
            writer.close();
            reader.close();
            
            File realName = file;
            realName.delete();
            new File(file + "Temp.txt").renameTo(realName);
            monitor.endWrite();
            return "Wrote " + data + " in File";
            
        } catch (FileNotFoundException e) {
            monitor.endWrite();
            e.printStackTrace();
            return "This File doesn't exist";
        } catch (IOException e) {
            monitor.endWrite();
            e.printStackTrace();
            return "ERROR";
        }
    }
}
