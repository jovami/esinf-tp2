package jovami.trees;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author DEI-ESINF
 */
public class TreeWordsTest {

    public TreeWordsTest() {
    }

    /**
     * Test of createTree method, of class TreeWords.
     */
    @Test
    public void testCreateTree() throws Exception {
        System.out.println("createTree");
        TreeWords instance = new TreeWords();
        instance.createTree();
        System.out.println(instance);
    }
    /**
     * Test of getWordOccurrences method, of class TreeWords.
     */
    @Test
    public void testGetWordsOcorrences() throws Exception {
        System.out.println("getwordsoccurrences");
        int[] occurExpected = {1,2,3};
        String[][] wordsExpected = {
            {"casaco","correu","do","estava","fecho","frio","pois"},    //1
            {"Luis","a","disse","o","ola"},                             //2
            {"Maria"}                                                   //3
        };

        TreeWords instance = new TreeWords();
        instance.createTree();
        Map<Integer,List<String>> occur = instance.getWordsOccurrences();

        int idx=0;
        for(Map.Entry<Integer,List<String>> e: occur.entrySet()){
            assertEquals(occurExpected[idx], e.getKey().intValue());
            assertEquals( Arrays.asList(wordsExpected[idx++]), e.getValue());
        }
    }
}
