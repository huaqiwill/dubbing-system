import com.moyin.tts.service.IMoyinAudioService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest(classes = com.moyin.MoyinApplication.class)
public class TestAudioService {

    @Resource
    private IMoyinAudioService audioService;

    @Test
    public void testSaveAudioDataFromFile() {
//        File file = new File("D:\\Data\\User\\A-个人业务\\商单列表\\2024\\2024.06\\2024.6.6_2000到1200\\交付物\\MoyinServer\\moyin-api\\src\\test\\resources\\tts_sample.mp3"); // 更新为你的文件路径
//        byte[] fileBytes = readAllBytes(file);
//        audioService.saveAudioData("audition", fileBytes);
    }

    @Test
    public void testReadAudioData() {
//        byte[] dataBytes = audioService.getAudioData("audition");
//        log.info(Arrays.toString(dataBytes));
    }

    public void play() {

    }


    public byte[] readAllBytes(File file) {
        byte[] fileBytes = new byte[(int) file.length()];
        int bytesRead;

        try (FileInputStream fis = new FileInputStream(file)) {
            int offset = 0;
            byte[] buffer = new byte[4096]; // 使用缓冲区读取文件
            while ((bytesRead = fis.read(buffer)) != -1) {
                System.arraycopy(buffer, 0, fileBytes, offset, bytesRead);
                offset += bytesRead;
            }
            // 如果文件大小不是4096的倍数，需要调整数组大小
            if (file.length() != fileBytes.length) {
                byte[] newBytes = new byte[(int) file.length()];
                System.arraycopy(fileBytes, 0, newBytes, 0, (int) file.length());
                fileBytes = newBytes;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
        return fileBytes;
    }
}