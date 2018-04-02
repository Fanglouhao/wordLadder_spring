package word_ladder;

import com.example.wordladder.Word_ladder;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** 
* word_ladder Tester. 
* 
* @author <Fang Louhao>
* @since <pre>���� 10, 2018</pre> 
* @version 1.0 
*/ 
public class Word_ladderTest {

@Before
public void before() throws Exception {
    System.out.println("before test");
} 

@After
public void after() throws Exception {
    System.out.println("after test");
}


/**
*
* Method: wordLadder(String start_word, String end_word, HashSet<String> dictionary, List<List<String>>output)
*
*/
@Test
public void testWordLadder() throws Exception {
    HashSet<String> dict = Word_ladder.readFileByLines("/Users/apple/IdeaProjects/wordladder/src/main/resources/EnglishWords.txt");
    List<List<String>> actual = new ArrayList<List<String>>();
    List<List<String>> expect = new ArrayList<List<String>>();
    List<String> temp1 = new ArrayList<String>();
    temp1.add("dog");
    temp1.add("doe");
    temp1.add("dee");
    temp1.add("see");
    expect.add(temp1);
    int ret = Word_ladder.wordLadder("dog","see", dict, actual);
    assertEquals(expect, actual);
}

} 
