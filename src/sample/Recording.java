package sample;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Recording {
    //текущий звуковой поток
    private File file;
    // номер файла
    private int suffix = 0;

    // аудио формат
    private final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private final int MONO = 1;
    // определение формата аудио данных
    private AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 44100, 16, MONO, 2, 44100, false);
    // микрофонный вход
    private TargetDataLine mike;

    // создать новый файл
    File getNewFile() {
        try {
            do {
                String filename = "record_";
                String soundFileName = filename + (suffix++) + "." + fileType.getExtension();
                file = new File(soundFileName);
            } while (!file.createNewFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }
    // запуск записи
    public void startRecording() {
        new Thread() {
            public void run() {
                // линию соединения
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                // проверить, поддерживается ли линия
                if (!AudioSystem.isLineSupported(info)) {
                    JOptionPane.showMessageDialog(null, "Line not supported" +
                                    info, "Line not supported",
                            JOptionPane.ERROR_MESSAGE);
                }
                try {
                    // получить подходящую линию
                    mike = (TargetDataLine) AudioSystem.getLine(info);
                    // открываем линию соединения с указанным
                    // форматом и размером буфера
                    mike.open(format, mike.getBufferSize());
                    // поток микрофона
                    AudioInputStream sound = new AudioInputStream(mike);
                    // запустить линию соединения
                    mike.start();
                    // записать содержимое потока в файл
                    AudioSystem.write(sound, fileType, file);
                } catch (LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(null, "Line not available" +
                                    ex, "Line not available",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "I/O Error " + ex,
                            "I/O Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }

    // остановка записи
    public void stopRecording() {
        mike.stop();
        mike.close();
    }

}
