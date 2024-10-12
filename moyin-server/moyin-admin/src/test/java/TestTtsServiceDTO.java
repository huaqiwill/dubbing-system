import com.moyin.tts.service.IMoyinAudioService;
import com.moyin.tts.service.IMoyinTtsService;
import com.moyin.tts.service.impl.MoyinTtsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = com.moyin.MoyinApplication.class)
public class TestTtsServiceDTO {

    @Resource
    private IMoyinTtsService ttsService;

    @Resource
    private IMoyinAudioService audioService;

    @Test
    public void testTts() {
//        MoyinTtsResultVo ttsResult = ttsService.testTts();
//
//        String key = Sha256Util.getSha256Hash(ttsResult.getAudioBytes());
//        audioService.saveAudioData(key, ttsResult.getAudioBytes());
//
//        System.out.println(Arrays.toString(ttsResult.getAudioBytes()));
    }

    @Test
    public void testGetWords() {
        IMoyinTtsService moyinTtsService = new MoyinTtsServiceImpl();
        moyinTtsService.getWords("你");
    }

    @Test
    public void testTnList() {
        IMoyinTtsService moyinTtsService = new MoyinTtsServiceImpl();
        moyinTtsService.tnList("1");
    }
}
