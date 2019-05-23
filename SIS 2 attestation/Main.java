package P;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.FileInputStream;

public class Main {
    static AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; //mono
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public static void main(String[] args) throws IOException {
        AudioFormat format = getFormat();

        DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
        DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
        FileWriter fw = new FileWriter("notes.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("A");

        bw.close();
        fw.close();

        try {
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetLine.open(format);
            targetLine.start();

            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
            sourceLine.open(format);
            sourceLine.start();

            int numBytesRead;

            byte[] targetData = new byte[targetLine.getBufferSize() / 5];



            while (true) {
                System.out.println(format.getSampleRate() + " ");
                numBytesRead = targetLine.read(targetData, 0, targetData.length);

                if (numBytesRead == -1)	{

                    break;
                }

                sourceLine.write(targetData, 0, numBytesRead);
                System.out.println(numBytesRead);

            }

        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

}
